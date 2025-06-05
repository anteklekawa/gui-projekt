package threads;

import controllers.GameController;

public class TimerThread extends Thread {
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

    @Override
    public void interrupt() {
        super.interrupt();
        isRunning = false;
    }
}
