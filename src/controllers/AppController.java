package controllers;

import components.GameTable;
import enums.AppState;
import models.AppModel;
import models.GameModel;
import views.ChooseSizeView;
import views.HighScoreView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AppController {
    private final JFrame appFrame;
    private AppState appState;
    private final ChooseSizeView chooseSizeView;
    private AppModel appModel;

    public AppController() {
        appFrame = new JFrame("Pacman");
        appFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        appFrame.setSize(1000, 1000);
        appFrame.setVisible(true);

        appModel = new AppModel();

        chooseSizeView = new ChooseSizeView();

        chooseSizeView.chooseSizeListener(new AppController.ChooseSizeListener());

        showMenu();
    }

    public void showGame(GameTable gameTable) {
        appState = AppState.GAME;

        SwingUtilities.invokeLater(() -> new GameController(this, gameTable));
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
            showMenu();
            appModel.setMapSize(chooseSizeView.getMapSize(), AppController.this);
        }
    }

    public void showRanking() {
        appState = AppState.RANKING;

        SwingUtilities.invokeLater(() -> new HighScoreView(this));
    }

    public AppState getAppState() {
        return appState;
    }
}
