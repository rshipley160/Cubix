package cubix.objects;

import cubix.FinalProject;
import edu.utc.game.Game;
import edu.utc.game.GameObject;
import edu.utc.game.Sound;
import edu.utc.game.Texture;
import cubix.levels.Level;
import org.lwjgl.opengl.GL11;

public class Switch extends GameObject {
    private Texture texture = new Texture("res\\switches.png");
    private Player.COLORS color;
    private boolean isOn = false;
    private int timer = 0;
    private Player.COLORS touching = null;
    private Sound sound = new Sound("res\\switch.wav");

    public Switch (int x, int y, Player.COLORS color)
    {
        this.hitbox.setBounds(Game.ui.getWidth()/2+x*32,Game.ui.getHeight()/2+y*32, 32, 32);
        this.color = color;
        this.sound.setGain(0.2f);
    }

    public void toggle()
    {
        sound.play();
        if (this.isOn) {
            this.isOn = false;
        }
        else {
            this.isOn = true;

        }
        for (Trap t : FinalProject.currentLevel().getTraps())
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
        float adjust = 0;
        if (color == Player.COLORS.RED)
            adjust = 0.5f;
        if (isOn)
            adjust += 0.25f;
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
        Player blue = Level.getPlayer(Player.COLORS.BLUE);
        Player red = Level.getPlayer(Player.COLORS.RED);
        if (this.hitbox.intersects(blue.getHitbox())) {
            if (blue.isActive() &&
                    !blue.isKinematic() &&
                    touching == null) {
                toggle();
                touching = Player.COLORS.BLUE;
                return;
            }
        }
        else if (this.hitbox.intersects(red.getHitbox())) {
            if (red.isActive() &&
                    !red.isKinematic() &&
                    touching == null) {
                toggle();
                touching = Player.COLORS.RED;
                return;
            }
        }
        else
        {
            touching = null;
        }
    }
}
