package Game;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;



public class MapGen extends BasicGameState
{
	private static int size;
	private static int width, height;
	
	boolean climateShowing;
	
	static LandMap land;
    static ClimateMap climate;
	
	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException 
	{
		size = 5;
		width = 800;
		height = 600;
		land = new LandMap(width, height, size);
        climate = new ClimateMap(width, height, size);
        climateShowing = false;
		generateMap();
	}
	
	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException 
	{
		if(climateShowing)
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
			generateMap();
		}
		if(input.isKeyPressed(Input.KEY_Q))
		{
			game.enterState(0);
		}
		
		if(input.isKeyPressed(Input.KEY_ENTER))
		{
			game.enterState(2);
		}
		
		if(input.isKeyPressed(Input.KEY_L))
		{
			game.enterState(3);
		}
		
		if(input.isKeyPressed(Input.KEY_C))
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
