package threads;

import components.GameTable;
import controllers.GameController;
import models.GameModel;
import views.GameView;

import javax.swing.*;

public class PacmanAnim implements Runnable {
    private boolean pacmanFrame = true;
    private boolean isRunning = true;
    private static GameTable gameTable;

    public PacmanAnim() {
        gameTable = GameModel.getGameTable();
    }

    @Override
    public void run() {
        try {
            while (isRunning) {
                nextFrame();

                SwingUtilities.invokeLater(() -> gameTable.fireTableDataChanged());

                Thread.sleep(150);
            }
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }

    }

    public void nextFrame() {
        pacmanFrame = !pacmanFrame;
        GameController.setPacmanFrame(pacmanFrame);
    }

    public void stop() {
        isRunning = false;
    }
}
