package controllers;

import enums.AppState;
import models.GameModel;
import views.ChooseSizeView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AppController {
    private final JFrame appFrame;
    private AppState appState;
    private final ChooseSizeView chooseSizeView;

    public AppController() {
        appFrame = new JFrame("Pacman");
        appFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        appFrame.setSize(1000, 1000);
        appFrame.setVisible(true);

        chooseSizeView = new ChooseSizeView();

        chooseSizeView.chooseSizeListener(new AppController.ChooseSizeListener());

        showMenu();
    }

    public void showGame() {
        appState = AppState.GAME;

        SwingUtilities.invokeLater(() -> new GameController(this));
    }

    public void showMenu() {
        appState = AppState.MENU;

        MenuController menuController = new MenuController(this);

        appFrame.setContentPane(menuController.getMenuView().getContentPane());
        appFrame.revalidate();
        appFrame.repaint();
    }

    public void chooseSize() {
        appState = AppState.CHOOSE_SIZE;

        appFrame.setContentPane(this.getChooseSizeView().getContentPane());
        appFrame.revalidate();
        appFrame.repaint();
    }

    public ChooseSizeView getChooseSizeView() {
        return chooseSizeView;
    }

    class ChooseSizeListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            GameModel.setMapSize(chooseSizeView.getMapSize(), AppController.this);
        }
    }

    public void showRanking() {

    }

    public AppState getAppState() {
        return appState;
    }
}
