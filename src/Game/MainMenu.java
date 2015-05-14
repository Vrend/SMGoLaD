package Game;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.*;

public class MainMenu extends BasicGameState 
{

	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException 
	{
		Music music = new Music("TRains.ogg");
		music.loop();
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException 
	{

	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException 
	{
		
	}

	@Override
	public int getID() 
	{
		return 0;
	}

}
