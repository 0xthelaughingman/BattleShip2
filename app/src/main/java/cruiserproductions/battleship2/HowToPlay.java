package cruiserproductions.battleship2;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class HowToPlay extends Activity{
    String uname;

    Button b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.how_to_play);
        uname=getIntent().getStringExtra("uname");
    }
    public void gotoOptions(View v)
    {
        Intent i=new Intent(this,GameOptions.class);
        i.putExtra("uname",uname);
        startActivity(i);

        overridePendingTransition(R.anim.loaderanim,0);
        finish();
    }
    public void showPlacingHint(View v)
    {
        AlertDialog.Builder a=new AlertDialog.Builder(this);
        View v1=getLayoutInflater().inflate(R.layout.boatplacerule,null);
        a.setView(v1);
        final Dialog d=a.create();
        b=(Button) v1.findViewById(R.id.button14);

        b.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                d.dismiss();

            }

        });

        d.show();
    }
    public void showBoatTypes(View v)
    {
        AlertDialog.Builder a=new AlertDialog.Builder(this);
        View v1=getLayoutInflater().inflate(R.layout.boattypes,null);
        a.setView(v1);
        final Dialog d=a.create();
        b=(Button) v1.findViewById(R.id.button15);

        b.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                d.dismiss();

            }

        });

        d.show();
    }
    public void showBattleHint(View v)
    {
        AlertDialog.Builder a=new AlertDialog.Builder(this);
        View v1=getLayoutInflater().inflate(R.layout.battlerules,null);
        a.setView(v1);
        final Dialog d=a.create();
        b=(Button) v1.findViewById(R.id.button16);

        b.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                d.dismiss();

            }

        });

        d.show();
    }
}
