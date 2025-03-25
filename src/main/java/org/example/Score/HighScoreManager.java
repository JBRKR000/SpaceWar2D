package org.example.Score;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class HighScoreManager {

    private static final String SCORES_FILE_PATH = "assets/bin/scores.txt";
    private final URL resource = getClass().getClassLoader().getResource(SCORES_FILE_PATH);
    
    
    public List<String> readHighScores() {
        List<String> highScores = new ArrayList<>();
        if(resource!=null) {
            try (BufferedReader br = new BufferedReader(new FileReader(new File(resource.toURI())))) {
                String line;
                while ((line = br.readLine()) != null) {
                    highScores.add(line);
                }
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
            }
            return highScores;

        }else {
            throw new IllegalArgumentException("");
        }
        
    }

    public void writeHighScore(String playerName, int score) {
        try {
            assert resource != null;
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(new File(resource.toURI()), true))) {
                writer.write(playerName + ":" + score);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}