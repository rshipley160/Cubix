package cubix.levels;

import cubix.FinalProject;
import cubix.objects.*;
import edu.utc.game.*;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

import java.util.List;

import static cubix.objects.Player.COLORS.BLUE;
import static cubix.objects.Player.COLORS.RED;
import static edu.utc.game.Game.ui;

public class Menu implements Scene {
    private static class Transition extends GameObject
    {
        @Override
        public void setColor(float r, float g, float b, float a) {
            super.setColor(r, g, b, a);
        }
    }

    private static Transition transition = new Transition();

    private List<Button> buttons = new java.util.ArrayList<>();

    private List<GameObject> platforms = new java.util.LinkedList<>();

    private Player redPlayer = new Player(-13, +4, RED);
    private Player bluePlayer = new Player(-9, +4, BLUE);

    //GO for background image
    public final static GameObject background = new GameObject();

    //Background texture
    public final static Texture bg = new Texture("res\\background.png");

    public Menu()
    {
        background.getHitbox().setBounds(0, 0, ui.getWidth(), ui.getHeight());
        transition.getHitbox().setBounds(0,0,ui.getWidth(),ui.getHeight());

        buttons.add(new Button(+3, -5, BLUE,"Start Game"));
        buttons.add(new Button(+3, +3, RED,"Exit"));


        platforms.add(new Platform(-14, +6, Platform.PlatformType.BLUE));
        platforms.add(new Platform(-10, +6, Platform.PlatformType.RED));
        platforms.add(new Platform(-10, -1, Platform.PlatformType.BLUE));
        platforms.add(new Platform(-14, -1, Platform.PlatformType.RED));

        platforms.add(new Wall(-15, -1, Platform.PlatformType.GRAY));
        platforms.add(new Wall(-15, +3, Platform.PlatformType.WHITE));
        platforms.add(new Wall(-6, +3, Platform.PlatformType.GRAY));
        platforms.add(new Wall(-6, -1, Platform.PlatformType.WHITE));

        List<GameObject> colliders = new java.util.LinkedList<>();
        colliders.addAll(platforms);
        colliders.add(redPlayer);
        colliders.add(bluePlayer);
        bluePlayer.setColliders(colliders);
        redPlayer.setColliders(colliders);
        redPlayer.setActive(true);
        bluePlayer.setActive(true);
    }

    @Override
    public void onKeyEvent(int key, int scancode, int action, int mods) {
        if (key== GLFW.GLFW_KEY_SPACE & action==GLFW.GLFW_PRESS)
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
            else {
                // If it gets this far the player cannot be toggled
            }
        }
    }

    @Override
    public Scene drawFrame(int delta) {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT); // clear the framebuffer
        GL11.glColor3f(1, 1, 1);
        bg.draw(background);
        if (delta > 1000/30) {
            delta = 1000/30;
        }
        //Update each player
        redPlayer.update(delta);
        bluePlayer.update(delta);

        // Draw platforms & walls
        for (GameObject p : platforms){
            p.draw();
        }

        //Draw players
        bluePlayer.draw();
        redPlayer.draw();

        for (Button b : buttons)
        {
            b.draw();
        }

        return this;
    }
}
