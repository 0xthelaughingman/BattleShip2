package cruiserproductions.battleship2;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class UserBefore extends Activity{

    Button b,b1,b2,go; EditText et1,et2;
    String user ;
    EditText uname;
    Data da=new Data(this,"database2",null,1);

    public void call(){
        AlertDialog.Builder a=new AlertDialog.Builder(this);
        View v1=getLayoutInflater().inflate(R.layout.dig,null);
        a.setView(v1);
        final Dialog d=a.create();
        b1=(Button) v1.findViewById(R.id.d1);
        b2=(Button)v1.findViewById(R.id.d2);
        et1=(EditText)v1.findViewById(R.id.et1);
        et2=(EditText)v1.findViewById(R.id.et2);
        b1.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                d.dismiss();

            }

        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s =et1.getText().toString();
                String i=et2.getText().toString();
                da.Insert(s,i);
                user=et1.getText().toString();
                d.dismiss();
                uname.setText(user);
                go.setEnabled(true);
                Log.d("user",user);
            }
        });
        d.show();

    }
    public void hopToOptions(View v)
    {
        SharedPreferences sp=getSharedPreferences("derp",MODE_PRIVATE);
        SharedPreferences.Editor spe=sp.edit();
        spe.putString("uname",uname.getText().toString());
        spe.commit();
        Intent i=new Intent(this, LandingMenu.class);
        i.putExtra("uname",uname.getText().toString());
        startActivity(i);
        overridePendingTransition(R.anim.loaderanim,0);
        finish();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_before);
        b=(Button) findViewById(R.id.but);
        uname=(EditText) findViewById(R.id.etuname);
        go=(Button)findViewById(R.id.button3);
        SharedPreferences sp=getSharedPreferences("derp",MODE_PRIVATE);
        String s=sp.getString("uname","NA");
        if(!s.equals("NA"))
        {
            uname.setText(s);
            go.setEnabled(true);
        }

    }
    public void go(View v){
        ArrayList<String> a1=da.show();
        if(a1==null){
            call();
        }else{
            a1.add("Select another player");
            AlertDialog.Builder cusAlert=new AlertDialog.Builder(this);
            View v2=getLayoutInflater().inflate(R.layout.dig2,null);
            cusAlert.setView(v2);
            final Dialog alertDialog=cusAlert.create();
            ListView listV=(ListView)v2.findViewById(R.id.list);
            listV.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,a1));
            alertDialog.show();
            listV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(getApplicationContext(),(String)parent.getItemAtPosition(position),Toast.LENGTH_SHORT).show();
                    String s=(String)parent.getItemAtPosition(position);
                    if(s.equals("Select another player")){
                        call();
                    }else{
                        user=(String)parent.getItemAtPosition(position);
                        uname.setText(user);
                        go.setEnabled(true);
                        Log.d("user",user);
                    }
                    alertDialog.dismiss();
                }});
        }
    }
}

