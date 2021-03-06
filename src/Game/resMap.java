package Game;

import static java.lang.Math.random;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import java.io.*;

//does the resources on the map
public class resMap
{

    //instance fields
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
    private final int iron = 2;        //armor and weapons
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

    //constructor
    public resMap(int w, int h, int s)
    {
        width = w;
        height = h;
        size = s;
        res = new int[width/size][height/size];
    }

    //for each tile, grab the terrain and use the checkVal method to generate resources
    public void generateResources()
    {
        for(int r = 0; r < res.length; r++)
        {
            for(int c = 0; c < res[r].length; c++)
            {
            	int terrain = MapGen.geo.getGeo(r, c);
            	
            	res[r][c] = checkVal(terrain, r, c);
            }
        }
    }

    //A bunch of random probability for each resource based on each geographical feature -- (0 is nothing, no resource)
    public int checkVal(int type, int r, int c)
    {
    	if(type == ocean)
    	{
    		double num = random();
    		if(!MapGen.geo.nextTo(beach, r, c, 1) && num > .98)
        	{
        		return whales;
        	}
    		else if(MapGen.geo.nextTo(beach, r, c, 1) && random() > .7)
    		{
    			return fish;
    		}
    		else if(MapGen.geo.nextTo(beach, r, c, 1))
    		{
    			return freshwater;
    		}
    		else
    		{
    			return 0;
    		}
    	}
    	
    	else if(type == desert)
    	{
    		if(random() > .95)
    		{
    			if(random() > .5)
    			{
    				return copper;
    			}
    			else
    			{
    				return tin;
    			}
    		}

    		else if(random() > .97)
    		{
    			return coal;
    		}
    		else
    		{
    			return 0;
    		}
    	}
    	
    	else if(type == tundra)
    	{
    		double num = random();
    		if(num > .95) //change
    		{
    			return iron;
    		}
    		else if(random() > .96)
    		{
    			return beasts;
    		}
    		else
    		{
    			return 0;
    		}
    	}
    	
    	else if(type == beach)
    	{
    		if(random() > .9)
    		{
    			return silver;
    		}
    		else
    		{
    			return 0;
    		}
    	}
    	
    	else if(type == hill)
    	{
    		double num = random();
    		if(num > .9)
    		{
    			return iron;
    		}
    		else if(num > .7)
    		{
    			if(random() > .5)
    			{
    				return copper;
    			}
    			else
    			{
    				return tin;
    			}
    		}
    		else if(num > .6)
    		{
    			if(random() > .7)
    			{
    				return gold;
    			}
    			else
    			{
    				return silver;
    			}
    		}
    		else
    		{
    			return 0;
    		}
    	}
    	
    	else if(type == plains)
    	{
    		double num = random();
    		if(num >= .93)
    		{
    			return grove;
    		}
    		else if(random() >= .98)
    		{
    			return horses;
    		}
    		else if(random() > .95)
    		{
    			return livestock;
    		}
    		else
    		{
    			return 0;
    		}
    	}
    	
    	else if(type == mountain)
    	{
    		if(random() > .7)
    		{
    			double num = random();
                if(num > .8)
                {
                    return coal;
                }
    			else if(num > .7)
    			{
    				return gold;
    			}
    			else if(num > .6)
    			{
    				return silver;
    			}
    			else if(num > .4)
    			{
    				if(random() > .8)
    				{
    					return iron;
    				}
    				else if(random() > .7)
    				{
    					return copper;
    				}
    				else
    				{
    					return tin;
    				}
    			}
    			else
    			{
    				return 0;
    			}
    		}
    		else
    		{
    			return 0;
    		}
    	}
    	
    	else if(type == rainforest)
    	{
    		double num = random();
    		
    		if(num > .85)
    		{
    			return bananas;
    		}
    		else if(random() > .92)
    		{
    			return beasts;
    		}
    		else if(num > .6)
    		{
    			return grove;
    		}
    		else
    		{
    			return 0;
    		}
    	}
    	
    	else if(type == temperateforest)
    	{
    		double num = random();
    	
    		if(num > .95)
    		{
    			if(random() > .95)
    			{
    				return horses;
    			}
    			else if(random() > .95)
    			{
    				return beasts;
    			}
                else
                {
                    return 0;
                }
    		}
    		else if(random() > .95)
    		{
    			return livestock;
    		}
    		else if(random() > .9)
    		{
    			return grove;
    		}
    		else
    		{
    			return 0;
    		}
    	}
    	
    	else if(type == grassland)
    	{
    		double num = random();
    		
    		if(num > .95)
    		{
    			return grove;
    		}
    		else if(num > .8)
    		{
    			if(random() > .95)
    			{
    				return horses;
    			}
    			else if(random() >= .93)
    			{
    				return livestock;
    			}
    			else
    			{
    				return 0;
    			}
    		}
    		else
    		{
    			return 0;
    		}
    	}
    	
    	else
    	{
    		return 0;
    	}
    }

    //draws the map
    public void drawRes(Graphics g)
    {
        //for each tile
    	for(int r = 0; r < res.length; r++)
    	{
    		for(int c = 0; c < res[r].length; c++)
    		{
                //grab the resource
    			int resource = res[r][c];

                //if ocean or ice
    			if(MapGen.geo.getGeo(r, c) == ocean || MapGen.geo.getGeo(r, c) == ice)
    			{
                    //draw these resources
    				switch(resource)
        			{
        			case whales: g.setColor(new Color(7, 230, 181));
        			break;
        			
        			case fish: g.setColor(new Color(4, 79, 60));
        			break;

                    case freshwater: g.setColor(new Color(0, 255, 242));
                    break;
        			
        			default: g.setColor(new Color(99, 98, 138));
        			break;
        			}
    				
    				g.fillRect(r * 5, c * 5, 5, 5);

                    //and give them a gray outline
                    g.setColor(Color.gray);
                    g.drawRect(r * 5, c * 5, 5, 5);
    			}

                //or for the land resources
    			else
    			{
                    //select the color matching the resource
    				switch(resource)
        			{
        			case gold: g.setColor(new Color(255, 225, 0));
        			break;

        			case silver: g.setColor(new Color(219, 219, 219));
        			break;

        			case copper: g.setColor(new Color(222, 98, 31));
        			break;

        			case tin: g.setColor(new Color(94, 91, 89));
        			break;

        			case iron: g.setColor(new Color(128, 116, 110));
        			break;

        			case coal: g.setColor(Color.black);
        			break;

        			case grove: g.setColor(new Color(0, 46, 11));
        			break;

        			case livestock: g.setColor(new Color(61, 143, 81));
        			break;

        			case horses: g.setColor(new Color(51, 255, 0));
        			break;

        			case beasts: g.setColor(new Color(74, 28, 13));
        			break;

        			case bananas: g.setColor(new Color(227, 216, 95));
        			break;

                    //or just red if no resource
        			default: g.setColor(Color.red);
        			break;
        			}

                    //fill the tile
    				g.fillRect(r * 5, c * 5, 5, 5);

                    //gives a black outline
                    g.setColor(Color.black);
                    g.drawRect(r * 5, c * 5, 5, 5);
    			}
    		}
    	}
    }

    //saves the map
    public void saveResMap(String s) throws IOException
    {
        //creates res file
        File input = new File(s + "/res.dat");

        if(!input.exists())
        {
            input.createNewFile();
        }

        //creates writers
        FileWriter fw = new FileWriter(input);
        BufferedWriter bw = new BufferedWriter(fw);

        //for each row
        for(int r = 0; r < res.length; r++)
        {
            //put every value between a "x"
            String saveline = "";
            for(int c = 0; c < res[r].length; c++)
            {
                saveline = saveline + Integer.toString(res[r][c]) + 'x';
            }
            //write line to file
            bw.write(saveline);
            //new line
            bw.newLine();
        }

        //close writers
        bw.close();
        fw.close();
    }

    public void loadResMap(String s) throws IOException
    {
        //find res file
        File input = new File(s + "/res.dat");

        //create readers
        FileReader fr = new FileReader(input);
        BufferedReader br = new BufferedReader(fr);

        //for each row
        for(int r = 0; r < res.length; r++)
        {
            //grab the line
            String loadLine = br.readLine();
            //turn into a char array
            char[] ch = loadLine.toCharArray();
            //x is the array pointer/counter
            int x = 0;
            for(int c = 0; c < res[r].length; c++)
            {
                //add each char until you reach "x" -- and then increment x
                String text = "";
                while(true)
                {
                    if(ch[x] != 'x')
                    {
                        text += ch[x];
                    }
                    else
                    {
                        x++;
                        break;
                    }
                    x++;
                }
                //turn the string into the number and add it to the array
                res[r][c] = Integer.parseInt(text);
            }
        }

        //close the readers
        br.close();
        fr.close();
    }

    //returns the element at r, c
    public int getElement(int r, int c)
    {
        return res[r][c];
    }
    
}