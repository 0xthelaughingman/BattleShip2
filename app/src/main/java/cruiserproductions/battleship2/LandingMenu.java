package cruiserproductions.battleship2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;


import android.view.View;
import android.widget.TextView;


public class LandingMenu extends Activity {
    String uname;
    TextView t;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_options);
        uname=getIntent().getStringExtra("uname");
        t=(TextView)findViewById(R.id.textView11);
        t.setText(t.getText().toString()+uname);





    }
    public void moveToPlay(View v)
    {
        Intent i=new Intent(this, PlayerPlacement.class);
        i.putExtra("uname",uname);
        Intent mu=new Intent(this,MyService.class);
        stopService(mu);
        startActivity(i);
        overridePendingTransition(R.anim.loaderanim,0);
        finish();

    }
    public void moveToStats(View v)
    {
        Intent i=new Intent(this,UserStats.class);
        i.putExtra("uname",uname);
        startActivity(i);
        overridePendingTransition(R.anim.loaderanim,0);
        finish();
    }
    public void moveToHow(View v)
    {
        Intent i=new Intent(this,HowToPlay.class);
        i.putExtra("uname",uname);
        startActivity(i);
        overridePendingTransition(R.anim.loaderanim,0);
        finish();
    }

}
