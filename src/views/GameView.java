package views;

import controllers.GameController;
import enums.GameField;
import renderers.TableCell;

import javax.swing.*;
import javax.swing.table.TableColumn;
import java.awt.*;

public class GameView extends JFrame {
    GameController gameController;
    private static JTable gameTable;

    public GameView(GameController gameController) {
        setTitle("Pacman");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 1000);

        this.gameController = gameController;
        GameView.gameTable = new JTable(GameController.getGameTable());
        gameTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        gameTable.setRowHeight(24);

        for (int i = 0; i < gameTable.getColumnCount(); i++) {
            TableColumn column = gameTable.getColumnModel().getColumn(i);
            column.setPreferredWidth(24);
            column.setMaxWidth(24);
            column.setMinWidth(24);
        }

        gameTable.setDefaultRenderer(GameField.class, new TableCell());
        gameTable.setShowGrid(false);
        gameTable.setIntercellSpacing(new Dimension(0, 0));

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        mainPanel.add(gameTable, BorderLayout.CENTER);

        setContentPane(mainPanel);

        pack();

        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void repaintGame() {
        gameTable.repaint();
    }
}
