package controllers;

import components.HighScore;
import enums.Direction;
import enums.GameField;
import enums.GhostName;
import models.GameModel;
import threads.*;
import views.GameView;
import components.GameTable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Map;


public class GameController {
    private GameView gameView;
    private GameModel gameModel;
    private HighScore highScoreManager;
    private AppController appController;

    private TimerThread timerThread;

    private AnimThread animThread;
    private PacmanMove pacmanMove;

    private GhostsMove ghostsMove;

    private DropPowerup dropPowerUps;

    public GameController(AppController appController, GameTable gameTable) {
        gameModel = new GameModel(this, gameTable);

        this.appController = appController;

        highScoreManager = new HighScore();

        SwingUtilities.invokeLater(() -> {
            gameView = new GameView(this);
            initKeyListener();
            gameView.setFocusable(true);
            gameView.requestFocusInWindow();
            gameView.setVisible(true);
        });

        timerThread = new TimerThread(this);

        animThread = new AnimThread(this);
        pacmanMove = new PacmanMove(this);

        ghostsMove = new GhostsMove(this, new GhostName[]{GhostName.BLINKY, GhostName.CLYDE, GhostName.INKY, GhostName.PINKY});

        dropPowerUps = new DropPowerup(this, new GhostName[]{GhostName.BLINKY, GhostName.CLYDE, GhostName.INKY, GhostName.PINKY});


        new Thread(ghostsMove).start();

        new Thread(animThread).start();
        new Thread(pacmanMove).start();


        new Thread(dropPowerUps).start();
    }

    public void startTimer() {
        new Thread(timerThread).start();
    }

    public void stopAllThreads() {
        ghostsMove.interrupt();

        animThread.interrupt();
        pacmanMove.interrupt();

        timerThread.interrupt();

        dropPowerUps.interrupt();
    }

    public void endGame() {
        boolean isNicknameSet = false;
        String playerNick = "Unknown";

        while (!isNicknameSet) {
            playerNick = JOptionPane.showInputDialog(gameView, "You lost! \n\n Your score was: " + gameModel.getGamePoints() + "\n\n Type in your name:");

            if (playerNick.length() > 16)
                JOptionPane.showMessageDialog(gameView, "Your name is too long! (Max 24 chars)");
            else
                isNicknameSet = true;
        }

        int score = gameModel.getGamePoints();

        highScoreManager.saveHighScore(playerNick, score);

        gameModel = null;

        gameView.dispose();
    }

    public boolean isMapSmall() {
        return gameModel.isMapSmall();
    }

    public void updateTimer() {
        gameView.updateTimer(gameModel.getTimer());
        gameView.updateGamePoints(gameModel.getGamePoints());
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
                setDirection(e);
            }
        });
    }

    public void addSec() {
        if (gameModel != null)
            gameModel.addSec();
    }

    public void powerUpKill() {
        KillThread killThread = new KillThread(this);
        new Thread(killThread).start();
    }

    public void powerUpStop() {
        StopThread stopThread = new StopThread(this);
        new Thread(stopThread).start();
    }

    public void powerUpSlow() {
        SlowThread slowThread = new SlowThread(this);
        new Thread(slowThread).start();
    }

    public void powerUpSpeed() {
        SpeedThread speedThread = new SpeedThread(this);
        new Thread(speedThread).start();
    }

    public void powerUpChance() {
        ChanceThread chanceThread = new ChanceThread(this);
        new Thread(chanceThread).start();
    }

    public synchronized void setKillPowerUp(boolean killPowerUp) {
        gameModel.setKillPowerUp(killPowerUp);
    }

    public synchronized void setStopPowerUp(boolean stopPowerUp) {
        gameModel.setStopPowerUp(stopPowerUp);
    }

    public synchronized void setSlowPowerUp(boolean slowPowerUp) {
        gameModel.setSlowPowerUp(slowPowerUp);
    }

    public synchronized void setSpeedPowerUp(boolean speedPowerUp) {
        gameModel.setSpeedPowerUp(speedPowerUp);
    }

    public synchronized void setChancePowerUp(boolean chancePowerUp) {
        gameModel.setChancePowerUp(chancePowerUp);
    }

    public boolean getKillPowerUp() {
        return gameModel.getKillPowerUp();
    }

    public boolean getSlowPowerUp() {
        return gameModel.getSlowPowerUp();
    }

    public boolean getSpeedPowerUp() {
        return gameModel.getSpeedPowerUp();
    }

    public int getCellSize() {
        return gameModel.getCellSize();
    }

    public void setDirection(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP: {
                gameModel.setDirection(Direction.UP);
                break;
            }

            case KeyEvent.VK_DOWN: {
                gameModel.setDirection(Direction.DOWN);
                break;
            }

            case KeyEvent.VK_LEFT: {
                gameModel.setDirection(Direction.LEFT);
                break;
            }

            case KeyEvent.VK_RIGHT: {
                gameModel.setDirection(Direction.RIGHT);
                break;
            }
        }

        if (e.isControlDown() && e.isShiftDown() && e.getKeyCode() == KeyEvent.VK_Q) {
            stopAllThreads();
            gameModel = null;
            gameView.dispose();
            appController.showMenu();
        }
    }

    public Direction getDirection() {
        return gameModel.getDirection();
    }

    public synchronized void pacmanMove() {
        if (gameModel != null)
            gameModel.movePacman();
    }

    public synchronized void ghostsMove(GhostName ghostsName) {
        if (gameModel != null)
            gameModel.moveGhosts(ghostsName);
    }

    public synchronized GameTable getGameTable() {
        return gameModel.getGameTable();
    }

    public synchronized void setGameTable(GameTable gameTable, int row, int column, GameField value) {
        gameTable.setValueAt(row, column, value);
    }

    public synchronized GameField getGameField(GameTable gameTable, int row, int column) {
        return gameTable.getValueAt(row, column);
    }

    public synchronized void deleteEnemy(GhostName ghostName) {
        ghostsMove.deleteGhost(ghostName);
        dropPowerUps.deleteGhost(ghostName);
    }

    public synchronized void setPowerUpFrame(boolean powerUpFrame) {
        gameModel.setPowerUpFrame(powerUpFrame);
    }

    public synchronized boolean getPowerUpFrame() {
        return gameModel.getPowerUpFrame();
    }

    public synchronized int getPacmanFrame() {
        return gameModel.getPacmanFrame();
    }

    public synchronized void setPacmanFrame(int pacmanFrame) {
        if (gameModel != null)
            gameModel.setPacmanFrame(pacmanFrame);
    }

    public void setEnemysLocation() {
        gameModel.setEnemysLocation();
    }

    public Map<GhostName, Point> getEnemysLocation() {
        return gameModel.getEnemysLocation();
    }

    public synchronized void dropPowerUp(GhostName ghostName) {
        if (gameModel != null)
            gameModel.dropPowerUp(ghostName);
    }
}
