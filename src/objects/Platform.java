package objects;

import edu.utc.game.GameObject;

public class Platform extends GameObject {
    public Platform(int x, int y, int width, int height){
        this.hitbox.setBounds(x,y,width,height);
    }

    @Override
    public void setColor(float r, float g, float b) {
        super.setColor(r, g, b);
    }
}
