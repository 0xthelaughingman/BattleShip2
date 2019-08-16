package cruiserproductions.battleship2;

/**
 * Created by Derpp on 4/5/2017.
 */

public class Player {
    String name;
    String password;
    int played;
    int won;
    int fired;
    int hit;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPlayed() {
        return played;
    }

    public void setPlayed(int played) {
        this.played = played;
    }

    public int getWon() {
        return won;
    }

    public void setWon(int won) {
        this.won = won;
    }

    public int getFired() {
        return fired;
    }

    public void setFired(int fired) {
        this.fired = fired;
    }

    public int getHit() {
        return hit;
    }

    public void setHit(int hit) {
        this.hit = hit;
    }
}
