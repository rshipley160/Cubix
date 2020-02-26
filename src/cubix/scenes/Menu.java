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

    private boolean exiting;
    private boolean starting;
    private int transitionTimer = 0;

    private static Texture titleTex = new Texture("res\\title.png");
    private static Text authorText;

    private static GameObject title = new GameObject();

    private Scene nextScene;

    private ButtonGroup menuButtons;

    public Menu()
    {
        // Build the scene
        Level.background.getHitbox().setBounds(0, 0, ui.getWidth(), ui.getHeight());
        Level.transition.getHitbox().setBounds(0,0,ui.getWidth(),ui.getHeight());
        title.getHitbox().setBounds(128, 256, 448, 160);

        buttons.add(new Button(+3, -5, 4, BLUE,"Start Game"));
        buttons.add(new Button(+3, +3, 4, RED,"Exit"));

        menuButtons =  new ButtonGroup(buttons);
        authorText = new Text(128, 448, 48, 48, "by Riley Shipley");
        authorText.setColor(.45f, .65f, 1f);
    }

    @Override
    public void onKeyEvent(int key, int scancode, int action, int mods) {
        if (key== GLFW.GLFW_KEY_UP & action==GLFW.GLFW_PRESS)
        {
            menuButtons.up_select();
        }
        else if (key== GLFW.GLFW_KEY_DOWN & action==GLFW.GLFW_PRESS)
        {
            menuButtons.down_select();
        }
        else if (key== GLFW.GLFW_KEY_ENTER & action==GLFW.GLFW_PRESS)
        {
            int selection = menuButtons.get_selected_index();
            if (selection == 0)
            {
                nextScene = Cubix.levels().get(0);
                exiting = true;
                transitionTimer = 0;
            }
            else
            {
                nextScene = null;
                exiting = true;
                transitionTimer = 0;
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

        titleTex.draw(title);
        authorText.draw();

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
                exiting = false;
                transitionTimer = 0;
                return nextScene;
            }
        }
        return this;
    }
}
