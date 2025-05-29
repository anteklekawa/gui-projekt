package threads;

import controllers.GameController;
import enums.GhostName;

import javax.swing.*;

public class DropPowerup implements Runnable {
    private boolean isRunning = true;
    private GameController gameController;
    private GhostName ghostName;

    public DropPowerup(GameController gameController, GhostName ghostName) {
        this.gameController = gameController;
        this.ghostName = ghostName;
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
                gameController.dropPowerUp(ghostName);
            });
        }
    }

    public void stop() {
        isRunning = false;
    }
}
