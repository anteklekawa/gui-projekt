package views;

import components.HighScore;
import controllers.AppController;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.List;

public class HighScoreView extends JFrame {
    private HighScore highScoreManager;
    private AppController appController;

    private JList<HighScore.NewHighScore> list;
    private JPanel mainPanel;
    private JLabel highScoreText;
    private JScrollPane scrollPane;
    private JButton backButton;

    public HighScoreView(AppController appController) {
        this.appController = appController;

        setSize(1000, 1000);
        setTitle("Pacman");

        mainPanel = new JPanel();
        mainPanel.setBackground(Color.BLACK);
        mainPanel.setLayout(new GridLayout(3, 1));
        mainPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.setAlignmentY(Component.CENTER_ALIGNMENT);

        highScoreManager = new HighScore();

        backButton = new JButton("Back");
        backButton.setForeground(Color.BLACK);
        backButton.setBackground(Color.YELLOW);
        backButton.setFont(new Font("Arial", Font.BOLD, 50));
        backButton.setHorizontalAlignment(SwingConstants.CENTER);
        backButton.setVerticalAlignment(SwingConstants.CENTER);
        backButton.setBorderPainted(false);


        highScoreText = new JLabel("High Scores");
        highScoreText.setForeground(Color.WHITE);
        highScoreText.setFont(new Font("Arial", Font.BOLD, 50));
        highScoreText.setHorizontalAlignment(SwingConstants.CENTER);
        highScoreText.setVerticalAlignment(SwingConstants.CENTER);

        list = new JList<HighScore.NewHighScore>(highScoreManager.readHighScores().toArray(new HighScore.NewHighScore[0]));

        list.setBorder(new LineBorder(Color.WHITE, 5));
        list.setBackground(Color.YELLOW);
        list.setForeground(Color.BLACK);
        list.setFont(new Font("Arial", Font.BOLD, 40));
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setLayoutOrientation(JList.VERTICAL);

        scrollPane = new JScrollPane(list);


        mainPanel.add(highScoreText);
        mainPanel.add(scrollPane);
        mainPanel.add(backButton);

        setContentPane(mainPanel);

        backButton.addActionListener(e -> {
            this.dispose();
        });

        setLocationRelativeTo(null);
        setVisible(true);
    }
}
