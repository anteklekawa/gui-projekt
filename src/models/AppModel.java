package models;

import components.GameTable;
import controllers.AppController;
import enums.GameField;

import javax.swing.*;

public class AppModel {

    private GameTable gameTable;

    public void setMapSize(String[] tab, AppController appController) {

        try {
            int parsedRows = Integer.parseInt(tab[0]);
            int parsedColumns = Integer.parseInt(tab[1]);

            if ((parsedRows >= 10 || parsedColumns >= 10) && (parsedRows <= 100 || parsedColumns <= 100)) {
                mazeGeneration(parsedRows, parsedColumns, appController);

            } else {
                JOptionPane.showMessageDialog(null, "Values must be between 10 and 100");
            }
        } catch (NumberFormatException error) {
            JOptionPane.showMessageDialog(null, "Values must be numbers");
        }
    }

    public void mazeGeneration(int rows, int columns, AppController appController) {
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

            if (gameTable.getValueAt(row, column) == GameField.DOT) {
                gameTable.setValueAt(row, column, GameField.PLAYER);
                isPlayerSet = true;
            }
        }

        int enemyCounter = 0;

        while (enemyCounter < 4) {
            int row = (int) (Math.random() * rows);
            int column = (int) (Math.random() * columns);

            if (gameTable.getValueAt(row, column) == GameField.DOT) {
                gameTable.setValueAt(row, column, GameField.ENEMY);
                enemyCounter++;
            }
        }



        appController.showGame(gameTable);
    }
}
