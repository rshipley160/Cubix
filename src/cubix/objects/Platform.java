package cubix.objects;

import cubix.Cubix;
import edu.utc.game.Game;
import edu.utc.game.GameObject;
import edu.utc.game.Texture;
import org.lwjgl.opengl.GL11;

public class Platform extends GameObject {
    // Texture for all 4 platform types
    private static Texture texture = new Texture("res\\platforms.png");

    // Fixed width and height for all platforms
    private static final int width = 4*Cubix.cellSize;
    private static final int height = Cubix.cellSize;

    public enum PlatformType {
        RED(0),
        WHITE(1),
        BLUE(2),
        GRAY(3);

        float drawAdjust;

        PlatformType(int num) {this.drawAdjust = num/4f;}
    }

    // Selected color for this platform
    private PlatformType color;

    public Platform(int cellX, int cellY, PlatformType type){
        this.hitbox.setBounds(Game.ui.getWidth()/2 + Cubix.cellSize*cellX, Game.ui.getHeight()/2+Cubix.cellSize*cellY,width,height);
        this.setColor(1f, 1f, 1f);
        this.color = type;
    }

    @Override
    public void draw() {
        // Draw the corresponding portion of the platform texture over the hitbox
        GL11.glColor3f(1,1,1);
        texture.bind();
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glTexCoord2f(0f,color.drawAdjust);
        GL11.glVertex2f(this.hitbox.x, this.hitbox.y);
        GL11.glTexCoord2f(1f,color.drawAdjust);
        GL11.glVertex2f(this.hitbox.x+this.hitbox.width, this.hitbox.y);
        GL11.glTexCoord2f(1f,color.drawAdjust+0.25f);
        GL11.glVertex2f(this.hitbox.x+this.hitbox.width, this.hitbox.y+this.hitbox.height);
        GL11.glTexCoord2f(0f,color.drawAdjust+0.25f);
        GL11.glVertex2f(this.hitbox.x, this.hitbox.y+this.hitbox.height);
        GL11.glEnd();
        GL11.glBindTexture(GL11.GL_TEXTURE_2D,  0);
    }
}
