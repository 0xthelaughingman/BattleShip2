package cruiserproductions.battleship2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class UserStats extends Activity {
    String uname;
    TextView t1,t2,t3,t4,t5,t6,t7;
    ImageView i;
    Data ob=new Data(this,"database2",null,1);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_stats);
        uname=getIntent().getStringExtra("uname");
        t1=(TextView)findViewById(R.id.textView12);
        t2=(TextView)findViewById(R.id.textView13);
        t3=(TextView)findViewById(R.id.textView17);
        t4=(TextView)findViewById(R.id.textView18);
        t5=(TextView)findViewById(R.id.textView25);
        t6=(TextView)findViewById(R.id.textView26);
        t7=(TextView)findViewById(R.id.textView28);
        Player p=ob.getstats(uname);
        t1.setText(t1.getText().toString()+p.getName());
        t2.setText(t2.getText().toString()+p.getPlayed());
        t3.setText(t3.getText().toString()+p.getWon());
        t4.setText(t4.getText().toString()+p.getFired());
        t5.setText(t5.getText().toString()+p.getHit());
        float acc=0;
        float ratio=0;
        try{
            acc=p.getWon()/(float)p.getPlayed();
        }catch(ArithmeticException ae){
            acc=0;
        }
        try{
            ratio=p.getHit()/(float)p.getFired();
        }catch(ArithmeticException ae){
            ratio=0;
        }
        t6.setText(t6.getText().toString()+ratio*100+"%");
        t7.setText(t7.getText().toString()+acc*100+"%");
        i=(ImageView)findViewById(R.id.imageView10);
        i.setImageResource(R.drawable.statmedal);

    }
    public void gotoOptions(View v)
    {
        Intent i=new Intent(this,GameOptions.class);
        i.putExtra("uname",uname);
        startActivity(i);

        overridePendingTransition(R.anim.loaderanim,0);
        finish();
    }
}
