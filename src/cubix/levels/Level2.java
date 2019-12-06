package cubix.levels;

import cubix.FinalProject;
import cubix.objects.*;

public class Level2 extends Level {
    public Level2()
    {
        //Environment creation - platforms
        platforms.add(new Platform(-11, +2, Platform.PlatformType.GRAY));
        platforms.add(new Platform(-4, +4, Platform.PlatformType.BLUE));
        platforms.add(new Platform(+0, +4, Platform.PlatformType.RED));
        platforms.add(new Platform(+6, +7, Platform.PlatformType.WHITE));

        traps.add(new Trap(-11, -2, 4, Player.COLORS.RED));

        switches.add(new Switch(+8, +6, Player.COLORS.RED));

        blueExit = new Exit(+13, +4, Player.COLORS.BLUE);
        redExit  = new Exit(-18, -2, Player.COLORS.RED);

        colliders.addAll(platforms);
        colliders.add(blueExit);
        colliders.add(redExit);

        //Player setup
        bluePlayer = new Player(+1, 0, Player.COLORS.BLUE);
        bluePlayer.setActive(true);
        colliders.add(bluePlayer);

        redPlayer =  new Player(-3, 0, Player.COLORS.RED);
        redPlayer.setActive(false);
        colliders.add(redPlayer);

        //Set each player's colliders
        bluePlayer.setColliders(colliders);
        redPlayer.setColliders(colliders);
    }

}
