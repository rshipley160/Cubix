package cubix.levels;

import cubix.FinalProject;
import cubix.objects.*;
import edu.utc.game.Scene;

public class Level3 extends Level {
    public Level3()
    {
        //Environment creation
        platforms.add(new Platform(-12, +2, Platform.PlatformType.RED));
        platforms.add(new Platform(-8, +2, Platform.PlatformType.BLUE));
        platforms.add(new Platform(-4, +2, Platform.PlatformType.GRAY));
        platforms.add(new Platform(0, +2, Platform.PlatformType.BLUE));
        platforms.add(new Platform(+4, +2, Platform.PlatformType.WHITE));

        traps.add(new Trap(+4, -2, 4, Player.COLORS.RED));
        traps.add(new Trap(-4, -2, 4, Player.COLORS.BLUE));

        switches.add(new Switch(-10, +1, Player.COLORS.RED));
        switches.add(new Switch(+2, +1, Player.COLORS.BLUE));

        blueExit = new Exit(+10, -1, Player.COLORS.BLUE);
        redExit = new Exit(+14, -1, Player.COLORS.RED);

        colliders.addAll(platforms);
        colliders.add(blueExit);
        colliders.add(redExit);

        //Player setup
        bluePlayer = new Player(-6, 0, Player.COLORS.BLUE);
        bluePlayer.setActive(true);
        colliders.add(bluePlayer);

        redPlayer =  new Player(+5, 0, Player.COLORS.RED);
        redPlayer.setActive(false);
        colliders.add(redPlayer);

        //Set each player's colliders
        bluePlayer.setColliders(colliders);
        redPlayer.setColliders(colliders);
    }
}
