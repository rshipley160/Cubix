package cubix.objects;

import cubix.Cubix;
import edu.utc.game.*;
import cubix.scenes.Level;
import org.lwjgl.opengl.GL11;

public class Switch extends GameObject {
    // Switch texture - holds switches of both colors and both on and off positions
    private static Texture texture = new Texture("res\\switches.png");
    // Switch sound
    private static Sound sound = new Sound("res\\switch.wav");

    // Determines what color traps this switch will control
    private Cubie.COLORS color;

    // is the switch on?
    private boolean isOn = false;

    // is there a Cubie touching this switch?
    private Cubie.COLORS touching = null;

    // Set sound volume
    static
    {
        sound.setGain(0.2f);
    }

    public Switch (int x, int y, Cubie.COLORS color)
    {
        this.hitbox.setBounds(Game.ui.getWidth()/2+x*32,Game.ui.getHeight()/2+y*Cubix.cellSize, Cubix.cellSize, Cubix.cellSize);
        this.color = color;
    }

    public void toggle()
    {
        // Play the switch sound
        sound.play();

        // toggle the state of the switch
        if (this.isOn) {
            this.isOn = false;
        }
        else {
            this.isOn = true;
        }

        // toggle all traps of matching color
        for (Trap t : Cubix.currentLevel().getTraps())
        {
            if (t.getColor() == this.color) {
                t.toggle();
            }
        }
    }

    public void turnOn(boolean on)
    {
        this.isOn = on;
    }

    @Override
    public void draw()
    {
        // Adjust to set color
        float adjust = color.adjust;

        // Then adjust for on off state
        if (isOn)
            adjust += 0.25f;

        // Draw the selected portion of the texture on the hitbox
        GL11.glColor3f(1,1,1);
        texture.bind();
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glTexCoord2f(0f + adjust,0);
        GL11.glVertex2f(this.hitbox.x, this.hitbox.y);
        GL11.glTexCoord2f(0.25f + adjust,0);
        GL11.glVertex2f(this.hitbox.x+this.hitbox.width, this.hitbox.y);
        GL11.glTexCoord2f(0.25f + adjust,1);
        GL11.glVertex2f(this.hitbox.x+this.hitbox.width, this.hitbox.y+this.hitbox.height);
        GL11.glTexCoord2f(0f + adjust,1);
        GL11.glVertex2f(this.hitbox.x, this.hitbox.y+this.hitbox.height);
        GL11.glEnd();
        GL11.glBindTexture(GL11.GL_TEXTURE_2D,  0);
    }

    @Override
    public void update(int delta) {
        // Get the players
        Cubie blue = Level.getPlayer(Cubie.COLORS.BLUE);
        Cubie red = Level.getPlayer(Cubie.COLORS.RED);

        // If the switch's hit box intersects either player and the player is active
            //toggle the switch state and note that they are touching the switch
            //which prevents them from doing multiple toggles with one touch
        if (this.hitbox.intersects(blue.getHitbox())) {
            if (blue.isActive() &&
                    !blue.isKinematic() &&
                    touching == null) {
                toggle();
                touching = Cubie.COLORS.BLUE;
                return;
            }
        }
        else if (this.hitbox.intersects(red.getHitbox())) {
            if (red.isActive() &&
                    !red.isKinematic() &&
                    touching == null) {
                toggle();
                touching = Cubie.COLORS.RED;
                return;
            }
        }
        // If no one is touching, set that so the switch can be used again
        else
        {
            touching = null;
        }
    }
}
