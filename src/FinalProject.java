import edu.utc.game.*;
import org.lwjgl.opengl.GL11;
import levels.Level1;

public class FinalProject extends Game {
    static Scene activeScene;

    public static void main(String[] args)
    {
        FinalProject game = new FinalProject();
        Scene lvl = new Level1();
        activeScene = lvl;
        game.registerGlobalCallbacks();

        game.setScene(lvl);
        game.gameLoop();
    }

    public FinalProject() {
        // inherited from the Game class, this sets up the window and allows us to access
        // Game.ui
        initUI(1280, 768, "Final Project");

        // screen clear is white (this could go in drawFrame if you wanted it to change
        GL11.glClearColor(0f, 0f, 0f, 0f);
    }
}
