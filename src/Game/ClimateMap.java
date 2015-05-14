package Game;

public class ClimateMap
{
    private int width, height, size;
    private float[][] climate;

    public ClimateMap(int w, int h, int s)
    {
        width = w;
        height = h;
        size = s;
        climate = new float[width/size][height/size];
    }


    public void genClimate()
    {
        int equator = height/2;

        for(int r = 0; r < climate.length; r++)
        {
            for(int c = 0; c < climate[r].length; c++)
            {
                climate[r][c] = getTemp(equator);
            }
        }

    }



    public float getTemp(int e)
    {
        float temp = 0f;


        return temp;
    }






}
