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

    private JLabel timerText;
    private JLabel gamePoints;
    private JLabel lifeCounter;

    public GameView(GameController gameController) {
        setTitle("Pacman");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 1000);

        this.gameController = gameController;
        GameView.gameTable = new JTable(gameController.getGameTable());
        gameTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        gameTable.setRowHeight(gameController.getCellSize());

        for (int i = 0; i < gameTable.getColumnCount(); i++) {
            TableColumn column = gameTable.getColumnModel().getColumn(i);
            column.setPreferredWidth(gameController.getCellSize());
            column.setMaxWidth(gameController.getCellSize());
            column.setMinWidth(gameController.getCellSize());
        }

        gameTable.setDefaultRenderer(GameField.class, new TableCell(gameController));
        gameTable.setShowGrid(false);
        gameTable.setIntercellSpacing(new Dimension(0, 0));

        timerText = new JLabel("Time: 00:00:00");
        timerText.setForeground(Color.WHITE);
        timerText.setAlignmentX(Component.LEFT_ALIGNMENT);
        timerText.setFont(new Font("Arial", Font.BOLD, 20));

        gamePoints = new JLabel("Score: 0");
        gamePoints.setForeground(Color.WHITE);
        gamePoints.setAlignmentX(Component.CENTER_ALIGNMENT);
        gamePoints.setFont(new Font("Arial", Font.BOLD, 20));

        lifeCounter = new JLabel("One life left!");
        lifeCounter.setForeground(Color.WHITE);
        lifeCounter.setAlignmentX(Component.RIGHT_ALIGNMENT);
        lifeCounter.setFont(new Font("Arial", Font.BOLD, 20));

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.X_AXIS));

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        bottomPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        bottomPanel.add(lifeCounter);

        bottomPanel.add(Box.createHorizontalGlue());

        bottomPanel.add(gamePoints);

        bottomPanel.add(Box.createHorizontalGlue());

        bottomPanel.add(timerText);
        bottomPanel.add(Box.createRigidArea(new Dimension(10, 0)));

        bottomPanel.setBackground(Color.BLACK);

        mainPanel.add(gameTable, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        setContentPane(mainPanel);

        if (gameController.isMapSmall()) {
            timerText.setFont(new Font("Arial", Font.BOLD, 10));
            gamePoints.setFont(new Font("Arial", Font.BOLD, 10));
            lifeCounter.setFont(new Font("Arial", Font.BOLD, 10));
        }

        pack();

        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void updateTimer(String time) {
        this.timerText.setText("Time: " + time);
        this.timerText.repaint();
    }

    public void updateGamePoints(int gamePoints) {
        this.gamePoints.setText("Score: " + gamePoints);
        this.gamePoints.repaint();
    }
}
