package threads;

import controllers.GameController;

public class KillThread implements Runnable {
    private GameController gameController;

    public KillThread(GameController gameController) {
        this.gameController = gameController;
    }
    @Override
    public void run() {
        gameController.setKillPowerUp(true);

        try {
            Thread.sleep(2500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        gameController.setKillPowerUp(false);
    }
}
