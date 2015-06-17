package Game;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

//State for closing animation
public class CloseState extends BasicGameState
{
    //id for main class/entering
    public int getID()
    {
        return 4;
    }

    //doesnt do anything
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException
    {

    }

    //just a black screen
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics g) throws SlickException
    {
        g.setColor(Color.black);
        g.fillRect(0, 0, 800, 600);
    }

    //when the music gets low, the game closes
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException
    {
        if(MainMenu.music.getVolume() < 0.1f)
        {
            //close command
            gameContainer.exit();
        }
    }
}
