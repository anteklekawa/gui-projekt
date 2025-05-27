package views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MenuView extends JFrame {
    private final JPanel menuPanel;
    private final JLabel pacmanText;
    private final JButton newGameButton;
    private final JButton rankingButton;
    private final JButton exitButton;

    public MenuView() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 1000);
        setTitle("Pacman");

        menuPanel = new JPanel();
        menuPanel.setLayout(new GridLayout(4, 1, 0, 50));

        pacmanText = new JLabel("Pacman");
        pacmanText.setForeground(Color.WHITE);
        pacmanText.setFont(new Font("Arial", Font.BOLD, 100));
        pacmanText.setHorizontalAlignment(SwingConstants.CENTER);
        pacmanText.setVerticalAlignment(SwingConstants.CENTER);

        newGameButton = new JButton("New Game");
        newGameButton.setForeground(Color.BLACK);
        newGameButton.setBackground(Color.YELLOW);
        newGameButton.setFont(new Font("Arial", Font.BOLD, 50));
        newGameButton.setHorizontalAlignment(SwingConstants.CENTER);
        newGameButton.setVerticalAlignment(SwingConstants.CENTER);
        newGameButton.setBorderPainted(false);

        rankingButton = new JButton("High Scores");
        rankingButton.setForeground(Color.BLACK);
        rankingButton.setBackground(Color.YELLOW);
        rankingButton.setFont(new Font("Arial", Font.BOLD, 50));
        rankingButton.setHorizontalAlignment(SwingConstants.CENTER);
        rankingButton.setVerticalAlignment(SwingConstants.CENTER);
        rankingButton.setBorderPainted(false);

        exitButton = new JButton("Exit");
        exitButton.setForeground(Color.BLACK);
        exitButton.setBackground(Color.YELLOW);
        exitButton.setFont(new Font("Arial", Font.BOLD, 50));
        exitButton.setHorizontalAlignment(SwingConstants.CENTER);
        exitButton.setVerticalAlignment(SwingConstants.CENTER);
        exitButton.setBorderPainted(false);

        menuPanel.setBackground(Color.BLACK);

        menuPanel.add(pacmanText);
        menuPanel.add(newGameButton);
        menuPanel.add(rankingButton);
        menuPanel.add(exitButton);

        getContentPane().add(menuPanel);
    }

    public void newGameListener(ActionListener actionListener) {
        newGameButton.addActionListener(actionListener);
    }

    public void rankingListener(ActionListener actionListener) {
        rankingButton.addActionListener(actionListener);
    }

    public void exitListener(ActionListener actionListener) {
        exitButton.addActionListener(actionListener);
    }
}
