package Game;

import org.newdawn.slick.*;
import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

//State responsible for setting the players
public class SetupState extends BasicGameState
{
    //instance fields
    private int playerCount;
    private static Player[] players;
    private ArrayList<String> names = new ArrayList<String>();

    //unique state id
    public int getID()
    {
        return 5;
    }

    //initializes the player count
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException
    {
        playerCount = 0;
    }

    //when entered, show no info
    public void enter(GameContainer gc, StateBasedGame game) throws SlickException
    {
        super.enter(gc, game);
        GameC.showInfo(false, new JLabel());
        GameC.isInfoEnabled();
    }

    //display text and how many players will be in game using new font
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
        //grab input
        Input input = gameContainer.getInput();

        //set the player count based on key [ressed
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

        //if the players have been set and enter pressed
        if(input.isKeyPressed(Input.KEY_ENTER) && playerCount > 0)
        {
            //create player array
            players = new Player[playerCount];
            //fill in the array
            fillPlayers(players);
            //show general button and change states to the game
            GameC.showGeneral(true);
            stateBasedGame.enterState(6, new FadeOutTransition(), new FadeInTransition());
        }

        //if q is pressed go back to mapgen
        if(input.isKeyPressed(Input.KEY_Q))
        {
            stateBasedGame.enterState(1, new FadeOutTransition(), new FadeInTransition());
            GameC.mapHelp(true);
        }

    }

    //gives players random names
    private void fillPlayers(Player[] p)
    {
        //name list
        names.add("Hobar");
        names.add("Barn");
        names.add("Yundar");
        names.add("Derperyn");
        names.add("Jun Snew");
        names.add("Tyrone");

        //for each player
        for(int x = 0; x < p.length; x++)
        {
            //get a random name
            int index = (int) (Math.random() * names.size());
            //create new player with the name
            p[x] = new Player(names.get(index));
            //remove name
            names.remove(index);
        }
    }

    //returns player array
    public static Player[] getPlayers()
    {
        return players;
    }

}