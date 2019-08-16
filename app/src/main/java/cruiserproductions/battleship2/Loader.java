package cruiserproductions.battleship2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
/*
Post boot. "Loading" screen.
*/
public class Loader extends Activity {
    ProgressBar pb;
    TextView t1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loader);
        pb = (ProgressBar) findViewById(R.id.progressBar);
        t1=(TextView)findViewById(R.id.textView);
        AlphaAnimation blinker=new AlphaAnimation(0,1);
        blinker.setDuration(400);
        blinker.setInterpolator(new LinearInterpolator());
        blinker.setRepeatCount(6);
        blinker.setRepeatMode(Animation.REVERSE);
        t1.startAnimation(blinker);

    }
    @Override
    public void onResume()
    {   super.onResume();
        Thread t=new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                while(pb.getProgress()<1000)
                {

                    try{
                        Thread.sleep(120);
                        pb.incrementProgressBy(70);
                    }catch (Exception e){;}
                }
                try{
                    Thread.sleep(120);
                    Intent i=new Intent(getApplicationContext(),UserBefore.class);


                    startActivity(i);
                    overridePendingTransition(R.anim.loaderanim,0);
                    finish();
                }catch (Exception e){;}
            }
        });
        t.start();
    }
}
