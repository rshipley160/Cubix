package cubix.objects;

import edu.utc.game.GameObject;
import org.lwjgl.opengl.GL11;

import java.awt.Rectangle;

public class Collider extends GameObject {
    protected Rectangle shape;

    public Collider(Rectangle shape, Rectangle collider)
    {
        this.shape = shape;
        this.hitbox = collider;
    }

    public void setCollider(Rectangle collider) {
        this.hitbox = collider;
    }

    public Rectangle getCollider() {
        return hitbox;
    }

    @Override
    public void draw() {
        GL11.glColor3f(r,g,b);

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
