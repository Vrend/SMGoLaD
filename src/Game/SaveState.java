package Game;


import java.io.File;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.state.*;

public class SaveState extends BasicGameState 
{
	TextField field;
	boolean hover;
	boolean pressed;
	boolean move;
	
	int xpos, ypos;
	
	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException 
	{
		Font font = new TrueTypeFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 14), false);
		field = new TextField(container, font, 260, 150, 300, 50);
		field.setBackgroundColor(new Color(Color.white));
		field.setBorderColor(Color.gray);
		field.setTextColor(new Color(Color.black));
		hover = false;
		pressed = false;
		move = false;
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException 
	{
		g.setColor(Color.blue);
		g.fillRect(0, 0, 800, 600);
		g.setColor(Color.white);
		g.drawString("What is the name of your save?", 280, 120);
		field.render(container, g);
		g.setColor(Color.gray);
		g.fillRect(383, 203, 54, 24);
		
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

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException 
	{
		 Input input = container.getInput();
		 xpos = input.getMouseX();
		 ypos = input.getMouseY();
		 
		 if(xpos > 385 && xpos < 435 && ypos > 204 && ypos < 224)
		 {
			 hover = true;
		 }
		 else
		 {
			 hover = false;
		 }
		 
		 if(input.isMousePressed(Input.MOUSE_LEFT_BUTTON) && hover)
		 {
			 pressed = true;
			 prep();
			 
		 }
		 else
		 {
			 pressed = false;
		 }
		 
		 if(input.isKeyPressed(Input.KEY_Q))
		 {
			 game.enterState(0);
		 }
		 
		 if(move)
		 {
			 move = false;
			 game.enterState(0);
		 }
		
	}
	
	public static void save(String saveName) 
	{	
		File savefolder = new File("saves");
		
		if(!savefolder.exists())
		{
			savefolder.mkdir();
			System.out.println("PROBLEM");
		}
		
		String s = "saves/" + saveName;
		File directory = new File(s);
		if(!directory.exists())
		{
			directory.mkdir();
		}

		MapGen.land.saveLandMap(s);
	}
	
	
	public void prep()
	{
		String val = field.getText().trim();
		if(val != null)
		{
			val = trimName(val);
			if(val.length() > 3 && val.length() < 21)
			{
				save(val);
				move = true;
			}
		}
	}
	
	@Override
	public int getID() 
	{
		return 2;
	}

	public String trimName(String s)
	{
		String end = "";
		
		char[] ch = s.toCharArray();
		
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
		
		return end;
	}
	
	
}
