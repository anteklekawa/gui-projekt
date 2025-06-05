package components;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class HighScore implements Serializable {

    public class NewHighScore implements Serializable {
        private String playerNick;
        private int score;

        public NewHighScore(String playerNick, int score) {
            this.playerNick = playerNick;
            this.score = score;
        }

        @Override
        public String toString() {
            return playerNick + ": " + score;
        }
    }

    public void saveHighScore(String playerNick, int score) {
        List<NewHighScore> highScoresList = new ArrayList<>(readHighScores());

        highScoresList.add(new NewHighScore(playerNick, score));

        highScoresList.sort((o1, o2) -> o2.score - o1.score);

        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("highScores.ser"))) {
            outputStream.writeObject(highScoresList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<NewHighScore> readHighScores() {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("highScores.ser"))) {
            List<NewHighScore> highScoreList = (List<NewHighScore>) inputStream.readObject();
            return highScoreList;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
