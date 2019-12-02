package cubix.levels;

import cubix.FinalProject;
import cubix.objects.Button;
import edu.utc.game.*;
import org.lwjgl.opengl.GL11;

import java.util.List;

public class Menu implements Scene {
    private List<Button> buttons = new java.util.ArrayList<>();
    public Menu()
    {
        buttons.add(new Button(+3, -4, "Start", FinalProject.scenes().get(1)));
        buttons.get(0).setColor(0.7f, 0.7f, 1f);
    }

    public Scene drawFrame(int delta)
    {

        return this;
    }
}
