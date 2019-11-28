package objects;

import edu.utc.game.Game;
import edu.utc.game.GameObject;
import edu.utc.game.Texture;
import org.lwjgl.opengl.GL11;

public class Platform extends GameObject {
    private static Texture texture = new Texture("res\\platforms.png");
    private static int width = 128;
    private static int height = 32;
    private static int cellConversion = 32;

    public enum PlatformType {
        RED(0),
        WHITE(1),
        BLUE(2),
        GRAY(3);

        int num;

        PlatformType(int num) {this.num = num;}
    }

    private float num;
    public Platform(int cellX, int cellY, PlatformType type){

        this.hitbox.setBounds(Game.ui.getWidth()/2 + cellConversion*cellX, Game.ui.getHeight()/2+cellConversion*cellY,width,height);
        this.setColor(1f, 1f, 1f);
        this.num = type.num;
    }

    @Override
    public void draw() {
        GL11.glColor3f(1,1,1);
        texture.bind();
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glTexCoord2f(0f,num/4);
        GL11.glVertex2f(this.hitbox.x, this.hitbox.y);
        GL11.glTexCoord2f(1f,num/4);
        GL11.glVertex2f(this.hitbox.x+this.hitbox.width, this.hitbox.y);
        GL11.glTexCoord2f(1f,(num+1)/4);
        GL11.glVertex2f(this.hitbox.x+this.hitbox.width, this.hitbox.y+this.hitbox.height);
        GL11.glTexCoord2f(0f,(num+1)/4);
        GL11.glVertex2f(this.hitbox.x, this.hitbox.y+this.hitbox.height);
        GL11.glEnd();

        GL11.glBindTexture(GL11.GL_TEXTURE_2D,  0);
    }
}
