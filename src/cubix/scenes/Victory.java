package cubix.scenes;

import cubix.Cubix;
import cubix.objects.*;
import edu.utc.game.*;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

import java.util.List;

import static cubix.objects.Cubie.COLORS.*;

public class Victory implements Scene {
    private Button winText  = new Button(-4, -8, 4, BLUE, "You Win!");
    private Button mainMenu = new Button(-3, +2, 3, RED, "Main Menu");
    private List<Button> buttonList = new java.util.LinkedList<>(java.util.Arrays.asList(new Button[] {mainMenu}));
    private ButtonGroup menuGroup = new ButtonGroup(buttonList);

    private Scene nextScene = this;

    private boolean exiting;
    private int transitionTimer = 0;


    @Override
    public void onMouseEvent(int button, int action, int mods) {
        // Test to see if player clicks main menu button and respond appropriately
        XYPair<Integer> mousePos = Game.ui.getMouseLocation();
        if (button==0 && action== GLFW.GLFW_PRESS) {
            if (mainMenu.tryClick(mousePos.x, mousePos.y)) {
                nextScene = Cubix.menu;
                exiting = true;
                transitionTimer = 0;
            }
        }
    }

    @Override
    public void onKeyEvent(int key, int scancode, int action, int mods) {
        if (key== GLFW.GLFW_KEY_J & action==GLFW.GLFW_PRESS)
        {
                nextScene = Cubix.menu;
                exiting = true;
                transitionTimer = 0;
        }
    }

    @Override
    public Scene drawFrame(int delta) {

        // Draw background and buttons
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT); // clear the framebuffer
        GL11.glColor3f(1, 1, 1);
        Level.bg.draw(Level.background);

        winText.draw();
        mainMenu.draw();

        // transition out when exiting to main menu
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
