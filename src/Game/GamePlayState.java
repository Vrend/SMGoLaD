package Game;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import java.util.Set;


public class GamePlayState extends BasicGameState
{
    private Player[] players;


    public int getID()
    {
        return 6;
    }


    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException
    {
        players = SetupState.getPlayers();
    }


    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics g) throws SlickException
    {

    }


    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int delta) throws SlickException
    {

    }
}
