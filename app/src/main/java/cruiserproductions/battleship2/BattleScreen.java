package cruiserproductions.battleship2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


/*
Main battle screen. Initializes both the player/boat grids behind the scenes. Runs a thread task.
 */
public class BattleScreen extends Activity {
    ArrayList<Boat> playerBoatList;
    static int shotsfire=0; static int shotshit=0;
    int turnCount;
    AlphaAnimation blinker;
    ArrayList<Boat> botBoatList;
    ArrayList<ImageView> playerSetup =new ArrayList<>();
    ArrayList<ImageView> botSetup =new ArrayList<>();
    int[][] pMatrix =new int[8][8];
    int[][] botMatrix =new int[8][8];
    int[][] pcurr=new int[8][8];
    static ArrayList<Integer> boatTypes =new ArrayList<>();
    BotAI BOT;
    ImageView turn;
    GridView gvp,gvb;
    Handler h=new Handler();
    Runnable task;
    String user_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        playerBoatList =(ArrayList)getIntent().getSerializableExtra("pboat");
        setContentView(R.layout.battle_screen);
        gvp=(GridView)findViewById(R.id.gvplay);
         gvb=(GridView)findViewById(R.id.gvbot);
        user_name=getIntent().getStringExtra("uname");
        turn=(ImageView)findViewById(R.id.TurnDisp);
        blinker=new AlphaAnimation(0,1);
        blinker.setInterpolator(new LinearInterpolator());
        blinker.setDuration(300);
        blinker.setRepeatMode(Animation.REVERSE);
        blinker.setRepeatCount(Animation.INFINITE);
        turn.setAnimation(blinker);
        turn.startAnimation(blinker);
        for(int i=0;i<64;i++)
        {
            ImageView z=new ImageView(this);

            z.setImageResource(R.drawable.test);
            playerSetup.add(z);


        }
        for(int i=0;i<64;i++)
        {
            ImageView z=new ImageView(this);
            z.setImageResource(R.drawable.test);

            botSetup.add(z);
        }
        TextView d=(TextView)findViewById(R.id.textView7);
        d.setText(user_name);
        boatTypes.add(3);
        boatTypes.add(2);
        boatTypes.add(2);
        boatTypes.add(2);
        boatTypes.add(1);
        boatTypes.add(1);
        //set player grid
        for(int i = 0; i< playerBoatList.size(); i++)
        {
            Boat ob=(Boat) playerBoatList.get(i);
            for(int j=0; j<ob.moves.size();j++)
            {
                Move move=ob.moves.get(j);
                pMatrix[move.i][move.j]=ob.HP;
                ImageView v=(ImageView) playerSetup.get(move.i*8+move.j);
                v.setImageResource(move.img);
            }
        }
        //get BOT boat info and set grid
        BOT=new BotAI(boatTypes, pMatrix);
        botBoatList =BOT.genBoats();
        for(int i = 0; i< botBoatList.size(); i++)
        {
            Boat ob=(Boat) botBoatList.get(i);
            for(int j=0; j<ob.moves.size();j++)
            {
                Move move=ob.moves.get(j);
                botMatrix[move.i][move.j]=ob.HP;

            }
        }
        task=new Runnable() {
            @Override
            public void run() {
                getBotAction();
                turn.setImageResource(R.drawable.playerturn);
                turn.setAnimation(blinker);
                turn.startAnimation(blinker);
                gvb.setAlpha(1);
                turnCount =0;
            }
        };
        PlayerBattleGrid playerBG=new PlayerBattleGrid(this, playerSetup);
        gvp.setAdapter(playerBG);
        PlayerBattleGrid botBG=new PlayerBattleGrid(this, botSetup);

        gvb.setAdapter(botBG);
        gvb.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(turnCount ==0)
                {int x=position/8; int y=position%8;
                PlayerBattleGrid a=(PlayerBattleGrid) gvb.getAdapter();
                ImageView hitbox = (ImageView) a.getView(position, null, null);
                if(botMatrix[x][y]==0 && pcurr[x][y]==0)
                {

                    hitbox.setImageResource(R.drawable.miss);
                    pcurr[x][y]++;shotsfire++;

                        turn.setImageResource(R.drawable.botturn);
                        turn.setAnimation(blinker);gvb.setAlpha((float)0.45);
                        turn.startAnimation(blinker);
                        turnCount =1;
                        h.postDelayed(task,800);




                }
                else if(botMatrix[x][y]!=0 && pcurr[x][y]==0)
                {   shotsfire++;shotshit++;
                    hitbox.setImageResource(R.drawable.hit);
                    pcurr[x][y]++;
                    for(int i = 0; i< botBoatList.size(); i++)
                    {
                        Boat boat= botBoatList.get(i);
                        for(int j=0;j<boat.moves.size();j++)
                        {   Move z=boat.moves.get(j);
                            if(z.i==x && z.j==y)
                            {
                                boat.HP--;
                                if(boat.HP ==0)
                                {
                                    botBoatList.remove(i);
                                    updateBoatsBot(boat.moves.size());
                                    if(botBoatList.size()==0)
                                    {
                                        Toast.makeText(getApplicationContext(),"PLAYER WON :O",Toast.LENGTH_LONG).show();
                                        Intent zz=new Intent(BattleScreen.this,MatchOverview.class);
                                        zz.putExtra("uname",user_name);
                                        zz.putExtra("result",1);
                                        zz.putExtra("fired",shotsfire);
                                        zz.putExtra("hit",shotshit);
                                        startActivity(zz);
                                        overridePendingTransition(R.anim.loaderanim,0);
                                        finish();
                                    }
                                }
                            }
                        }
                    }

                        //Call the thread with animations for the Bot. Then wait till player clicks and this maintains turnwise logic....

                        turn.setImageResource(R.drawable.botturn);
                        turn.setAnimation(blinker);
                        turn.startAnimation(blinker);
                        gvb.setAlpha((float)0.45);
                        turnCount =1;
                        h.postDelayed(task,800);

                }


            }
        }



     });
    }



    //get the moves BOT has decided

    void getBotAction()
    {
        Move m= BotAI.tryMove();
        int x=m.i;int y=m.j;
        PlayerBattleGrid a=(PlayerBattleGrid) gvp.getAdapter();
        ImageView d = (ImageView) a.getView(x*8+y, null, null);
        if(pMatrix[x][y]==0)
        {
            d.setImageResource(R.drawable.miss);
        }
        else
        {
            d.setImageResource(R.drawable.hit);
            for(int i = 0; i< playerBoatList.size(); i++)
            {
                Boat b= playerBoatList.get(i);
                for(int j=0;j<b.moves.size();j++)
                {   Move z=b.moves.get(j);
                    if(z.i==x && z.j==y)
                    {
                        b.HP--;
                        if(b.HP ==0)
                        {
                           playerBoatList.remove(i);
                            updateBoatsPlayer(b.moves.size());
                            if(playerBoatList.size()==0)
                            {
                                Toast.makeText(getApplicationContext(),"BOT WON!!:O",Toast.LENGTH_SHORT).show();

                                Intent zz=new Intent(BattleScreen.this,MatchOverview.class);
                                zz.putExtra("uname",user_name);
                                zz.putExtra("result",0);
                                zz.putExtra("fired",shotsfire);
                                zz.putExtra("hit",shotshit);
                                startActivity(zz);
                                overridePendingTransition(R.anim.loaderanim,0);
                                finish();
                            }
                        }
                    }
                }
            }
        }
    }
    void updateBoatsBot(int n)
    {
        TextView t1;
        if(n==1)
        {
            t1=(TextView)findViewById(R.id.textView22);

        }
        else if(n==2)
        {
            t1=(TextView)findViewById(R.id.textView23);
        }
        else
        {
            t1=(TextView)findViewById(R.id.textView24);
        }
        int num=Integer.parseInt(t1.getText().toString());
        num--;
        t1.setText(""+num);

    }
    void updateBoatsPlayer(int n)
    {
        TextView t1;
        if(n==1)
        {
            t1=(TextView)findViewById(R.id.textView19);

        }
        else if(n==2)
        {
            t1=(TextView)findViewById(R.id.textView20);
        }
        else
        {
            t1=(TextView)findViewById(R.id.textView21);
        }
        int num=Integer.parseInt(t1.getText().toString());
        num--;
        t1.setText(""+num);
    }

}
