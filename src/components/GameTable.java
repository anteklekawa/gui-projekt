package components;

import enums.GameField;

import javax.swing.table.AbstractTableModel;

public class GameTable extends AbstractTableModel {
    GameField[][] gameTable;

    public GameTable(int rows, int columns) {
        gameTable = new GameField[rows][columns];
    }

    @Override
    public int getRowCount() {
        return gameTable.length;
    }

    @Override
    public int getColumnCount() {
        return gameTable[0].length;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return GameField.class;
    }

    @Override
    public synchronized GameField getValueAt(int rowIndex, int columnIndex) {
        return gameTable[rowIndex][columnIndex];
    }

    public synchronized void setValueAt(int rowIndex, int columnIndex, GameField value) {
        this.gameTable[rowIndex][columnIndex] = value;
        this.fireTableCellUpdated(rowIndex, columnIndex);
    }

}
