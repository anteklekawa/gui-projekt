package threads;

import controllers.GameController;

import javax.swing.*;

public class PacmanMove extends Thread {
    private GameController gameController;
    private boolean isPacmanMoving;

    public PacmanMove(GameController gameController) {
        this.gameController = gameController;
        isPacmanMoving = true;
    }

    @Override
    public void run() {

        while (isPacmanMoving) {

            SwingUtilities.invokeLater(() -> {
                gameController.pacmanMove();
            });

            try {
                if (gameController.getSpeedPowerUp()) {
                    Thread.sleep(100);
                } else
                    Thread.sleep(150);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @Override
    public void interrupt() {
        super.interrupt();
        isPacmanMoving = false;
    }
}
