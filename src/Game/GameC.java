package Game;


import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class GameC extends StateBasedGame
{
	protected GameC(String name) 
	{
		super(name);
	}


	public void initStatesList(GameContainer gc) throws SlickException 
	{	
		addState(new MainMenu());
		addState(new MapGen());
		addState(new SaveState());
		addState(new LoadState());
        addState(new CloseState());
	}

	public static void main(String[] args)
	{
		try
		{
			AppGameContainer container = new AppGameContainer(new GameC("Sid Meier's Game of Lords and Dragons"));
			container.setDisplayMode(800, 600, false);
			container.setIcon("icon.png");
            container.setShowFPS(false);
			container.start();
		}
		catch(SlickException e)
		{
			System.out.print("You aint da MVP");
		}
	}
	
}