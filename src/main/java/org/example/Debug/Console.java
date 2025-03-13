
        package org.example.Debug;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import org.example.Init.InitSettings;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class Console extends VBox {
    private final TextArea inputArea;
    private final Map<String, Consumer<String[]>> commands = new HashMap<>();

    public Console() {
        inputArea = new TextArea();
        inputArea.setPrefRowCount(1);
        inputArea.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                String input = inputArea.getText().trim();
                inputArea.clear();
                processCommand(input);
                event.consume();
            } else if (event.getCode() == KeyCode.BACK_QUOTE) {
                // Consume the tilde key event to prevent it from being added to the TextArea
                event.consume();
            }
        });

        getChildren().add(inputArea);
        setPrefSize(400, 200);
        setStyle("-fx-background-color: black; -fx-text-fill: white;");

        // Register default commands
        registerCommand("set_wave", this::setWave);
        registerCommand("god", args -> FXGL.<InitSettings>getAppCast().toggleGodMode());
        registerCommand("set_score", this::setScore);
        registerCommand("spawn_enemy", this::spawnEnemy);
        registerCommand("toggle_debug", args -> InitSettings.isDebugEnabled = !InitSettings.isDebugEnabled);
        registerCommand("help", this::showHelp);
        registerCommand("restart", this::restartGame);
    }

    public void registerCommand(String command, Consumer<String[]> action) {
        commands.put(command, action);
    }

    private void processCommand(String input) {
        String[] parts = input.split(" ");
        String command = parts[0];
        String[] args = new String[parts.length - 1];
        System.arraycopy(parts, 1, args, 0, args.length);

        Consumer<String[]> action = commands.get(command);
        if (action != null) {
            action.accept(args);
        } else {
            FXGL.getNotificationService().pushNotification("Unknown command: " + command);
        }
    }

    private void setWave(String[] args) {
        if (args.length > 0) {
            try {
                int wave = Integer.parseInt(args[0]);
                InitSettings.wave = wave;
                FXGL.getNotificationService().pushNotification("Wave set to " + wave);
            } catch (NumberFormatException e) {
                FXGL.getNotificationService().pushNotification("Invalid wave number");
            }
        }
    }

    private void setScore(String[] args) {
        if (args.length > 0) {
            try {
                int score = Integer.parseInt(args[0]);
                FXGL.set("score", score);
                FXGL.getNotificationService().pushNotification("Score set to " + score);
            } catch (NumberFormatException e) {
                FXGL.getNotificationService().pushNotification("Invalid score number");
            }
        }
    }

    private void spawnEnemy(String[] args) {
        if (args.length > 0) {
            String enemyType = args[0];
            FXGL.spawn(enemyType, FXGL.random(0, FXGL.getAppWidth()), FXGL.random(0, FXGL.getAppHeight()));
            FXGL.getNotificationService().pushNotification("Spawned enemy: " + enemyType);
        }
    }

    private void showHelp(String[] args) {
        StringBuilder helpMessage = new StringBuilder("Available commands:\n");
        commands.keySet().forEach(command -> helpMessage.append(command).append("\n"));
        FXGL.getNotificationService().pushNotification(helpMessage.toString());
    }

    private void restartGame(String[] args) {
        FXGL.getGameWorld().getEntitiesCopy().forEach(Entity::removeFromWorld);
        FXGL.getGameController().gotoMainMenu();
        FXGL.getUIFactoryService().onGameReset();
        InitSettings.enemiesDefeated = 0;
        InitSettings.enemyCount = 0;
        InitSettings.enemiesToDestroy = 10;
        InitSettings.wave = 1;
        InitSettings.powerup = 0;
        InitSettings.setWave(1);
        FXGL.getGameController().startNewGame();
        FXGL.getNotificationService().pushNotification("Game restarted");
    }
}