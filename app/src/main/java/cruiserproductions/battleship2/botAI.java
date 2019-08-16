package cruiserproductions.battleship2;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Derpp on 4/2/2017.
 */

public class BotAI {
    static int field[][]=  new int[8][8];// copy player field
    static ArrayList<Integer> boatHPs;
    static int pMatrix[][]=new int[8][8];
    static int boat_count;
    static int boatHP =0;
    static char curboat='0';
    static char currfield[][]=new char[8][8];
    static ArrayList<Move> move_set;
    static ArrayList<Move> guess_set =new ArrayList<>() ;
    static Move startpos=null;
    static Move curpath=null;
    static Move backpath=null;
    //static int lock=0;
    //static int lockloss=0;
    static int hit=0;
    //static int lastmov;
    BotAI(ArrayList<Integer>al, int[][] f)
    {
        boatHPs =al;
        move_set =new ArrayList<Move>();
        for(int i=0;i<field.length;i++)
        {
            for(int j=0;j<field.length;j++)
            {
                Move ob=new Move(i,j);
                move_set.add(ob);
            }
        }
        for(int i=0;i<field.length;i++)
        {
          for(int j=0;j<field.length;j++)
          {
              field[i][j]=f[i][j];
          }
        }
    }
     ArrayList<Boat> genBoats()
    {
        ArrayList<Boat> aiBoatList=new ArrayList<>();
        while(boatHPs.size()!=0)
        {
            int pick= ThreadLocalRandom.current().nextInt(0, move_set.size());
            Move ob= move_set.get(pick);
            int ori=ThreadLocalRandom.current().nextInt(0,2);

            int hp=(int) boatHPs.get(0);
            if(ori==1 && ob.i>=8-hp)continue;
            else if(ori==0 && ob.j>=8-hp)continue;
            Boat b=genboat(hp,ob,ori);
            if(b!=null) {
                for (int i = 0; i < b.HP; i++) {
                    Move obb = b.moves.get(i);

                    int xx = obb.i;
                    int yy = obb.j;
                    pMatrix[xx][yy] = b.HP;

                }
                aiBoatList.add(b);
                boatHPs.remove(0);
            }
        }
        return aiBoatList;
    }
    static Move tryMove()
    {   Move decided;
        if(hit==1 )
        {
            if(backpath==null)
            {	int pick= ThreadLocalRandom.current().nextInt(0, guess_set.size());
                Move ob=(Move) guess_set.get(pick);
                decided=ob;
                if(field[ob.i][ob.j]!=0)
                {
                    currfield[ob.i][ob.j]='H';

                    boatHP = boatHP -1;
                    if(boatHP ==0)
                    {
                        boat_count--;

                        if(boat_count==0)
                        {System.out.println("\nVICTORY");}
                        hit=0;
                    }
                    else
                    {
                        curpath=new Move(ob.i,ob.j,ob.direction);
                        if(ob.direction.equals(">"))
                        {
                            backpath=new Move(ob.i,ob.j-2,"<");
                        }
                        else if(ob.direction.equals("<"))
                        {
                            backpath=new Move(ob.i,ob.j+2,">");
                        }
                        else if(ob.direction.equals("^"))
                        {
                            backpath=new Move(ob.i+2,ob.j,"v");
                        }
                        else
                        {
                            backpath=new Move(ob.i-2,ob.j,"^");
                        }
                    }


                }
                else
                {
                    currfield[ob.i][ob.j]='B';
                }

                guess_set.remove(pick);

            }
            else
            {	if(curpath!=null)
            {	curpath=makemove(curpath);decided=curpath;
                if(curpath.i>=field.length || curpath.j>=field.length || curpath.i<0 || curpath.j<0)
                {
                    curpath=backpath;
                    if(field[curpath.i][curpath.j]!=0)
                    {
                        currfield[curpath.i][curpath.j]='H';

                        boatHP = boatHP -1;
                        if(boatHP ==0)
                        {
                            boat_count--;


                            hit=0;
                            curpath=null;
                            backpath=null;

                        }

                    }
                }
                else if(field[curpath.i][curpath.j]!=0)
                {
                    currfield[curpath.i][curpath.j]='H';

                    boatHP = boatHP -1;
                    if(boatHP ==0)
                    {
                        boat_count--;


                        hit=0;curpath=null;
                        backpath=null;
                    }

                }
                else
                {	currfield[curpath.i][curpath.j]='B';
                    curpath=null;}
            }
            else
            {
                curpath=backpath;decided=curpath;
                if(field[curpath.i][curpath.j]!=0)
                {
                    currfield[curpath.i][curpath.j]='H';

                    boatHP = boatHP -1;
                    if(boatHP ==0)
                    {
                        boat_count--;


                        hit=0;curpath=null;
                        backpath=null;
                    }

                }
            }
            }
            move_set =new ArrayList<Move>();
            for(int i=0;i<field.length;i++)
            {
                for(int j=0;j<field.length;j++)
                {	/*if(currfield[i][j]=='\u0000' && currfield[i][j]=='\u0000')
					{	move ob=new move(i,j);
						move_set.add(ob);
					}
					*/
                    //uncomment above for dumb ai =which attacks border of known ship and comment the below
                    // below code refreshes possible move list based on surrounding of blank cells, if surrounding hit/boat/blank shot not in movelist!
                    if(currfield[i][j]=='\u0000' )
                    {
                        if(i>0 && i<field.length-1)
                        {
                            if(j>0 && j<field.length-1)
                            {
                                if((currfield[i][j-1]=='\u0000' || currfield[i][j-1]=='B') &&
                                        (currfield[i][j+1]=='\u0000'  || currfield[i][j+1]=='B' ) &&
                                        (currfield[i-1][j]=='\u0000'	|| currfield[i-1][j]=='B') &&
                                        (currfield[i+1][j]=='\u0000' || currfield[i+1][j]=='B'))
                                {	Move ob=new Move(i,j);
                                    move_set.add(ob);}
                            }
                            else if(j==0||j==field.length-1)
                            {
                                if(j==0)
                                {	if((currfield[i-1][j]=='\u0000'	|| currfield[i-1][j]=='B')&&
                                        (currfield[i+1][j]=='\u0000' ||  currfield[i+1][j]=='B') &&
                                        (currfield[i][j+1]=='\u0000'	||currfield[i][j+1]=='B'))
                                {	Move ob=new Move(i,j);
                                    move_set.add(ob);
                                }
                                }
                                else if(j==field.length-1)
                                {
                                    if((	currfield[i-1][j]=='\u0000' ||	currfield[i-1][j]=='B')	&&
                                            (currfield[i+1][j]=='\u0000' || currfield[i+1][j]=='B') &&
                                            (currfield[i][j-1]=='\u0000' || currfield[i][j-1]=='B' ))
                                    {	Move ob=new Move(i,j);
                                        move_set.add(ob);}
                                }
                            }
                        }
                        else if(i==0||i==field.length-1)
                        {
                            if(j>0 && j<field.length-1)
                            {
                                if((currfield[i][j-1]=='\u0000' || currfield[i][j-1]=='B')&&
                                        (currfield[i][j+1]=='\u0000' || currfield[i][j+1]=='B'))
                                {	Move ob=new Move(i,j);
                                    move_set.add(ob);}
                            }
                            else if(j==0||j==field.length-1)
                            {
                                if( i==0 && j==0)
                                {
                                    if((currfield[i][j+1]=='\u0000' || currfield[i][j+1]=='B')&&
                                            (currfield[i+1][j]=='\u0000' ||currfield[i+1][j]=='B' ))
                                    {	Move ob=new Move(i,j);
                                        move_set.add(ob);}
                                }
                                else if(i==field.length-1 && j==field.length-1)
                                {
                                    if((currfield[i][j-1]=='\u0000' || currfield[i][j-1]=='B' )&&
                                            (currfield[i-1][j]=='\u0000' || currfield[i-1][j]=='B' ))
                                    {	Move ob=new Move(i,j);
                                        move_set.add(ob);}
                                }
                                else
                                {	Move ob=new Move(i,j);
                                    move_set.add(ob);}

                            }
                        }


                    }

                }
            }
        }
        else
        {
            int pick= ThreadLocalRandom.current().nextInt(0, move_set.size());
            Move ob=(Move) move_set.get(pick);decided=ob;
            if(field[ob.i][ob.j]!=0)
            {
                currfield[ob.i][ob.j]='H';
                hit=1;
                boatHP =field[ob.i][ob.j]-1;
                if(boatHP ==0)
                {
                    boat_count--;


                    hit=0;curpath=null;
                    backpath=null;
                }
                else
                {startpos=new Move(ob.i,ob.j);

                    makeguesses();}
            }
            else
            {
                currfield[ob.i][ob.j]='B';
            }

            move_set.remove(pick);

        }
        return decided;
    }
    static void printfield()
    {
        for(int i=0;i<currfield.length;i++)
        {
            for(int j=0;j<currfield[1].length;j++)
            {
                System.out.print(" "+currfield[i][j]);
            }
            System.out.println();

        }
    }
    static void makeguesses()
    {
        guess_set =new ArrayList<Move>();
        if(startpos.i+1<field.length && currfield[startpos.i+1][startpos.j]=='\u0000')
            guess_set.add(new Move(startpos.i+1,startpos.j,"v"));
        if(startpos.i-1>=0 && currfield[startpos.i-1][startpos.j]=='\u0000')
            guess_set.add(new Move(startpos.i-1,startpos.j,"^"));
        if(startpos.j+1<field.length && currfield[startpos.i][startpos.j+1]=='\u0000')
            guess_set.add(new Move(startpos.i,startpos.j+1,">"));
        if(startpos.j-1>=0 && currfield[startpos.i][startpos.j-1]=='\u0000')
            guess_set.add(new Move(startpos.i,startpos.j-1,"<"));
    }
    static Move makemove(Move ob)
    {
        if(ob.direction.equals(">"))
        {
            return new Move(ob.i,ob.j+1,">");
        }
        else if(ob.direction.equals("<"))
        {
            return new Move(ob.i,ob.j-1,"<");
        }
        else if(ob.direction.equals("^"))
        {
            return new Move(ob.i-1,ob.j,"^");
        }
        else
        {
            return new Move(ob.i+1,ob.j,"v");
        }

    }
    static boolean checkhor(int x, int y){
        int t1=1,t2=1;
        if(y>0)
        {
            if(pMatrix[x][y-1]!=0)t1=0;

        }
        if(y< pMatrix.length-1)
        {
            if(pMatrix[x][y+1]!=0)t2=0;
        }

        if(t1==1 && t2==1)
            return true;
        else
            return false;
    }

    static boolean checkver(int x, int y){
        int t1=1,t2=1;
        if(x>0)
        {
            if(pMatrix[x-1][y]!=0)t1=0;

        }
        if(x< pMatrix.length-1)
        {
            if(pMatrix[x+1][y]!=0)t2=0;
        }

        if(t1==1 && t2==1)
            return true;
        else
            return false;

    }

    static Boat genboat(int hp,Move move, int ori)
    {
        int x=move.i; int y=move.j;
        Boat b=new Boat(hp);

        boolean t1,t2;
        t1=checkhor(x,y);
        t2=checkver(x,y);
        if(t1 && t2)
        {
            if (hp == 1) {
                if (ori == 1) move.img = R.drawable.subv;
                else
                    move.img=R.drawable.sub;
                b.addMove(move);
            }
            else if(hp==2)
            { b=genboat2(move,ori);}
            else
            { b=genboat3(move ,ori);}

        }
        else
            b=null;



        return b;
    }
    static Boat genboat2(Move move,int ori){
        Boat b=new Boat(2);
        int x=move.i;
        int y=move.j;
        if(ori==0)
        {
            if(y<= pMatrix.length-2)
            {
                boolean t1,t2;
                t1=checkver(x,y+1);
                t2=checkhor(x,y+1);
                if(t1  && t2)
                {   move.setImg(R.drawable.batfront);
                    b.addMove(move);
                    b.addMove(new Move(x,y+1,R.drawable.batback));
                }
                else
                {
                    b=null;
                }
            }
            else
                b=null;

        }
        else
        {
            if(x<= pMatrix.length-2)
            {
                boolean t1,t2;
                t1=checkhor(x+1,y);
                t2=checkver(x+1,y);
                if(t1  && t2)
                {   move.setImg(R.drawable.batfrontpv);
                    b.addMove(move);
                    b.addMove(new Move(x+1,y,R.drawable.batbackpv));
                }
                else
                    b=null;
            }
            else b=null;
        }
        return  b;
    }
    static Boat genboat3(Move move,int ori)
    {
        Boat b=new Boat(3);
        int x=move.i;
        int y=move.j;
        if(ori==0)
        {
            if(y<= pMatrix.length-2)
            {
                boolean t1,t2,t3,t4;
                t1=checkver(x,y+1);
                t2=checkhor(x,y+1);
                t3=checkhor(x,y+2);
                t4=checkver(x,y+2);
                if(t1  && t2 &&t3 &&t4)
                {   move.setImg(R.drawable.bossfront);
                    b.addMove(move);
                    b.addMove(new Move(x,y+1,R.drawable.bossmid));
                    b.addMove(new Move(x,y+2,R.drawable.bossback));
                }
                else
                {
                    b=null;
                }
            }
            else
                b=null;

        }
        else
        {
            if(x<= pMatrix.length-2)
            {
                boolean t1,t2,t3,t4;
                t1=checkver(x+1,y);
                t2=checkhor(x+1,y);
                t3=checkhor(x+2,y);
                t4=checkver(x+2,y);
                if(t1  && t2 &&t3 &&t4)
                {   move.setImg(R.drawable.bossbackv);
                    b.addMove(move);
                    b.addMove(new Move(x+1,y,R.drawable.bossmidv));
                    b.addMove(new Move(x+2,y,R.drawable.bossfrontv));
                }
                else
                    b=null;
            }
            else b=null;
        }
        return  b;

    }

}

