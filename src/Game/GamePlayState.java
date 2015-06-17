package Game;

import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import javax.swing.*;

//The actual game
public class GamePlayState extends BasicGameState implements MouseListener
{
    //array of players
    private static Player[] players;
    //who's turn is it?
    private int currentTurn;
    //turn count
    private int turnCount;
    //transition variable
    boolean trans;
    //is the player viewing screen
    boolean playerView;
    //which view/map
    int view;
    //current player
    private Player currentPlayer;
    //playermap, showing cities and land owned
    private PlayerMap p;
    //current selected tile
    private int[] selectedTile;
    //is a tile being selected
    private boolean selected;

    //id for transitioning and for main class
    public int getID()
    {
        return 6;
    }

    //initializes turn, turncount, transition, playerview, type of view, and creates selected tile array
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException
    {
        currentTurn = 0;
        turnCount = 1;
        trans = true;
        playerView = true;
        view = 0;
        selectedTile = new int[2];
        selected = false;
    }

    //when the state is entered
    public void enter(GameContainer container, StateBasedGame game) throws SlickException
    {
        super.enter(container, game);
        //grabs players
        players = SetupState.getPlayers();
        //creates playermap and generates
        p = new PlayerMap();
        p.generate(players.length, players);
        //adds a warrior to each player's capital city
        for(int x = 0; x < players.length; x++)
        {
            players[x].addUnit(new Warrior(0, 0), players[x].getCity(0));
        }
        //test, adds two archers to first player
        players[0].addUnit(new Archer(0, 0), players[0].getCity(0));
        players[0].addUnit(new Archer(0, 0), players[0].getCity(0));
        //sets players for info frame
        GameC.setPlayers(players.length, players);
        //enables info button
        GameC.isInfoEnabled();
    }


    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics g) throws SlickException {
        //if in turn transition
        if (trans) {
            drawTrans(g);
        }
        //no?, draw map
        else if (playerView) {
            //which map?
            switch (view) {
                //playermap
                case 0:
                    drawPlayerMap(g);
                    break;

                //climate
                case 1:
                    currentPlayer.getcMap().drawClimate(g);
                    break;

                //geographic
                case 2:
                    currentPlayer.getgMap().drawGeo(g);
                    break;

                //resource
                case 3:
                    currentPlayer.getrMap().drawRes(g);
                    break;

                //land
                default:
                    currentPlayer.getlMap().drawLandMap(g);
            }

            //draws selected square, a pink one
            if (selected) {
                g.setColor(new Color(255, 0, 208));
                g.fillRect(selectedTile[0] * 5, selectedTile[1] * 5, 5, 5);
            }
        }
    }

    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int delta) throws SlickException
    {
        //gets input
        Input input = gameContainer.getInput();

        //quit if q is pressed
        if(input.isKeyPressed(Input.KEY_Q))
        {
            //enters closing state
            stateBasedGame.enterState(4, new FadeOutTransition(), new FadeInTransition());      //Proper closing until saves created
        }

        //if trans, disable it and start a new turn
        if(input.isKeyPressed(Input.KEY_ENTER) && trans)
        {
            trans = !trans;
            Turn();
        }

        //if not in transition, enable trans when esc is pressed
        if(input.isKeyPressed(Input.KEY_ESCAPE) && !trans)
        {
            trans = !trans;
        }

        //show climate
        if(input.isKeyPressed(Input.KEY_C))
        {
            view = 1;
        }

        //show geography
        if(input.isKeyPressed(Input.KEY_G))
        {
            view = 2;
        }

        //show resources
        if(input.isKeyPressed(Input.KEY_R))
        {
            view = 3;
        }

        //show land
        if(input.isKeyPressed(Input.KEY_L))
        {
            view = 8;
        }

        //show players
        if(input.isKeyPressed(Input.KEY_P))
        {
            view = 0;
        }

        //selection for tiles, left mouse click
        if(input.isMousePressed(0))
        {
            boolean test = false;

            //grabs mouse coords
            int x = input.getMouseX();
            int y = input.getMouseY();
            //turns them into tile coords
            x = (x/5);
            y = (y/5);

            //grabs value
            int val = p.getElement(x, y);

            //if you select an already selected tile
            if(val >= 60)
            {
                //lower val to normal
                val -= 60;
                //don't show info
                test = true;
                //nothing is selected
                selected = false;
            }
            else
            {
                //otherwise, look for any other selected tile and disable it
                for(int r = 0; r < p.getlength(); r++)
                {
                    for(int c = 0; c < p.getclength(); c++)
                    {
                        int t = p.getElement(r, c);
                        if(t >= 60)
                        {
                            t -= 60;
                            p.setElement(r, c, t);
                        }
                    }
                }
                //add 60 to select tile
                val += 60;
                //there is a selected tile
                selected = true;
            }

            p.setElement(x, y, val);

            //tile coords are added
            selectedTile[0] = x;
            selectedTile[1] = y;

            //if not selected, dont show anything
            if(test)
            {
                GameC.showInfo(false, new JLabel());
            }
            //or show it from getInfo()
            else
            {
                GameC.showInfo(true, getInfo());
            }
        }
    }

    //standard turn
    public void Turn()
    {
        //raises the current persons turn
        currentTurn++;

        //if everyone has gone
        if(currentTurn >= players.length)
        {
            //turn number increases
            turnCount++;
            //resets turn
            currentTurn = 0;
            //checks for land growth
            landGrowth();
        }
        //sets current player
        currentPlayer = players[currentTurn];
    }

    //draws transition, black background saying who's turn it is
    private void drawTrans(Graphics g)
    {
        g.setColor(Color.black);
        g.fillRect(0, 0, 800, 600);
        g.setColor(Color.white);
        g.drawString("It's "+players[currentTurn].getName()+"'s turn!", 335, 200);
        g.drawString("Press Enter to Continue", 325, 400);
    }

    //method for drawing player map
    public void drawPlayerMap(Graphics g)
    {
        //for each tile
        for (int r = 0; r < p.getlength(); r++)
        {
            for (int c = 0; c < p.getclength(); c++)
            {
                //get value
                int z = p.getElement(r, c);

                //if ocean
                if(z == (players.length * 2 + 1))
                {
                    g.setColor(Color.blue);
                }
                //if land
                else if(z == (players.length * 2))
                {
                    g.setColor(Color.white);
                }
                //if a city tile
                else if(z < players.length)
                {
                    for(int x = 0; x < players.length; x++)
                    {
                        if(z == x)
                        {
                            //grabs correct color of player
                            g.setColor(players[x].getColor());
                            break;
                        }
                    }
                }
                //otherwise check until you find the right alpha color for the right player
                else
                {
                    for(int x = 0; x < players.length; x++)
                    {
                        if(z == (x + players.length))
                        {
                            g.setColor(players[x].getAlpha());
                            break;
                        }
                    }
                }
                //fill in tile
                g.fillRect(r*5, c*5, 5, 5);
                //draws grid
                g.setColor(Color.black);
                g.drawRect(r*5, c*5, 5, 5);
            }
        }
    }

    //sets what the info label will show
    public JLabel getInfo()
    {
        //creates new label, will use html formatting
        JLabel label = new JLabel();
        //string created
        String s = "<html>";
        //grabs climate, resource, and geo string
        String geo;
        String resource;
        //grabs temp from selected tile
        float f = currentPlayer.getcMap().getClimate(selectedTile[0], selectedTile[1]);
        String climate;
        //checks what it should be labeled based on temp
        if(f > 70)
        {
          climate = "Arid";
        }
        else if(f > 40)
        {
            climate = "Temperate";
        }
        else if(f > 20)
        {
            climate = "Cold";
        }
        else
        {
            climate = "Arctic";
        }

        //grabs geo feature
        int g = currentPlayer.getgMap().getGeo(selectedTile[0], selectedTile[1]);

        //converts to string
        switch(g)
        {
            case 1: geo = "Ocean";
                break;

            case 2: geo = "Plains";
                break;

            case 3: geo = "Beach";
                break;

            case 4: geo = "Grassland";
                break;

            case 5: geo = "Tundra";
                break;

            case 6: geo = "Mountain";
                break;

            case 7: geo = "Hill";
                break;

            case 8: geo = "Desert";
                break;

            case 9: geo = "Temperate Forest";
                break;

            case 10: geo = "Rain Forest";
                break;

            case 11: geo = "Ice";
                break;
            //random shrek reference
            default: geo = "Swamp";
                break;
        }

        //grabs resource val
        int r = currentPlayer.getrMap().getElement(selectedTile[0], selectedTile[1]);

        //converts to string
        switch(r)
        {
            case 1: resource = "Gold";
                break;

            case 2: resource = "Iron";
                break;

            case 3: resource = "Silver";
                break;

            case 4: resource = "Copper";
                break;

            case 5: resource = "Tin";
                break;

            case 6: resource = "Whales";
                break;

            case 7: resource = "Fish";
                break;

            case 8: resource = "Livestock";
                break;

            case 9: resource = "Horses";
                break;

            case 10: resource  = "Beasts";
                break;

            case 11: resource = "Coal";
                break;

            case 12: resource = "Bananas";
                break;

            case 13: resource = "Grove";
                break;

            case 14: resource = "Fresh Water";
                break;

            default: resource = "None";
        }

        //adds climate value, geo value, and resource value to string
        s += "Climate: " + climate + "<br><br><br>" + "Geography: " + geo + "<br><br><br>" + "Resource: " + resource;
        //vars for counting units
        int wc = 0;
        int ac = 0;
        int sc = 0;
        int pc = 0;
        //any units on tile?
        boolean isthere = false;

        //for each player...
        for(int x = 0; x < players.length; x++)
        {
            //grabs their unit array
            Unit[] units = players[x].getUnits();

            for(Unit unit : units)
            {
                    //grabs position
                    int[] lo = unit.getPosition();
                    //if they are on selected tile..
                    if(selectedTile[0] == lo[0] && selectedTile[1] == lo[1])
                    {
                        //grabs right type and increments counter
                        if(unit instanceof Warrior)
                        {
                            wc++;
                        }
                        else if(unit instanceof Archer)
                        {
                            ac++;
                        }
                        else if(unit instanceof Scout)
                        {
                            sc++;
                        }
                        else
                        {
                            pc++;
                        }
                        //there is at least one unit
                        isthere = true;
                    }

            }

            //stop looking, no two players will have units on same tile
            if(isthere)
            {
                break;
            }
        }
        //formatting for units
        s += "<br><br><br><strong>Units:</strong> ";

        //if there are units
        if(isthere)
        {
            //say how many warriors
            if(wc > 0)
            {
                s += "<br><br>";
                s += wc + " Warrior";
                if(wc > 1)
                {
                    s += "s";
                }
            }

            //how many archers
            if(ac > 0)
            {
                s += "<br><br>";
                s += ac + " Archer";
                if(ac > 1)
                {
                    s += "s";
                }
            }

            //how many scouts
            if(sc > 0)
            {
                s += "<br><br>";
                s += sc + " Scout";
                if(sc > 1)
                {
                    s+= "s";
                }
            }

            //how many peasants
            if(pc > 0)
            {
                s +=  "<br><br>";
                s += pc + " Peasant";
                if(pc > 1)
                {
                    s += "s";
                }
            }
        }
        //otherwise, just write none
        else
        {
            s += "None";
        }

        //add ending html tag
        s += "</html>";

        //sets label to string
        label.setText(s);

        //label given
        return label;
    }

    //grows land size
    private void landGrowth()
    {
        //every seven turns
        if(turnCount % 7 == 0)
        {
            //each player
            for(int x = 0; x < players.length; x++)
            {
                //checks if a tile is next to their territory
                OUTER:
                for(int r = 0; r < p.getlength(); r++)
                {
                    for(int c = 0; c < p.getclength(); c++)
                    {
                        if(p.getElement(r, c) == (players.length * 2))
                        {
                            if(p.nextTo(r, c, (x + players.length)))
                            {
                                //if it is found
                                if(Math.random() > .6)
                                {
                                    //random tile, then sets it to the owner
                                    p.setElement(r, c, (x+players.length));
                                    //breaks to next player
                                    break OUTER;
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}