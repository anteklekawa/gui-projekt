package threads;

import controllers.GameController;

public class SlowThread implements Runnable {
    private GameController gameController;

    public SlowThread(GameController gameController) {
        this.gameController = gameController;
    }

    @Override
    public void run() {
        gameController.setSlowPowerUp(true);

        try {
            Thread.sleep(2500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        gameController.setSlowPowerUp(false);
    }
}
