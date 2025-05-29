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

    private Map<GhostName, int[]> enemyPos = new HashMap<GhostName, int[]>();
    private Map<GhostName, Direction> enemyDirections = new HashMap<GhostName, Direction>();
    private Map<GhostName, int[]> lastEnemyPos = new HashMap<GhostName, int[]>();
    private Map<GhostName, GameField> steppedInto = new HashMap<GhostName, GameField>();
    private Map<GhostName, Boolean> isGhostKilled = new HashMap<GhostName, Boolean>();
    private Map<Point, PowerUp> powerUpPos = new HashMap<>();

    private boolean killPowerUp = false;

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

    public boolean getKillPowerUp() {
        return killPowerUp;
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

                for (Map.Entry<GhostName, int[]> enemy : enemyPos.entrySet()) {
                    if (enemy.getValue()[0] == newPosRow && enemy.getValue()[1] == newPosColumn) {
                        toRemove.add(enemy.getKey());
                    }
                }

                for (GhostName ghostName : toRemove) {
                    deleteGhost(newPosRow, newPosColumn, ghostName);
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
                    }
                }
            }

            gameTable.setValueAt(newPosRow, newPosColumn, GameField.PLAYER);

            gameTable.fireTableDataChanged();
        }
    }

    public synchronized void moveGhosts(GhostName ghostName) {
            int oldPosRow = enemyPos.get(ghostName)[0];
            int oldPosColumn = enemyPos.get(ghostName)[1];

            int newPosRow = oldPosRow;
            int newPosColumn = oldPosColumn;

            Direction ghostDirection = enemyDirections.get(ghostName);

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

            if (gameTable.getValueAt(newPosRow, newPosColumn) == GameField.PLAYER && !gameEnded && !killPowerUp) {
                gameEnded = true;
                gameController.endGame();
            }

            if (gameTable.getValueAt(newPosRow, newPosColumn) == GameField.PLAYER && killPowerUp) {
                Iterator<Map.Entry<GhostName, int[]>> iterator = enemyPos.entrySet().iterator();

                while (iterator.hasNext()) {
                    Map.Entry<GhostName, int[]> enemy = iterator.next();

                    if (enemy.getValue()[0] == newPosRow && enemy.getValue()[1] == newPosColumn) {
                        deleteGhost(newPosRow, newPosColumn, enemy.getKey());
                    }
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
                                powerUpPos.put(new Point(oldPosRow, oldPosColumn), PowerUp.KILL);
                                break;
                            }

                            case 2: {
                                powerUpPos.put(new Point(oldPosRow, oldPosColumn), PowerUp.KILL);
                                break;
                            }

                            case 3: {
                                powerUpPos.put(new Point(oldPosRow, oldPosColumn), PowerUp.KILL);
                                break;
                            }

                            case 4: {
                                powerUpPos.put(new Point(oldPosRow, oldPosColumn), PowerUp.KILL);
                                break;
                            }
                        }
                    }

                    gameTable.setValueAt(oldPosRow, oldPosColumn, oldField);

                    steppedInto.put(ghostName, gameTable.getValueAt(newPosRow, newPosColumn));

                    gameTable.setValueAt(newPosRow, newPosColumn, GameField.ENEMY);

                    lastEnemyPos.put(ghostName, new int[]{oldPosRow, oldPosColumn});
                    enemyPos.put(ghostName, new int[]{newPosRow, newPosColumn});

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

    private synchronized void deleteGhost(int newPosRow, int newPosColumn, GhostName ghostName) {
        Iterator<Map.Entry<GhostName, int[]>> iterator = enemyPos.entrySet().iterator();

        while (iterator.hasNext()) {

            Map.Entry<GhostName, int[]> ghost = iterator.next();

            if (ghost.getValue()[0] == newPosRow && ghost.getValue()[1] == newPosColumn) {
                gameController.deleteGhost(ghostName);
                enemyDirections.remove(ghostName);
                lastEnemyPos.remove(ghostName);
                steppedInto.remove(ghostName);
                gameTable.setValueAt(ghost.getValue()[0], ghost.getValue()[1], GameField.DOT);
                isGhostKilled.put(ghost.getKey(), true);
                iterator.remove();
            }
        }
    }


    public synchronized void dropPowerUp(GhostName ghostName) {
        if (Math.random() > 0.75) {
            steppedInto.put(ghostName, GameField.POWERUP);
        }
    }

    public void setDirection(Direction direction) {
        GameModel.direction = direction;
    }

    public Direction getDirection() {
        return direction;
    }

    public static int[] getPacmanLocation() {
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
                            enemyPos.put(GhostName.BLINKY, new int[]{i, j});
                            lastEnemyPos.put(GhostName.BLINKY, new int[]{i, j});
                            enemyCounter++;
                            break;
                        }

                        case 1: {
                            enemyPos.put(GhostName.CLYDE, new int[]{i, j});
                            lastEnemyPos.put(GhostName.CLYDE, new int[]{i, j});
                            enemyCounter++;
                            break;
                        }

                        case 2: {
                            enemyPos.put(GhostName.INKY, new int[]{i, j});
                            lastEnemyPos.put(GhostName.INKY, new int[]{i, j});
                            enemyCounter++;
                            break;
                        }

                        case 3: {
                            enemyPos.put(GhostName.PINKY, new int[]{i, j});
                            lastEnemyPos.put(GhostName.PINKY, new int[]{i, j});
                            enemyCounter++;
                            break;
                        }
                    }
                }
            }
        }
    }

    public Map<GhostName, int[]> getEnemysLocation() {
        return enemyPos;
    }
}
