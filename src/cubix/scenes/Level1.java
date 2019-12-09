package cubix.scenes;

import static cubix.objects.Cubie.COLORS.BLUE;
import static cubix.objects.Cubie.COLORS.RED;

import cubix.objects.*;

public class Level1 extends Level {
    public Level1()
    {
        //Environment creation
        platforms.add(new Platform(-8, +2, Platform.PlatformType.BLUE));
        platforms.add(new Platform(-2, +2, Platform.PlatformType.GRAY));

        blueExit = new Exit(+6, -1, BLUE);
        redExit = new Exit(+10, -1, RED);

        colliders.addAll(platforms);
        colliders.add(blueExit);
        colliders.add(redExit);

        //Cubie setup
        blueCubie = new Cubie(-7, 0, BLUE);
        blueCubie.setActive(true);
        colliders.add(blueCubie);

        redCubie =  new Cubie(-1, 0, RED);
        redCubie.setActive(false);
        colliders.add(redCubie);

        //Set each player's colliders
        blueCubie.setColliders(colliders);
        redCubie.setColliders(colliders);

        startColor = RED;
    }
}
