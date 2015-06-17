package Game;


import java.io.File;
import java.io.IOException;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.state.*;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

//state responsible for saving
public class SaveState extends BasicGameState 
{
    //textfield for typing save name
	TextField field;

    //hover, press for button
	boolean hover;
	boolean pressed;

    //ready to move state
	boolean move;

    //coords for mouse
	int xpos, ypos;

	public void init(GameContainer container, StateBasedGame game) throws SlickException 
	{
        //initiates the text field and colors and variables
		Font font = new TrueTypeFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 14), false);
		field = new TextField(container, font, 260, 150, 300, 50);
		field.setBackgroundColor(new Color(Color.white));
		field.setBorderColor(Color.gray);
		field.setTextColor(new Color(Color.black));
		hover = false;
		pressed = false;
		move = false;
	}

	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException 
	{
        //draws the screen with the text box and save "button'
		g.setColor(Color.blue);
		g.fillRect(0, 0, 800, 600);
		g.setColor(Color.white);
		g.drawString("What is the name of your save?", 280, 120);
		field.render(container, g);
		g.setColor(Color.gray);
		g.fillRect(383, 203, 54, 24);

        //changes 'button' color when variables activated
		if(pressed)
		{
			g.setColor(new Color(28, 28, 28));
		}
		else if(hover)
		{
			g.setColor(new Color(150, 150, 150));
		}
		else
		{
			g.setColor(new Color(79, 79, 79));
		}
		g.fillRect(385, 205, 50, 20);
		g.setColor(Color.white);
		g.drawString("Save", 390, 205);
	}

	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException 
	{
         //get input
		 Input input = container.getInput();

         //get mouse coords
		 xpos = input.getMouseX();
		 ypos = input.getMouseY();

         //if hovering over pixel range, its hovering
		 if(xpos > 385 && xpos < 435 && ypos > 204 && ypos < 224)
		 {
			 hover = true;
		 }
		 else
		 {
			 hover = false;
		 }

         //if hovering and left mouse button pressed, its pressing
		 if(input.isMousePressed(Input.MOUSE_LEFT_BUTTON) && hover)
		 {
			 pressed = true;
             //run prep
			 prep();
			 
		 }
		 else
		 {
			 pressed = false;
		 }

         //go back to mapgen if q is pressed
		 if(input.isKeyPressed(Input.KEY_Q))
		 {
			 game.enterState(1, new FadeOutTransition(), new FadeInTransition());
		 }

         //if ready to move, go back to mapgen
		 if(move)
		 {
			 move = false;
			 game.enterState(1, new FadeOutTransition(), new FadeInTransition());
		 }
		
	}

    //takes in save name
	public static void save(String saveName) 
	{
        //creates save folder
		File savefolder = new File("saves");

		
		if(!savefolder.exists())
		{
			savefolder.mkdir();
		}

        //creates save directory
		String s = "saves/" + saveName;
		File directory = new File(s);
		if(!directory.exists())
		{
			directory.mkdir();
		}
        //adds map folder
        s += "/map";
        //creates new folder
        File mapFolder = new File(s);
        if(!mapFolder.exists())
        {
            mapFolder.mkdir();
        }

        //saves each map
		try 
		{
			MapGen.land.saveLandMap(s);
			MapGen.climate.saveClimate(s);
            MapGen.geo.saveGeo(s);
            MapGen.res.saveResMap(s);
		} 
		catch (IOException e) 
		{
            //if problem, print error
			System.out.println("Error saving!");
		}
	}
	
	//prepares to move back to the mapgen
	public void prep()
	{
        //removes spaces
		String val = field.getText().trim();
        //if not empty
		if(val != null)
		{
			val = trimName(val);
            //if within size limit
			if(val.length() > 3 && val.length() < 21)
			{
                //save and be ready to move
				save(val);
				move = true;
			}
		}
	}

    //unique state id
	public int getID() 
	{
		return 2;
	}

    //fixes naming conventions
	public String trimName(String s)
	{
        //final string
		String end = "";

		//turns input to char array
		char[] ch = s.toCharArray();

        //for each character, if it is a number or letter, add it to the final string, if its a space change it to an "_"
        //anything else is dropped
		for(int x = 0; x < ch.length; x++)
		{
			if(Character.isLetter(ch[x]) || Character.isDigit(ch[x]))
			{
				end += ch[x];
			}
			else if(Character.isSpaceChar(ch[x]))
			{
				end += "_";
			}
		}

        //return final string
		return end;
	}
	
	//when the state is entered, clear the textfield
	public void enter(GameContainer container, StateBasedGame game) throws SlickException
	{
		super.enter(container, game);
		field.setText("");
	}
	
	
}
