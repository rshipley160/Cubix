package levels;

import static edu.utc.game.Game.ui;
import edu.utc.game.Scene;
import objects.*;

import java.util.List;

import org.lwjgl.opengl.GL11;

public class Level1 implements Scene {
    private List<Platform> platforms = new java.util.LinkedList<>();
    private Player pl;

    public Level1() {
        Platform newPlat = new Platform(0, ui.getHeight()-20, ui.getWidth(), 20);
        newPlat.setColor(0.5f, 0.5f, 0.5f);
        platforms.add(newPlat);
        pl = new Player(ui.getWidth()/2, ui.getHeight()/2);
    }

    @Override
    public Scene drawFrame(int delta) {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT); // clear the framebuffer

        for (Platform p : platforms){
            p.draw();
        }

        pl.draw();

        return this;
    }
}
