package levels;

import static edu.utc.game.Game.ui;

import edu.utc.game.GameObject;
import edu.utc.game.Scene;
import javafx.print.PageLayout;
import objects.*;

import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.system.CallbackI;

public class Level1 implements Scene {
    private List<Platform> platforms = new java.util.LinkedList<>();
    private List<GameObject> colliders = new java.util.LinkedList<>();
    private List<Player> players = new java.util.LinkedList<>();

    private List<Trap> traps = new java.util.LinkedList<>();

    public Level1() {

        platforms.add(new Platform(0, ui.getHeight()-20, ui.getWidth(), 100));
        platforms.add(new Platform(200, 200, 300, 20));
        players.add(new Player(ui.getWidth()/2-75, ui.getHeight()/2-100, Player.COLORS.BLUE));
        players.add(new Player(ui.getWidth()/2+25, ui.getHeight()/2-100, Player.COLORS.RED));
        colliders.addAll(platforms);
        colliders.addAll(players);
        for (Player p : players)
        {
            p.setColliders(colliders);
        }
    }

    @Override
    public Scene drawFrame(int delta) {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT); // clear the framebuffer

        for (Trap t : traps)
        {
            t.update(delta);
        }

        for (Player p : players)
        {
            p.update(delta);
        }

        for (Platform p : platforms){
            p.draw();
        }
        for (Trap t : traps)
        {
            t.draw();
        }

        for (Player p : players)
        {
            p.draw();
        }
        return this;
    }
}
