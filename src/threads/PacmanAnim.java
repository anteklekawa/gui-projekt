package threads;

import models.GameModel;
import views.GameView;

import javax.swing.*;

public class PacmanAnim implements Runnable {
    private int pacmanFrame = 0;
    private boolean isRunning = true;

    @Override
    public void run() {
        try {
            while (isRunning) {
                SwingUtilities.invokeLater(() -> {
                    nextFrame();

                    GameView.repaintGameTable();
                });
                Thread.sleep(150);
            }
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }

    }

    public void nextFrame() {
        pacmanFrame = (pacmanFrame + 1) % 2;
    }

    public void stop() {
        isRunning = false;
    }

    public synchronized boolean getPacmanFrame() {
        if (pacmanFrame == 1) {
            return true;
        } else return false;
    }
}
