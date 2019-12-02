package cubix.objects;

import cubix.FinalProject;
import cubix.levels.Menu;
import edu.utc.game.*;

public class Button extends GameObject {
    private Text text;
    private Scene scene;
    public Button(int x, int y, String text, Scene scene)
    {
        this.hitbox.setBounds(Game.ui.getWidth()/2+32*x,Game.ui.getHeight()/2+32*y,64,16);
        int textX = Game.ui.getWidth()/2+32*x + 64 - text.length()*20;
        this.text = new Text(textX, Game.ui.getHeight()/2+32*y, 20, 20, text);
    }

    public boolean tryClick(int x, int y)
    {
        if (x >= this.hitbox.x && x <= this.hitbox.x + this.hitbox.width &&
            y >= this.hitbox.y && y <= this.hitbox.y + this.hitbox.height)
        {
            return true;
        }
        return false;
    }
}
