package cubix.objects;

import cubix.Cubix;
import edu.utc.game.Game;
import edu.utc.game.GameObject;
import edu.utc.game.Texture;
import org.lwjgl.opengl.GL11;

public class Wall extends GameObject {
    // The four wall textures
    private static Texture texture = new Texture("res\\walls.png");
    // Set width and height of Walls
    private static int height = Cubix.cellSize*4;
    private static int width = Cubix.cellSize;

    private float colorAdjust;
    public Wall(int x, int y, Platform.PlatformType type){
        this.hitbox.setBounds(Game.ui.getWidth()/2+x*Cubix.cellSize,Game.ui.getHeight()/2+y*Cubix.cellSize,width,height);
        this.setColor(1f, 1f, 1f);
        this.colorAdjust = type.drawAdjust;
    }

    @Override
    public void draw() {
        GL11.glColor3f(1,1,1);
        texture.bind();
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glTexCoord2f(colorAdjust, 0f);
        GL11.glVertex2f(this.hitbox.x, this.hitbox.y);
        GL11.glTexCoord2f(colorAdjust+0.25f,0f);
        GL11.glVertex2f(this.hitbox.x+this.hitbox.width, this.hitbox.y);
        GL11.glTexCoord2f(colorAdjust+0.25f,1f);
        GL11.glVertex2f(this.hitbox.x+this.hitbox.width, this.hitbox.y+this.hitbox.height);
        GL11.glTexCoord2f(colorAdjust,1f);
        GL11.glVertex2f(this.hitbox.x, this.hitbox.y+this.hitbox.height);
        GL11.glEnd();
        GL11.glBindTexture(GL11.GL_TEXTURE_2D,  0);
    }
}
