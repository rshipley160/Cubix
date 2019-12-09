package cubix.objects;

import cubix.Cubix;
import edu.utc.game.*;
import org.lwjgl.opengl.GL11;

public class Button extends GameObject {
    // Texture for red and blue buttons
    private static Texture texture = new Texture("res\\buttons.png");

    // Text displayed on the button
    private Text text;

    // Color of the button
    private Cubie.COLORS color;

    public Button(int x, int y, int size, Cubie.COLORS color, String text)
    {
        // Set size
        this.hitbox.setBounds(Game.ui.getWidth()/2+ Cubix.cellSize*x,Game.ui.getHeight()/2+Cubix.cellSize*y,2*Cubix.cellSize*size,Cubix.cellSize*size);

        // Scale and position text to fit button
        int textSize = 10 * size;
        int xPos = Game.ui.getWidth()/2+Cubix.cellSize*x + (2*Cubix.cellSize*size-(text.length()*textSize/2+textSize))/2;
        this.text = new Text(xPos, Game.ui.getHeight()/2+Cubix.cellSize*y+(3*Cubix.cellSize*size)/8, textSize, textSize, text);

        // Set color
        this.color = color;
    }

    public boolean tryClick(int x, int y)
    {
        // Determine if the mouse is located in the bounds of the button
        if (x >= this.hitbox.x && x <= this.hitbox.x + this.hitbox.width &&
            y >= this.hitbox.y && y <= this.hitbox.y + this.hitbox.height)
        {
            return true;
        }
        return false;
    }

    @Override
    public void draw() {
        // Adjust the texture to show either the red or blue button
        float adjust = 0;
        if (color == Cubie.COLORS.RED)
            adjust = 0.5f;

        // Draw the texture selected
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

        //Draw the text on top of the button
        text.draw();
    }
}
