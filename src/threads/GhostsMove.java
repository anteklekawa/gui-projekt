package threads;

import controllers.GameController;
import enums.GhostName;

import javax.swing.*;

public class GhostsMove implements Runnable {
    private GameController gameController;
    private boolean areGhostsMoving;
    private GhostName[] ghostNames;

    public GhostsMove(GameController gameController, GhostName[] ghostNames) {
        this.ghostNames = ghostNames;
        this.gameController = gameController;
        areGhostsMoving = true;
    }

    @Override
    public void run() {

        while (areGhostsMoving) {

            SwingUtilities.invokeLater(() -> {
                for (GhostName ghostName : ghostNames) {
                    if (ghostName != null)
                        gameController.ghostsMove(ghostName);
                }
            });

            try {
                if (gameController.getSlowPowerUp())
                    Thread.sleep(300);
                else
                    Thread.sleep(150);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void stop() {
        areGhostsMoving = false;
    }

    public void deleteGhost(GhostName ghostName) {
        for (int i = 0; i < ghostNames.length; i++) {
            if (ghostNames[i] == ghostName) {
                ghostNames[i] = null;
            }
        }
    }
}
