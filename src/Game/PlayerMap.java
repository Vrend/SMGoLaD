package Game;

//map for cities and (hopefully) units
public class PlayerMap
{
    //instance fields
    private int height, width, size;

    //map
    private int[][] map;

    //constructor
    public PlayerMap()
    {
        width = 800;
        height = 600;
        size = 5;
        map = new int[width/size][height/size];
    }

    //generates the cities
    public void generate(int pc, Player[] players)
    {

        //sets up the land and water
        for(int r = 0; r < map.length; r++)
        {
            for (int c = 0; c < map[r].length; c++)
            {
                if(MapGen.land.getElement(r, c) == 1)
                {
                    map[r][c] = pc+pc;
                }
                else
                {
                    map[r][c] = pc+pc+1;
                }
            }
        }

        //for each player
        for(int x = 0; x < pc; x++)
        {
            int numx;
            int numy;

            //pick a random land tile and name it their city
            while(true)
            {
                numx = (int) (Math.random() * map.length);
                numy = (int) (Math.random() * map[0].length);

                if(map[numx][numy] == pc+pc)
                {
                    map[numx][numy] = x;
                    players[x].addCity(numx, numy, x);
                    break;
                }
            }

            //gives them 4 tiles around their city if not already owned
            if(numx > 0)
            {
                if(map[numx-1][numy] == pc+pc || map[numx-1][numy] == pc+pc+1)
                {
                    map[numx-1][numy] = x+pc;
                }
            }
            if(numx < map.length)
            {
                if(map[numx+1][numy] == pc+pc || map[numx+1][numy] == pc+pc+1)
                {
                    map[numx+1][numy] = x+pc;
                }
            }
            if(numy > 0)
            {
                if(map[numx][numy-1] == pc+pc || map[numx][numy-1] == pc+pc+1)
                {
                    map[numx][numy-1] = x+pc;
                }
            }
            if(numy < map[0].length)
            {
                if(map[numx][numy+1] == pc+pc || map[numx][numy+1] == pc+pc+1)
                {
                    map[numx][numy+1] = x+pc;
                }
            }
        }
    }

    //next two methods return lengths of map

    public int getlength()
    {
        return map.length;
    }

    public int getclength()
    {
        return map[0].length;
    }

    //returns element at r, c
    public int getElement(int r, int c)
    {
        return map[r][c];
    }

    //sets the element at r, c
    public void setElement(int r, int c, int v)
    {
        map[r][c] = v;
    }

    //checks if next to a certain tile
    public boolean nextTo(int r, int c, int val)
    {
        if(r > 0)
        {
            if(map[r-1][c] == val)
            {
                return true;
            }
        }

        if(r < map.length)
        {
            if(map[r+1][c] == val)
            {
                return true;
            }
        }

        if(c > 0)
        {
            if(map[r][c-1] == val)
            {
                return true;
            }
        }

        if(c < map[0].length)
        {
            if(map[r][c+1] == val)
            {
                return true;
            }
        }

        return false;
    }
}
