package levels;

import static edu.utc.game.Game.ui;

import objects.*;


public class Level1 extends Level {
    public Level1()
    {
        //Environment creation
        platforms.add(new Platform(-8, -2, Platform.PlatformType.BLUE));
        platforms.add(new Platform(-8, +2, Platform.PlatformType.BLUE));
        platforms.add(new Platform(-4, +2, Platform.PlatformType.BLUE));
        platforms.add(new Platform(0, +2, Platform.PlatformType.RED));
        platforms.add(new Platform(+4, +2, Platform.PlatformType.RED));

        blueExit = new Exit(+10, -1, Player.COLORS.BLUE);
        redExit = new Exit(+14, -1, Player.COLORS.RED);

        colliders.addAll(platforms);
        colliders.add(blueExit);
        colliders.add(redExit);

        //Player setup
        bluePlayer = new Player(ui.getWidth()/2-75, ui.getHeight()/2-100, Player.COLORS.BLUE);
        bluePlayer.setActive(true);
        colliders.add(bluePlayer);

        redPlayer =  new Player(ui.getWidth()/2+25, ui.getHeight()/2-100, Player.COLORS.RED);
        redPlayer.setActive(false);
        colliders.add(redPlayer);

        //Set each player's colliders
        bluePlayer.setColliders(colliders);
        redPlayer.setColliders(colliders);
    }
}
