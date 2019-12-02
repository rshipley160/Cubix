package cubix.levels;

import cubix.objects.*;

public class Level2 extends Level {
    public Level2()
    {
        //Environment creation - platforms
        platforms.add(new Platform(-8, -1, Platform.PlatformType.GRAY));
        platforms.add(new Platform(-8, +2, Platform.PlatformType.GRAY));
        platforms.add(new Platform(-4, +2, Platform.PlatformType.GRAY));
        platforms.add(new Platform(0, +2, Platform.PlatformType.GRAY));
        platforms.add(new Platform(+4, +2, Platform.PlatformType.GRAY));

        blueExit = new Exit(+10, -1, Player.COLORS.BLUE);
        redExit = new Exit(+14, -1, Player.COLORS.RED);

        colliders.addAll(platforms);
        colliders.add(blueExit);
        colliders.add(redExit);

        //Player setup
        bluePlayer = new Player(-1, 0, Player.COLORS.BLUE);
        bluePlayer.setActive(true);
        colliders.add(bluePlayer);

        redPlayer =  new Player(+1, 0, Player.COLORS.RED);
        redPlayer.setActive(false);
        colliders.add(redPlayer);

        //Set each player's colliders
        bluePlayer.setColliders(colliders);
        redPlayer.setColliders(colliders);
    }

}
