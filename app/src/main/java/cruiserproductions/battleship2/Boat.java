package cruiserproductions.battleship2;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Derpp on 4/1/2017.
 */

public class Boat implements Serializable{
    int HP =0;
    ArrayList<Move> moves=new ArrayList<>();
    Boat(int HP)
    {
        this.HP = HP;



    }
    void addMove(Move move)
    {
        moves.add(move);
    }



}
