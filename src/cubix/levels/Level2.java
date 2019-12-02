package cubix.levels;

import cubix.FinalProject;
import cubix.objects.*;

public class Level2 extends Level {
    public Level2()
    {
        //Environment creation - platforms
        platforms.add(new Platform(-10, +2, Platform.PlatformType.WHITE));
        platforms.add(new Platform(-6, +2, Platform.PlatformType.BLUE));
        platforms.add(new Platform(-2, +2, Platform.PlatformType.RED));
        platforms.add(new Platform(+2, +2, Platform.PlatformType.BLUE));
        platforms.add(new Wall(-11, -2, Platform.PlatformType.GRAY));

        traps.add(new Trap(+2, -2, 4, Player.COLORS.RED));

        switches.add(new Switch(-9, +1, Player.COLORS.RED));

        blueExit = new Exit(+10, -1, Player.COLORS.BLUE);
        redExit = new Exit(+14, -1, Player.COLORS.RED);

        colliders.addAll(platforms);
        colliders.add(blueExit);
        colliders.add(redExit);

        //Player setup
        bluePlayer = new Player(-1, 0, Player.COLORS.BLUE);
        bluePlayer.setActive(true);
        colliders.add(bluePlayer);

        redPlayer =  new Player(+3, 0, Player.COLORS.RED);
        redPlayer.setActive(false);
        colliders.add(redPlayer);

        //Set each player's colliders
        bluePlayer.setColliders(colliders);
        redPlayer.setColliders(colliders);
    }

}
