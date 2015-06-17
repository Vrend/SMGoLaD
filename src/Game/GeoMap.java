package Game;

//boring imports
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import java.io.*;
import java.util.Random;

//Geographic map
public class GeoMap 
{
    //required instance fields
	private int width, height, size;
	
	private int[][] geo;

    //easy constants for each terrain type
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

    //constructor, sets tile sizes and creates int array
	public GeoMap(int w, int h, int s)
	{
		width = w;
		height = h;
		size = s;
		geo = new int[width/size][height/size];
	}

    //generates geo features
	public void genGeo()
	{
		try
		{
            //clears map
			clear();
			//generates oceans
			oceans();
			//generates ice
			ice();
			//finds and labels coastline as beach
			coast();
			//generates tundra
			tundra();	
			//creates deserts
			desert((int) ((Math.random() * 5) + 1));
			//creates the mountains
            mountainGen();
            //adds hills
            hillGen();
            //generates rainforest
            rainforestGen();
            //adds in temperate forests
            tempForestGen();
            //fills in remaining tiles with hills, temp forest, plains, and grassland
            fillIn();
		}
		catch(Exception e)
		{
            //if there in an io error regenrate the map
			System.out.println("Error generating!");
			System.out.println("Regenerating map....");
			genGeo();
		}
	}

    //gens coast

    //if next to water, it is now beach
	public void coast()
	{
		for(int r = 0; r < geo.length; r++)
		{
			for(int c = 0; c < geo[0].length; c++)
			{
				float temp = MapGen.climate.getClimate(r, c);

                //lowers temp by -10
				if(MapGen.land.onCoast(r, c) && MapGen.land.getElement(r, c) == 1)
				{
					temp -= 10;
					MapGen.climate.setClimate(r, c, temp);
					geo[r][c] = beach;
				}
			}
		}
	}

	//water, if water make it ocean
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
	
	//if cold enough, chance of becoming ice -- the colder the more likely
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

        //loops check for lone ice and ocean tiles, fills them in
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

    //generates tundra, uses random map-wide variable for chance/curve -- the colder the easier it is to become tundra
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

        //fills in tiles that are next to tundra
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
		
		//eliminates sole tundra tiles
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

        //modifies climate by -10
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

    //desert
	public void desert(int l)
	{
		int row;
		int col;

        int limit = 3840;
        int check = 0;

		double curve = Math.random();

        //grabs random tiles on map for desert, one for l, the amount of deserts
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

        //runs 5 times, arbitrary number -- the hotter it is, the more likely it will become desert
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

                        //if next to desert and bigger than curve
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

        //fills in tiles extra desert tiles
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
		
		//gets rid of lone desert tiles and changes temp
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
					//increases temp by 20 degrees if possible
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

    //mountain creation
	public void mountainGen()
    {
        Random rand = new Random();
        //number of mountain chains
        int curve = rand.nextInt(4) + 1;

        int row, col;

        //grabs random mountain tiles
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

        //runs up to 4 times, adding mountains to each range
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

        //changes temperature by -20
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

    //creates hills, which appear next to mountains, with a 2/3 chance
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

        //lowers temp by -10
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

    //creates rainforest
    public void rainforestGen()
    {
        //up to 4 rainforests
        Random rand = new Random();
        int curve = rand.nextInt(5);

        //grabs random rainforest tiles within climate
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

        //runs up to 4 times, making the rainforests bigger -- has to be within climate and next to another rainforest and not desert
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

        //fills in more rainforest
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

    //normal forests
    public void tempForestGen()
    {
        Random rand = new Random();
        //up to 7 forests
        int curve = rand.nextInt(7) + 1;

        int count = 0;

        //only certain amount of tries for making forests -- grabs random tiles for generation
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

        //loops 6 times, grabs tiles nearby that are in climate and next to forests
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

        //fills in more forest tiles
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

    //general method to fill in the map
    public void fillIn()
    {
        //for each tile
        for(int r = 0; r < geo.length; r++)
        {
            for(int c = 0; c < geo[r].length; c++)
            {
                //if a land tile and empty
                if(MapGen.land.getElement(r, c) == 1 && geo[r][c] == 0)
                {
                    //could become buffer of rainforest and desert
                    if (nextTo(desert, r, c, 1) && nextTo(rainforest, r, c, 1))
                    {
                        geo[r][c] = plains;
                    }
                    //could become forest around rainforests
                    else if(nextTo(rainforest, r, c, 2) && geo[r][c] == 0)
                    {
                        geo[r][c] = temperateforest;
                    }
                    //could become a temperate forest with random luck
                    else if(nextTo(rainforest, r, c, 1))
                    {
                        if(Math.random() > .4)
                        {
                            geo[r][c] = temperateforest;
                        }
                    }
                    //has a chance of becoming plains or grassland if next to exactly 2 desert tiles
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
                    //if temp is not too cold become plains the warmer it is, or just become grassland
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
                    }
                    //if it's really cold, become forest, hill, or grassland
                    else if(MapGen.climate.getClimate(r, c) < 0)
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
                    //when all else fails, become grassland
                    else
                    {
                        geo[r][c] = grassland;
                    }
                }
            }
        }


        //removes solo plains and grasslands, fills them into their opposites
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

        //fills in some desert tiles and surrounds them with plains
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

                    //its too cold to be grassland, become a hill or forest
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

    //checks if tile is next to type
	public boolean nextTo(int type, int r, int c, int count)
	{
		int sum = 0;

        //adds one to sum if within bounds of map and tile to left, right, up, and down are the type denoted
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

        //if the sum of the tiles is greater or equal to the count given, its true
		if(sum >= count)
		{
			return true;
		}
		//or just false
		return false;
	}
	
	//draws geo map
	public void drawGeo(Graphics g)
    {
        for (int r = 0; r < geo.length; r++)
        {
            for (int c = 0; c < geo[r].length; c++)
            {
                //if a water tile do this
                if (geo[r][c] == ocean && MapGen.land.getElement(r, c) == 0)
                {
                    g.setColor(new Color(0, 0, 97));
                    g.fillRect(r * 5, c * 5, 5, 5);
                    g.setColor(Color.gray);
                    g.drawRect(r * 5, c * 5, 5, 5);
                }
                //or just choose the color that matches the tile
                else
                {
                    switch (geo[r][c])
                    {
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

                    //fill it in and give it a black outline
                    g.fillRect(r * 5, c * 5, 5, 5);

                    g.setColor(Color.black);
                    g.drawRect(r * 5, c * 5, 5, 5);
                }
            }
        }
    }
	
	//clears the board, setting it all to 0's
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

    //saves geo
    public void saveGeo(String s) throws IOException
    {
        //creates file
        File input = new File(s + "/geo.dat");

        if(!input.exists())
        {
            input.createNewFile();
        }

        //writers are created
        FileWriter fw = new FileWriter(input);
        BufferedWriter bw = new BufferedWriter(fw);

        //for each row
        for(int r = 0; r < geo.length; r++)
        {
            //create string
            String saveline = "";
            //for the column
            for(int c = 0; c < geo[r].length; c++)
            {
                //seperate each value with an "x"
                saveline = saveline + Integer.toString(geo[r][c]) + 'x';
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

    //loads geo
    public void loadGeo(String s) throws IOException
    {
        //grabs geo file
        File input = new File(s + "/geo.dat");

        //creates reader objects
        FileReader fr = new FileReader(input);
        BufferedReader br = new BufferedReader(fr);

        //for each row
        for(int r = 0; r < geo.length; r++)
        {
            //load the line from the file
            String loadLine = br.readLine();
            //turn it into a char array
            char[] ch = loadLine.toCharArray();
            //x is the array pointer/tracker
            int x = 0;
            for(int c = 0; c < geo[r].length; c++)
            {
                //creates string to grab values in between "x"
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
                //turns the value into a number
                geo[r][c] = Integer.parseInt(text);
            }
        }

        //closes readers
        br.close();
        fr.close();
    }

    //returns geo value
    public int getGeo(int r, int c)
    {
    	return geo[r][c];
    }
}