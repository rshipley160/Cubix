package cubix.scenes;

import cubix.objects.*;

import static cubix.objects.Cubie.COLORS.BLUE;

public class Level3 extends Level {
    public Level3()
    {
        //Environment creation
        platforms.add(new Platform(-18, +7, Platform.PlatformType.RED));
        platforms.add(new Platform(-11, +4, Platform.PlatformType.BLUE));
        platforms.add(new Platform(-7, +4, Platform.PlatformType.GRAY));
        platforms.add(new Platform(+9, +1, Platform.PlatformType.BLUE));
        platforms.add(new Platform(+4, +2, Platform.PlatformType.WHITE));

        traps.add(new Trap(+4, -2,  Cubie.COLORS.RED));
        traps.add(new Trap(-7, +0,  BLUE));

        switches.add(new Switch(-16, +6, Cubie.COLORS.RED));
        switches.add(new Switch(+11, +0, BLUE));

        blueExit = new Exit(+0, +1, BLUE);
        redExit = new Exit(+14, -2, Cubie.COLORS.RED);

        colliders.addAll(platforms);
        colliders.add(blueExit);
        colliders.add(redExit);

        //Cubie setup
        blueCubie = new Cubie(-10, 0, BLUE);
        blueCubie.setActive(true);
        colliders.add(blueCubie);

        redCubie =  new Cubie(+5, 0, Cubie.COLORS.RED);
        redCubie.setActive(false);
        colliders.add(redCubie);

        //Set each player's colliders
        blueCubie.setColliders(colliders);
        redCubie.setColliders(colliders);

        startColor = BLUE;
    }
}
