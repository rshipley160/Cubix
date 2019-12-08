package cubix.scenes;

import cubix.FinalProject;
import cubix.objects.*;
import edu.utc.game.*;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

import static cubix.objects.Player.COLORS.*;

public class Victory implements Scene {
    private Button winText  = new Button(-4, -8, 4, BLUE, "You Win!");
    private Button mainMenu = new Button(-3, +2, 3, RED, "Main Menu");

    private Scene nextScene = this;

    private boolean exiting;
    private int transitionTimer = 0;

    public Victory()
    {

    }

    @Override
    public void onMouseEvent(int button, int action, int mods) {
        XYPair<Integer> mousePos = Game.ui.getMouseLocation();
        if (button==0 && action== GLFW.GLFW_PRESS) {
            if (mainMenu.tryClick(mousePos.x, mousePos.y)) {
                nextScene = FinalProject.menu;
                exiting = true;
                transitionTimer = 0;
            }
        }
    }

    @Override
    public Scene drawFrame(int delta) {

        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT); // clear the framebuffer
        GL11.glColor3f(1, 1, 1);
        Level.bg.draw(Level.background);

        winText.draw();
        mainMenu.draw();

        if (exiting)
        {
            if (transitionTimer <= 1500) {
                Level.transition.setColor(1f, 1f, 1f, transitionTimer / 1500f);
                Level.transition.draw();
                transitionTimer += delta;
            }
            else {
                return nextScene;
            }
        }
        return this;
    }
}
