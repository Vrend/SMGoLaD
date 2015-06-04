package Game;

import org.newdawn.slick.*;
import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import java.awt.*;
import java.util.ArrayList;

public class SetupState extends BasicGameState
{
    private int playerCount;
    private static Player[] players;
    private ArrayList<String> names = new ArrayList<String>();

    public int getID()
    {
        return 5;
    }


    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException
    {
        playerCount = 0;
    }


    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics g) throws SlickException
    {
        String intro = "How many players will be in this game? Press a number between 2 and 6";

        g.setColor(Color.black);
        g.fillRect(0, 0, 800, 600);
        g.setColor(Color.white);
        g.drawString(intro, 125, 100);
        g.drawString("Press Enter to continue", 300, 500);
        Font font = new TrueTypeFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 24), false);
        g.setFont(font);
        g.drawString("Players: "+playerCount, 350, 400);
    }


    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int delta) throws SlickException
    {
        Input input = gameContainer.getInput();

        if(input.isKeyPressed(Input.KEY_2))
        {
            playerCount = 2;
        }
        if(input.isKeyPressed(Input.KEY_3))
        {
            playerCount = 3;
        }
        if(input.isKeyPressed(Input.KEY_4))
        {
            playerCount = 4;
        }
        if(input.isKeyPressed(Input.KEY_5))
        {
            playerCount = 5;
        }
        if(input.isKeyPressed(Input.KEY_6))
        {
            playerCount = 6;
        }

       /* if(input.isKeyPressed(Input.KEY_E) && playerCount > 0)
        {
            players = new Player[playerCount];
            fillPlayers(players);
            System.out.println("Edit the map");
        }*/

        if(input.isKeyPressed(Input.KEY_ENTER) && playerCount > 0)
        {
            players = new Player[playerCount];
            fillPlayers(players);
            stateBasedGame.enterState(6, new FadeOutTransition(), new FadeInTransition());
        }
    }

    private void fillPlayers(Player[] p)
    {
        names.add("Hobar");
        names.add("Barn");
        names.add("Yundar");
        names.add("Derperyn");
        names.add("Jun Snew");
        names.add("Tyrone");

        for(int x = 0; x < p.length; x++)
        {
            int index = (int) (Math.random() * names.size());
            p[x] = new Player(names.get(index));
            names.remove(index);
        }
    }

    public static Player[] getPlayers()
    {
        return players;
    }

}
