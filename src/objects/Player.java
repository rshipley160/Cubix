package objects;

import edu.utc.game.GameObject;

public class Player extends GameObject {
    public enum COLORS {RED, BLUE};
    final int size = 50;
    private COLORS color = COLORS.BLUE;

    public Player(int x, int y){
        this.hitbox.setBounds(x,y,size,size);
        this.r = 1f;
        this.g = 1f;
        this.b = 1f;
        this.setColor(color);
    }

    public void setColor(COLORS color) {
        this.color = color;
        switch (color)
        {
            case RED:
                this.setTexture("res\\RedPlayer.png");
                break;
            default:
                this.setTexture("res\\BluePayer.png");
                break;
        }
    }
}
