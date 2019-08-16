package cruiserproductions.battleship2;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
/*
App Boot. "Splash" screen.
*/

public class PreLoader extends Activity {
    ImageView v1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AlphaAnimation blinker=new AlphaAnimation(0,1);
        blinker.setDuration(2000);
        blinker.setInterpolator(new LinearInterpolator());
        v1=(ImageView)findViewById(R.id.imageView);
        v1.startAnimation(blinker);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent i=new Intent(PreLoader.this,Loader.class);
                startActivity(i);
                overridePendingTransition(R.anim.loaderanim,0);
                finish();

            }
        }, 2001);
        Intent mu=new Intent(this,MyService.class);
        startService(mu);

    }

}
