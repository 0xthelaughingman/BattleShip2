package cruiserproductions.battleship2;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Derpp on 4/5/2017.
 */

public class MyService extends Service implements MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener {
    private static final String ACTION_PLAY = "com.example.action.PLAY";
    MediaPlayer mMediaPlayer = null;
    Context ct;

    public int onStartCommand(Intent intent, int flags, int startId) {

        if(mMediaPlayer==null)
        {mMediaPlayer = MediaPlayer.create(this,R.raw.deus);
            mMediaPlayer.setLooping(true);
            mMediaPlayer.setOnPreparedListener(this);
            try{
                mMediaPlayer.prepare(); // prepare async to not block main thread
            }catch (Exception e){;}
        Log.d("MEDIA","PRE-STARTED");}
        return super.onStartCommand(intent, flags, startId);

    }

    /** Called when MediaPlayer is ready */
    public void onPrepared(MediaPlayer player) {
        player.start();
        Log.d("MEDIA","STARTED");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
       // Toast.makeText(getApplicationContext(),"NOPE",Toast.LENGTH_LONG).show();
        return false;
    }

    @Override
    public void onDestroy() {
        mMediaPlayer.stop();

        super.onDestroy();
    }
}
