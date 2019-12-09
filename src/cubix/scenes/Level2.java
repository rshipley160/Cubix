package cubix.scenes;

import cubix.objects.*;

import static cubix.objects.Cubie.COLORS.BLUE;

public class Level2 extends Level {
    public Level2()
    {
        //Environment creation - platforms
        platforms.add(new Platform(-11, +2, Platform.PlatformType.GRAY));
        platforms.add(new Platform(-4, +4, Platform.PlatformType.BLUE));
        platforms.add(new Platform(+0, +4, Platform.PlatformType.RED));
        platforms.add(new Platform(+6, +7, Platform.PlatformType.WHITE));

        traps.add(new Trap(-11, -2, Cubie.COLORS.RED));

        switches.add(new Switch(+8, +6, Cubie.COLORS.RED));

        blueExit = new Exit(+13, +4, BLUE);
        redExit  = new Exit(-18, -2, Cubie.COLORS.RED);

        colliders.addAll(platforms);
        colliders.add(blueExit);
        colliders.add(redExit);

        //Cubie setup
        blueCubie = new Cubie(+1, 0, BLUE);
        blueCubie.setActive(true);
        colliders.add(blueCubie);

        redCubie =  new Cubie(-3, 0, Cubie.COLORS.RED);
        redCubie.setActive(false);
        colliders.add(redCubie);

        //Set each player's colliders
        blueCubie.setColliders(colliders);
        redCubie.setColliders(colliders);

        startColor = BLUE;
    }

}
