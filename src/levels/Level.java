package levels;

import edu.utc.game.GameObject;
import edu.utc.game.Scene;
import edu.utc.game.Texture;
import objects.Exit;
import objects.Player;
import objects.Trap;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

import java.util.List;

import static edu.utc.game.Game.ui;

public class Level implements Scene {
    //GO for background image
    private GameObject background = new GameObject();
    //Background texture
    private Texture bg = new Texture("res\\background.png");

    //Platforms & walls
    protected List<GameObject> platforms = new java.util.LinkedList<>();

    //Player traps
    protected List<Trap> traps = new java.util.LinkedList<>();

    //Player objects
    protected Player redPlayer;
    protected Player bluePlayer;

    //Level exits
    protected Exit blueExit;
    protected Exit redExit;

    //List of platforms, traps, exits, walls, and players
    protected List<GameObject> colliders = new java.util.LinkedList<>();

    {
        background.getHitbox().setBounds(0, 0, ui.getWidth(), ui.getHeight());
    }

    /**
     * Toggle which player is active
     * Only toggles if the currently inactive player is also not kinematic
     */
    public void togglePlayers()
    {
        // If the first player is active and the second isn't frozen
        if (bluePlayer.isActive() && !redPlayer.isKinematic())
        {
            //Toggle
            bluePlayer.setActive(false);
            redPlayer.setActive(true);
        }
        //If the second player is active and the first isn't frozen
        else if (!bluePlayer.isKinematic())
        {
            //Toggle
            bluePlayer.setActive(true);
            redPlayer.setActive(false);
        }
        // If it gets this far the player cannot be toggled
    }

    @Override
    public void onKeyEvent(int key, int scancode, int action, int mods) {
        if (key== GLFW.GLFW_KEY_SPACE & action==GLFW.GLFW_PRESS)
        {
            togglePlayers();
        }
    }

    @Override
    public Scene drawFrame(int delta) {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT); // clear the framebuffer
        GL11.glColor3f(1, 1, 1);
        bg.draw(background);

        //Update moving parts of the environment
        for (Trap t : traps)
        {
            t.update(delta);
        }
        redExit.update(delta);
        blueExit.update(delta);

        //Update each player
        redPlayer.update(delta);
        bluePlayer.update(delta);


        for (GameObject p : platforms){
            p.draw();
        }
        for (Trap t : traps)
        {
            t.draw();
        }

        bluePlayer.draw();
        redPlayer.draw();

        //Exits have to be drawn after players for glow effect to show on them
        blueExit.draw();
        redExit.draw();

        return this;
    }
}
