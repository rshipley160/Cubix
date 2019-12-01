package cubix.levels;

import static cubix.objects.Player.COLORS.BLUE;
import static cubix.objects.Player.COLORS.RED;

import cubix.objects.*;
import edu.utc.game.Scene;


public class Level1 extends Level {
    public Level1()
    {
        platforms.clear();
        //Environment creation
        platforms.add(new Platform(-8, -1, Platform.PlatformType.BLUE));
        platforms.add(new Platform(-8, +2, Platform.PlatformType.GRAY));
        platforms.add(new Platform(-4, +2, Platform.PlatformType.WHITE));
        platforms.add(new Platform(0, +2, Platform.PlatformType.WHITE));
        platforms.add(new Platform(+4, +2, Platform.PlatformType.GRAY));

        traps.clear();
        traps.add(new Trap(-8, -5, 4, BLUE));
        traps.add(new Trap(0, -2, 4, RED));

        switches.clear();
        switches.add(new Switch(-3, +1, RED));
        switches.add(new Switch(-6, +1, BLUE));

        blueExit = new Exit(+10, -1, BLUE);
        redExit = new Exit(+14, -1, RED);

        colliders.clear();
        colliders.addAll(platforms);
        colliders.add(blueExit);
        colliders.add(redExit);

        //Player setup
        bluePlayer = new Player(-1, 0, BLUE);
        bluePlayer.setActive(true);
        colliders.add(bluePlayer);

        redPlayer =  new Player(+1, 0, RED);
        redPlayer.setActive(false);
        colliders.add(redPlayer);

        //Set each player's colliders
        bluePlayer.setColliders(colliders);
        redPlayer.setColliders(colliders);

        bluePlayer.respawn();
        redPlayer.respawn();
    }

    @Override
    public Scene drawFrame(int delta) {
        System.out.println("Level 1");
        return super.drawFrame(delta);
    }
}
