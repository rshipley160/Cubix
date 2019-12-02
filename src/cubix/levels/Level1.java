package cubix.levels;

import static cubix.objects.Player.COLORS.BLUE;
import static cubix.objects.Player.COLORS.RED;

import cubix.FinalProject;
import cubix.objects.*;

public class Level1 extends Level {
    public Level1()
    {
        //Environment creation
        platforms.add(new Platform(-10, +2, Platform.PlatformType.GRAY));
        platforms.add(new Platform(-6, +2, Platform.PlatformType.BLUE));
        platforms.add(new Platform(-2, +2, Platform.PlatformType.GRAY));
        platforms.add(new Wall(-11, -2, Platform.PlatformType.RED));

        blueExit = new Exit(+6, -1, BLUE);
        redExit = new Exit(+10, -1, RED);

        colliders.addAll(platforms);
        colliders.add(blueExit);
        colliders.add(redExit);

        //Player setup
        bluePlayer = new Player(-4, 0, BLUE);
        bluePlayer.setActive(true);
        colliders.add(bluePlayer);

        redPlayer =  new Player(-1, 0, RED);
        redPlayer.setActive(false);
        colliders.add(redPlayer);

        //Set each player's colliders
        bluePlayer.setColliders(colliders);
        redPlayer.setColliders(colliders);
    }
}
