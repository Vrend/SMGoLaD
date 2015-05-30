package Game;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import java.io.*;
import java.util.Random;

public class GeoMap 
{
	private int width, height, size;
	
	private int[][] geo;
	
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
	
	public GeoMap(int w, int h, int s)
	{
		width = w;
		height = h;
		size = s;
		geo = new int[width/size][height/size];
	}
	
	public void genGeo()
	{
		try
		{
			clear();
			System.out.println("P1");
			oceans();
			System.out.println("P2");
			ice();
			System.out.println("P3");
			coast();
			System.out.println("P4");
			tundra();	
			System.out.println("P5");
			desert((int) ((Math.random() * 5) + 1));
			System.out.println("P6");
            mountainGen();
            System.out.println("P7");
            hillGen();
            System.out.println("P8");
            rainforestGen();
            System.out.println("P9");
            tempForestGen();
            System.out.println("P10");
            fillIn();
		}
		catch(Exception e)
		{
			System.out.println("Error generating!");
			System.out.println("Regenerating map....");
			genGeo();
		}
	}
	
	public void coast()
	{
		for(int r = 0; r < geo.length; r++)
		{
			for(int c = 0; c < geo[0].length; c++)
			{
				float temp = MapGen.climate.getClimate(r, c);
				
				if(MapGen.land.onCoast(r, c) && MapGen.land.getElement(r, c) == 1)
				{
					temp -= 10;
					MapGen.climate.setClimate(r, c, temp);
					geo[r][c] = beach;
				}
			}
		}
	}
	
	public void oceans()
	{
		for(int r = 0; r < geo.length; r++)
		{
			for(int c = 0; c < geo[r].length; c++)
			{
				if(MapGen.land.getElement(r, c) == 0)
				{
					geo[r][c] = ocean;
				}
			}
		}
	}
	
	
	public void ice()
	{

			for(int r = 0; r < geo.length; r++)
			{
				for(int c = 0; c < geo[r].length; c++)
				{
					if(MapGen.land.getElement(r, c) == 0)
					{
						double num = Math.random();
						if(MapGen.climate.getClimate(r, c) <= -50)
						{
							if(num >= .4)
							{
								geo[r][c] = ice;
							}
						}
						else if(MapGen.climate.getClimate(r, c) <= -40)
						{
							if(num >= .6)
							{
								geo[r][c] = ice;
							}
						}
						
						else if(MapGen.climate.getClimate(r, c) <= -30)
						{
							if(num >= .8)
							{
								geo[r][c] = ice;
							}
						}
					}
				}

		}
		
		for(int r = 0; r < geo.length; r++)
		{
			for(int c = 0; c < geo[r].length; c++)
			{
				if(geo[r][c] == ocean && nextTo(ice, r, c, 3))
				{
					geo[r][c] = ice;
				}
			}
		}
		
		
		for(int r = 0; r < geo.length; r++)
		{
			for(int c = 0; c < geo[r].length; c++)
			{
				if(geo[r][c] == ice && !nextTo(ice, r, c, 1))
				{
					geo[r][c] = ocean;
				}
			}
		}

        for(int r = 0; r < geo.length; r++)
        {
            for(int c = 0; c < geo[r].length; c++)
            {
                if(geo[r][c] == ocean && !nextTo(ocean, r, c, 1))
                {
                    geo[r][c] = ice;
                }
            }
        }
		
	}
	
	public void tundra()
	{
		double chance = Math.random();
		
		for(int r = 0; r < geo.length; r++)
		{
			for(int c = 0; c < geo[r].length; c++)
			{
				if(MapGen.land.getElement(r, c) == 1 && MapGen.climate.getClimate(r, c) <= 0)
				{
					double val = Math.random();
					
					if(MapGen.climate.getClimate(r, c) <= -40)
					{
						val += .1;
					}
					
					else if(MapGen.climate.getClimate(r, c) <= -30)
					{
						val -= .2;
					}
					
					else 
					{
						val -= .5;
					}
					
					if(val >= chance)
					{
						geo[r][c] = tundra;
					}
				}
			}
		}
		
		for(int r = 0; r < geo.length; r++)
		{
			for(int c = 0; c < geo[r].length; c++)
			{
				if(MapGen.land.getElement(r, c) == 1 && nextTo(tundra, r, c, 2))
				{
						geo[r][c] = tundra;
				}
			}
		}
		
		
		for(int r = 0; r < geo.length; r++)
		{
			for(int c = 0; c < geo[r].length; c++)
			{
				if(geo[r][c] == tundra)
				{
					if(!nextTo(tundra, r, c, 2))
					{
						geo[r][c] = 0;
					}
				}
			}
		}

        for(int r = 0; r < geo.length; r++)
        {
            for(int c = 0; c < geo[r].length; c++)
            {
                if(geo[r][c] == tundra)
                {
                    float t = MapGen.climate.getClimate(r, c);
                    t -= 10;
                    MapGen.climate.setClimate(r, c, t);
                }
            }
        }
		
		
	}
	
	public void desert(int l)
	{
		int row;
		int col;

        int limit = 3840;
        int check = 0;

		double curve = Math.random();

		INNERDESERT:
		for(int x = 0; x < l; x++)
		{
            int count = 0;
			while(true)
			{
                if(count > 25)
                {
                    break INNERDESERT;
                }

				row = (int) (Math.random() * geo.length);
				col = (int) (Math.random() * geo[0].length);
				
				if(row > 0 && row < (geo.length) && col > 0 && col < (geo[0].length)) 
				{
					if(MapGen.land.getElement(row, col) == 1)
					{
						if(geo[row][col] == 0 && !nextTo(beach, row, col, 1) && !nextTo(tundra, row, col, 1))
						{
							break;
						}
                        else
                        {
                            count++;
                        }
					}
				}
			}

            geo[row][col] = desert;
            check++;
		}
		
		for(int y = 0; y < 5; y++)
		{
			for(int r = 0; r < geo.length; r++)
			{
				for(int c = 0; c < geo[r].length; c++)
				{
					if(MapGen.land.getElement(r, c) == 1)
					{	
						double val = Math.random();

						if(MapGen.climate.getClimate(r, c) > 70)
						{
							val += .1;
						}
						else if(MapGen.climate.getClimate(r, c) <= 40)
						{
							val -= .2;
						}
						
						if(val >= curve && nextTo(desert, r, c, 1) && !nextTo(beach, r, c, 1) && !nextTo(tundra, r, c, 1))
						{
                            if(check > limit)
                            {
                                return;
                            }
							geo[r][c] = desert;
                            check++;
						}
					}
				}
			}	
		}
		
		for(int r = 0; r < geo.length; r++)
		{
			for(int c = 0; c < geo[r].length; c++)
			{
				if(nextTo(desert, r, c, 3) && !nextTo(beach, r, c, 1) && !nextTo(tundra, r, c, 1))
				{
					geo[r][c] = desert;
				}
			}
		}
		
		
		for(int r = 0; r < geo.length; r++)
		{
			for(int c = 0; c < geo[r].length; c++)
			{
				if(geo[r][c] == desert)
				{
					if(!nextTo(desert, r, c, 1))
					{
						geo[r][c] = 0;
					}
					
					else
					{
						float t = MapGen.climate.getClimate(r, c);
						
						if(t <= 70)
						{
							t += 20;
						}
						else if(t <= 80)
						{
							t += 10;
						}
						
						MapGen.climate.setClimate(r, c, t);
					}
				}
			}
		}
	}
	
	public void mountainGen()
    {
        Random rand = new Random();

        int curve = rand.nextInt(4) + 1;

        int row, col;

        for (int x = 0; x < curve; x++)
        {
            while (true)
            {
                row = (int) (Math.random() * geo.length);
                col = (int) (Math.random() * geo[0].length);

                if (row > 0 && row < (geo.length) && col > 0 && col < (geo[0].length))
                {
                    if (MapGen.land.getElement(row, col) == 1)
                    {
                        break;
                    }
                }
            }

            geo[row][col] = mountain;
        }

        int len = rand.nextInt(5);

        for(int y = 0; y < len; y++)
        {
            for(int r = 0; r < geo.length; r++)
            {
                for(int c = 0; c < geo[r].length; c++)
                {
                    if(nextTo(mountain, r, c, 1) && !nextTo(ocean, r, c, 1))
                    {
                        geo[r][c] = mountain;
                        break;
                    }
                }
            }
        }

            for(int r = 0; r < geo.length; r++)
            {
                for(int c = 0; c < geo[r].length; c++)
                {
                    if(geo[r][c] == mountain)
                    {
                        float t = MapGen.climate.getClimate(r, c);
                        t -= 20;
                        MapGen.climate.setClimate(r, c, t);
                    }
                }
            }
    }


    public void hillGen()
    {
        for(int r = 0; r < geo.length; r++)
        {
            for(int c = 0; c < geo[r].length; c++)
            {
                if(nextTo(mountain, r, c, 1) && !nextTo(ocean, r, c, 1) && geo[r][c] != mountain)
                {
                    if(Math.random() > .33)
                    {
                        geo[r][c] = hill;
                    }
                }
            }
        }


        for(int r = 0; r < geo.length; r++)
        {
            for(int c = 0; c < geo[r].length; c++)
            {
                if(geo[r][c] == hill)
                {
                    if(MapGen.climate.getClimate(r, c) < 90)
                    {
                        float t = MapGen.climate.getClimate(r, c);

                        if(Math.random() > .5)
                        {
                            t -= 10;
                        }
                        MapGen.climate.setClimate(r, c, t);
                    }
                }
            }
        }

    }


    public void rainforestGen()
    {

        Random rand = new Random();
        int curve = rand.nextInt(5);

        for(int x = 0; x < curve; x++)
        {
                outer:
                for(int r = 0; r < geo.length; r++)
                {
                    for(int c = 0; c < geo[r].length; c++)
                    {
                        if(MapGen.land.getElement(r, c) == 1)
                        {
                            float clim = MapGen.climate.getClimate(r, c);

                            if(geo[r][c] != mountain)
                            {
                                if(!nextTo(beach, r, c, 1) && (clim <= 90 && clim >= 70) && Math.random() > .4 && !nextTo(desert, r, c, 1))
                                {
                                    geo[r][c] = rainforest;
                                    break outer;
                                }
                            }
                        }
                    }
                }
        }

        int len = rand.nextInt(4);
        len += 1;

        for(int x = 0; x < len; x++)
        {
            for(int r = 0; r < geo.length; r++)
            {
                for(int c = 0; c < geo[r].length; c++)
                {
                    float clim = MapGen.climate.getClimate(r, c);

                    if(clim >= 70 && clim <= 90)
                    {
                        if((Math.random() > .33) && nextTo(rainforest, r, c, 1) && !nextTo(ocean, r, c, 1) && !nextTo(desert, r, c, 1))
                        {
                            geo[r][c] = rainforest;
                        }
                    }
                }
            }
        }

        for(int r = 0; r < geo.length; r++)
        {
            for(int c = 0; c < geo[r].length; c++)
            {
                if(nextTo(rainforest, r, c, 3) && !nextTo(ocean, r, c, 1))
                {
                    geo[r][c] = rainforest;
                }
            }
        }


    }

    public void tempForestGen()
    {
        Random rand = new Random();
        int curve = rand.nextInt(7) + 1;

        int count = 0;
        touter:
        for(int l = 0; l < curve; l++)
        {
            while(true)
            {
                if(count > 30)
                {
                    break touter;
                }
                int r = (int) (Math.random() * geo.length);
                int c = (int) (Math.random() * geo[0].length);

                if (r > 0 && r < (geo.length) && c > 0 && c < (geo[0].length))
                {
                    if (MapGen.land.getElement(r, c) == 1)
                    {
                        if(!nextTo(desert, r, c, 1) && MapGen.climate.getClimate(r, c) < 70 && MapGen.climate.getClimate(r, c) >= 0)
                        {
                            geo[r][c] = temperateforest;
                            break;
                        }
                    }
                }
                count++;
             }
        }

        for(int x = 0; x < 6; x++)
        {
            for(int r = 0; r < geo.length; r++)
            {
                for(int c = 0; c < geo[r].length; c++)
                {
                    if (MapGen.land.getElement(r, c) == 1)
                    {
                        if(!nextTo(desert, r, c, 1) && MapGen.climate.getClimate(r, c) < 70 && MapGen.climate.getClimate(r, c) >= 0 && nextTo(temperateforest, r, c, 1))
                        {
                            if(Math.random() > .4)
                            {
                                geo[r][c] = temperateforest;
                            }
                        }
                    }
                }
            }
        }


        for(int x = 0; x < 3; x++)
        {
            for(int r = 0; r < geo.length; r++)
            {
                for(int c = 0; c < geo[r].length; c++)
                {
                    if(!nextTo(desert, r, c, 1) && nextTo(temperateforest, r, c, 3) && MapGen.land.getElement(r, c) == 1)
                    {
                        geo[r][c] = temperateforest;
                    }
                }
            }
        }
    }


    public void fillIn()
    {
        for(int r = 0; r < geo.length; r++)
        {
            for(int c = 0; c < geo[r].length; c++)
            {
                if(MapGen.land.getElement(r, c) == 1 && geo[r][c] == 0)
                {
                    if (nextTo(desert, r, c, 1) && nextTo(rainforest, r, c, 1))
                    {
                        geo[r][c] = plains;
                    }
                    else if(nextTo(rainforest, r, c, 2) && geo[r][c] == 0)
                    {
                        geo[r][c] = temperateforest;
                    }

                    else if(nextTo(rainforest, r, c, 1))
                    {
                        if(Math.random() > .4)
                        {
                            geo[r][c] = temperateforest;
                        }
                    }

                    else if(nextTo(desert, r, c, 1) && !nextTo(desert, r, c, 3))
                    {
                        if(Math.random() > .4)
                        {
                            geo[r][c] = plains;
                        }
                        else
                        {
                            geo[r][c] = grassland;
                        }
                    }
                    else if(MapGen.climate.getClimate(r, c) >= 40)
                    {
                        float temp = MapGen.climate.getClimate(r, c);

                        if(temp == 40 && Math.random() > .7)
                        {
                            geo[r][c] = plains;
                        }

                        else if(temp == 50 && Math.random() > .5)
                        {
                            geo[r][c] = plains;
                        }
                        else if(temp > 60 && Math.random() > .3)
                        {
                            geo[r][c] = plains;
                        }
                        else
                        {
                            geo[r][c] = grassland;
                        }
                    } else if(MapGen.climate.getClimate(r, c) < 0)
                    {
                        float t = MapGen.climate.getClimate(r, c);

                        if(t == 0)
                        {
                            if(Math.random() > .6)
                            {
                                geo[r][c] = temperateforest;
                            }
                            else if(Math.random() > .6)
                            {
                                geo[r][c] = hill;
                            }
                            else
                            {
                                geo[r][c] = grassland;
                            }
                        }
                        else if(t == -10)
                        {
                            if(Math.random() > .6)
                            {
                                geo[r][c] = temperateforest;
                            }
                            else if(Math.random() > .5)
                            {
                                geo[r][c] = hill;
                            }
                            else
                            {
                                geo[r][c] = grassland;
                            }
                        }
                        else
                        {
                            if(Math.random() > .6)
                            {
                                geo[r][c] = temperateforest;
                            }
                            else
                            {
                                geo[r][c] = hill;
                            }
                        }


                    }
                    else
                    {
                        geo[r][c] = grassland;
                    }
                }
            }
        }



        for(int r = 0; r < geo.length; r++)
        {
            for(int c = 0; c < geo[r].length; c++)
            {
                if(geo[r][c] == plains)
                {
                    if(!nextTo(plains, r, c, 2))
                    {
                        geo[r][c] = grassland;
                    }
                }
                else if(geo[r][c] == grassland)
                {
                    if(!nextTo(grassland, r, c, 2))
                    {
                        geo[r][c] = plains;
                    }
                }
            }
        }


        for(int x = 0; x < 4; x++)
        {
            for(int r = 0; r < geo.length; r++)
            {
                for(int c = 0; c < geo[r].length; c++)
                {
                    if(geo[r][c] == grassland)
                    {
                        if(nextTo(desert, r, c, 3))
                        {
                            geo[r][c] = desert;
                        }
                        else if(nextTo(desert, r, c, 1))
                        {
                            if(Math.random() > .6)
                            {
                                geo[r][c] = plains;
                            }
                        }
                    }
                    else if(geo[r][c] == grassland)
                    {
                        if(!nextTo(grassland, r, c, 2))
                        {
                            geo[r][c] = plains;
                        }
                    }
                    else if(geo[r][c] == plains)
                    {
                        if(!nextTo(plains, r, c, 2) && !nextTo(desert, r, c, 1))
                        {
                            geo[r][c] = grassland;
                        }

                        else if(nextTo(desert, r, c, 2))
                        {
                            geo[r][c] = desert;
                        }
                    }

                    else if(MapGen.climate.getClimate(r, c) < 0)
                    {
                        if(geo[r][c] == grassland)
                        {
                            if(!nextTo(grassland, r, c, 2))
                            {
                                if(Math.random() > .4)
                                {
                                    geo[r][c] = temperateforest;
                                }
                                else
                                {
                                    geo[r][c] = hill;
                                }
                            }
                        }

                        else if(geo[r][c] == hill)
                        {
                            if(nextTo(grassland, r, c, 2))
                            {
                                geo[r][c] = grassland;
                            }
                        }
                        else if(geo[r][c] == temperateforest)
                        {
                            if(nextTo(grassland, r, c, 2))
                            {
                                geo[r][c] = grassland;
                            }
                        }
                    }
                }
            }
        }


    }


	public boolean nextTo(int type, int r, int c, int count)
	{
		int sum = 0;
		
		if(r > 0)
		{
			if(geo[r-1][c] == type)
			{
				sum++;
			}
		}
		
		if(r < (geo.length-1))
		{
			if(geo[r+1][c] == type)
			{
				sum++;
			}
		}
		
		if(c > 0)
		{
			if(geo[r][c-1] == type)
			{
				sum++;
			}
		}
		
		if(c < (geo[0].length-1))
		{
			if(geo[r][c+1] == type)
			{
				sum++;
			}
		}
		
		if(sum >= count)
		{
			return true;
		}
		
		return false;
	}
	
	
	public void drawGeo(Graphics g) {
        for (int r = 0; r < geo.length; r++) {
            for (int c = 0; c < geo[r].length; c++) {
                if (geo[r][c] == ocean && MapGen.land.getElement(r, c) == 0) {
                    g.setColor(new Color(0, 0, 97));
                    g.fillRect(r * 5, c * 5, 5, 5);
                    g.setColor(Color.gray);
                    g.drawRect(r * 5, c * 5, 5, 5);
                } else {
                    switch (geo[r][c]) {
                        case beach:
                            g.setColor(new Color(250, 241, 177));
                            break;

                        case ice:
                            g.setColor(new Color(10, 36, 252));
                            break;

                        case tundra:
                            g.setColor(new Color(84, 84, 84));
                            break;

                        case desert:
                            g.setColor(new Color(255, 119, 0));
                            break;

                        case mountain:
                            g.setColor(new Color(153, 104, 14));
                            break;

                        case hill:
                            g.setColor(new Color(255, 191, 0));
                            break;

                        case rainforest:
                            g.setColor(Color.red);
                            break;

                        case temperateforest:
                            g.setColor(new Color(0, 59, 8));
                            break;

                        case plains:
                            g.setColor(new Color(196, 172, 134));
                            break;

                        case grassland:
                            g.setColor(new Color(0, 191, 35));
                            break;

                        default:
                            g.setColor(Color.red);
                            break;
                    }

                    g.fillRect(r * 5, c * 5, 5, 5);

                    g.setColor(Color.black);
                    g.drawRect(r * 5, c * 5, 5, 5);
                }
            }
        }
    }
	
	
	private void clear()
	{
		for(int r = 0; r < geo.length; r++)
		{
			for(int c = 0; c < geo[r].length; c++)
			{
				geo[r][c] = 0;
			}
		}
	}


    public void saveGeo(String s) throws IOException
    {
        File input = new File(s + "/geo.dat");

        if(!input.exists())
        {
            input.createNewFile();
        }

        FileWriter fw = new FileWriter(input);
        BufferedWriter bw = new BufferedWriter(fw);

        for(int r = 0; r < geo.length; r++)
        {
            String saveline = "";
            for(int c = 0; c < geo[r].length; c++)
            {
                saveline = saveline + Integer.toString(geo[r][c]) + 'x';
            }
            bw.write(saveline);
            bw.newLine();
        }

        bw.close();
        fw.close();
    }

    public void loadGeo(String s) throws IOException
    {
        File input = new File(s + "/geo.dat");

        FileReader fr = new FileReader(input);
        BufferedReader br = new BufferedReader(fr);


        for(int r = 0; r < geo.length; r++)
        {
            String loadLine = br.readLine();
            char[] ch = loadLine.toCharArray();
            int x = 0;
            for(int c = 0; c < geo[r].length; c++)
            {
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
                geo[r][c] = Integer.parseInt(text);
            }
        }

        br.close();
        fr.close();
    }
    
    
    public int getGeo(int r, int c)
    {
    	return geo[r][c];
    }
	
}
