package controllers;

import enums.Direction;
import enums.GameField;
import models.GameModel;
import threads.PacmanAnim;
import views.GameView;
import components.GameTable;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


public class GameController {
    private GameView gameView;
    private GameModel gameModel;

    public GameController(AppController appController) {
        gameModel = new GameModel(this);

        SwingUtilities.invokeLater(() -> {
            gameView = new GameView(this);
            initKeyListener();
            gameView.setFocusable(true);
            gameView.requestFocusInWindow();
            gameView.setVisible(true);
        });

        PacmanAnim pacmanAnim = new PacmanAnim(this, gameModel);
        new Thread(pacmanAnim).start();
    }

    public GameView getGameView() {
        return gameView;
    }

    public GameController getGameController() {
        return this;
    }

    public void initKeyListener() {
        gameView.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                pacmanMove(e);
            }
        });
    }

    public void pacmanMove(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP: {
                gameModel.movePacman(Direction.UP);
                break;
            }

            case KeyEvent.VK_DOWN: {
                gameModel.movePacman(Direction.DOWN);
                break;
            }

            case KeyEvent.VK_LEFT: {
                gameModel.movePacman(Direction.LEFT);
                break;
            }

            case KeyEvent.VK_RIGHT: {
                gameModel.movePacman(Direction.RIGHT);
                break;
            }
        }
        gameView.repaint();
    }

    public GameTable getGameTable() {
        return gameModel.getGameTable();
    }

    public synchronized void setGameTable(GameTable gameTable, int row, int column, GameField value) {
        gameTable.setValueAt(row, column, value);
    }

    public synchronized GameField getGameField(GameTable gameTable, int row, int column) {
        return gameTable.getValueAt(row, column);
    }

    public synchronized boolean getPacmanFrame() {
        return gameModel.getPacmanFrame();
    }

    public synchronized void setPacmanFrame(boolean pacmanFrame) {
        GameModel.setPacmanFrame(pacmanFrame);
    }
}
