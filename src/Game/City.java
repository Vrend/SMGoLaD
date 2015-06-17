package Game;

//City class
public class City
{
    //instance fields
    private int x, y;
    private int owner;
    private String name;

    //constructor
    public City(int r, int c, int o)
    {
        x = r;
        y = c;
        owner = o;
    }

    //gives location
    public int[] getLoc()
    {
        int[] arr = {x, y};
        return arr;
    }

    //returns owner and name fields
    public String getName()
    {
        return name;
    }

    public int getOwner()
    {
        return owner;
    }
}
