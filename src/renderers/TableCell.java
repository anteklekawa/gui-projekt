package renderers;

import controllers.GameController;
import enums.GameField;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class TableCell extends JLabel implements TableCellRenderer {
    private GameField gameField;

    public TableCell() {
        setOpaque(true);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

        if (value instanceof GameField) {
            gameField = (GameField) value;

            switch (gameField) {
                case WALL: {
                    setBackground(Color.BLUE);
                    break;
                }

                case EMPTY, DOT, POWERUP: {
                    setBackground(Color.BLACK);
                    break;
                }

                case PLAYER: {
                    setBackground(Color.BLACK);
                    if (GameController.getPacmanFrame()) {
                        setIcon(new ImageIcon(getClass().getResource("/assets/pacmanOpen.png")));
                    } else
                        setIcon(new ImageIcon(getClass().getResource("/assets/pacmanClosed.png")));
                    break;
                }

                case ENEMY: {
                    setBackground(Color.RED);
                    break;
                }
            }
        }
        return this;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        switch (gameField) {
            case DOT: {
                g.setColor(Color.WHITE);
                g.fillOval(getWidth() / 2 - 2, getHeight() / 2 - 2, 4, 4);
                break;
            }

            case POWERUP: {
                g.setColor(Color.WHITE);
                g.fillOval(getWidth() / 2 - 4, getHeight() / 2 - 4, 8, 8);
            }
        }
    }
}
