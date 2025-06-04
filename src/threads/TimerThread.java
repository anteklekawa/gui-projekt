package threads;

import controllers.GameController;

public class TimerThread implements Runnable {
    private boolean isRunning = true;
    private GameController gameController;

    public TimerThread(GameController gameController) {
        this.gameController = gameController;
    }

    @Override
    public void run() {
        while (isRunning) {
            try {
                gameController.updateTimer();
                Thread.sleep(10);
                gameController.addSec();
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void stop() {
        isRunning = false;
    }
}
