package threads;

import controllers.GameController;
import enums.GhostName;

import javax.swing.*;

public class DropPowerup implements Runnable {
    private boolean isRunning = true;
    private GameController gameController;
    private GhostName[] ghostNames;

    public DropPowerup(GameController gameController, GhostName[] ghostNames) {
        this.gameController = gameController;
        this.ghostNames = ghostNames;
    }

    @Override
    public void run() {
        while (isRunning) {

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }


            SwingUtilities.invokeLater(() -> {
                for (GhostName ghostName : ghostNames) {
                    if (ghostName != null)
                        gameController.dropPowerUp(ghostName);
                }
            });
        }
    }

    public void stop() {
        isRunning = false;
    }

    public void deleteGhost(GhostName ghostName) {
        for (int i = 0; i < ghostNames.length; i++) {
            if (ghostNames[i] == ghostName) {
                ghostNames[i] = null;
            }
        }
    }
}
