package Game;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

//generates land map and loads it and stuff
public class LandMap
{

    //instance fields
	private int width, length;
	private int size;

	private int[][] map;

    //constructor
	public LandMap(int w, int l, int s)
	{
		width = w;
		length = l;
		size = s;
		map = new int[width/size][length/size];
	}

    //saves map
	public void saveLandMap(String s)
	{
        //creates map file
		File mapfile = new File(s + "/map.dat");

		if(!mapfile.exists())
		{
			try
			{
				mapfile.createNewFile();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}

		try
		{
            //creates writers
			FileWriter fw = new FileWriter(mapfile);
			BufferedWriter bw = new BufferedWriter(fw);

            //for each tile
			for(int r = 0; r < map.length; r++)
			{
				for(int c = 0; c < map[0].length; c++)
				{
                    //grabs value from array
					int num = map[r][c];
                    //turns it into either an "x" or "y"
					char character;
					if(num == 0)
						character = 'x';
					else
						character = 'y';
                    //writes it to file
					bw.write(character);
				}
                //new line
				bw.newLine();
			}
			//closes writers
			bw.close();
			fw.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

	}

	//actual geneation
	public void genLand(int num)
	{
        Random rand = new Random();

        //creates odds of land
       double swag = (.4 * Math.random() ) + .2;

        //clears board
		for(int r = 0; r < map.length; r++)
		{
			for(int c = 0; c < map[r].length; c++)
			{
				map[r][c] = 0;
			}
		}

        //creates number of times to loop over for creation of land
        int randomNum = rand.nextInt(11);
        randomNum += 5;

        //how many continents, grabs random tile to start
		for(int x = 0; x < num; x++)
		{
			map[(int)(Math.random() * map.length)][(int)(Math.random() * map[0].length)] = 1;
		}

        //loops over randomNum times
		for(int y = 0; y < randomNum; y++)
		{
            //for each tile
			for(int r = 0; r < map.length; r++)
			{
				for(int c = 0; c < map[r].length; c++)
				{
                    //if within bounds
					if(r > 0 && r < map.length-1 && c > 0 && c < map[0].length-1)
					{
                        //if next to land fill it in if higher than the curve
						if(map[r-1][c] == 1 || map[r+1][c] == 1 || map[r][c-1] == 1 || map[r][c+1] == 1 || map[r-1][c-1] == 1 || map[r+1][c+1] == 1 || map[r-1][c+1] == 1 || map[r+1][c-1] == 1)
						{

							if(Math.random() <= swag)
							{
								map[r][c] = 1;
							}
						}
					}
				}
			}
		}

		//go through each tile
		for(int r = 0; r < map.length; r++)
		{
			for(int c = 0; c < map[r].length; c++)
			{
                //if within bounds, count out surrounding tiles, if next to 4 land or more, make it a land tile
				if(r > 0 && r < map.length-1 && c > 0 && c < map[0].length-1)
				{
					int count = 0;

					if(map[r-1][c] == 1)
					{
						count++;
					}

					if(map[r+1][c] == 1)
					{
						count++;
					}

					if(map[r][c-1] == 1)
					{
						count++;
					}

					if(map[r][c+1] == 1)
					{
						count++;
					}

					if(map[r+1][c+1] == 1)
					{
						count++;
					}

					if(map[r-1][c-1] == 1)
					{
						count++;
					}

					if(map[r-1][c+1] == 1)
					{
						count++;
					}

					if(map[r+1][c-1] == 1)
					{
						count++;
					}

					if(count > 4)
						map[r][c] = 1;
				}
			}
		}

        //same thing, except it has to be next to more than 6
		for(int r = 0; r < map.length; r++)
		{
			for(int c = 0; c < map[r].length; c++)
			{
				if(r > 0 && r < map.length-1 && c > 0 && c < map[0].length-1)
				{
					int count = 0;

					if(map[r-1][c] == 1)
					{
						count++;
					}

					if(map[r+1][c] == 1)
					{
						count++;
					}

					if(map[r][c-1] == 1)
					{
						count++;
					}

					if(map[r][c+1] == 1)
					{
						count++;
					}

					if(map[r+1][c+1] == 1)
					{
						count++;
					}

					if(map[r-1][c-1] == 1)
					{
						count++;
					}

					if(map[r-1][c+1] == 1)
					{
						count++;
					}

					if(map[r+1][c-1] == 1)
					{
						count++;
					}

					if(count > 6)
						map[r][c] = 1;

				}
			}
		}


	}

    //draws land, if ocean make it blue, if not make it white
	public void drawLandMap(Graphics g)
	{
		for(int r = 0; r < map.length; r++)
		{
			for(int c = 0; c < map[r].length; c++)
			{
				g.setColor(Color.blue);
				g.fillRect(r*5, c*5, 5, 5);

				if(map[r][c] == 1)
				{
					g.setColor(Color.white);
					g.fillRect(r*5, c*5, 5, 5);
				}
			}
		}
	}

    //loads the land
	public void loadLandMap(String saveName)
	{
        //creates temperary array
		int[][] temp = new int[width/size][length/size];

        //finds map file
		File save = new File(saveName + "/map.dat");
		//if the save exists
		if(save.exists())
		{
			try
			{
                //create readers
				FileReader fr = new FileReader(save);
				BufferedReader br = new BufferedReader(fr);
				//keeps track of row
				int count = 0;

                //grabs line in file
				String s = br.readLine();

				while(s != null)
				{
                    //turns it into a char array
					char[] c = s.toCharArray();

                    //for each thing
					for(int x = 0; x < c.length; x++)
					{
                        //if an 'x' it is ocean, otherwise it is land
						if(c[x] == 'x')
							temp[count][x] = 0;
						else
							temp[count][x] = 1;
					}
                    //increment array
					count++;
                    //read the next line
					s = br.readLine();
				}

                //close readers
				br.close();
				fr.close();
                //assigns map to the temp array
				map = temp;
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
		}

		else
		{
            //if a problem, it will say error loading
			System.out.println("Error Loading");
		}
	}

	//gets element value at r, c
	public int getElement(int r, int c)
	{
		return map[r][c];
	}

    //checks if tile is on the coast, returns true if it is, false if not
	public boolean onCoast(int r, int c)
	{
		if(r > 0)
		{
			if(map[r-1][c] == 0)
			{
				return true;
			}
		}

		if(c > 0)
		{
			if(map[r][c-1] == 0)
			{
				return true;
			}
		}

		if(r < (map.length-1))
		{
			if(map[r+1][c] == 0)
			{
				return true;
			}
		}

		if(c < (map[0].length-1))
		{
			if(map[r][c+1] == 0)
			{
				return true;
			}
		}

		return false;
	}
}