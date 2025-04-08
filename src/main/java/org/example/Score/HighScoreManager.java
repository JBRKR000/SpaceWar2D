package org.example.Score;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class HighScoreManager {

    private static final String SCORES_FILE_NAME = "scores.txt";
    private final Path scoresFilePath;

    public HighScoreManager() {
        // Tworzenie ścieżki do pliku w katalogu użytkownika
        scoresFilePath = Paths.get(System.getProperty("user.home"), SCORES_FILE_NAME);
        ensureFileExists();
    }

    private void ensureFileExists() {
        // Tworzy plik, jeśli nie istnieje
        File file = scoresFilePath.toFile();
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public List<String> readHighScores() {
        List<String> highScores = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(scoresFilePath.toFile()))) {
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
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(scoresFilePath.toFile(), true))) {
            writer.write(playerName + ":" + score);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}