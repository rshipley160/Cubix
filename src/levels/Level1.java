package levels;

import static edu.utc.game.Game.ui;

import edu.utc.game.Game;
import edu.utc.game.GameObject;
import edu.utc.game.Scene;
import edu.utc.game.Texture;
import javafx.print.PageLayout;
import objects.*;

import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.system.CallbackI;
import org.w3c.dom.Text;

public class Level1 implements Scene {
    private GameObject background = new GameObject();

    private List<GameObject> platforms = new java.util.LinkedList<>();
    private List<GameObject> colliders = new java.util.LinkedList<>();
    private List<Player> players = new java.util.LinkedList<>();
    private List<Trap> traps = new java.util.LinkedList<>();

    public Level1() {
        background.getHitbox().setBounds(0, 0, ui.getWidth(), ui.getHeight());
        background.setColor(.9f, 0.9f, 0.9f);
        colliders.add(new GameObject());
        colliders.get(0).getHitbox().setBounds(-100, 0,100, ui.getHeight());
        colliders.add(new GameObject());
        colliders.get(1).getHitbox().setBounds(ui.getWidth(), 0,100, ui.getHeight());
        colliders.add(new GameObject());
        colliders.get(2).getHitbox().setBounds(0, -100,ui.getWidth(), 100);
        colliders.add(new GameObject());
        colliders.get(3).getHitbox().setBounds(0, ui.getHeight(),ui.getWidth(), 100);


        platforms.add(new Platform(ui.getWidth()/2-ui.getWidth()/4, ui.getHeight()-ui.getWidth()/16, Platform.PlatformType.BLUE));
        platforms.add(new Platform(ui.getWidth()/2, ui.getHeight()-ui.getWidth()/16, Platform.PlatformType.GRAY));
        platforms.add(new Wall(ui.getWidth()/4, 200, Platform.PlatformType.WHITE));
        platforms.add(new Wall(ui.getWidth()*3/4, 200, Platform.PlatformType.RED));
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

        background.draw();

        for (Trap t : traps)
        {
            t.update(delta);
        }

        for (Player p : players)
        {
            p.update(delta);
        }

        for (GameObject p : platforms){
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
