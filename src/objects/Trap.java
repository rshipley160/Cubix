package objects;

import edu.utc.game.Game;
import edu.utc.game.GameObject;
import edu.utc.game.Texture;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

import java.util.List;

public class Trap extends GameObject {
    private static Texture blueT = new Texture("res\\BlueArea.png");
    private static Texture redT = new Texture("res\\RedArea.png");
    private Texture texture;
    private Player.COLORS color;
    private List<Player> players;
    private List<Player> capturedPlayers = new java.util.LinkedList<>();
    private boolean active = true;

    public Trap (int x, int y, int size, Player.COLORS col)
    {
        this.hitbox.setBounds(x, y, size, size);
        this.color = col;
        switch (color)
        {
            case RED:
                this.texture = redT;
                break;
            default:
                this.texture = blueT;
                break;
        }
        this.r = 1f;
        this.g = 1f;
        this.b = 1f;
    }

    public Trap (int x, int y, int size, Player.COLORS col, List<Player> players)
    {
        this.hitbox.setBounds(x, y, size, size);
        this.color = col;
        switch (color)
        {
            case RED:
                this.texture = redT;
                break;
            default:
                this.texture = blueT;
                break;
        }
        this.r = 1f;
        this.g = 1f;
        this.b = 1f;
        this.players = players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public void draw()
    {
        texture.bind();
        float temp = 0;
        if (this.active)
            temp = 0.5f;

        GL11.glColor3f(1,1,1);
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glTexCoord2f(0+temp,0);
        GL11.glVertex2f(this.hitbox.x, this.hitbox.y);
        GL11.glTexCoord2f(0.5f+temp,0);
        GL11.glVertex2f(this.hitbox.x+this.hitbox.width, this.hitbox.y);
        GL11.glTexCoord2f(0.5f+temp,1);
        GL11.glVertex2f(this.hitbox.x+this.hitbox.width, this.hitbox.y+this.hitbox.height);
        GL11.glTexCoord2f(0+temp,1);
        GL11.glVertex2f(this.hitbox.x, this.hitbox.y+this.hitbox.height);
        GL11.glEnd();

        GL11.glBindTexture(GL11.GL_TEXTURE_2D,  0);
    }

    public void deactivate()
    {
        for (Player p : capturedPlayers)
        {
            p.unfreeze();
        }
        capturedPlayers.clear();
        active = false;
        super.deactivate();
    }

    @Override
    public void update(int delta) {
        if (active) {
            for (Player p : players) {
                if (this.intersects(p) && this.intersection(p).equals(p.getHitbox())) {
                    if (p.getColor().equals(this.color)) {
                        p.freeze();
                        capturedPlayers.add(p);
                    } else {
                        p.unfreeze();
                    }
                }
            }
        }

    }
}
