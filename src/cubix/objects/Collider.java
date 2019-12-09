package cubix.objects;

import edu.utc.game.GameObject;
import org.lwjgl.opengl.GL11;
import java.awt.Rectangle;

/*
 * Class for GameObjects which have separate drawn shapes and physics colliders
 */
public class Collider extends GameObject {
    // The drawn shape of the object
    protected Rectangle shape;

    // Inherited hitbox is the physics collider

    public Collider(Rectangle shape, Rectangle collider)
    {
        this.shape = shape;
        this.hitbox = collider;
    }


    @Override
    public void draw() {
        GL11.glColor3f(r,g,b);

        //Draw the object based on shape and not hitbox
        float x=(float)shape.x;
        float y=(float)shape.y;
        float width=(float)shape.width;
        float height=(float)shape.height;


        GL11.glBegin(GL11.GL_QUADS);
        GL11.glVertex2f(x, y);
        GL11.glVertex2f(x+width, y);
        GL11.glVertex2f(x+width, y+height);
        GL11.glVertex2f(x, y+height);
        GL11.glEnd();
    }
}
