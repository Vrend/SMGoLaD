package Game;

import java.awt.Font;
import java.io.*;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.state.*;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

//The state responsible for loading a specific save
public class LoadState extends BasicGameState 
{
    //grabs the save folder
	File dir = new File("saves");
    //creates string array for each save
	String[] saves;
    //xpos and ypos of mouse
	private int xpos, ypos;
    //arrays for hovering or pressing a specific region
	boolean[] hover;
	boolean[] pressed;
    //delete mode
	boolean delete;

    //creates saves, hover, and pressed arrays, then makes saves directory if it has to
	public void init(GameContainer container, StateBasedGame game) throws SlickException 
	{
		saves = new String[8];
		hover = new boolean[8];
		pressed = new boolean[8];
		for(int i = 0; i < hover.length; i++)
		{
			hover[i] = false;
		}
		for(int c = 0; c < pressed.length; c++)
		{
			pressed[c] = false;
		}
		delete = false;
		
		if(!dir.exists())
		{
			dir.mkdir();
		}
	}

    //when it enters the state
	public void enter(GameContainer container, StateBasedGame game) throws SlickException
	{
		super.enter(container, game);
        //creates directory list using saves array
		saves = dir.list();
        //if a hidden file, dont show it
        for(int i = 0; i < saves.length; i++)
        {
        		if(saves[i].indexOf(".") != -1)
                {
                    saves[i] = null;
                }
        }
        //delete mode is not on
        delete = false;
	}

    //draws everything
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException 
	{
        //draws the save menu stuff. Changes color of "buttons" when hovering and pressing
		g.drawString("Choose a save to load", 150, 75);
		g.setColor(new Color(79, 77, 79));
		g.fillRect(90, 50, 600, 500);
		g.setFont(new TrueTypeFont(new java.awt.Font("Arial", Font.BOLD, 28), false));
		for(int x = 0; x < 8; x++)
		{
			if(pressed[x])
			{
				g.setColor(new Color(43, 43, 43));
			}
			
			else if(hover[x] && delete)
			{
				g.setColor(new Color(133, 0, 0));
			}
			
			else if(hover[x])
			{
				g.setColor(new Color(230, 230, 230));
			}
			else
			{
				g.setColor(Color.gray);
			}
			g.fillRect(250, 120 + (x*50), 300, 40);
			g.setColor(Color.white);
			try
			{
				g.drawString(saves[x], 252, 121 + (x*50));
			}
			catch(Exception e)
			{
				g.drawString("None", 252, 121 + (x*50));
			}
		}
		//changes text based on delete mode
		if(!delete)
		{
			g.drawString("Choose a save to load", 257, 70);
		}
		else
		{
			g.setColor(new Color(140, 0, 0));
			g.drawString("Delete mode", 310, 70);
		}
		
	}

	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException 
	{
        //grabs input
		Input input = container.getInput();

        //go to main menu if q is pressed
		if(input.isKeyPressed(Input.KEY_Q))
		{
			game.enterState(0, new FadeOutTransition(), new FadeInTransition());
		}

        //go to map gen if enter is pressed
		if(input.isKeyPressed(Input.KEY_ENTER))
		{
			game.enterState(1, new FadeOutTransition(), new FadeInTransition());
		}
		//enter delete mode/exit it
		if(input.isKeyPressed(Input.KEY_X))
		{
			delete = !delete;
		}
		//grabs xpositions
		xpos = input.getMouseX();
		ypos = input.getAbsoluteMouseY();
        //checks if hovering
		mcollision();

        //checks if "button" is being pressed, and loads map
		try
		{
			mpressed(input, game);
		}
		catch(Exception e)
		{
		}
	}

    //actual load function
	public static void load(String saveName)
	{
        //grabs the save name and creates the correct directory
		saveName = "saves/"+ saveName + "/map";

        //loads each type of map
		try 
		{
			MapGen.land.loadLandMap(saveName);
			MapGen.climate.loadClimate(saveName);
            MapGen.geo.loadGeo(saveName);
            MapGen.res.loadResMap(saveName);
		} 
		catch (IOException e) 
		{
			System.out.println("ERROR WITH LOADING");
		}
	}
	
    //checks for hovering based on mouse position coordinates
	public void mcollision()
	{
		if(xpos > 250 && xpos < 550)
		{
			if(ypos > 120 && ypos < 160)
			{
				hover[0] = true;
			}
			else
			{
				hover[0] = false;
			}
			
			if(ypos > 170 && ypos < 210)
			{
				hover[1] = true;
			}
			else
			{
				hover[1] = false;
			}
			
			if(ypos > 220 && ypos < 260)
			{
				hover[2] = true;
			}
			else
			{
				hover[2] = false;
			}
			
			if(ypos > 270 && ypos < 310)
			{
				hover[3] = true;
			}
			else
			{
				hover[3] = false;
			}
			
			if(ypos > 320 && ypos < 360)
			{
				hover[4] = true;
			}
			else
			{
				hover[4] = false;
			}
			
			if(ypos > 370 && ypos < 410)
			{
				hover[5] = true;
			}
			else
			{
				hover[5] = false;
			}
			
			if(ypos > 420 && ypos < 460)
			{
				hover[6] = true;
			}
			else
			{
				hover[6] = false;
			}
			
			if(ypos > 470 && ypos < 510)
			{
				hover[7] = true;
			}
			else
			{
				hover[7] = false;
			}
		}
		
		else
		{
			for(int i = 0; i < hover.length; i++)
			{
				hover[i] = false;
			}
		}
	}


    //checks if a button is being pressed while hovering, then loads the map if not 'None", or will delete file if in delete mode
	public void mpressed(Input input, StateBasedGame game) throws Exception
	{
		if(xpos > 250 && xpos < 550)
		{
            //first button
			if(ypos > 120 && ypos < 160)
			{
                //if hovering and mouse is pressed
				if(hover[0] && input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON))
				{
                    //it is being pressed
					pressed[0] = true;

                    //if delete mode active and not empty
					if(delete && saves[0] != null)
					{
						try
						{
                            //delete the save and refresh this
							delete(saves[0]);
							game.enterState(3);
						}
						catch(IOException e)
						{
							System.out.println("Error deleting save");
						}
					}

                    //or if save not empty, load the map and change to mapgen
					else if(saves[0] != null)
					{
						load(saves[0]);
						game.enterState(1, new FadeOutTransition(), new FadeInTransition());
                        MapGen.changeShowing();
					}
				}
                //or it just isnt being pressed
				else
				{
					pressed[0] = false;
				}
			}
			else
			{
				pressed[0] = false;
			}

            //SAME THING FOR EVERY BUTTON
			
			if(ypos > 170 && ypos < 210)
			{
				if(hover[1] && input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON))
				{
					pressed[1] = true;
					
					if(delete && saves[1] != null)
					{
						try
						{
							delete(saves[1]);
							game.enterState(3);
						}
						catch(IOException e)
						{
							System.out.println("Error deleting save");
						}
					}
					
					else if(saves[1] != null)
					{
						load(saves[1]);
						game.enterState(1, new FadeOutTransition(), new FadeInTransition());
                        MapGen.changeShowing();
					}
				}
				else
				{
					pressed[1] = false;
				}
			}
			else
			{
				pressed[1] = false;
			}
			
			if(ypos > 220 && ypos < 260)
			{
				if(hover[2] && input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON))
				{
					pressed[2] = true;
					
					if(delete && saves[2] != null)
					{
						try
						{
							delete(saves[2]);
							game.enterState(3);
						}
						catch(IOException e)
						{
							System.out.println("Error deleting save");
						}
					}
					
					else if(saves[2] != null)
					{
						load(saves[2]);
						game.enterState(1, new FadeOutTransition(), new FadeInTransition());
                        MapGen.changeShowing();
					}
					
				}
				else
				{
					pressed[2] = false;
				}
			}
			else
			{
				pressed[2] = false;
			}
			
			if(ypos > 270 && ypos < 310)
			{
				if(hover[3] && input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON))
				{
					pressed[3] = true;
					
					if(delete && saves[3] != null)
					{
						try
						{
							delete(saves[3]);
							game.enterState(3);
						}
						catch(IOException e)
						{
							System.out.println("Error deleting save");
						}
					}
					
					else if(saves[3] != null)
					{
						load(saves[3]);
						game.enterState(1, new FadeOutTransition(), new FadeInTransition());
                        MapGen.changeShowing();
					}
				}
				else
				{
					pressed[3] = false;
				}
			}
			else
			{
				pressed[3] = false;
			}
			
			if(ypos > 320 && ypos < 360)
			{
				if(hover[4] && input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON))
				{
					pressed[4] = true;
					
					if(delete && saves[4] != null)
					{
						try
						{
							delete(saves[4]);
							game.enterState(3);
						}
						catch(IOException e)
						{
							System.out.println("Error deleting save");
						}
					}
					
					else if(saves[4] != null)
					{
						load(saves[4]);
						game.enterState(1, new FadeOutTransition(), new FadeInTransition());
                        MapGen.changeShowing();
					}
				}
				else
				{
					pressed[4] = false;
				}
			}
			else
			{
				pressed[4] = false;
			}
			
			if(ypos > 370 && ypos < 410)
			{
				if(hover[5] && input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON))
				{
					pressed[5] = true;
					
					if(delete && saves[5] != null)
					{
						try
						{
							delete(saves[5]);
							game.enterState(3);
						}
						catch(IOException e)
						{
							System.out.println("Error deleting save");
						}
					}
					
					else if(saves[5] != null)
					{
						load(saves[5]);
						game.enterState(1, new FadeOutTransition(), new FadeInTransition());
                        MapGen.changeShowing();
					}
				}
				else
				{
					pressed[5] = false;
				}
			}
			else
			{
				pressed[5] = false;
			}
			
			if(ypos > 420 && ypos < 460)
			{
				if(hover[6] && input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON))
				{
					pressed[6] = true;
					
					if(delete && saves[6] != null)
					{
						try
						{
							delete(saves[6]);
							game.enterState(3);
						}
						catch(IOException e)
						{
							System.out.println("Error deleting save");
						}
					}
					
					else if(saves[6] != null)
					{
						load(saves[6]);
						game.enterState(1, new FadeOutTransition(), new FadeInTransition());
                        MapGen.changeShowing();
					}
				}
				else
				{
					pressed[6] = false;
				}
			}
			else
			{
				pressed[6] = false;
			}
			
			if(ypos > 470 && ypos < 510)
			{
				if(hover[7] && input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON))
				{
					pressed[7] = true;
					
					if(delete && saves[7] != null)
					{
						try
						{
							delete(saves[7]);
							game.enterState(3);
						}
						catch(IOException e)
						{
							System.out.println("Error deleting save");
						}
					}
					
					else if(saves[7] != null)
					{
						load(saves[7]);
						game.enterState(1, new FadeOutTransition(), new FadeInTransition());
                        MapGen.changeShowing();
					}
				}
				else
				{
					pressed[7] = false;
				}
			}
			else
			{
				pressed[7] = false;
			}
		}
		
		else
		{
			for(int i = 0; i < pressed.length; i++)
			{
				pressed[i] = false;
			}
		}
	}

    //unique id for main class and transitioning
	public int getID() 
	{
		return 3;
	}

    //deletes everything in a specified save folder
	public void delete(String saveName) throws IOException
	{
		File delmap = new File("saves/" + saveName);
        //checks if thing exists
		if(delmap.exists())
		{
            //creates a  file array
			File[] files = delmap.listFiles();
			
			for(File f : files)
			{
                //if its a directory
				if(f.isDirectory())
                {
                    //do the same thing and delete all files
                    File[] temp = f.listFiles();

                    for(File f2 : temp)
                    {
                        f2.delete();
                    }
                    //delete actual directory
                    f.delete();
                }
			}
		}
        //delete the folder itself
		delmap.delete();
	}

}
