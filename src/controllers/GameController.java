package controllers;

import enums.GameField;
import models.GameModel;
import threads.PacmanAnim;
import views.GameView;
import components.GameTable;

import javax.swing.*;


public class GameController {
    private final AppController appController;
    private GameView gameView;

    public GameController(AppController appController) {
        this.appController = appController;

        SwingUtilities.invokeLater(() -> new GameView(this));
        PacmanAnim pacmanAnim = new PacmanAnim();
        new Thread(pacmanAnim).start();
    }

    public static GameTable getGameTable() {
        return GameModel.getGameTable();
    }

    public synchronized static void setGameTable(GameTable gameTable, int row, int column, GameField value) {
        gameTable.setValueAt(row, column, value);
    }

    public synchronized static GameField getGameField(GameTable gameTable, int row, int column) {
        return gameTable.getValueAt(row, column);
    }

    public synchronized static boolean getPacmanFrame() {
        return GameModel.getPacmanFrame();
    }
}
