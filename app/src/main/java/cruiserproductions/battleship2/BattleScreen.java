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

import org.w3c.dom.Text;

import java.lang.annotation.Repeatable;
import java.util.ArrayList;

public class BattleScreen extends Activity {
    ArrayList<Boat> pboatlist;
    static int shotsfire=0; static int shotshit=0;
    int turncount;
    AlphaAnimation blinker;
    ArrayList<Boat> botboatlist;
    ArrayList<ImageView> playersetup=new ArrayList<>();
    ArrayList<ImageView> botsetup=new ArrayList<>();
    int[][] pmat=new int[8][8];
    int[][] botmat=new int[8][8];
    int[][] pcurr=new int[8][8];
    static ArrayList<Integer> boathp=new ArrayList<>();
    botAI BOT;
    ImageView turn;
    GridView gvp,gvb;
    Handler h=new Handler();
    Runnable task;
    String uname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pboatlist=(ArrayList)getIntent().getSerializableExtra("pboat");
        setContentView(R.layout.battle_screen);
        gvp=(GridView)findViewById(R.id.gvplay);
         gvb=(GridView)findViewById(R.id.gvbot);
        uname=getIntent().getStringExtra("uname");
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
            playersetup.add(z);


        }
        for(int i=0;i<64;i++)
        {
            ImageView z=new ImageView(this);
            z.setImageResource(R.drawable.test);

            botsetup.add(z);
        }
        TextView d=(TextView)findViewById(R.id.textView7);
        d.setText(uname);
        boathp.add(3);
        boathp.add(2);
        boathp.add(2);
        boathp.add(2);
        boathp.add(1);
        boathp.add(1);

        for(int i=0;i<pboatlist.size();i++)
        {
            Boat ob=(Boat)pboatlist.get(i);
            for(int j=0; j<ob.moves.size();j++)
            {
                Move move=ob.moves.get(j);
                pmat[move.i][move.j]=ob.hp;
                ImageView v=(ImageView)playersetup.get(move.i*8+move.j);
                v.setImageResource(move.img);
            }
        }
        BOT=new botAI(boathp,pmat);
        botboatlist=BOT.genBoats();
        for(int i=0;i<botboatlist.size();i++)
        {
            Boat ob=(Boat)botboatlist.get(i);
            for(int j=0; j<ob.moves.size();j++)
            {
                Move move=ob.moves.get(j);
                botmat[move.i][move.j]=ob.hp;

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
                turncount=0;
            }
        };
        PlayerBattleGrid pbg=new PlayerBattleGrid(this,playersetup);
        gvp.setAdapter(pbg);
        PlayerBattleGrid pbb=new PlayerBattleGrid(this,botsetup);

        gvb.setAdapter(pbb);
        gvb.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(turncount==0)
                {int x=position/8; int y=position%8;
                PlayerBattleGrid a=(PlayerBattleGrid) gvb.getAdapter();
                ImageView d = (ImageView) a.getView(position, null, null);
                if(botmat[x][y]==0 && pcurr[x][y]==0)
                {

                    d.setImageResource(R.drawable.miss);
                    pcurr[x][y]++;shotsfire++;

                        turn.setImageResource(R.drawable.botturn);
                        turn.setAnimation(blinker);gvb.setAlpha((float)0.45);
                        turn.startAnimation(blinker);
                        turncount=1;
                        h.postDelayed(task,800);




                }
                else if(botmat[x][y]!=0 && pcurr[x][y]==0)
                {   shotsfire++;shotshit++;
                    d.setImageResource(R.drawable.hit);
                    pcurr[x][y]++;
                    for(int i=0;i<botboatlist.size();i++)
                    {
                        Boat b=botboatlist.get(i);
                        for(int j=0;j<b.moves.size();j++)
                        {   Move z=b.moves.get(j);
                            if(z.i==x && z.j==y)
                            {
                                b.hp--;
                                if(b.hp==0)
                                {
                                    botboatlist.remove(i);
                                    updateboatbot(b.moves.size());
                                    if(botboatlist.size()==0)
                                    {
                                        Toast.makeText(getApplicationContext(),"PLAYER WON :O",Toast.LENGTH_LONG).show();
                                        Intent zz=new Intent(BattleScreen.this,MatchOverview.class);
                                        zz.putExtra("uname",uname);
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



                        turn.setImageResource(R.drawable.botturn);
                        turn.setAnimation(blinker);
                        turn.startAnimation(blinker);
                        gvb.setAlpha((float)0.45);
                        turncount=1;
                        h.postDelayed(task,800);

                }


            }
        }



     });
    }
    void getBotAction()
    {
        Move m=botAI.trymove();
        int x=m.i;int y=m.j;
        PlayerBattleGrid a=(PlayerBattleGrid) gvp.getAdapter();
        ImageView d = (ImageView) a.getView(x*8+y, null, null);
        if(pmat[x][y]==0)
        {
            d.setImageResource(R.drawable.miss);
        }
        else
        {
            d.setImageResource(R.drawable.hit);
            for(int i=0;i<pboatlist.size();i++)
            {
                Boat b=pboatlist.get(i);
                for(int j=0;j<b.moves.size();j++)
                {   Move z=b.moves.get(j);
                    if(z.i==x && z.j==y)
                    {
                        b.hp--;
                        if(b.hp==0)
                        {
                           pboatlist.remove(i);updateboatplayer(b.moves.size());
                            if(pboatlist.size()==0)
                            {
                                Toast.makeText(getApplicationContext(),"BOT WON!!:O",Toast.LENGTH_SHORT).show();

                                Intent zz=new Intent(BattleScreen.this,MatchOverview.class);
                                zz.putExtra("uname",uname);
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
    void updateboatbot(int n)
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
    void updateboatplayer(int n)
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
