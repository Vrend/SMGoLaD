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

public class LandMap
{
	private int width, length;
	private int size;
	
	private int[][] map;
	
	public LandMap(int w, int l, int s)
	{
		width = w;
		length = l;
		size = s;
		map = new int[width/size][length/size];
	}
	
	public void saveLandMap(String s)
	{	
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
			FileWriter fw = new FileWriter(mapfile);
			BufferedWriter bw = new BufferedWriter(fw);
			
			for(int r = 0; r < map.length; r++)
			{
				for(int c = 0; c < map[0].length; c++)
				{
					int num = map[r][c];
					char character;
					if(num == 0)
						character = 'x';
					else
						character = 'y';
					bw.write(character);
				}
				bw.newLine();
			}
			
			bw.close();
			fw.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}	
	
		
	public void genLand(int num)
	{	
		for(int r = 0; r < map.length; r++)
		{
			for(int c = 0; c < map[r].length; c++)
			{
				map[r][c] = 0;
			}
		}

        Random rand = new Random();
        int randomNum = rand.nextInt(11);
        randomNum += 5;
		
		
		for(int x = 0; x < num; x++)
		{
			map[(int)(Math.random() * map.length)][(int)(Math.random() * map[0].length)] = 1;
		}
		
		for(int y = 0; y < randomNum; y++)
		{
			for(int r = 0; r < map.length; r++)
			{
				for(int c = 0; c < map[r].length; c++)
				{
					if(r > 0 && r < map.length-1 && c > 0 && c < map[0].length-1)
					{
						if(map[r-1][c] == 1 || map[r+1][c] == 1 || map[r][c-1] == 1 || map[r][c+1] == 1 || map[r-1][c-1] == 1 || map[r+1][c+1] == 1 || map[r-1][c+1] == 1 || map[r+1][c-1] == 1)
						{
							if(Math.random() <= .33)
							{
								map[r][c] = 1;
							}
						}
					}
				}
			}	
		}
		
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
					
					if(count > 4)
						map[r][c] = 1;
					
				}
			}
		}	
		
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
	
	public void loadLandMap(String saveName)
	{
		int[][] temp = new int[width/size][length/size];
		
		File save = new File(saveName + "/map.dat");
		
		if(save.exists())
		{
			try
			{
				FileReader fr = new FileReader(save);
				BufferedReader br = new BufferedReader(fr);
				
				int count = 0;
				
				String s = br.readLine();
				
				while(s != null)
				{
					char[] c = s.toCharArray();
					
					for(int x = 0; x < c.length; x++)
					{
						if(c[x] == 'x')
							temp[count][x] = 0;
						else
							temp[count][x] = 1;
					}
					count++;
					s = br.readLine();
				}
				
				br.close();
				fr.close();
				map = temp;
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
		}
		
		else
		{
			System.out.println("Error Loading");
		}
	}
	
	
	public int getElement(int r, int c)
	{
		return map[r][c];
	}
	
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