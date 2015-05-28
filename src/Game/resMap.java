package Game;

public class resMap
{
    private int width, height, size;

    private int[][] res;

    //Biomes
    private final int beach = 3;
    private final int plains = 2;
    private final int ocean = 1;
    private final int tundra = 5;
    private final int desert = 8;
    private final int mountain = 6;
    private final int hill = 7;
    private final int grassland = 4;
    private final int temperateforest = 9;
    private final int rainforest = 10;
    private final int ice = 11;


    //Resources

    private final int gold = 1;        //luxury
    private final int iron = 2;        //armour and weapons
    private final int silver = 3;      //luxury
    private final int copper = 4;      //luxury and for bronze
    private final int tin = 5;         //luxury and for bronze
    private final int whales = 6;      //Whales for oil
    private final int fish = 7;        //Fish and sea creatures edible
    private final int livestock = 8;   //Oxen, cows, sheep, pigs, etc
    private final int horses = 9;      //What the hell do you think this is for?
    private final int beasts = 10;     //wild animals for trapping and training
    private final int coal = 11;       //Used for heat and steel
    private final int bananas = 12;    //tropic fruit
    private final int grove = 13;      //fruits n' shit
    private final int freshwater = 14; //important for population and growth

    public resMap(int w, int h, int s)
    {
        width = w;
        height = h;
        size = s;
        res = new int[width/size][height/size];
    }



    public void generateResources()
    {
        for(int r = 0; r < res.length; r++)
        {
            for(int c = 0; c < res[r].length; c++)
            {

            }
        }


    }




}
