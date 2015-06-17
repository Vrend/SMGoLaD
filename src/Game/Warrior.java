package Game;

//standard warrior unit
public class Warrior implements Unit
{
    //instance fields
    private int status;
    private int march;
    private int[] position;
    private int health;

    //constructor
    public Warrior(int r, int c)
    {
        status = 0;
        march = 0;
        position = new int[2];
        position[0] = r;
        position[1] = c;
        health = 10;
    }

    //standard methods which set and return instance values

    public int attack()
    {
        return 3;
    }


    public int move()
    {
        return 1;
    }

    public int range()
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
        return position;
    }


    public void setPosition(int r, int c)
    {
        position[0] = r;
        position[1] = c;
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
