package objects;

import edu.utc.game.Game;
import edu.utc.game.GameObject;
import edu.utc.game.Texture;
import edu.utc.game.XYPair;
import org.lwjgl.opengl.GL11;

import static org.lwjgl.glfw.GLFW.*;

import java.util.List;

public class Player extends GameObject {
    private XYPair<Float> velocity;
    private XYPair<Float> accel = new XYPair<>(0f,5f);
    private float mass;
    public enum COLORS {RED, BLUE};
    final int size = 50;
    private COLORS color = COLORS.BLUE;
    private static Texture blueTexture = new Texture("res\\BluePlayer.png");
    private static Texture redTexture = new Texture("res\\RedPlayer.png");
    private Texture currentTexture = blueTexture;
    private boolean kinematic = false;
    private boolean active = true;
    private float speed = 0.5f;
    private boolean grounded = false;
    private List<GameObject> colliders = new java.util.LinkedList();
    {
        this.r = 1f;
        this.g = 1f;
        this.b = 1f;
        this.velocity = new XYPair<>(0f, 0f);
    }


    public Player(int x, int y){
        this.hitbox.setBounds(x,y,size,size);

    }

    public Player(int x, int y, COLORS color){
        this.hitbox.setBounds(x,y,size,size);
        this.color = color;
        this.active = false;
    }

    public void setColor(COLORS color)
    {
        this.color = color;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setColliders(List<GameObject> colliders)
    {
        this.colliders = colliders;
    }

    int timer = 0;
    @Override
    public void update(int delta) {
        double x = hitbox.getX();
        double y = hitbox.getY();

        Float time = delta / 1000f;

        if (this.kinematic)
            return;

        if (grounded)
        {
            accel.y = 0f;
            velocity.y = 0f;
        }
        else {
            accel.y = 5f;
        }

        // update velocity based on acceleration
        velocity.x += accel.x*time;
        velocity.y += accel.y*time;

        // update position based on velocity
        x += delta*velocity.x;
        y += delta*velocity.y;


        this.hitbox.setLocation((int)x, (int)y);

        if (Game.ui.keyPressed(GLFW_KEY_W))
        {
            if (grounded)
                this.velocity.y = -1.5f;
        }

        if (Game.ui.keyPressed(GLFW_KEY_A))
        {
            this.hitbox.translate((int)(-speed*delta), 0);
        }

        if (Game.ui.keyPressed(GLFW_KEY_D))
        {
            this.hitbox.translate((int)(speed*delta), 0);
        }

        grounded = false;

        checkCollisions();
    }

    public void checkCollisions()
    {
        int newX = this.hitbox.x;
        int newY = this.hitbox.y;
        int margin = 10;
        for (GameObject p : colliders)
        {
            if (p.equals(this))
                continue;
            if (this.intersects(p))
            {
                if (this.hitbox.y < p.getHitbox().y) {
                    this.accel.y = 0f;
                    this.velocity.y = 0f;

                    System.out.println("Fell onto something!");

                    newY = p.getHitbox().y - this.hitbox.height;

                    grounded = true;
                }

                else if (this.hitbox.y > p.getHitbox().y) {// && this.hitbox.y + this.hitbox.height > p.getHitbox().y  + p.getHitbox().height) {
                    this.velocity.y = 0f;

                    System.out.println("Hit my head!");

                    newY = p.getHitbox().y + p.getHitbox().height;

                }

            }
            if (this.hitbox.x < 0) {
                newX = 0;
            }
            if (this.hitbox.x + this.hitbox.width > Game.ui.getWidth()) {
                newX = Game.ui.getWidth() - this.hitbox.width;
            }
        }
        this.hitbox.setLocation(newX, newY);
    }

    public void draw()
    {
        switch (color) {
            case RED:
                currentTexture = redTexture;
                break;
            default:
                currentTexture = blueTexture;
        }

        float adjust = 0f;
        if (this.kinematic) {adjust = 0.5f;}

        GL11.glColor3f(1,1,1);
        currentTexture.bind();
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glTexCoord2f(0+adjust,0);
        GL11.glVertex2f(this.hitbox.x, this.hitbox.y);
        GL11.glTexCoord2f(0.5f+adjust,0);
        GL11.glVertex2f(this.hitbox.x+this.hitbox.width, this.hitbox.y);
        GL11.glTexCoord2f(0.5f+adjust,1);
        GL11.glVertex2f(this.hitbox.x+this.hitbox.width, this.hitbox.y+this.hitbox.height);
        GL11.glTexCoord2f(0+adjust,1);
        GL11.glVertex2f(this.hitbox.x, this.hitbox.y+this.hitbox.height);
        GL11.glEnd();

        GL11.glBindTexture(GL11.GL_TEXTURE_2D,  0);
    }

    public void freeze()
    {
        this.kinematic = true;
    }

    public void unfreeze()
    {
        this.kinematic = false;
    }

    public COLORS getColor() {
        return color;
    }
}
