package levels;

import static edu.utc.game.Game.ui;

import edu.utc.game.GameObject;
import edu.utc.game.Scene;
import edu.utc.game.Texture;
import objects.*;

import java.util.List;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.CallbackI;
import org.w3c.dom.Text;

public class Level1 implements Scene {
    private GameObject background = new GameObject();
    private Texture bg = new Texture("res\\background.png");
    private List<GameObject> platforms = new java.util.LinkedList<>();
    private List<GameObject> colliders = new java.util.LinkedList<>();
    private List<Player> players = new java.util.LinkedList<>();
    private List<Trap> traps = new java.util.LinkedList<>();
    private Player activePlayer;

    public Level1() {
        background.getHitbox().setBounds(0, 0, ui.getWidth(), ui.getHeight());

        platforms.add(new Platform(-8, -2, Platform.PlatformType.BLUE));
        platforms.add(new Platform(-8, +2, Platform.PlatformType.BLUE));
        platforms.add(new Platform(-4, +2, Platform.PlatformType.BLUE));
        platforms.add(new Platform(0, +2, Platform.PlatformType.RED));
        platforms.add(new Platform(+4, +2, Platform.PlatformType.RED));
        platforms.add(new Platform(+10, -2, Platform.PlatformType.GRAY));
        platforms.add(new Platform(+14, -2, Platform.PlatformType.GRAY));
        players.add(new Player(ui.getWidth()/2-75, ui.getHeight()/2-100, Player.COLORS.BLUE));
        players.add(new Player(ui.getWidth()/2+25, ui.getHeight()/2-100, Player.COLORS.RED));
        colliders.addAll(platforms);
        colliders.addAll(players);
        for (Player p : players)
        {
            p.setColliders(colliders);
            p.setActive(false);
        }
        players.get(0).setActive(true);
    }

    /**
     * Toggle which player is active
     * Only toggles if the currently inactive player is also not kinematic
     */
    public void togglePlayers()
    {
        System.out.println("Toggling players!");
        // If the first player is active and the second isn't frozen
        if (players.get(0).isActive() && !players.get(1).isKinematic())
        {
            //Toggle
            players.get(0).setActive(false);
            players.get(1).setActive(true);
        }
        //If the second player is active and the first isn't frozen
        else if (!players.get(0).isKinematic())
        {
            //Toggle
            players.get(0).setActive(true);
            players.get(1).setActive(false);
        }
        // If it gets this far the player cannot be toggled
    }

    @Override
    public void onKeyEvent(int key, int scancode, int action, int mods) {
        if (key==GLFW.GLFW_KEY_SPACE & action==GLFW.GLFW_PRESS)
        {
            togglePlayers();
        }
    }

    @Override
    public Scene drawFrame(int delta) {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT); // clear the framebuffer
        GL11.glColor3f(1, 1, 1);
        bg.draw(background);

        for (Trap t : traps)
        {
            t.update(delta);
        }

        for (Player p : players)
        {
            p.update(delta);
        }

        for (GameObject p : platforms){
            p.draw();
        }
        for (Trap t : traps)
        {
            t.draw();
        }

        for (Player p : players)
        {
            p.draw();
        }
        return this;
    }
}
