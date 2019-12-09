package cubix.scenes;

import cubix.objects.*;

import static cubix.objects.Cubie.COLORS.BLUE;
import static cubix.objects.Cubie.COLORS.RED;

public class Level5 extends Level {
    public Level5()
    {
        //Environment creation
        platforms.add(new Platform(-11, -7, Platform.PlatformType.WHITE));
        platforms.add(new Wall(-7, -11, Platform.PlatformType.GRAY));
        platforms.add(new Platform(-16, -2, Platform.PlatformType.BLUE));
        platforms.add(new Platform(0, 0, Platform.PlatformType.GRAY));
        platforms.add(new Platform(+13, -2, Platform.PlatformType.RED));
        platforms.add(new Platform(+5, -5, Platform.PlatformType.WHITE));
        platforms.add(new Platform(-1, +6, Platform.PlatformType.BLUE));
        //platforms.add(new Wall(-5, -6, Platform.PlatformType.RED));

        traps.add(new Trap(-12, -1, RED, false));
        traps.add(new Trap(-4, +1, BLUE));
        traps.add(new Trap(+5, -9, RED));

        switches.add(new Switch(-15, -3, RED));
        switches.add(new Switch(+2, -1, RED));
        switches.add(new Switch(+14, -3, BLUE));

        blueExit = new Exit(+6, +2, BLUE);
        redExit = new Exit(-15, +5, RED);

        colliders.addAll(platforms);
        colliders.add(blueExit);
        colliders.add(redExit);

        //Cubie setup
        blueCubie = new Cubie(-10, -9, BLUE);
        blueCubie.setActive(true);
        colliders.add(blueCubie);

        redCubie =  new Cubie(+7, -7, RED);
        redCubie.setActive(false);
        colliders.add(redCubie);

        //Set each player's colliders
        blueCubie.setColliders(colliders);
        redCubie.setColliders(colliders);

        startColor = BLUE;
    }
}
