package Game;

//Ranged unit
public class Archer implements Unit
{
    //Instance fields
    private int march, status, health;

    private int[] loc;

    //Constructor
    public Archer(int r, int c)
    {
        loc = new int[2];
        loc[0] = r;
        loc[1] = c;

        march = 0;
        status = 0;
        health = 5;
    }

    //standard methods for setting and returning instance values

    public int attack()
    {
        return 3;
    }

    public int move()
    {
        return 1;
    }

    public int getStatus()
    {
        return status;
    }

    public void setStatus(int s)
    {
        status = s;
    }

    public int getMarchTime()
    {
        return march;
    }

    public void setMarchTime(int m)
    {
        march = m;
    }

    public int[] getPosition()
    {
        return loc;
    }

    public void setPosition(int r, int c)
    {
        loc[0] = r;
        loc[1] = c;
    }

    public int range()
    {
        return 2;
    }

    public int getHealth()
    {
        return health;
    }

    public void setHealth(int h)
    {
        health = h;
    }
}
