package controllers;

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

    private PacmanAnim pacmanAnim;
    private PacmanMove pacmanMove;

    private GhostsMove blinkyMove;
    private GhostsMove clydeMove;
    private GhostsMove inkyMove;
    private GhostsMove pinkyMove;

    private DropPowerup dropPowerupBlinky;
    private DropPowerup dropPowerupClyde;
    private DropPowerup dropPowerupInky;
    private DropPowerup dropPowerupPinky;

    private PowerupAnim powerupAnim;

    public GameController(AppController appController, GameTable gameTable) {
        gameModel = new GameModel(this, gameTable);

        SwingUtilities.invokeLater(() -> {
            gameView = new GameView(this);
            initKeyListener();
            gameView.setFocusable(true);
            gameView.requestFocusInWindow();
            gameView.setVisible(true);
        });

        powerupAnim = new PowerupAnim(this);

        pacmanAnim = new PacmanAnim(this);
        pacmanMove = new PacmanMove(this);

        blinkyMove = new GhostsMove(this, GhostName.BLINKY);
        clydeMove = new GhostsMove(this, GhostName.CLYDE);
        inkyMove = new GhostsMove(this, GhostName.INKY);
        pinkyMove = new GhostsMove(this, GhostName.PINKY);

        dropPowerupBlinky = new DropPowerup(this, GhostName.BLINKY);
        dropPowerupClyde = new DropPowerup(this, GhostName.CLYDE);
        dropPowerupInky = new DropPowerup(this, GhostName.INKY);
        dropPowerupPinky = new DropPowerup(this, GhostName.PINKY);

        new Thread(blinkyMove).start();
        new Thread(clydeMove).start();
        new Thread(inkyMove).start();
        new Thread(pinkyMove).start();

        new Thread(pacmanAnim).start();
        new Thread(pacmanMove).start();

        new Thread(powerupAnim).start();

        new Thread(dropPowerupBlinky).start();
        new Thread(dropPowerupClyde).start();
        new Thread(dropPowerupInky).start();
        new Thread(dropPowerupPinky).start();
    }

    public void endGame() {
        pacmanAnim.stop();
        pacmanMove.stop();

        powerupAnim.stop();

        blinkyMove.stop();
        clydeMove.stop();
        inkyMove.stop();
        pinkyMove.stop();

        dropPowerupBlinky.stop();
        dropPowerupClyde.stop();
        dropPowerupInky.stop();
        dropPowerupPinky.stop();

        JOptionPane.showMessageDialog(null, "You lost!");

        gameView.dispose();
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
        gameView.repaint();
    }

    public Direction getDirection() {
        return gameModel.getDirection();
    }

    public synchronized void pacmanMove() {
        gameModel.movePacman();
    }

    public synchronized void ghostsMove(GhostName ghostsName) {
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
        switch (ghostName) {
            case BLINKY: {
                blinkyMove.stop();
                dropPowerupBlinky.stop();
                break;
            }

            case CLYDE: {
                clydeMove.stop();
                dropPowerupClyde.stop();
                break;
            }

            case INKY: {
                inkyMove.stop();
                dropPowerupInky.stop();
                break;
            }

            case PINKY: {
                pinkyMove.stop();
                dropPowerupPinky.stop();
                break;
            }
        }
    }

    public synchronized void setPowerUpFrame(boolean powerUpFrame) {
        gameModel.setPowerUpFrame(powerUpFrame);
    }

    public boolean getPowerUpFrame() {
        return gameModel.getPowerUpFrame();
    }

    public synchronized int getPacmanFrame() {
        return gameModel.getPacmanFrame();
    }

    public synchronized void setPacmanFrame(int pacmanFrame) {
        gameModel.setPacmanFrame(pacmanFrame);
    }

    public void setEnemysLocation() {
        gameModel.setEnemysLocation();
    }

    public Map<GhostName, Point> getEnemysLocation() {
        return gameModel.getEnemysLocation();
    }

    public synchronized void dropPowerUp(GhostName ghostName) {
        gameModel.dropPowerUp(ghostName);
    }
}
