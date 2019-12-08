package cubix;

import edu.utc.game.*;
import org.lwjgl.opengl.GL11;
import cubix.levels.*;

import java.util.List;

public class FinalProject extends Game {
    static List<Scene> _levels = new java.util.LinkedList();
    public static int currentIndex = 0;
    public static Menu menu;

    public static void main(String[] args)
    {
        FinalProject game = new FinalProject();

        game.registerGlobalCallbacks();

        _levels.add(new Level5());

        _levels.add(new Level1());
        _levels.add(new Level2());
        _levels.add(new Level3());
        _levels.add(new Level4());
        _levels.add(new Level5());

        menu = new Menu();


        game.setScene(menu);
        game.gameLoop();
    }

    public static List<Scene> levels()
    {
        return _levels;
    }

    public static Level currentLevel()
    {
        return (Level) _levels.get(currentIndex);
    }

    public FinalProject() {
        // inherited from the Game class, this sets up the window and allows us to access
        // Game.ui
        initUI(1280, 768, "Cubix");

        // screen clear is white (this could go in drawFrame if you wanted it to change
        GL11.glClearColor(0f, 0f, 0f, 0f);
    }
}
