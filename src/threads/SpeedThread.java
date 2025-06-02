package threads;

import controllers.GameController;

public class SpeedThread implements Runnable {
    private GameController gameController;

    public SpeedThread(GameController gameController) {
        this.gameController = gameController;
    }

    @Override
    public void run() {
        gameController.setSpeedPowerUp(true);

        try {
            Thread.sleep(2500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        gameController.setSpeedPowerUp(false);
    }
}
