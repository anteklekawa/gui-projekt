package threads;

import controllers.GameController;

public class ChanceThread implements Runnable {
    private GameController gameController;

    public ChanceThread(GameController gameController) {
        this.gameController = gameController;
    }

    @Override
    public void run() {
        gameController.setChancePowerUp(true);

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        gameController.setChancePowerUp(false);
    }
}
