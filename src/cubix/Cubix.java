package cubix;

import edu.utc.game.*;
import org.lwjgl.opengl.GL11;
import cubix.scenes.*;

import java.util.List;

public class Cubix extends Game {
    private static List<Scene> _levels = new java.util.LinkedList();
    public final static int cellSize = 32;
    public static int currentIndex = 0;
    public static Menu menu;
    public static Victory victory;

    public static void main(String[] args)
    {
        Cubix game = new Cubix();

        game.registerGlobalCallbacks();

        _levels.add(new Level1());
        _levels.add(new Level2());
        _levels.add(new Level3());
        _levels.add(new Level4());
        _levels.add(new Level5());

        menu = new Menu();
        victory = new Victory();

        currentIndex = 0;
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

    public Cubix() {
        // inherited from the Game class, this sets up the window and allows us to access
        // Game.ui
        // initUI(40*Cubix.cellSize, 24*Cubix.cellSize, "Cubix");
        initUI(1920, 1080, "Cubix");
        // screen clear is white (this could go in drawFrame if you wanted it to change
        GL11.glClearColor(0f, 0f, 0f, 0f);
    }
}
