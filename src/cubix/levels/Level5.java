package cubix.levels;

import cubix.objects.*;

import static cubix.objects.Player.COLORS.BLUE;
import static cubix.objects.Player.COLORS.RED;

public class Level5 extends Level {
    public Level5()
    {
        //Environment creation
        platforms.add(new Platform(-11, -7, Platform.PlatformType.WHITE));
        platforms.add(new Platform(-16, -2, Platform.PlatformType.BLUE));
        platforms.add(new Platform(0, 0, Platform.PlatformType.GRAY));
        platforms.add(new Platform(+13, -2, Platform.PlatformType.RED));
        platforms.add(new Platform(+5, -5, Platform.PlatformType.WHITE));
        platforms.add(new Platform(-1, +6, Platform.PlatformType.BLUE));
        //platforms.add(new Wall(-5, -6, Platform.PlatformType.RED));

        traps.add(new Trap(-12, -1, RED, false));
        traps.add(new Trap(-5, +2, BLUE));
        traps.add(new Trap(+5, -9, RED));

        switches.add(new Switch(-15, -3, RED));
        switches.add(new Switch(+2, -1, RED));
        switches.add(new Switch(+14, -3, BLUE));

        blueExit = new Exit(+6, +2, BLUE);
        redExit = new Exit(-15, +5, RED);

        colliders.addAll(platforms);
        colliders.add(blueExit);
        colliders.add(redExit);

        //Player setup
        bluePlayer = new Player(-10, -9, BLUE);
        bluePlayer.setActive(true);
        colliders.add(bluePlayer);

        redPlayer =  new Player(+7, -7, RED);
        redPlayer.setActive(false);
        colliders.add(redPlayer);

        //Set each player's colliders
        bluePlayer.setColliders(colliders);
        redPlayer.setColliders(colliders);
    }
}
