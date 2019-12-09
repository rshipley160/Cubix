package cubix.objects;

import cubix.Cubix;
import edu.utc.game.Game;
import edu.utc.game.GameObject;
import edu.utc.game.Texture;
import cubix.scenes.Level;
import org.lwjgl.opengl.GL11;

import java.util.List;

import static cubix.objects.Cubie.COLORS.BLUE;
import static cubix.objects.Cubie.COLORS.RED;

public class Trap extends GameObject {
    // Trap textures
    private static Texture blueT = new Texture("res\\BlueArea.png");
    private static Texture redT = new Texture("res\\RedArea.png");

    // Color this trap will affect
    private Cubie.COLORS color;

    // Any players stuck in this trap
    private List<Cubie> capturedCubies = new java.util.LinkedList<>();

    // Is the trap on?
    private boolean active = true;

    // Whether the trap starts as on or off
    private boolean initialState;

    public Trap (int x, int y, Cubie.COLORS col)
    {
        this.hitbox.setBounds(Game.ui.getWidth()/2+x* Cubix.cellSize, Game.ui.getHeight()/2+y*Cubix.cellSize,
                4*Cubix.cellSize, 4*Cubix.cellSize);
        this.color = col;

        this.r = 1f;
        this.g = 1f;
        this.b = 1f;
        this.initialState = true;
    }

    public Trap (int x, int y, Cubie.COLORS col, boolean initialState)
    {
        this.hitbox.setBounds(Game.ui.getWidth()/2+x*Cubix.cellSize, Game.ui.getHeight()/2+y*Cubix.cellSize,
                4*Cubix.cellSize, 4*Cubix.cellSize);
        this.color = col;
        this.r = 1f;
        this.g = 1f;
        this.b = 1f;
        this.initialState = initialState;
    }

    public Cubie.COLORS getColor() {
        return color;
    }

    public void draw()
    {
        // Set the appropriate texture
        switch (color)
        {
            case RED:
                redT.bind();
                break;
            default:
                blueT.bind();
                break;
        }
        // adjust to show off / on state
        float temp = 0;
        if (this.active)
            temp = 0.5f;

        // Draw portion of texture on the hitbox
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
        // Unfreeze any Cubie captured
        for (Cubie p : capturedCubies)
        {
            p.setKinematic(false);
        }

        // Toggle Cubies if any were freed
        if (capturedCubies.size() > 0)
            Level.togglePlayers();

        // Clear captured list
        capturedCubies.clear();

        // Set inactive
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
        active = initialState;
        capturedCubies.clear();
    }

    @Override
    public void update(int delta) {
        // How sticky the trap is
        // Higher tack means less distance the player has to be in before being stuck
        float tack = 0.6f;

        // Get the amount of 1D intersection that has to occur before the player is stuck
        float tackWidth = (1f-tack) * Level.getPlayer(RED).getHitbox().width;

        if (active)
        {
            // test if the Cubie of matching color intersects this Trap
            // and the amount of the intersection meets the tack requirement
                // if the matching Cubie is active, set the other player active
                // freeze the Cubie and add to the list of captures
                if (this.intersects(Level.getPlayer(this.color)))
                {
                    if (    this.intersection(Level.getPlayer(this.color)).width >= tackWidth &&
                            this.intersection(Level.getPlayer(this.color)).height >= tackWidth ) {
                        if (Level.getPlayer(this.color).isActive()) {
                            Level.togglePlayers();
                        }
                        Level.getPlayer(this.color).setKinematic(true);
                        capturedCubies.add(Level.getPlayer(this.color));
                    }
                }
                return;
        }
    }
}
