package cubix.levels;

import cubix.FinalProject;
import cubix.objects.*;
import edu.utc.game.*;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

import java.util.List;

import static cubix.objects.Player.COLORS.BLUE;
import static cubix.objects.Player.COLORS.RED;

public class Menu extends Level {
    private List<Button> buttons = new java.util.ArrayList<>();
    public Menu()
    {
        buttons.add(new Button(+3, -4, 4,2,"Start Game", FinalProject.levels().get(0)));
        buttons.get(0).setColor(0.7f, 0.7f, 1f);
        redPlayer = new Player(-13, +4, RED);
        bluePlayer = new Player(-9, +4, BLUE);

        platforms.add(new Platform(-14, +6, Platform.PlatformType.BLUE));
        platforms.add(new Platform(-10, +6, Platform.PlatformType.RED));
        platforms.add(new Platform(-10, -1, Platform.PlatformType.BLUE));
        platforms.add(new Platform(-14, -1, Platform.PlatformType.RED));

        platforms.add(new Wall(-15, -1, Platform.PlatformType.GRAY));
        platforms.add(new Wall(-15, +3, Platform.PlatformType.WHITE));
        platforms.add(new Wall(-6, +3, Platform.PlatformType.GRAY));
        platforms.add(new Wall(-6, -1, Platform.PlatformType.WHITE));

        colliders.addAll(platforms);
        colliders.add(redPlayer);
        colliders.add(bluePlayer);
        bluePlayer.setColliders(colliders);
        redPlayer.setColliders(colliders);
        redPlayer.setActive(true);
        bluePlayer.setActive(true);
        traps.clear();
    }

    @Override
    public void onKeyEvent(int key, int scancode, int action, int mods) {
        if (key== GLFW.GLFW_KEY_SPACE & action==GLFW.GLFW_PRESS)
        {
            System.out.println("Toggling!");
            togglePlayers();
        }

        if (key== GLFW.GLFW_KEY_R & action==GLFW.GLFW_PRESS)
        {
            reset();

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
