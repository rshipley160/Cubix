package objects;

import edu.utc.game.Game;
import edu.utc.game.GameObject;
import edu.utc.game.Texture;
import org.lwjgl.opengl.GL11;

public class Wall extends GameObject {
    private static Texture texture = new Texture("res\\walls.png");
    private static int height = Game.ui.getHeight()/4;
    private static int width = height/4;


    private float num;
    public Wall(int x, int y, Platform.PlatformType type){
        this.hitbox.setBounds(x,y,width,height);
        this.setColor(1f, 1f, 1f);
        this.num = type.num;
    }

    @Override
    public void draw() {
        GL11.glColor3f(1,1,1);
        texture.bind();
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glTexCoord2f(num/4, 0f);
        GL11.glVertex2f(this.hitbox.x, this.hitbox.y);
        GL11.glTexCoord2f((num+1)/4,0f);
        GL11.glVertex2f(this.hitbox.x+this.hitbox.width, this.hitbox.y);
        GL11.glTexCoord2f((num+1)/4,1f);
        GL11.glVertex2f(this.hitbox.x+this.hitbox.width, this.hitbox.y+this.hitbox.height);
        GL11.glTexCoord2f(num/4,1f);
        GL11.glVertex2f(this.hitbox.x, this.hitbox.y+this.hitbox.height);
        GL11.glEnd();

        GL11.glBindTexture(GL11.GL_TEXTURE_2D,  0);
    }
}
