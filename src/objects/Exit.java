package objects;

import edu.utc.game.Game;
import edu.utc.game.GameObject;
import edu.utc.game.Texture;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class Exit extends Collider {
    private int cellConversion = 32;
    private Texture texture = new Texture("res\\exits.png");
    private Player.COLORS color = Player.COLORS.RED;
    private static int ID = 1;
    private String tag;

    public Exit (int x, int y, Player.COLORS color)
    {
        super(
                new Rectangle(Game.ui.getWidth()/2+x*32,Game.ui.getHeight()/2+y*32, 128, 64),
                new Rectangle(Game.ui.getWidth()/2+x*32,Game.ui.getHeight()/2+(y+1)*32, 128, 32)
        );
        this.color = color;
    }

    public Player.COLORS getColor() {
        return color;
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
        GL11.glVertex2f(this.shape.x, this.shape.y);
        GL11.glTexCoord2f(0.5f + adjust,0);
        GL11.glVertex2f(this.shape.x+this.shape.width, this.shape.y);
        GL11.glTexCoord2f(0.5f + adjust,1);
        GL11.glVertex2f(this.shape.x+this.shape.width, this.shape.y+this.shape.height);
        GL11.glTexCoord2f(0f + adjust,1);
        GL11.glVertex2f(this.shape.x, this.shape.y+this.shape.height);
        GL11.glEnd();

        GL11.glBindTexture(GL11.GL_TEXTURE_2D,  0);
    }
}
