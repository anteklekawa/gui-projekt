package models;

import components.GameTable;
import controllers.AppController;
import controllers.GameController;
import enums.Direction;
import enums.GameField;
import threads.PacmanAnim;

import javax.swing.*;

public class GameModel {
    static GameTable gameTable;
    static PacmanAnim pacman;
    static boolean pacmanFrame;
    static Direction direction = Direction.RIGHT;


    public static void setMapSize(String[] tab, AppController appController) {
        pacman = new PacmanAnim();

        try {
            int parsedRows = Integer.parseInt(tab[0]);
            int parsedColumns = Integer.parseInt(tab[1]);

            if ((parsedRows >= 10 || parsedColumns >= 10) && (parsedRows <= 100 || parsedColumns <= 100)) {
                GameModel.mazeGeneration(parsedRows, parsedColumns, appController);

            } else {
                JOptionPane.showMessageDialog(null, "Values must be between 10 and 100");
            }
        } catch (NumberFormatException error) {
            JOptionPane.showMessageDialog(null, "Values must be numbers");
        }
    }

    public static void mazeGeneration(int rows, int columns, AppController appController) {
        gameTable = new GameTable(rows, columns);

        for (int i = 0; i < rows; i++) {
            if (i == 0) {
                for (int j = 0; j < columns; j++) {
                    gameTable.setValueAt(i, j, GameField.WALL);
                }
            } else if (i == rows - 1) {
                for (int j = 0; j < columns; j++) {
                    gameTable.setValueAt(i, j, GameField.WALL);
                }
            } else {
                gameTable.setValueAt(i, 0, GameField.WALL);
                gameTable.setValueAt(i, columns - 1, GameField.WALL);
            }
        }

        int wallsCounter = 2;

        int smaller;

        if (rows < columns)
            smaller = rows;
        else
            smaller = columns;

        while (wallsCounter < smaller/2) {
            for (int i = wallsCounter; i < rows - wallsCounter; i++) {
                if (i == wallsCounter || i == rows - wallsCounter - 1) {
                    for (int j = wallsCounter; j < columns - wallsCounter; j++) {
                        if (Math.random() < 0.80) {
                            gameTable.setValueAt(i, j, GameField.WALL);
                        }
                        else
                            gameTable.setValueAt(i, j, GameField.DOT);
                    }
                } else {
                    if (Math.random() < 0.80) {
                        gameTable.setValueAt(i, wallsCounter, GameField.WALL);
                    }
                    else
                        gameTable.setValueAt(i, wallsCounter, GameField.DOT);

                    if (Math.random() < 0.80) {
                        gameTable.setValueAt(i, columns - wallsCounter - 1, GameField.WALL);
                    }
                    else
                        gameTable.setValueAt(i, columns - wallsCounter - 1, GameField.DOT);
                }
            }
            wallsCounter += 2;
        }

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (gameTable.getValueAt(i, j) == null) {
                    gameTable.setValueAt(i, j, GameField.DOT);
                }
            }
        }

        boolean isPlayerSet = false;

        while (!isPlayerSet) {
            int row = (int) (Math.random() * rows);
            int column = (int) (Math.random() * columns);

            if (GameController.getGameField(gameTable, row, column) == GameField.DOT) {
                GameController.setGameTable(gameTable, row, column, GameField.PLAYER);
                isPlayerSet = true;
            }
        }

        int enemyCounter = 0;

        int maxEnemies = 4;

        if (rows > 40 && columns > 40)
            maxEnemies = 6;

        if (rows > 60 && columns > 60)
            maxEnemies = 8;

        while (enemyCounter < maxEnemies) {
            int row = (int) (Math.random() * rows);
            int column = (int) (Math.random() * columns);

            if (GameController.getGameField(gameTable, row, column) == GameField.DOT) {
                GameController.setGameTable(gameTable, row, column, GameField.ENEMY);
                enemyCounter++;
            }
        }

        appController.showGame();
    }

    public static boolean getPacmanFrame() {
        return pacmanFrame;
    }

    public static GameTable getGameTable() {
        return gameTable;
    }

    public static void setPacmanFrame(boolean pacmanFrame) {
        GameModel.pacmanFrame = pacmanFrame;
    }

    public void movePacman(Direction direction) {
        GameModel.direction = direction;
        int oldPosX = getPacmanLocation()[0];
        int oldPosY = getPacmanLocation()[1];

        switch (direction) {
            case LEFT: {
                if (GameController.getGameField(gameTable, oldPosX, oldPosY - 1) != GameField.WALL) {
                    GameController.setGameTable(gameTable, oldPosX, oldPosY, GameField.EMPTY);
                    GameController.setGameTable(gameTable, oldPosX, oldPosY - 1, GameField.PLAYER);
                }
                break;
            }

            case RIGHT: {
                if (GameController.getGameField(gameTable, oldPosX, oldPosY + 1) != GameField.WALL) {
                    GameController.setGameTable(gameTable, oldPosX, oldPosY, GameField.EMPTY);
                    GameController.setGameTable(gameTable, oldPosX, oldPosY + 1, GameField.PLAYER);
                }
                break;
            }

            case UP: {
                if (GameController.getGameField(gameTable, oldPosX - 1, oldPosY) != GameField.WALL) {
                    GameController.setGameTable(gameTable, oldPosX, oldPosY, GameField.EMPTY);
                    GameController.setGameTable(gameTable, oldPosX - 1, oldPosY, GameField.PLAYER);
                }
                break;
            }

            case DOWN: {
                if (GameController.getGameField(gameTable, oldPosX + 1, oldPosY) != GameField.WALL) {
                    GameController.setGameTable(gameTable, oldPosX, oldPosY, GameField.EMPTY);
                    GameController.setGameTable(gameTable, oldPosX + 1, oldPosY, GameField.PLAYER);
                }
                break;
            }
        }

        gameTable.fireTableDataChanged();
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
}
