package threads;

import components.GameTable;
import controllers.GameController;

public class AnimThread extends Thread {
    private int pacmanFrame = 0;
    private int frameDirection = 1;
    private boolean isRunning = true;
    private static GameTable gameTable;
    private GameController gameController;
    private boolean powerUpFrame = true;

    public AnimThread(GameController gameController) {
        gameTable = gameController.getGameTable();
        this.gameController = gameController;
    }

    @Override
    public void run() {
        try {
            while (isRunning) {
                nextFrame();

                Thread.sleep(150);
            }
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }

    }

    public void nextFrame() {
        pacmanFrame += frameDirection;

        if (pacmanFrame == 2)
            frameDirection = -1;
        else if (pacmanFrame == 0)
            frameDirection = 1;

        gameController.setPacmanFrame(pacmanFrame);

        powerUpFrame = !powerUpFrame;

        gameController.setPowerUpFrame(powerUpFrame);
    }

    @Override
    public void interrupt() {
        super.interrupt();
        isRunning = false;
    }
}
