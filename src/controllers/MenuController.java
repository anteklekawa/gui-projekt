package controllers;

import views.MenuView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuController {
    private final MenuView menuView;
    private AppController appController;

    public MenuController(AppController appController) {
        this.menuView = new MenuView();
        this.appController = appController;

        this.menuView.newGameListener(new NewGameHandler());
        this.menuView.rankingListener(new RankingHandler());
        this.menuView.exitListener(new ExitHandler());
    }

    public MenuView getMenuView() {
        return menuView;
    }

    class NewGameHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            appController.chooseSize();
        }
    }

    class RankingHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            appController.showRanking();
        }
    }

    class ExitHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }
}