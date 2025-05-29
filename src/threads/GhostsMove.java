package threads;

import controllers.GameController;
import enums.GhostName;

import javax.swing.*;

public class GhostsMove implements Runnable {
    private GameController gameController;
    private boolean areGhostsMoving;
    private GhostName ghostName;

    public GhostsMove(GameController gameController, GhostName ghostName) {
        this.ghostName = ghostName;
        this.gameController = gameController;
        areGhostsMoving = true;
    }

    @Override
    public void run() {

        while (areGhostsMoving) {

            SwingUtilities.invokeLater(() -> {
                gameController.ghostsMove(ghostName);
            });

            try {
                Thread.sleep(150);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void stop() {
        areGhostsMoving = false;
    }
}
