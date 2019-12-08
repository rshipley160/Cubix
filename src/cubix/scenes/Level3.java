package cubix.scenes;

import cubix.objects.*;

public class Level3 extends Level {
    public Level3()
    {
        //Environment creation
        platforms.add(new Platform(-18, +7, Platform.PlatformType.RED));
        platforms.add(new Platform(-11, +4, Platform.PlatformType.BLUE));
        platforms.add(new Platform(-7, +4, Platform.PlatformType.GRAY));
        platforms.add(new Platform(+9, +1, Platform.PlatformType.BLUE));
        platforms.add(new Platform(+4, +2, Platform.PlatformType.WHITE));

        traps.add(new Trap(+4, -2,  Player.COLORS.RED));
        traps.add(new Trap(-7, +0,  Player.COLORS.BLUE));

        switches.add(new Switch(-16, +6, Player.COLORS.RED));
        switches.add(new Switch(+11, +0, Player.COLORS.BLUE));

        blueExit = new Exit(+0, +1, Player.COLORS.BLUE);
        redExit = new Exit(+14, -2, Player.COLORS.RED);

        colliders.addAll(platforms);
        colliders.add(blueExit);
        colliders.add(redExit);

        //Player setup
        bluePlayer = new Player(-10, 0, Player.COLORS.BLUE);
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
