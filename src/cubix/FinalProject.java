package cubix;

import edu.utc.game.*;
import org.lwjgl.opengl.GL11;
import cubix.levels.*;

import java.util.List;

public class FinalProject extends Game {
    static List<Scene> levels = new java.util.ArrayList();
    public static int currentIndex = 0;

    public static void main(String[] args)
    {
        FinalProject game = new FinalProject();

        game.registerGlobalCallbacks();

        levels.add(new Level1());
        levels.add(new Level2());
        levels.add(new Level3());

        game.setScene(levels.get(0));
        game.gameLoop();
    }

    public static List<Scene> scenes()
    {
        return levels;
    }

    public static Level currentLevel()
    {
        return (Level) levels.get(currentIndex);
    }

    public FinalProject() {
        // inherited from the Game class, this sets up the window and allows us to access
        // Game.ui
        initUI(1280, 768, "Cubix");

        // screen clear is white (this could go in drawFrame if you wanted it to change
        GL11.glClearColor(0f, 0f, 0f, 0f);
    }
}
