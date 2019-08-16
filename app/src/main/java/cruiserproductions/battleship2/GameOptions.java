package cruiserproductions.battleship2;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;


import android.view.View;
import android.widget.TextView;


public class GameOptions extends Activity {
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
    public void movetoplay(View v)
    {
        Intent i=new Intent(this,gridTest.class);
        i.putExtra("uname",uname);
        Intent mu=new Intent(this,MyService.class);
        stopService(mu);
        startActivity(i);
        overridePendingTransition(R.anim.loaderanim,0);
        finish();

    }
    public void movetostats(View v)
    {
        Intent i=new Intent(this,UserStats.class);
        i.putExtra("uname",uname);
        startActivity(i);
        overridePendingTransition(R.anim.loaderanim,0);
        finish();
    }
    public void movetohow(View v)
    {
        Intent i=new Intent(this,HowToPlay.class);
        i.putExtra("uname",uname);
        startActivity(i);
        overridePendingTransition(R.anim.loaderanim,0);
        finish();
    }

}
