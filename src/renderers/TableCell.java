package renderers;

import controllers.GameController;
import enums.GameField;
import models.GameModel;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class TableCell extends JLabel implements TableCellRenderer {
    private GameField gameField;
    private static Image pacmanOpenImg;
    private static Image pacmanClosedImg;
    private GameController gameController;

    public TableCell(GameController gameController) {

        this.gameController = gameController;

        setOpaque(true);

        ImageIcon pacmanOpenImgIcon = new ImageIcon(getClass().getResource("/assets/pacmanOpen.png"));
        ImageIcon pacmanClosedImgIcon = new ImageIcon(getClass().getResource("/assets/pacmanClosed.png"));

        Image pacmanOpenImgOg = pacmanOpenImgIcon.getImage();
        Image pacmanClosedImgOg = pacmanClosedImgIcon.getImage();

        pacmanOpenImg = pacmanOpenImgOg.getScaledInstance(24, 24, Image.SCALE_SMOOTH);
        pacmanClosedImg = pacmanClosedImgOg.getScaledInstance(24, 24, Image.SCALE_SMOOTH);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        setIcon(null);
        setText("");

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
                    setBackground(Color.WHITE);
                    if (gameController.getPacmanFrame())
                        setIcon(new ImageIcon(pacmanOpenImg));
                    else
                        setIcon(new ImageIcon(pacmanClosedImg));
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
