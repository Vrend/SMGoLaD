package Game;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;



public class MapGen extends BasicGameState
{
	private static int size;
	private static int width, height;
	
	boolean climateShowing;
	
	static LandMap land;
    static ClimateMap climate;
    
    //Necessary for showing non-loaded maps
    public static boolean showing;
	
	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException 
	{
		size = 5;
		width = 800;
		height = 600;
		land = new LandMap(width, height, size);
        climate = new ClimateMap(width, height, size);
        climateShowing = false;
        showing = false;
	}
	
	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException 
	{
		if(showing)
		{
			g.setColor(Color.blue);
			g.fillRect(0, 0, 800, 600);
			g.setColor(Color.white);
			g.drawString("Press R to generate a Map", 300, 200);
			g.drawString("Press C to view the climate", 300, 250);
			
		}
		else if(climateShowing)
		{
			climate.drawClimate(g);
		}
		else
		{
			land.drawLandMap(g);
		}
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException 
	{
		Input input = container.getInput();
		if(input.isKeyPressed(Input.KEY_R))
		{
			showing = false;
			generateMap();
		}
		if(input.isKeyPressed(Input.KEY_Q))
		{
			game.enterState(0, new FadeOutTransition(), new FadeInTransition());
		}
		
		if(input.isKeyPressed(Input.KEY_ENTER))
		{
			game.enterState(2, new FadeOutTransition(), new FadeInTransition());
		}
		
		if(input.isKeyPressed(Input.KEY_L))
		{
			game.enterState(3, new FadeOutTransition(), new FadeInTransition());
		}
		
		if(input.isKeyPressed(Input.KEY_C) && !showing)
		{
			climateShowing = !climateShowing;
		}
		
	}
	

	@Override
	public int getID() 
	{
		return 1;
	}
	
	
	public static void generateMap()
	{
		int contnum = (int) ((Math.random() * 4) + 1);
		land.genLand(contnum);
		climate.genClimate();
	}
	
	

}
