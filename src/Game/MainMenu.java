package Game;

import java.util.Random;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.*;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

public class MainMenu extends BasicGameState 
{
	static Music music;
	private Image[] images;
	private int anim = 0;
	private int count = 0;
    private int gifInterpolation;
    String os;

	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException 
	{
		Random rand = new Random();
		int num = rand.nextInt(4);

		music = new Music("res/sounds/Rains.wav");
		music.loop();
		
		switch(num)
		{
		case 0: images = fill("joffrey", 252);
		break;
		
		case 1: images = fill("oberyn", 292);
		break;
		
		case 2: images = fill("ned", 262);
		break;
		
		case 3: images = fill("fire", 181);
		}

        os = System.getProperty("os.name");


        if(os.contains("Mac"))
        {
            gifInterpolation = 25;
        }
        else if(os.contains("Windows"))
        {
            gifInterpolation = 100;
        }

	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException 
	{
		
		g.drawImage(images[anim], 170, 60);
		
		g.setColor(Color.red);
		g.drawString("Sid Meier's Game of Lords and Dragons", 245, 30);

		g.setColor(Color.white);
		g.drawString("Press ENTER to continue", 300, 450);
		
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException 
	{
		count++;
		
		if(count % gifInterpolation == 0)
		{
			anim++;
		}
		
		if(anim > images.length-1)
		{
			anim = 0;
		}
		
		Input input = container.getInput();
		
		if(input.isKeyPressed(Input.KEY_Q))
		{
            music.fade(400, 0.01f, true);
            game.enterState(4, new FadeOutTransition(), new FadeInTransition());
		}
		
		if(input.isKeyPressed(Input.KEY_ENTER))
		{
			game.enterState(3, new FadeOutTransition(), new FadeInTransition());
			
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

	public Image[] fill(String name, int l) throws SlickException
	{
		Image[] img = new Image[l];
		
		int x = 1;
		int y = 0;
		int z = 0;
		
		for(int i = 0; i < img.length; i++)
		{
			img[i] = new Image("res/gifs/" + name + "/" + name + "_frame_0" + Integer.toString(z) + Integer.toString(y) + Integer.toString(x) + ".png");
			x++;
			
			if(x > 9)
			{
				x = 0;
				y++;
			}
			if(y > 9)
			{
				y = 0;
				z++;
			}
		}
		
		return img;
	}
	
	
}
