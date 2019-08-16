package cruiserproductions.battleship2;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;

public class gridTest extends Activity {
    GridView gv;
    static int boatori=0;
    static ArrayList<Integer> boathp=new ArrayList<>();
    static ArrayList<Boat> boatlist=new ArrayList<Boat>();
    static int[][] pmat=new int[8][8];
    RadioButton r1,r2;
    RadioGroup rg;
    TextView t1,t2;
    ImageView i1;
    static int hp=3;
    String uname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_test);
        t1=(TextView)findViewById(R.id.textView2) ;
        t2=(TextView)findViewById(R.id.textView3) ;
        rg=(RadioGroup)findViewById(R.id.rg);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId==R.id.radioButton)boatori=0;
                else boatori=1;
                updatestyle(hp,boatori);
            }
        });
        i1=(ImageView)findViewById(R.id.imageView2);
        boathp.add(3);
        boathp.add(2);
        boathp.add(2);
        boathp.add(2);
        boathp.add(1);
        boathp.add(1);
        ArrayList<ImageView> list=new ArrayList<>();
        for(int i=0;i<8;i++)
        {
            for(int j=0;j<8;j++)
            {
                ImageView v=new ImageView(this);
                v.setImageResource(R.drawable.test);


                list.add(v);
            }
        }
        uname=getIntent().getStringExtra("uname");

         gv=(GridView)findViewById(R.id.gv);
        gridAda ga=new gridAda(this,list);
        gv.setAdapter(ga);
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                gridAda a=(gridAda)gv.getAdapter();
                /*ImageView d=(ImageView)a.getView(position,view,parent);
                d.setImageResource(R.mipmap.ic_launcher);*/
                int x=position/8; int y=position%8;
                 hp=boathp.get(0);

                Move start=new Move(x,y);
                Boat b=genboat(hp,start,boatori);
                if(b!=null)
                {   for(int i=0;i<b.hp;i++)
                    {
                        Move ob=b.moves.get(i);

                        int xx=ob.i;
                        int yy=ob.j;
                        pmat[xx][yy]=b.hp;
                    ImageView d = (ImageView) a.getView(xx*8+yy, null, null);
                    d.setImageResource(ob.img);
                    }
                    boatlist.add(b);
                    boathp.remove(0);
                    if(boathp.size()==0)
                    {   Toast.makeText(getApplicationContext(),""+boatlist.size(),Toast.LENGTH_SHORT).show();
                        hoptoplay();

                    }
                    else
                    {
                        updatestyle(boathp.get(0),boatori);

                    }
                }
            if(hp==3)hp--;
            }
        });

        updatestyle(boathp.get(0),boatori);
    }
    void updatestyle(int hp,int ori)
    {
        String[] names=getResources().getStringArray(R.array.boat);
        if(hp==1)
        { t2.setText(names[0]);
            if(ori==1)i1.setImageResource(R.drawable.subv);
            else
                i1.setImageResource(R.drawable.sub);
        }
        else if(hp==2)
        { t2.setText(names[1]);
            if(ori==1)i1.setImageResource(R.drawable.batv);
            else
                i1.setImageResource(R.drawable.bat);
        }
        else
        { t2.setText(names[2]);
            if(ori==1)i1.setImageResource(R.drawable.bossv);
            else
                i1.setImageResource(R.drawable.boss);
        }
    }
    public void gobattle(View v)
    {
        Intent i=new Intent(this,BattleScreen.class);
        /*Bundle b=new Bundle();
        b.putSerializable("pboat",boatlist);*/
        i.putExtra("pboat",(Serializable)boatlist);
        i.putExtra("uname",uname);
        startActivity(i);
        overridePendingTransition(R.anim.loaderanim,0);
        finish();
    }
    public void hoptoplay()
    {
        AlertDialog.Builder db=new AlertDialog.Builder(this);
        View v=this.getLayoutInflater().inflate(R.layout.dialogprep,null);
        db.setView(v);db.setCancelable(false);
        AlertDialog d=db.create();

        d.show();
    }
    boolean checkhor(int x, int y){
        int t1=1,t2=1;
        if(y>0)
        {
            if(pmat[x][y-1]!=0)t1=0;

        }
        if(y<pmat.length-1)
        {
            if(pmat[x][y+1]!=0)t2=0;
        }

        if(t1==1 && t2==1)
            return true;
        else
            return false;
    }

    boolean checkver(int x, int y){
        int t1=1,t2=1;
        if(x>0)
        {
            if(pmat[x-1][y]!=0)t1=0;

        }
        if(x<pmat.length-1)
        {
            if(pmat[x+1][y]!=0)t2=0;
        }

        if(t1==1 && t2==1)
            return true;
        else
            return false;

    }

    Boat genboat(int hp,Move move, int ori)
    {
        int x=move.i; int y=move.j;
        Boat b=new Boat(hp);

        boolean t1,t2;
        t1=checkhor(x,y);
        t2=checkver(x,y);
        if(t1 && t2)
        {
            if (hp == 1) {
                if (ori == 1) move.img = R.drawable.subv;
                else
                    move.img=R.drawable.sub;
                b.addMove(move);
            }
            else if(hp==2)
            { b=genboat2(move,ori);}
            else
            { b=genboat3(move ,ori);}

        }
        else
            b=null;



        return b;
    }
    Boat genboat2(Move move,int ori){
        Boat b=new Boat(2);
        int x=move.i;
        int y=move.j;
        if(ori==0)
        {
            if(y<=pmat.length-2)
            {
                boolean t1,t2;
                t1=checkver(x,y+1);
                t2=checkhor(x,y+1);
                if(t1  && t2)
                {   move.setImg(R.drawable.batfront);
                    b.addMove(move);
                    b.addMove(new Move(x,y+1,R.drawable.batback));
                }
                else
                {
                    b=null;
                }
            }
            else
                b=null;

        }
        else
        {
            if(x<=pmat.length-2)
            {
                boolean t1,t2;
                t1=checkhor(x+1,y);
                t2=checkver(x+1,y);
                if(t1  && t2)
                {   move.setImg(R.drawable.batfrontpv);
                    b.addMove(move);
                    b.addMove(new Move(x+1,y,R.drawable.batbackpv));
                }
                else
                    b=null;
            }
            else b=null;
        }
        return  b;
    }
    Boat genboat3(Move move,int ori)
    {
        Boat b=new Boat(3);
        int x=move.i;
        int y=move.j;
        if(ori==0)
        {
            if(y<=pmat.length-2)
            {
                boolean t1,t2,t3,t4;
                t1=checkver(x,y+1);
                t2=checkhor(x,y+1);
                t3=checkhor(x,y+2);
                t4=checkver(x,y+2);
                if(t1  && t2 &&t3 &&t4)
                {   move.setImg(R.drawable.bossfront);
                    b.addMove(move);
                    b.addMove(new Move(x,y+1,R.drawable.bossmid));
                    b.addMove(new Move(x,y+2,R.drawable.bossback));
                }
                else
                {
                    b=null;
                }
            }
            else
                b=null;

        }
        else
        {
            if(x<=pmat.length-2)
            {
                boolean t1,t2,t3,t4;
                t1=checkver(x+1,y);
                t2=checkhor(x+1,y);
                t3=checkhor(x+2,y);
                t4=checkver(x+2,y);
                if(t1  && t2 &&t3 &&t4)
                {   move.setImg(R.drawable.bossbackv);
                    b.addMove(move);
                    b.addMove(new Move(x+1,y,R.drawable.bossmidv));
                    b.addMove(new Move(x+2,y,R.drawable.bossfrontv));
                }
                else
                    b=null;
            }
            else b=null;
        }
        return  b;

    }

}
