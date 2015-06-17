package Game;

import org.newdawn.slick.Color;
import java.util.ArrayList;

//Player object
public class Player
{
    //instance fields
    private String name;
    private Color color;
    private Color alpha;
    //useless currently
    private int score;

    //personal maps
    private GeoMap g;
    private ClimateMap c;
    private resMap r;
    private LandMap l;

    //arraylists for cities, resources, and units
    private ArrayList<Unit> units = new ArrayList<Unit>();
    private ArrayList<Resource> resources = new ArrayList<Resource>();
    private ArrayList<City> cities = new ArrayList<City>();

    //constructor
    public Player(String n)
    {
        name = n;
        resources.add(new Gold());
        resources.add(new Silver());
        resources.add(new Iron());
        resources.add(new Copper());
        resources.add(new Tin());
        resources.add(new Whales());
        resources.add(new Fish());
        resources.add(new Livestock());
        resources.add(new Horses());
        resources.add(new Beasts());
        resources.add(new Coal());
        resources.add(new Bananas());
        resources.add(new Grove());
        resources.add(new Freshwater());
        //generates random color and alpha
        color = genColor();
        score = 0;
        l = MapGen.land;
        c = MapGen.climate;
        g = MapGen.geo;
        r = MapGen.res;
    }

    //returns name
    public String getName()
    {
        return name;
    }

    //makes random color and alpha
    private Color genColor()
    {
        Color c;
        int int1 = (int)(Math.random() * 255);
        int int2 = (int)(Math.random() * 255);
        int int3 = (int)(Math.random() * 255);
        c = new Color(int1, int2, int3);
        alpha = new Color(int1, int2, int3, 120);
        return c;
    }

    //returns color
    public Color getColor()
    {
        return color;
    }

    //returns alpha
    public Color getAlpha()
    {
        return alpha;
    }

    //creates jpanel-friendly color (hexadecimal)
    public String getJColor()
    {
        int r = color.getRed();
        int b = color.getBlue();
        int g = color.getGreen();
        String hex = String.format("#%02x%02x%02x", r, g, b);
        return hex;
    }

    //next 4 methods returns their maps

    public ClimateMap getcMap()
    {
        return c;
    }

    public GeoMap getgMap()
    {
        return g;
    }

    public resMap getrMap()
    {
        return r;
    }

    public LandMap getlMap()
    {
        return l;
    }


    //adds unit
    public void addUnit(Unit u, City city)
    {
        //grabs city loc
        int[] loc = city.getLoc();
        //gets row and col from city loc
        int r = loc[0];
        int c = loc[1];

        //adds unit based on type
        if(u instanceof Warrior)
        {
            u = new Warrior(r, c);
            units.add(u);
        }
        else if(u instanceof Archer)
        {
            u = new Archer(r, c);
            units.add(u);
        }
        else if(u instanceof Scout)
        {
            u = new Scout(r, c);
            units.add(u);
        }
        else if(u instanceof Peasant)
        {
            u = new Peasant(r, c);
            units.add(u);
        }
    }

    //adds a city with row, col, and owner
    public void addCity(int r, int c, int o)
    {
        cities.add(new City(r, c, o));
    }

    //returns a city
    public City getCity(int c)
    {
        return cities.get(c);
    }

    //returns a unit array
    public Unit[] getUnits()
    {
        Unit[] u = new Unit[units.size()];

        for(int x = 0; x < units.size(); x++)
        {
            u[x] = units.get(x);
        }

        return u;
    }

}