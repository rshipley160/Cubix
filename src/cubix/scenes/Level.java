package cubix.scenes;

import cubix.Cubix;
import cubix.objects.*;
import edu.utc.game.*;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

import java.util.List;

import static edu.utc.game.Game.ui;

public class Level implements Scene {

    // Transition is just a GameObject that allows alpha to be changed
    public static class Transition extends GameObject
    {
        @Override
        public void setColor(float r, float g, float b, float a) {
            super.setColor(r, g, b, a);
        }
    }

    public static Transition transition = new Transition();

    //Current level
    protected static int sceneIndex = 0;
    protected int currentScene;

    //Platforms & walls
    protected List<GameObject> platforms = new java.util.LinkedList<>();

    //Cubie traps
    protected List<Trap> traps = new java.util.LinkedList<>();

    //Trap activation / de-activation switches
    protected List<Switch> switches = new java.util.LinkedList<>();

    //Cubies
    protected Cubie redCubie;
    protected Cubie blueCubie;

    //Level exits
    protected Exit blueExit;
    protected Exit redExit;

    //List of platforms, traps, exits, walls, and players
    protected List<GameObject> colliders = new java.util.LinkedList<>();

    // Which Cubie is active first
    protected Cubie.COLORS startColor;

    /// Level transition management
    // Entering a Level
    private static boolean starting = true;
    // Exiting a level
    private static boolean exiting = false;
    private static int transitionTimer = 0;
    private static boolean goToMenu = false;

    // background music
    private static Sound BGM = new Sound("res\\BGM.wav");

    // Player death / reset sound
    public static final Sound reset = new Sound("res\\reset.wav");
    //Background image hitbox
    public final static GameObject background = new GameObject();
    //Background texture
    public final static Texture bg = new Texture("res\\background.png");

    static
    {
        background.getHitbox().setBounds(0, 0, ui.getWidth(), ui.getHeight());
        transition.getHitbox().setBounds(0,0,ui.getWidth(),ui.getHeight());
        BGM.setGain(0.3f);
        BGM.setLoop(true);
        BGM.play();
        reset.setGain(0.3f);
    }

    {
        currentScene = sceneIndex++;
    }

    public static void togglePlayers()
    {
        Cubie blue = getPlayer(Cubie.COLORS.BLUE);
        Cubie red = getPlayer(Cubie.COLORS.RED);
        // If the first player is active and the second isn't frozen
        if (blue.isActive() && !red.isKinematic())
        {
            System.out.println("Toggling blue to red");
            //Toggle
            blue.setActive(false);
            red.setActive(true);
        }
        //If the second player is active and the first isn't frozen
        else if (!blue.isKinematic())
        {
            //Toggle
            blue.setActive(true);
            red.setActive(false);
        }
        else {
            // If it gets this far the player cannot be toggled
            System.out.println("Can't toggle");
        }
    }

    @Override
    public void onKeyEvent(int key, int scancode, int action, int mods) {
        // Toggle players when space is pressed
        if (key== GLFW.GLFW_KEY_SPACE & action==GLFW.GLFW_PRESS)
        {
            togglePlayers();
        }

        // Reset when R is pressed
        else if (key== GLFW.GLFW_KEY_R & action==GLFW.GLFW_PRESS)
        {
            reset.play();
            reset();
        }

        // Go to menu when M is pressed
        else if (key== GLFW.GLFW_KEY_M & action==GLFW.GLFW_PRESS)
        {
            exiting = true;
            transitionTimer = 0;
            goToMenu = true;
        }
    }

    @Override
    public Scene drawFrame(int delta) {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT); // clear the framebuffer
        GL11.glColor3f(1, 1, 1);
        // Draw background before everything else
        bg.draw(background);

        // Limit delta if screen freezes
        if (delta > 1000/30) {
            delta = 1000/30;
        }

        //Update moving parts of the environment
        //Switches control Traps, so they need to be updated in this order
        for (Switch s : switches)
        {
            s.update(delta);
        }

        //Traps can capture players so they need to be updated before players
        for (Trap t : traps)
        {
            t.update(delta);
        }

        //Update each player
        redCubie.update(delta);
        blueCubie.update(delta);

        //Traps are drawn behind everything else
        for (Trap t : traps)
        {
            t.draw();
        }

        // Draw platforms & walls
        for (GameObject p : platforms){
            p.draw();
        }

        //Draw players
        blueCubie.draw();
        redCubie.draw();

        //Switches are drawn in front of the player
        for (Switch s : switches)
        {
            s.draw();
        }

        //Exits have to be drawn after players for glow effect to show on them
        blueExit.draw();
        redExit.draw();

        //Win condition
        if (blueCubie.onExit() && redCubie.onExit()) {
            if (!exiting)
            {
                exiting = true;
                transitionTimer = 0;
            }
        }

        // If entering a level
        if (starting)
        {
            if (transitionTimer <= 1500) {
                // Fade in
                if (transitionTimer < 100)
                {
                    // Set the level up in the first 100 milliseconds
                    // Screen is covered when this happens
                    reset();
                }
                transition.setColor(1f, 1f, 1f, (1 - transitionTimer / 1500f));
                transitionTimer += delta;
            }
            else {
                // After 1.5 seconds, end the fade in
                starting = false;
                transitionTimer = 0;
            }
            transition.draw();
        }

        // Apply same fade to exit transition
        if (exiting)
        {
            if (transitionTimer <= 1500) {
                transition.setColor(1f, 1f, 1f, transitionTimer / 1500f);
                transition.draw();
                transitionTimer += delta;
            }
            else {
                transition.draw();
                // Set up the new level
                exiting = false;
                starting = true;
                transitionTimer = 0;

                // Determine which scene to go to next
                if (goToMenu)
                {
                    return Cubix.menu;
                }

                if (Cubix.currentIndex + 1 <  Cubix.levels().size()) {
                    Cubix.currentIndex++;
                    return Cubix.levels().get(this.currentScene + 1);
                }
                else
                    // If this is the last level, the player has won
                    Cubix.currentIndex = 0;
                    return Cubix.victory;
            }
        }
        return this;
    }

    public static Cubie getPlayer(Cubie.COLORS color)
    {
        switch (color)
        {
            case RED:
                return Cubix.currentLevel().redCubie;
            default:
                return Cubix.currentLevel().blueCubie;
        }
    }

    public List<Trap> getTraps()
    {
        return traps;
    }

    public void reset()
    {
        // Set player position
        redCubie.respawn();
        blueCubie.respawn();

        // Reset all switches and traps
        for (Switch s : switches)
        {
            s.turnOn(false);
        }
        for (Trap t : traps)
        {
            t.reset();
        }

        //Set the starting color active again
        switch (startColor)
        {
            case RED:
                redCubie.setActive(true);
                blueCubie.setActive(false);
                break;
            default:
                redCubie.setActive(false);
                blueCubie.setActive(true);
                break;
        }
        goToMenu = false;
    }
}
