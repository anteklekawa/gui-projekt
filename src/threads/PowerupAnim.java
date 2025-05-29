package threads;

import components.GameTable;
import controllers.GameController;
import models.GameModel;

import javax.swing.*;

public class PowerupAnim implements Runnable {
    private boolean isRunning = true;
    private GameController gameController;
    private static GameTable gameTable;
    private boolean powerUpFrame = true;

    public PowerupAnim(GameController gameController) {
        gameTable = gameController.getGameTable();
        this.gameController = gameController;
    }

    @Override
    public void run() {
        while (isRunning) {
            try {
                while (isRunning) {
                    nextFrame();

                    SwingUtilities.invokeLater(() -> gameTable.fireTableDataChanged());

                    Thread.sleep(150);
                }
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void stop() {
        isRunning = false;
    }

    public void nextFrame() {
        powerUpFrame = !powerUpFrame;

        gameController.setPowerUpFrame(powerUpFrame);
    }
}
