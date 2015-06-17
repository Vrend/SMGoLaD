package Game;

/*
* Sid Meier's Game of Lords and Dragons © John Broderick 1337-6969 (2015)
* Main class runs using slick2d and lwjgl
*
* Sources for api's are provided
*
* No big errors, only don't quit to menu too fast
*
* Resources are currently pretty much useless, most of the resource classes are tiny
*
* There are three big methods for each state:
*
* 1. Init - ran first time it is entered -- used for creation of arrays and variable initialization
* 2. Render - draws everything
* 3. Update - Event handling and input handling - deals with "updates"
*
*/

//boring imports
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameC extends StateBasedGame
{
    //required constructor
    protected GameC(String name)
    {
        super(name);
    }

    //general external use buttons and labels for my info pane
    final static JButton general = new JButton("General");
    final static JButton info = new JButton("Info");
    final static JLabel help1 = new JLabel("Press R to generate a new map");
    final static JLabel help2 = new JLabel("Press C to show climate");
    final static JLabel help3 = new JLabel("Press G to show Geography");
    final static JLabel help4 = new JLabel("Press E to show Resource");
    //label for info tab
    static JLabel infoLabel = new JLabel("Nothing here!");
    //label for players
    static JLabel players;

    //boolean for showing infobutton
    private static boolean infoShowing = false;

    //All of the states in my game, a state is a specific thing like score, main menu, etc..
    public void initStatesList(GameContainer gc) throws SlickException
    {
        //main menu
        addState(new MainMenu());

        //Map generator
        addState(new MapGen());

        //Save menu
        addState(new SaveState());

        //Load menu
        addState(new LoadState());

        //Closing effect
        addState(new CloseState());

        //Setup player count
        addState(new SetupState());

        //Actual game
        addState(new GamePlayState());
    }

    //Main method
    public static void main(String[] args)
    {

        //Basic frame info
        JFrame frame = new JFrame("Info");
        frame.setAlwaysOnTop(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(200, 600));
        frame.setResizable(false);
        //end of frame info

        //create 2 jpanels, one for help and other for buttons, using borderLayout
        JPanel list = new JPanel();
        JPanel Buttons = new JPanel();
        //container created and layout set
        Container c = frame.getContentPane();
        c.setLayout(new BorderLayout());
        //buttons are not seen in main menu
        general.setVisible(false);
        info.setVisible(false);
        //they can be clicked when visible
        general.setEnabled(true);
        info.setEnabled(true);

        //button listener, disables when clicked and enables info button, shows players and hides map help and infolabel
        general.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                general.setEnabled(false);
                mapHelp(false);
                showPlayers(true);
                info.setEnabled(true);
                infoLabel.setVisible(false);
            }
        });

        //button listener, disables itself and enables general button, hiding players and displaying map gen help or info for map
        info.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                info.setEnabled(false);
                showPlayers(false);
                if (MapGen.isIn)
                {
                    //if in mapgen state
                    mapHelp(true);
                }
                else
                {
                    //any other place
                    infoLabel.setVisible(true);
                }
                general.setEnabled(true);
            }
        });

        //buttons added to jpanel
        Buttons.add(general);
        Buttons.add(info);
        //infolabel and help labels added to list
        list.add(infoLabel);
        list.add(help1);
        list.add(help2);
        list.add(help3);
        list.add(help4);
        //everything is invisible in main menu
        help1.setVisible(false);
        help2.setVisible(false);
        help3.setVisible(false);
        help4.setVisible(false);
        infoLabel.setVisible(false);

        //players label is created and set invisible
        players = new JLabel();
        players.setVisible(false);
        //added to list
        list.add(players);

        //panel and player label locations set
        c.add(Buttons, "North");
        c.add(players, "West");

        //basic frame info
        frame.add(list);
        frame.pack();
        frame.setVisible(true);
        //end frame info

        //icon while running in windows
        String[] stuff = {"res/pics/icon32.png", "res/pics/icon24.png", "res/pics/icon16.png"};

        //slick2d api stuff

        try
        {
            //AppGameContainer is the general window, calls this class
            AppGameContainer container = new AppGameContainer(new GameC("Sid Meier's Game of Lords and Dragons"));
            container.setDisplayMode(800, 600, false);
            container.setIcons(stuff);
            container.setShowFPS(false);
            //sets size, sets icons from icon array, and hides fps marker
            container.start();
            //starts the program
        }
        catch (SlickException e)
        {
            //Any problem with container, throw container error
            System.out.println("Slick Container Error");
        }
    }

    //shows general button
    protected static void showGeneral(boolean b)
    {
        general.setVisible(b);
    }

    //shows map help
    protected static void mapHelp(boolean b)
    {
        help1.setVisible(b);
        help2.setVisible(b);
        help3.setVisible(b);
        help4.setVisible(b);
    }

    //shows players using html for formatting (&nbsp; is a space)
    protected static void setPlayers(int num, Player[] p)
    {
        String f = "<html>";
        for (int x = 0; x < num; x++)
        {
            //gets the Player name
            String name = p[x].getName();
            //gets the hex equivalent for Player
            String hex = p[x].getJColor();
            //adds player to string and colors it using the hex
            f += "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font color=" + hex + ">" + name + "</font><br><br>";
        }
        //html closing tag
        f += "</html>";
        //appends string to jlabel
        players.setText(f);
    }

    //toggles whether players are shown
    protected static void showPlayers(boolean b)
    {
        players.setVisible(b);
    }

    //toggles info button visible or not
    protected static void showInfoButton(boolean b)
    {
        info.setVisible(b);
    }

    //passes what is shown in infolabel, if false, sets it to "nothing here", if true, uses jlabel passed in
    protected static void showInfo(boolean b, JLabel jl)
    {
        infoShowing = b;

        if (infoShowing)
        {
            String s = jl.getText();
            infoLabel.setText(s);
        }
        else
        {
            infoLabel.setText("Nothing here!");
        }
    }

    //Fallback for showing/updating info if it is pressed when changing states
    public static void isInfoEnabled()
    {
        if (!info.isEnabled())
        {
            if (MapGen.isIn)
            {
                mapHelp(true);
            }
            else
            {
                infoLabel.setVisible(true);
            }
        }
    }
}