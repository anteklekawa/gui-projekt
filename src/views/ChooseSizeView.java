package views;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class ChooseSizeView extends JFrame {
    private final JPanel chooseSizePanel;
    private final JLabel mainText;
    private final JLabel rowText;
    private final JLabel columnText;
    private final JTextField rowField;
    private final JTextField columnField;
    private final JButton startButton;

    public ChooseSizeView() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 1000);
        setTitle("Pacman");

        chooseSizePanel = new JPanel();
        chooseSizePanel.setLayout(new GridLayout(4, 1, 0, 50));

        chooseSizePanel.setBackground(Color.BLACK);

        JPanel rowWrapper = new JPanel();
        rowWrapper.setLayout(new BoxLayout(rowWrapper, BoxLayout.X_AXIS));
        rowWrapper.setBackground(Color.BLACK);
        rowWrapper.setBorder(new EmptyBorder(10, 10, 10, 10));

        JPanel columnWrapper = new JPanel();
        columnWrapper.setLayout(new BoxLayout(columnWrapper, BoxLayout.X_AXIS));
        columnWrapper.setBackground(Color.BLACK);
        columnWrapper.setBorder(new EmptyBorder(10, 10, 10, 10));

        mainText = new JLabel("Choose size of the map");
        mainText.setForeground(Color.YELLOW);
        mainText.setFont(new Font("Arial", Font.BOLD, 50));
        mainText.setHorizontalAlignment(SwingConstants.CENTER);
        mainText.setVerticalAlignment(SwingConstants.CENTER);

        rowText = new JLabel("Number of rows: ");
        rowText.setForeground(Color.YELLOW);
        rowText.setFont(new Font("Arial", Font.PLAIN, 36));
        rowText.setHorizontalAlignment(SwingConstants.CENTER);
        rowText.setVerticalAlignment(SwingConstants.CENTER);
        rowText.setBackground(Color.BLACK);

        columnText = new JLabel("Number of columns: ");
        columnText.setForeground(Color.YELLOW);
        columnText.setFont(new Font("Arial", Font.PLAIN, 36));
        columnText.setHorizontalAlignment(SwingConstants.CENTER);
        columnText.setVerticalAlignment(SwingConstants.CENTER);
        columnText.setBackground(Color.BLACK);

        rowField = new JTextField();
        rowField.setMaximumSize(new Dimension(200, 80));
        rowField.setBackground(Color.BLACK);
        rowField.setForeground(Color.YELLOW);
        rowField.setFont(new Font("Arial", Font.PLAIN, 36));

        columnField = new JTextField();
        columnField.setMaximumSize(new Dimension(200, 80));
        columnField.setBackground(Color.BLACK);
        columnField.setForeground(Color.YELLOW);
        columnField.setFont(new Font("Arial", Font.PLAIN, 36));

        startButton = new JButton("Start");
        startButton.setForeground(Color.BLACK);
        startButton.setBackground(Color.YELLOW);
        startButton.setFont(new Font("Arial", Font.BOLD, 50));
        startButton.setHorizontalAlignment(SwingConstants.CENTER);
        startButton.setVerticalAlignment(SwingConstants.CENTER);
        startButton.setBorderPainted(false);

        rowWrapper.add(rowText);
        rowWrapper.add(rowField);

        columnWrapper.add(columnText);
        columnWrapper.add(columnField);

        chooseSizePanel.add(mainText);
        chooseSizePanel.add(rowWrapper);
        chooseSizePanel.add(columnWrapper);
        chooseSizePanel.add(startButton);

        getContentPane().add(chooseSizePanel);
    }

    public String[] getMapSize() {
        return new String[]{rowField.getText().trim(), columnField.getText().trim()};
    }

    public void chooseSizeListener(ActionListener actionListener) {
        startButton.addActionListener(actionListener);
    }
}
