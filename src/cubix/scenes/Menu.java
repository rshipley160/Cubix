package cubix.scenes;

import cubix.Cubix;
import cubix.objects.*;
import edu.utc.game.*;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

import java.util.List;

import static cubix.objects.Cubie.COLORS.*;
import static edu.utc.game.Game.ui;

public class Menu implements Scene {

    private List<Button> buttons = new java.util.ArrayList<>();

    private List<GameObject> platforms = new java.util.LinkedList<>();

    private Cubie redCubie = new Cubie(-13, +4, RED);
    private Cubie blueCubie = new Cubie(-9, +4, BLUE);

    private boolean exiting;
    private boolean starting;
    private int transitionTimer = 0;

    private static Texture titleTex = new Texture("res\\title.png");

    private static GameObject title = new GameObject();

    private Scene nextScene;

    public Menu()
    {
        // Build the scene
        Level.background.getHitbox().setBounds(0, 0, ui.getWidth(), ui.getHeight());
        Level.transition.getHitbox().setBounds(0,0,ui.getWidth(),ui.getHeight());
        title.getHitbox().setBounds(96, 96, 448, 160);

        buttons.add(new Button(+3, -5, 4, BLUE,"Start Game"));
        buttons.add(new Button(+3, +3, 4, RED,"Exit"));


        platforms.add(new Platform(-14, +6, Platform.PlatformType.BLUE));
        platforms.add(new Platform(-10, +6, Platform.PlatformType.RED));
        platforms.add(new Platform(-10, -1, Platform.PlatformType.BLUE));
        platforms.add(new Platform(-14, -1, Platform.PlatformType.RED));

        platforms.add(new Wall(-15, -1, Platform.PlatformType.GRAY));
        platforms.add(new Wall(-15, +3, Platform.PlatformType.WHITE));
        platforms.add(new Wall(-6, +3, Platform.PlatformType.GRAY));
        platforms.add(new Wall(-6, -1, Platform.PlatformType.WHITE));

        // Set up Cubies
        List<GameObject> colliders = new java.util.LinkedList<>();
        colliders.addAll(platforms);
        colliders.add(redCubie);
        colliders.add(blueCubie);
        blueCubie.setColliders(colliders);
        redCubie.setColliders(colliders);
        redCubie.setActive(false);
        blueCubie.setActive(true);
    }

    @Override
    public void onKeyEvent(int key, int scancode, int action, int mods) {
        // Toggle players when space is pressed
        if (key== GLFW.GLFW_KEY_SPACE & action==GLFW.GLFW_PRESS)
        {
            // If the first player is active and the second isn't frozen
            if (blueCubie.isActive() && !redCubie.isKinematic())
            {
                //Toggle
                blueCubie.setActive(false);
                redCubie.setActive(true);
            }
            //If the second player is active and the first isn't frozen
            else if (!blueCubie.isKinematic())
            {
                //Toggle
                blueCubie.setActive(true);
                redCubie.setActive(false);
            }
            else {
                // If it gets this far the player cannot be toggled
            }
        }
    }

    @Override
    public void onMouseEvent(int button, int action, int mods) {
        // When mouse is clicked, see if either button was pressed and do the appropriate action
        XYPair<Integer> mousePos = Game.ui.getMouseLocation();
        if (button==0 && action==GLFW.GLFW_PRESS) {
            if ( buttons.get(0).tryClick(mousePos.x, mousePos.y)) {
                nextScene = Cubix.levels().get(0);
                exiting = true;
                transitionTimer = 0;
            }
            else if ( buttons.get(1).tryClick(mousePos.x, mousePos.y)) {
                nextScene = null;
                exiting = true;
                transitionTimer = 0;
            }
        }
    }

    @Override
    public Scene drawFrame(int delta) {
        // See Level class for explanation of the drawFrame, as this is essentially a simplified version of that
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT); // clear the framebuffer
        GL11.glColor3f(1, 1, 1);
        Level.bg.draw(Level.background);
        if (delta > 1000/30) {
            delta = 1000/30;
        }
        //Update each player
        redCubie.update(delta);
        blueCubie.update(delta);

        // Draw platforms & walls
        for (GameObject p : platforms){
            p.draw();
        }

        titleTex.draw(title);

        //Draw players
        blueCubie.draw();
        redCubie.draw();

        for (Button b : buttons)
        {
            b.draw();
        }


        if (!exiting && transitionTimer == 0) {
            starting = true;
        }
        if (starting) {
            if (transitionTimer <= 1500) {
                Level.transition.setColor(1f, 1f, 1f, (1 - transitionTimer / 1500f));
                transitionTimer += delta;
            }
            else {
                starting = false;
            }
            Level.transition.draw();
        }
        if (exiting)
        {
            if (transitionTimer <= 1500) {
                Level.transition.setColor(1f, 1f, 1f, transitionTimer / 1500f);
                Level.transition.draw();
                transitionTimer += delta;
            }
            else {
                Level.transition.draw();
                blueCubie.respawn();
                redCubie.respawn();
                exiting = false;
                transitionTimer = 0;
                return nextScene;
            }
        }
        return this;
    }
}
