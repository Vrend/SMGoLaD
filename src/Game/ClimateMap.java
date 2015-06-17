package Game;

import org.newdawn.slick.Color;
import java.io.*;
import org.newdawn.slick.Graphics;

//generates, displays, saves, and loads climate map
public class ClimateMap
{
    //instance fields
    private int width, height, size;
    private float[][] climate;

    //constructor, fills in instance fields
    public ClimateMap(int w, int h, int s)
    {
        width = w;
        height = h;
        size = s;
        climate = new float[width/size][height/size];
    }

    //generates basic climate for each tile, based on getTemp()
    public void genClimate()
    {
        int equator = height/2;

        for(int r = 0; r < climate.length; r++)
        {
            for(int c = 0; c < climate[r].length; c++)
            {
            	
            	climate[r][c] = getTemp(equator, c);
            }
        }
    }


    //The closer to the equator, the warmer you are
    private float getTemp(int e, int col)
    {
        float temp = 0f;

        float diff = (float) Math.abs(e - (col*5));
        
        int x = 20;
        float y = 90f;

        while(true)
        {
        	 if(diff <= x)
             {
             	temp = y;
             	break;
             }
        	 
        	 x += 20;
        	 y -= 10;
        }
        
        return temp;
    }

    //draws the climate on the screen
    public void drawClimate(Graphics g)
    {
        //for each tile..
    	for(int r = 0; r < climate.length; r++)
    	{
    		for(int c = 0; c < climate[r].length; c++)
    		{
                //grabs climate value
    			int f = (int) climate[r][c];

                //if ocean, lower temp by 20
    			if(MapGen.land.getElement(r, c) == 0)
    				f -= 20;

                //chooses color based on temperature
    			switch(f)
    			{
    			case 90: g.setColor(new Color(153, 0, 0));
    			break;
    			
    			case 80: g.setColor(new Color(255, 0, 0));
    			break;
    			
    			case 70: g.setColor(new Color(255, 128, 0));
    			break;
    			
    			case 60: g.setColor(new Color(204, 102, 0));
    			break;
    			
    			case 50: g.setColor(new Color(255, 255, 0));
    			break;
    			
    			case 40: g.setColor(new Color(0, 255, 128));
    			break;
    			
    			case 30: g.setColor(new Color(51, 255, 255));
    			break;
    			
    			case 20: g.setColor(new Color(0, 0, 255));
    			break;
    			
    			case 10: g.setColor(new Color(0, 0, 153));
    			break;
    			
    			case 0: g.setColor(new Color(127, 0, 255));
    			break;
    			
    			case -10: g.setColor(new Color(51, 0, 102)); 
    			break;
    			
    			case -20: g.setColor(new Color(153, 0, 153));
    			break;
    			
    			case -30: g.setColor(new Color(255, 0, 255));
    			break;
    			
    			case -40: g.setColor(new Color(255, 153, 204));
    			break;
    			
    			case -50: g.setColor(new Color(255, 255, 255));
    			break;
    			
    			default: g.setColor(new Color(0, 0, 0));
    			break;
    			}

                //fills square
    			g.fillRect(r*5, c*5, 5, 5);

                //sets outline color based on land or ocean
    			if(MapGen.land.getElement(r, c) == 1)
    				g.setColor(new Color(0, 0, 0));
    			else
    				g.setColor(new Color(158, 158, 158));
    			
    			//draws outline
    			g.drawRect(r*5, c*5, 5, 5);
    		}
    	}
    }

    //save function
    public void saveClimate(String saveName) throws IOException
    {
        //creates file in save folder named climate.dat
    	File input = new File(saveName + "/climate.dat");
		if(!input.exists())
		{
			input.createNewFile();
		}

        //filewriter and buffer are created
    	FileWriter fw = new FileWriter(input);
		BufferedWriter bw = new BufferedWriter(fw);

        //goes through each row
    	for(int r = 0; r < climate.length; r++)
    	{
            //creates string for row
    		String s = "";
            //adds each value to string, with "x" in between each value
    		for(int c = 0; c < climate[r].length; c++)
    		{
    			s = s + climate[r][c] + "x";
    		}
            //writes line to file
    		bw.write(s);
            //new line
    		bw.newLine();
    	}

    	//closes the filewriter and bufferedwriter
    	bw.close();
    	fw.close();
    }

    //Loads climate
    public void loadClimate(String saveName) throws IOException
    {
        //file is found
    	File input = new File(saveName + "/climate.dat");

        //readers initialized
    	FileReader fr = new FileReader(input);
    	BufferedReader br = new BufferedReader(fr);

        //for each row
    	for(int r = 0; r < climate.length; r++)
    	{
            //grabs row of map
    		String s = br.readLine();
            //turns it to char array
    		char[] ch = s.toCharArray();
            //char array pointer placeholder
    		int x = 0;
    		for(int c = 0; c < climate[r].length; c++)
    		{
                //creates string to set value
    			String t = "";
    			while(true)
    			{	
    				if(ch[x] != 'x')
    				{
    					t += ch[x];
    				}
    				else
    				{
    					x++;
    					break;
    				}
    				x++;
    			}
                //parses string of temp and assigns to array
    			climate[r][c] = Float.parseFloat(t);
    		}
    	}

        //close readers
    	br.close();
    	fr.close();	
    }

    //allows getting of climate value and setting of it
    public float getClimate(int r, int c)
    {
    	return climate[r][c];
    }
    
    public void setClimate(int r, int c, float num)
    {
    	climate[r][c] = num;
    }
}
