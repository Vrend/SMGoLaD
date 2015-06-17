package Game;

//fast scout unit
public class Scout implements Unit
{
    //instance fields
    private int health, status, march;

    private int[] loc;

    //constructor
    public Scout(int r, int c)
    {
        loc = new int[2];
        loc[0] = r;
        loc[1] = c;
        health = 5;
        status = 0;
        march = 0;
    }

    //standard unit methods which set and return instance values
    public int attack()
    {
        return 1;
    }


    public int move()
    {
        return 3;
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
        return 1;
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
