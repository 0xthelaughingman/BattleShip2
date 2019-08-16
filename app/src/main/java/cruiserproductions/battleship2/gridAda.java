package cruiserproductions.battleship2;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by Derpp on 3/29/2017.
 */

public class gridAda extends BaseAdapter {
    ArrayList<ImageView> al;
    Context ct;
    public gridAda(Context ct,ArrayList<ImageView> st)
    {
        this.ct=ct;
        al=st;
    }
    @Override
    public int getCount() {
        return al.size();
    }

    @Override
    public Object getItem(int position) {
        return al.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public void update(int position, int draw)
    {

        ImageView v=new ImageView(ct);
        v.setImageResource(R.drawable.test);
        al.add(v);
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //ImageView first=(ImageView)al.get(position);
        ImageView imageView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = al.get(position);
            imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setPadding(1, 1, 1, 1);
        } else {
            imageView = (ImageView) convertView;
        }



        return imageView;



    }
}
