package Game;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

//State responsible for map generation
public class MapGen extends BasicGameState
{
    //am I in this state
    public static boolean isIn = false;

    //instance fields
	private int size;
	private int width, height;

    //booleans showing what map
	private boolean climateShowing;
	private boolean geoShowing;
	private boolean resShowing;

    //the map objects
	protected static LandMap land;
    protected static ClimateMap climate;
    protected static GeoMap geo;
    protected static resMap res;
    
    //Necessary for showing non-loaded maps
    private static boolean showing;


    //when entered, isIn is true and show the info button
    public void enter(GameContainer gc, StateBasedGame game) throws SlickException
    {
        super.enter(gc, game);
        isIn = true;
        GameC.showInfoButton(true);
    }

    //I am no longer in this state
    public void leave(GameContainer gc, StateBasedGame game) throws SlickException
    {
        super.leave(gc, game);
        isIn = false;
    }

    //creates all the size, width, height requirements. Initializes the maps and sets the map booleans
	public void init(GameContainer container, StateBasedGame game) throws SlickException 
	{
		size = 5;
		width = 800;
		height = 600;
		land = new LandMap(width, height, size);
        climate = new ClimateMap(width, height, size);
        geo = new GeoMap(width, height, size);
        res = new resMap(width, height, size);
        climateShowing = false;
        geoShowing = false;
        resShowing = false;
        showing = true;
	}

	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException 
	{
        //if no map is generated show this
		if(showing)
		{
			g.setColor(Color.blue);
			g.fillRect(0, 0, 800, 600);
			g.setColor(Color.white);
			g.drawString("Press R to generate a Map", 300, 200);
		}
        //or show one of these

        //resources
		else if(resShowing)
		{
			res.drawRes(g);
		}
        //geography
		else if(geoShowing)
		{
			geo.drawGeo(g);
		}
        //climate
		else if(climateShowing)
		{
			climate.drawClimate(g);
		}
        //land
		else
		{
			land.drawLandMap(g);
		}
	}

	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException 
	{
        //grabs input
		Input input = container.getInput();

        //make a new map if r is pressed
		if(input.isKeyPressed(Input.KEY_R))
		{
			showing = false;
			generateMap();
		}

        //quit to menu if q is pressed - maphelp hidden
		if(input.isKeyPressed(Input.KEY_Q))
		{
            GameC.mapHelp(false);
			game.enterState(0, new FadeOutTransition(), new FadeInTransition());
		}

        //saves the map - goes to save state
		if(input.isKeyPressed(Input.KEY_S) && !showing)
		{
			game.enterState(2, new FadeOutTransition(), new FadeInTransition());
		}

        //loads a new map if l is pressed
		if(input.isKeyPressed(Input.KEY_L))
		{
			game.enterState(3, new FadeOutTransition(), new FadeInTransition());
		}

        //shows climate
		if(input.isKeyPressed(Input.KEY_C) && !showing)
		{
			climateShowing = !climateShowing;
            geoShowing = false;
            resShowing = false;
		}

        //shows geography
		if(input.isKeyPressed(Input.KEY_G))
		{
			geoShowing = !geoShowing;
            resShowing = false;
            climateShowing = false;
		}

        //shows resources
		if(input.isKeyPressed(Input.KEY_E))
		{
			resShowing = !resShowing;
            geoShowing = false;
            climateShowing = false;
		}

        //moves to the setup state if map is made
        if(input.isKeyPressed(Input.KEY_ENTER) && !showing)
        {
            GameC.mapHelp(false);
            game.enterState(5, new FadeOutTransition(), new FadeInTransition());
        }
	}
	
    //state id
	public int getID() 
	{
		return 1;
	}
	
	//creates each of the maps
	public static void generateMap()
	{
		int continents = (int) ((Math.random() * 4) + 1);
		land.genLand(continents);
		climate.genClimate();
		geo.genGeo();	
		res.generateResources();
	}

    //allows external changing of 'showing' boolean
    public static void changeShowing()
    {
        showing = false;
    }

}
