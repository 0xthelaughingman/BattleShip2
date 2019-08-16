package cruiserproductions.battleship2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Derpp on 4/4/2017.
 */

public class Data extends SQLiteOpenHelper {
    Context ct;
    public Data(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        ct=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String s="create table players(id integer primary key,name varchar(20),password varchar(20),played integer,won integer,fired integer,hit integer)";
        db.execSQL(s);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public void Insert(String s,String pa){
        SQLiteDatabase s1=getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put("name",s);
        cv.put("password",""+pa);
        cv.put("played",0);
        cv.put("won",0);
        cv.put("fired",0);
        cv.put("hit",0);
        s1.insert("players",null,cv);
        Log.d("database","inserted value="+s);
        s1.close();
    }
    public ArrayList<String> show(){
        ArrayList<String> s=new ArrayList<>();
        SQLiteDatabase sr = getReadableDatabase();
        String s1 = "select * from players " ;

        Cursor cr =     sr.rawQuery(s1,null);

        while (cr.moveToNext())
        {
            int a  = cr.getInt(0);
            String  b = cr.getString(1);
            int c = cr.getInt(2);
            s.add(b);
            Log.d("database","inserted value="+s);
        }

        if(!cr.moveToFirst()){
            s=null;
        }
        sr.close();
        return s;
    }
    public Player getstats(String uname){

        SQLiteDatabase sr = getReadableDatabase();
        String values[]={ uname};

        Cursor cr =   sr.rawQuery("select * from players where name = ?", values);
        Player ob=new Player();
        while (cr.moveToNext())
        {

            int a  = cr.getInt(0);
            ob.setName( cr.getString(1));
            ob.setPassword( cr.getString(2));
            ob.setPlayed(cr.getInt(3));
            ob.setWon(cr.getInt(4));
            ob.setFired(cr.getInt(5));
            ob.setHit(cr.getInt(6));
            break;
            //Log.d("database","inserted value=");
        }


        sr.close();
        return ob;
    }
    public void setstats(Player p){
        ArrayList<String> s=new ArrayList<>();
        SQLiteDatabase wr = getWritableDatabase();
        String[] vals={p.getName()};
        ContentValues cv = new ContentValues();
        cv.put("played",p.getPlayed());
        cv.put("won",p.getWon());
        cv.put("fired",p.getFired());
        cv.put("hit",p.getHit());
        wr.update("players",cv,"name=?",vals);



        wr.close();

    }
}
