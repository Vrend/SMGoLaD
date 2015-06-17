package Game;

//Unit interface
public interface Unit
{
    /*
       INFO: Structure of a unit

    private int march, health, status
    private int[] loc


    public Unit(int r, int c)
    {
        march = 0;
        health = whatever;
        status = 0;
        loc[0] = r;
        loc[1] = c;
    }

     */

    //returns attack value
    public int attack();
    //returns movement per turn
    public int move();
    //returns status of unit
    public int getStatus();
    //changes status of unit
    public void setStatus(int s);
    //returns march time of unit
    public int getMarchTime();
    //sets march time of unit
    public void setMarchTime(int m);
    //returns position of unit
    public int[] getPosition();
    //sets position of unit using r, c
    public void setPosition(int r, int c);
    //returns range of unit
    public int range();
    //returns health
    public int getHealth();
    //sets health
    public void setHealth(int h);
}
