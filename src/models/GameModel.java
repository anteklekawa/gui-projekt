package models;

import components.GameTable;
import controllers.GameController;
import enums.Direction;
import enums.GameField;
import enums.GhostName;
import enums.PowerUp;
import threads.PacmanAnim;

import java.awt.*;
import java.util.*;
import java.util.List;

public class GameModel {
    static GameTable gameTable;
    static PacmanAnim pacman;
    static int pacmanFrame;
    static Direction direction = Direction.RIGHT;
    private GameController gameController;

    private Map<GhostName, Point> enemyPos = new HashMap<GhostName, Point>();
    private Map<GhostName, Direction> enemyDirections = new HashMap<GhostName, Direction>();
    private Map<GhostName, Point> lastEnemyPos = new HashMap<GhostName, Point>();
    private Map<GhostName, GameField> steppedInto = new HashMap<GhostName, GameField>();
    private Map<GhostName, Boolean> isEnemyKilled = new HashMap<GhostName, Boolean>();
    private Map<Point, PowerUp> powerUpPos = new HashMap<>();

    private boolean killPowerUp = false;
    private boolean stopPowerUp = false;
    private boolean slowPowerUp = false;
    private boolean speedPowerUp = false;
    private boolean chancePowerUp = false;

    private boolean gameEnded = false;

    private boolean powerUpFrame = true;

    public GameModel(GameController gameController, GameTable gameTable) {
        this.gameController = gameController;
        this.gameTable = gameTable;

        steppedInto.put(GhostName.BLINKY, GameField.DOT);
        steppedInto.put(GhostName.CLYDE, GameField.DOT);
        steppedInto.put(GhostName.INKY, GameField.DOT);
        steppedInto.put(GhostName.PINKY, GameField.DOT);

        enemyDirections.put(GhostName.BLINKY, Direction.LEFT);
        enemyDirections.put(GhostName.CLYDE, Direction.UP);
        enemyDirections.put(GhostName.INKY, Direction.RIGHT);
        enemyDirections.put(GhostName.PINKY, Direction.DOWN);

        setEnemysLocation();
    }

    public int getPacmanFrame() {
        return pacmanFrame;
    }

    public synchronized GameTable getGameTable() {
        return gameTable;
    }

    public synchronized void setPacmanFrame(int pacmanFrame) {
        this.pacmanFrame = pacmanFrame;
    }

    public synchronized void setPowerUpFrame(boolean powerUpFrame) {
        this.powerUpFrame = powerUpFrame;
    }

    public synchronized void setKillPowerUp(boolean killPowerUp) {
        this.killPowerUp = killPowerUp;
    }

    public synchronized void setStopPowerUp(boolean stopPowerUp) {
        this.stopPowerUp = stopPowerUp;
    }

    public synchronized void setSlowPowerUp(boolean slowPowerUp) {
        this.slowPowerUp = slowPowerUp;
    }

    public synchronized void setSpeedPowerUp(boolean speedPowerUp) {
        this.speedPowerUp = speedPowerUp;
    }

    public synchronized void setChancePowerUp(boolean chancePowerUp) {
        this.chancePowerUp = chancePowerUp;
    }


    public boolean getKillPowerUp() {
        return killPowerUp;
    }

    public boolean getSlowPowerUp() {
        return slowPowerUp;
    }

    public boolean getSpeedPowerUp() {
        return speedPowerUp;
    }

    public boolean getPowerUpFrame() {
        return powerUpFrame;
    }

    public synchronized void movePacman() {
        int oldPosRow = getPacmanLocation()[0];
        int oldPosColumn = getPacmanLocation()[1];

        int newPosRow = oldPosRow;
        int newPosColumn = oldPosColumn;

        switch (direction) {
            case LEFT: {
                newPosColumn--;
                break;
            }

            case RIGHT: {
                newPosColumn++;
                break;
            }

            case UP: {
                newPosRow--;
                break;
            }

            case DOWN: {
                newPosRow++;
                break;
            }
        }

        if (gameTable.getValueAt(newPosRow, newPosColumn) == GameField.ENEMY && !gameEnded && !killPowerUp) {
            gameEnded = true;
            gameController.endGame();
        }

        if (gameTable.getValueAt(newPosRow, newPosColumn) == GameField.ENEMY && killPowerUp) {

            synchronized (this) {
                List<GhostName> toRemove = new ArrayList<>();

                for (Map.Entry<GhostName, Point> enemy : enemyPos.entrySet()) {
                    if (enemy.getValue().x == newPosRow && enemy.getValue().y == newPosColumn) {
                        toRemove.add(enemy.getKey());
                    }
                }

                for (GhostName ghostName : toRemove) {
                    deleteEnemy(newPosRow, newPosColumn, ghostName);
                }
            }
        }

        if (gameTable.getValueAt(newPosRow, newPosColumn) != GameField.WALL) {
            gameTable.setValueAt(oldPosRow, oldPosColumn, GameField.EMPTY);
            if (gameTable.getValueAt(newPosRow, newPosColumn) == GameField.POWERUP) {
                PowerUp powerUp = powerUpPos.get(new Point(newPosRow, newPosColumn));
                if (powerUp != null) {
                    switch (powerUp) {
                        case KILL: {
                            gameController.powerUpKill();
                            break;
                        }

                        case STOP: {
                            gameController.powerUpStop();
                            break;
                        }

                        case SLOW: {
                            gameController.powerUpSlow();
                            break;
                        }

                        case SPEED: {
                            gameController.powerUpSpeed();
                            break;
                        }

                        case CHANCE: {
                            gameController.powerUpChance();
                            break;
                        }
                    }
                }
            }

            gameTable.setValueAt(newPosRow, newPosColumn, GameField.PLAYER);

            gameTable.fireTableDataChanged();
        }
    }

    public synchronized void moveGhosts(GhostName ghostName) {
            int oldPosRow = getEnemyPos(ghostName).x;
            int oldPosColumn = getEnemyPos(ghostName).y;

            int newPosRow = oldPosRow;
            int newPosColumn = oldPosColumn;

            Direction ghostDirection = enemyDirections.get(ghostName);

            if (!stopPowerUp) {
                switch (ghostDirection) {
                    case LEFT: {
                        newPosColumn--;
                        break;
                    }

                    case RIGHT: {
                        newPosColumn++;
                        break;
                    }

                    case UP: {
                        newPosRow--;
                        break;
                    }

                    case DOWN: {
                        newPosRow++;
                        break;
                    }
                }
            }

            if (gameTable.getValueAt(newPosRow, newPosColumn) == GameField.PLAYER && !gameEnded && !killPowerUp) {
                gameEnded = true;
                gameController.endGame();
            }

            if (gameTable.getValueAt(newPosRow, newPosColumn) == GameField.PLAYER && killPowerUp) {

                if (getEnemyPos(ghostName).x == newPosRow && getEnemyPos(ghostName).y == newPosColumn) {
                    deleteEnemy(newPosRow, newPosColumn, ghostName);
                }
            }

            else if (gameTable.getValueAt(newPosRow, newPosColumn) != GameField.WALL && gameTable.getValueAt(newPosRow, newPosColumn) != GameField.ENEMY) {

                    GameField oldField = steppedInto.get(ghostName);

                    if (steppedInto.get(ghostName) == GameField.POWERUP) {
                        Random r = new Random();
                        switch (r.nextInt(5)) {
                            case 0: {
                                powerUpPos.put(new Point(oldPosRow, oldPosColumn), PowerUp.KILL);
                                break;
                            }

                            case 1: {
                                powerUpPos.put(new Point(oldPosRow, oldPosColumn), PowerUp.STOP);
                                break;
                            }

                            case 2: {
                                powerUpPos.put(new Point(oldPosRow, oldPosColumn), PowerUp.SLOW);
                                break;
                            }

                            case 3: {
                                powerUpPos.put(new Point(oldPosRow, oldPosColumn), PowerUp.SPEED);
                                break;
                            }

                            case 4: {
                                powerUpPos.put(new Point(oldPosRow, oldPosColumn), PowerUp.CHANCE);
                                break;
                            }
                        }
                    }

                    gameTable.setValueAt(oldPosRow, oldPosColumn, oldField);

                    steppedInto.put(ghostName, gameTable.getValueAt(newPosRow, newPosColumn));

                    gameTable.setValueAt(newPosRow, newPosColumn, GameField.ENEMY);

                    lastEnemyPos.put(ghostName, new Point(oldPosRow, oldPosColumn));
                    enemyPos.put(ghostName, new Point(newPosRow, newPosColumn));

                    gameTable.fireTableDataChanged();

            } else {
                Random r = new Random();
                switch (r.nextInt(4)) {
                    case 0: {
                        enemyDirections.put(ghostName, Direction.LEFT);
                        break;
                    }

                    case 1: {
                        enemyDirections.put(ghostName, Direction.RIGHT);
                        break;
                    }

                    case 2: {
                        enemyDirections.put(ghostName, Direction.UP);
                        break;
                    }

                    case 3: {
                        enemyDirections.put(ghostName, Direction.DOWN);
                        break;
                    }
                }
            }
    }

    public synchronized void deleteEnemy(int newPosRow, int newPosColumn, GhostName ghostName) {

        if (getEnemyPos(ghostName).x == newPosRow && getEnemyPos(ghostName).y == newPosColumn) {
            gameTable.setValueAt(getEnemyPos(ghostName).x, getEnemyPos(ghostName).y, GameField.DOT);
            gameController.deleteEnemy(ghostName);
            enemyPos.remove(ghostName);
            enemyDirections.remove(ghostName);
            lastEnemyPos.remove(ghostName);
            steppedInto.remove(ghostName);
            isEnemyKilled.put(ghostName, true);
        }
    }

    public synchronized Point getEnemyPos(GhostName ghostName) {
        for (Map.Entry<GhostName, Point> enemy : enemyPos.entrySet()) {
            if (enemy.getKey() == ghostName) {
                return enemy.getValue();
            }
        }
        return null;
    }


    public synchronized void dropPowerUp(GhostName ghostName) {
        if (chancePowerUp) {
            if (Math.random() > 0.5) {
                steppedInto.put(ghostName, GameField.POWERUP);
            }
        } else {
            if (Math.random() > 0.75) {
                steppedInto.put(ghostName, GameField.POWERUP);
            }
        }
    }

    public void setDirection(Direction direction) {
        GameModel.direction = direction;
    }

    public Direction getDirection() {
        return direction;
    }

    public synchronized static int[] getPacmanLocation() {
        for (int i = 0; i < gameTable.getRowCount(); i++) {
            for (int j = 0; j < gameTable.getColumnCount(); j++) {
                if (gameTable.getValueAt(i, j) == GameField.PLAYER) {
                    return new int[]{i, j};
                }
            }
        }
        return null;
    }

    public synchronized void setEnemysLocation() {
        int enemyCounter = 0;
        for (int i = 0; i < gameTable.getRowCount(); i++) {
            for (int j = 0; j < gameTable.getColumnCount(); j++) {
                if (gameTable.getValueAt(i, j) == GameField.ENEMY) {
                    switch (enemyCounter) {
                        case 0: {
                            enemyPos.put(GhostName.BLINKY, new Point(i, j));
                            lastEnemyPos.put(GhostName.BLINKY, new Point(i, j));
                            enemyCounter++;
                            break;
                        }

                        case 1: {
                            enemyPos.put(GhostName.CLYDE, new Point(i, j));
                            lastEnemyPos.put(GhostName.CLYDE, new Point(i, j));
                            enemyCounter++;
                            break;
                        }

                        case 2: {
                            enemyPos.put(GhostName.INKY, new Point(i, j));
                            lastEnemyPos.put(GhostName.INKY, new Point(i, j));
                            enemyCounter++;
                            break;
                        }

                        case 3: {
                            enemyPos.put(GhostName.PINKY, new Point(i, j));
                            lastEnemyPos.put(GhostName.PINKY, new Point(i, j));
                            enemyCounter++;
                            break;
                        }
                    }
                }
            }
        }
    }

    public Map<GhostName, Point> getEnemysLocation() {
        return enemyPos;
    }
}
