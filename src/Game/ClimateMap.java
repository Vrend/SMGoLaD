package Game;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

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
            	
            		climate[r][c] = getTemp(equator, c);
            }
        }
    }



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


    public void drawClimate(Graphics g)
    {
    	for(int r = 0; r < climate.length; r++)
    	{
    		for(int c = 0; c < climate[r].length; c++)
    		{
    			int f = (int) climate[r][c];
    			
    			if(MapGen.land.getElement(r, c) == 0)
    				f -= 10;
    			
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
    			
    			g.fillRect(r*5, c*5, 5, 5);
    			
    			if(MapGen.land.getElement(r, c) == 1)
    				g.setColor(new Color(0, 0, 0));
    			else
    				g.setColor(new Color(158, 158, 158));
    			
    			
    			g.drawRect(r*5, c*5, 5, 5);
    		}
    	}
    }



}
