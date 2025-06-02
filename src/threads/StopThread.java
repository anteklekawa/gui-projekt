package threads;

import controllers.GameController;

public class StopThread implements Runnable {
    private GameController gameController;

    public StopThread(GameController gameController) {
        this.gameController = gameController;
    }

    @Override
    public void run() {
        gameController.setStopPowerUp(true);

        try {
            Thread.sleep(2500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        gameController.setStopPowerUp(false);
    }
}
