package objects;

import edu.utc.game.Game;
import edu.utc.game.GameObject;
import edu.utc.game.Texture;
import org.lwjgl.opengl.GL11;

public class Exit extends GameObject {
    private int cellConversion = 32;
    private Texture texture = new Texture("res\\exits.png");
    private Player.COLORS color = Player.COLORS.RED;


    public Exit (int x, int y, Player.COLORS color)
    {
        this.hitbox.setBounds(Game.ui.getWidth()/2+x*cellConversion,Game.ui.getHeight()/2+y*cellConversion, 128, 64);
        this.color = color;
    }

    @Override
    public void draw()
    {
        float adjust = 0;
        if (color == Player.COLORS.RED)
            adjust = 0.5f;
        GL11.glColor3f(1,1,1);
        texture.bind();
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glTexCoord2f(0f + adjust,0);
        GL11.glVertex2f(this.hitbox.x, this.hitbox.y);
        GL11.glTexCoord2f(0.5f + adjust,0);
        GL11.glVertex2f(this.hitbox.x+this.hitbox.width, this.hitbox.y);
        GL11.glTexCoord2f(0.5f + adjust,1);
        GL11.glVertex2f(this.hitbox.x+this.hitbox.width, this.hitbox.y+this.hitbox.height);
        GL11.glTexCoord2f(0f + adjust,1);
        GL11.glVertex2f(this.hitbox.x, this.hitbox.y+this.hitbox.height);
        GL11.glEnd();

        GL11.glBindTexture(GL11.GL_TEXTURE_2D,  0);
    }
}
