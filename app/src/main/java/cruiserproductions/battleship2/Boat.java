package cruiserproductions.battleship2;

import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Derpp on 4/1/2017.
 */

public class Boat implements Serializable{
    int hp=0;
    ArrayList<Move> moves=new ArrayList<>();
    Boat(int hp)
    {
        this.hp=hp;



    }
    void addMove(Move move)
    {
        moves.add(move);
    }



}
