package cruiserproductions.battleship2;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MatchOverview extends Activity {
    String uname;
    int fired,hit,won;
    ImageView im;
    Data ob=new Data(this,"database2",null,1);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.match_overview);
        Intent i=getIntent();
        uname=i.getStringExtra("uname");
        won=i.getIntExtra("result",0);
        fired=i.getIntExtra("fired",0);
        im=(ImageView)findViewById(R.id.imageView7) ;
        hit=i.getIntExtra("hit",0);
        try{
            Thread.sleep(500);
        go();}catch (Exception e){}
        dbupdate();
    }
    public void dbupdate()
    {
        Player p=ob.getstats(uname);
        p.setPlayed(p.getPlayed()+1);
        p.setWon(p.getWon()+won);
        p.setFired(p.getFired()+fired);
        p.setHit(p.getHit()+hit);
        ob.setstats(p);
        // UPDATE HERE
    }
    public  void go(){
        if(won==1){
            im.setImageResource(R.drawable.trans);
            ((TransitionDrawable) im.getDrawable()).startTransition(1500);
        }
        if(won==0){
            im.setImageResource(R.drawable.trans2);
            ((TransitionDrawable) im.getDrawable()).startTransition(1500);
        }

    }
    public void backToOptions(View v)
    {
        Intent i=new Intent(this, LandingMenu.class);
        i.putExtra("uname",uname);
        startActivity(i);

        overridePendingTransition(R.anim.loaderanim,0);
        finish();
    }
}
