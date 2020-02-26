package cubix.objects;

import cubix.Cubix;
import cubix.scenes.Level;
import edu.utc.game.*;
import org.lwjgl.opengl.GL11;
import static org.lwjgl.glfw.GLFW.*;

import java.awt.Rectangle;
import java.util.List;

public class Cubie extends GameObject {
    public enum COLORS {
        BLUE(0),
        RED(1);

        float adjust;

        COLORS(int num) {
            this.adjust = num/2f;
        }
    }

    // Cubie textures
    private static Texture blueTexture = new Texture("res\\BluePlayer.png");
    private static Texture redTexture = new Texture("res\\RedPlayer.png");

    // Jump sound
    private static Sound jump = new Sound("res\\jump.wav");

    // Representation of the force of gravity
    private static final float gravity = 3.5f;

    // Size of Cubies
    private static final int size = Math.round(1.5f*Cubix.cellSize);

    // Lateral speed of Cubies
    private static float speed = 0.5f;

    // velocity and acceleration in both directions
    private XYPair<Float> velocity;
    private XYPair<Float> accel = new XYPair<>(0f,gravity);

    // distance the player will be moved in each direction
    public XYPair<Double> delta = new XYPair(0,0);

    // Red or blue?
    private COLORS color;

    // Is the player frozen (no movement, physics disabled) ?
    private boolean kinematic = false;

    // Is the player active?
    // Inactive players cannot be moved be do use physics
    private boolean active = true;

    // Is the player touching a solid surface?
    private boolean grounded = false;

    // All of the colliders we want the player to interact with
    private List<GameObject> colliders = new java.util.LinkedList();

    // Where the player will spawn at
    private XYPair<Integer> startPos = new XYPair<>(0,0);

    // Is the player standing on the level exit?
    private boolean onExit = false;

////        Constructors / Initialization       \\\\
    // Init block - for all instantiations
    {
        this.r = 1f;
        this.g = 1f;
        this.b = 1f;
        this.velocity = new XYPair<>(0f, 0f);
    }

    static
    {
        // Lower jump volume
        jump.setGain(0.3f);
    }

    public Cubie(int x, int y, COLORS color){
        this.hitbox.setBounds(Game.ui.getWidth()/2+x*Cubix.cellSize,Game.ui.getHeight()/2+y*Cubix.cellSize,size,size);
        startPos.x = Game.ui.getWidth()/2+x*Cubix.cellSize;
        startPos.y = Game.ui.getHeight()/2+y*Cubix.cellSize;
        this.color = color;
        this.active = true;
    }

////            Getters                 \\\
    public COLORS getColor() {
    return color;
}

    @Override
    public boolean isActive() { return active; }

    public boolean isKinematic() { return kinematic; }

    public boolean onExit() { return onExit; }

////            Setters                 \\\\

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setColliders(List<GameObject> colliders) {  this.colliders = colliders; }

    public void setKinematic(boolean status) {
        kinematic = status;
    }

////            Cubie mechanics         \\\\

    public void respawn()
    {
        this.hitbox.x = startPos.x;
        this.hitbox.y = startPos.y;
        this.velocity.x = 0f;
        this.velocity.y = 0f;
        this.kinematic=false;
    }

    @Override
    public void update(int delta) {
        // If the player leaves the screen...
        if (this.hitbox.x < 0 || this.hitbox.x > Game.ui.getWidth() || this.hitbox.y > Game.ui.getHeight()) {
            // Play the reset sound and reset the level
            Level.reset.play();
            Cubix.currentLevel().reset();
        }

        // If the player is frozen
        if (this.kinematic) {
            // Don't accumulate gravity
            grounded = true;
            // Don't do any further update
            return;
        }

        // If the player is on top of something
        if (grounded)
        {
            // Don't apply gravity anymore
            accel.y = 0f;
            velocity.y = 0f;
        }
        else {
            // otherwise use gravity
            accel.y = gravity;
        }

        Float time = delta / 1000f;
        // update velocity based on acceleration
        velocity.x += accel.x*time;
        velocity.y += accel.y*time;

        // If the player is moveable and W is pressed
        if (this.active && Game.ui.keyPressed(GLFW_KEY_UP))
        {
            // make sure we are grounded...
            if (grounded) {
                // Play jump sound and apply upward velocity
                jump.play();
                this.velocity.y = -0.85f;
            }
        }

        // update position based on velocity
        this.delta.x = (double) delta*velocity.x;
        this.delta.y = (double) delta*velocity.y;

        // Set grounded to false - will be set to true when collision detection happens if needed
        grounded = false;

        // Handle left-right movement by adjusting distance moved in x direction
        if (this.active && Game.ui.keyPressed(GLFW_KEY_LEFT))
        {
            this.delta.x -= speed * delta;
        }

        if (this.active && Game.ui.keyPressed(GLFW_KEY_RIGHT))
        {
            this.delta.x += speed * delta;
        }

        // Apply movement in x-direction...
        this.translate(this.delta.x.intValue(),0);
        /// then y-direction. Doing them at the same time causes collision issues.
        // Y-direction has to be done last to detect if the Cubie is on an exit
        this.translate(0,this.delta.y.intValue());
    }

    public void translate(int x, int y)
    {
        // Make a rectangle that is where the Cubie is trying to move to
        Rectangle test = new Rectangle(this.hitbox.x + x, this.hitbox.y+y, this.hitbox.width, this.hitbox.height);
        int deltaX = x; int deltaY = y;
        test.translate(deltaX, deltaY);

        // We are not on the exit until it is proven that we are
        onExit = false;

        for (GameObject coll : colliders)
        {
            // Test for intersection with every collider in the Scene...
            if (test.intersects(coll.getHitbox()))
            {
                // Except for this Cubie
                if (coll.equals(this)) { continue;}

                // get the hitbox of the object we collide with
                Rectangle hit = coll.getHitbox();

                // if the bottom of the test square is lower than the top of the object
                // and we're moving in the positive y-direction (down)
                if (test.y + test.height > hit.y && deltaY > 0)
                {
                    // We've hit the top of the object

                    // Set the Cubie on top of the object (not inside it)
                    deltaY = hit.y - this.hitbox.y - this.hitbox.height;
                    // Register that we are on a solid surface
                    grounded = true;
                }
                // if the bottom of the test square is lower than the bottom of the object
                // and we're moving in the negative y-direction (up)
                else if (test.y + test.height > hit.y + hit.height && deltaY < 0)
                {
                    // We've hit the bottom of the object

                    // Set the Cubie just underneath the object
                    deltaY = hit.y + hit.height - this.hitbox.y;
                    // Remove all upward velocity so we don't keep trying to hit the object
                    this.velocity.y = 0f;
                }
                // if the right of the test square is to the right of the object's left side
                // and we're moving in the positive x-direction
                if (test.x + test.width > hit.x && deltaX > 0)
                {
                    // We've hit the left side of the object

                    // Set the Cubie just to the left of the object
                    deltaX = hit.x - this.hitbox.x  - this.hitbox.width;
                }

                // if the left of the test square is to the left of the right side of the object
                // and we're moving in the negative x-direction
                if (test.x < hit.x + hit.width && deltaX < 0)
                {
                    // We've hit the left side of the object

                    // Set the Cubie to the right of the object
                    deltaX = hit.x + hit.width - this.hitbox.x;
                }

                // If the thing we hit was an exit...
                if (coll.getClass()== Exit.class)
                {
                    Exit exit = (Exit) coll;
                    // and it matches our color and we are within its bounds on the x-axis
                    if (    exit.getColor() == this.color   &&
                            this.hitbox.x >= exit.getHitbox().x  &&
                            this.hitbox.x + this.hitbox.width <= exit.getHitbox().x+exit.getHitbox().width)
                    {
                        // We must be standing on it
                        onExit = true;
                    }
                }
            }
        }
        // translate the Cubie by the adjusted delta
        this.hitbox.translate(deltaX, deltaY);
    }

    public void draw()
    {
        // Bind the appropriate texture
        Texture currentTexture;
        if (color == COLORS.RED) {
            redTexture.bind();
        } else {
            blueTexture.bind();;
        }

        // Show the inactive texture if frozen or inactive
        float adjust = 0f;
        if (this.kinematic | !this.active) {adjust = 0.5f;}

        // Draw the texture
        GL11.glColor3f(1,1,1);
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
}
