package cubix.objects;

import cubix.Cubix;
import edu.utc.game.*;
import org.lwjgl.opengl.GL11;
import java.awt.Rectangle;

public class Exit extends Collider {
    // Exit texture
    private static Texture texture = new Texture("res\\exits.png");

    // Cubie this exit corresponds to
    private Cubie.COLORS color;

    public Exit (int x, int y, Cubie.COLORS color)
    {
        // Set up the collider so that the hitbox is only the bottom half of the shape
        super(
                new Rectangle(Game.ui.getWidth()/2+x*Cubix.cellSize,Game.ui.getHeight()/2+y*Cubix.cellSize, 4*Cubix.cellSize, 2*Cubix.cellSize),
                new Rectangle(Game.ui.getWidth()/2+x*Cubix.cellSize,Game.ui.getHeight()/2+(y+1)*Cubix.cellSize, 4*Cubix.cellSize, Cubix.cellSize)
        );

        // Set the color
        this.color = color;
    }

    public Cubie.COLORS getColor() {
        return color;
    }

    @Override
    public void draw()
    {
        // Draw the texture
        GL11.glColor3f(1,1,1);
        texture.bind();
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glTexCoord2f(0f + color.adjust,0);
        GL11.glVertex2f(this.shape.x, this.shape.y);
        GL11.glTexCoord2f(0.5f + color.adjust,0);
        GL11.glVertex2f(this.shape.x+this.shape.width, this.shape.y);
        GL11.glTexCoord2f(0.5f + color.adjust,1);
        GL11.glVertex2f(this.shape.x+this.shape.width, this.shape.y+this.shape.height);
        GL11.glTexCoord2f(0f + color.adjust,1);
        GL11.glVertex2f(this.shape.x, this.shape.y+this.shape.height);
        GL11.glEnd();
        GL11.glBindTexture(GL11.GL_TEXTURE_2D,  0);
    }
}
