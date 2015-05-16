package Game;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

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
		for(int x = 0; x < 3; x++)
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
					if(!nextTo(tundra, r, c, 1))
					{
						geo[r][c] = 0;
					}
				}
			}
		}
		
		
	}
	
	public void desert(int l)
	{
		int row;
		int col;
		
		double curve = Math.random();
		
		if(curve == 0)
		{
			curve = .1;
		}
		
		for(int x = 0; x < l; x++)
		{	
			while(true)
			{
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
					}
				}
			}

			geo[row][col] = desert;
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
						if(val == 0)
						{
							val = .01;
						}
						
						if(MapGen.climate.getClimate(r, c) > 70)
						{
							val += .2;
						}
						else if(MapGen.climate.getClimate(r, c) <= 40)
						{
							val -= .3;
						}
						
						if(val >= curve && nextTo(desert, r, c, 1) && !nextTo(beach, r, c, 1) && !nextTo(tundra, r, c, 1))
						{
							geo[r][c] = desert;
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
	
	
	public void drawGeo(Graphics g)
	{
		for(int r = 0; r < geo.length; r++)
		{
			for(int c = 0; c < geo[r].length; c++)
			{
				if(geo[r][c] == ocean && MapGen.land.getElement(r, c) == 0)
				{
					g.setColor(new Color(0, 0, 97));
					g.fillRect(r*5, c*5, 5, 5);
					g.setColor(Color.gray);
					g.drawRect(r*5, c*5, 5, 5);
				}
				
				else
				{
					switch(geo[r][c])
					{
						case beach: g.setColor(new Color(250, 241, 177));
						break;
						
						case ice: g.setColor(new Color(10, 36, 252));
						break;
						
						case tundra: g.setColor(new Color(84, 84, 84));
						break;
						
						case desert: g.setColor(new Color(209, 209, 0));
						break;
						
						default: g.setColor(Color.red);
						break;
					}
					
					g.fillRect(r*5, c*5, 5, 5);
					
					g.setColor(Color.black);
					g.drawRect(r*5, c*5, 5, 5);
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
	
}
