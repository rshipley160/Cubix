package cubix.scenes;

import static cubix.objects.Cubie.COLORS.BLUE;
import static cubix.objects.Cubie.COLORS.RED;

import cubix.objects.*;

public class Level4 extends Level {
    public Level4()
    {
        //Environment creation
        platforms.add(new Wall(-11,-9, Platform.PlatformType.BLUE));
        platforms.add(new Platform(-10, -5, Platform.PlatformType.WHITE));
        platforms.add(new Wall(-11,-2, Platform.PlatformType.GRAY));
        platforms.add(new Platform(-10, +1, Platform.PlatformType.BLUE));
        platforms.add(new Wall(+10,-9, Platform.PlatformType.RED));
        platforms.add(new Platform(+6, -5, Platform.PlatformType.GRAY));
        platforms.add(new Wall(+10,-2, Platform.PlatformType.WHITE));
        platforms.add(new Platform(+6, +1, Platform.PlatformType.RED));

        traps.add(new Trap(-6, -9, BLUE));
        traps.add(new Trap(-2, +4, RED));

        switches.add(new Switch(+8, 0, RED));
        switches.add(new Switch(+7, 0, BLUE));

        blueExit = new Exit(+13, +7, BLUE);
        redExit = new Exit(-2, +9, RED);

        colliders.addAll(platforms);
        colliders.add(blueExit);
        colliders.add(redExit);

        //Cubie setup
        blueCubie = new Cubie(-9, -7, BLUE);
        blueCubie.setActive(true);
        colliders.add(blueCubie);

        redCubie =  new Cubie(+7, -7, RED);
        redCubie.setActive(false);
        colliders.add(redCubie);

        //Set each player's colliders
        blueCubie.setColliders(colliders);
        redCubie.setColliders(colliders);

        startColor = RED;
    }
}
