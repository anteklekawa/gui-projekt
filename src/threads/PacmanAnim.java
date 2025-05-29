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
    private GameController gameController;
    private GameModel gameModel;

    public PacmanAnim(GameController gameController, GameModel gameModel) {
        this.gameModel = gameModel;
        gameTable = gameModel.getGameTable();
        this.gameController = gameController;
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
        gameController.setPacmanFrame(pacmanFrame);
    }

    public void stop() {
        isRunning = false;
    }
}
