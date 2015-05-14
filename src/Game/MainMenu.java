package Game;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.*;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

public class MainMenu extends BasicGameState 
{
	static Music music;

	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException 
	{
		music = new Music("Rains.wav");
		music.loop();
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException 
	{
		//Only temporary until real menu is made
		
		g.setColor(Color.red);
		g.drawString("Sid Meier's Game of Lords and Dragons", 245, 30);

		g.setColor(Color.white);
		g.drawString("Press ENTER to continue", 300, 450);
		
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException 
	{
		Input input = container.getInput();
		
		if(input.isKeyPressed(Input.KEY_Q))
		{
            music.fade(400, 0.1f, true);
            game.enterState(4, new FadeOutTransition(), new FadeInTransition());
		}
		
		//Only temporary so it can access load menu
		if(input.isKeyPressed(Input.KEY_ENTER))
		{
			game.enterState(3);
			
		}

        if(music.getVolume() < 1f)
        {
            music.setVolume(1f);
        }
	}

	@Override
	public int getID() 
	{
		return 0;
	}
	
	public void leave(GameContainer container, StateBasedGame game) throws SlickException
	{
		super.leave(container, game);
		music.fade(10000, 0.01f, false);
	}
	
	public void enter(GameContainer container, StateBasedGame game) throws SlickException
	{
		super.enter(container, game);
		music.setVolume(1f);
	}

}
