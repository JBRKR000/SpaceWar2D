package org.example.Score;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class HighScoreManager {

    private static final String SCORES_FILE_PATH = "C:/Users/kozim/SpaceWar2D/src/main/resources/assets/bin/scores.txt";

    public List<String> readHighScores() {
        List<String> highScores = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(SCORES_FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                highScores.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return highScores;
    }

    public void writeHighScore(String playerName, int score) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(SCORES_FILE_PATH, true))) {
            writer.write(playerName + ":" + score);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}