package objects;

import edu.utc.game.GameObject;

public class Platform extends GameObject {
    public Platform(int x, int y, int width, int height){
        this.hitbox.setBounds(x,y,width,height);
        this.setColor(0.5f, 0.5f, 0.5f);
    }
}
