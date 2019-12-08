package cubix.objects;

import edu.utc.game.Game;
import edu.utc.game.GameObject;
import edu.utc.game.Texture;
import cubix.scenes.Level;
import org.lwjgl.opengl.GL11;

import java.util.List;

import static cubix.objects.Player.COLORS.BLUE;
import static cubix.objects.Player.COLORS.RED;

public class Trap extends GameObject {
    private static Texture blueT = new Texture("res\\BlueArea.png");
    private static Texture redT = new Texture("res\\RedArea.png");
    private Texture texture;
    private Player.COLORS color;
    private List<Player> capturedPlayers = new java.util.LinkedList<>();
    private boolean active = true;

    // Whether the trap starts as on or off
    private boolean initalState;

    public Trap (int x, int y, Player.COLORS col)
    {
        this.hitbox.setBounds(Game.ui.getWidth()/2+x*32, Game.ui.getHeight()/2+y*32, 128, 128);
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
        this.initalState = true;
    }

    public Trap (int x, int y, Player.COLORS col, boolean initalState)
    {
        this.hitbox.setBounds(Game.ui.getWidth()/2+x*32, Game.ui.getHeight()/2+y*32, 128, 128);
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
        this.initalState = initalState;
    }

    public Player.COLORS getColor() {
        return color;
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
        if (capturedPlayers.size() > 0)
            Level.togglePlayers();
        capturedPlayers.clear();
        active = false;
        super.deactivate();
    }

    public void activate()
    {
        active = true;
    }

    public void toggle() {
        if (active)
        {
            deactivate();
        }
        else {
            activate();
        }
    }

    public void reset()
    {
        active = initalState;
        capturedPlayers.clear();
    }

    @Override
    public void update(int delta) {
        float tack = 0.4f;
        float tackWidth = tack * Level.getPlayer(RED).getHitbox().width;
        if (active)
        {
            switch (this.color)
            {
                case RED:
                    if (this.intersects(Level.getPlayer(RED)))// && this.intersection(Level.getPlayer(RED)).equals(Level.getPlayer(RED).getHitbox()))
                    {
                        if (    this.intersection(Level.getPlayer(RED)).width >= tackWidth &&
                                this.intersection(Level.getPlayer(RED)).height >= tackWidth ) {
                            if (Level.getPlayer(RED).getColor().equals(this.color)) {
                                if (Level.getPlayer(RED).isActive())
                                    Level.togglePlayers();
                                Level.getPlayer(RED).freeze();
                                capturedPlayers.add(Level.getPlayer(RED));
                            } else {
                                Level.getPlayer(RED).unfreeze();
                            }
                        }
                    }
                    return;
                default:
                    if (this.intersects(Level.getPlayer(BLUE)))// && this.intersection(Level.getPlayer(BLUE)).equals(Level.getPlayer(BLUE).getHitbox()))
                    {
                        if (    this.intersection(Level.getPlayer(BLUE)).width >= tackWidth &&
                                this.intersection(Level.getPlayer(BLUE)).height >= tackWidth ) {
                            if (Level.getPlayer(BLUE).getColor().equals(this.color)) {
                                if (Level.getPlayer(BLUE).isActive())
                                    Level.togglePlayers();
                                Level.getPlayer(BLUE).freeze();
                                capturedPlayers.add(Level.getPlayer(BLUE));
                            } else {
                                Level.getPlayer(BLUE).unfreeze();
                            }
                        }
                    }
                    return;
            }
        }
    }
}
