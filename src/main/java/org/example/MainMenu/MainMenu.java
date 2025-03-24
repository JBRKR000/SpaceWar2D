package org.example.MainMenu;

import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;
import com.almasb.fxgl.dsl.FXGL;
import javafx.beans.binding.Bindings;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import org.example.Score.HighScoreManager;

import java.io.IOException;
import java.util.List;

public class MainMenu extends FXGLMenu {
    private final HighScoreManager highScoreManager = new HighScoreManager();

    public MainMenu() {
        super(MenuType.MAIN_MENU);
        double appWidth = FXGL.getAppWidth();
        double appHeight = FXGL.getAppHeight();
        BackgroundSize backgroundSize = new BackgroundSize(appWidth, appHeight, true, true, true, true);
        Image backgroundImage = new Image("assets/textures/background3.jpg");
        BackgroundImage background = new BackgroundImage(backgroundImage,
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT, backgroundSize);
        getContentRoot().setBackground(new Background(background));
        Text title = FXGL.getUIFactoryService().newText("Space Invaders", Color.BLUEVIOLET, 48);
        title.setTranslateX(appWidth / 2 - title.getLayoutBounds().getWidth() / 2);
        title.setTranslateY(appHeight / 4);
        getContentRoot().getChildren().add(title);
        var startButton = new MenuButton("START", this::fireNewGame);
        var highScoresButton = new MenuButton("HIGH SCORES", this::showHighScores);
        var exitButton = new MenuButton("EXIT", this::fireExit);
        startButton.setTranslateX(appWidth / 2 - 100);
        startButton.setTranslateY(appHeight / 2 - 50);
        highScoresButton.setTranslateX(appWidth / 2 - 100);
        highScoresButton.setTranslateY(appHeight / 2);
        exitButton.setTranslateX(appWidth / 2 - 100);
        exitButton.setTranslateY(appHeight / 2 + 50);

        getContentRoot().getChildren().addAll(startButton, highScoresButton, exitButton);
    }



    private void showHighScores() {
        List<String> highScores = highScoreManager.readHighScores();
        StringBuilder scoresText = new StringBuilder("High Scores:\n");
        for (String score : highScores) {
            scoresText.append(score).append("\n");
        }

        FXGL.getDialogService().showMessageBox(scoresText.toString());
    }

    private static class MenuButton extends StackPane {
        public MenuButton(String name, Runnable action) {
            var bg = new Rectangle(200, 40);
            bg.setStroke(Color.WHITE);

            var text = FXGL.getUIFactoryService().newText(name, Color.WHITE, 18);

            bg.fillProperty().bind(
                    Bindings.when(hoverProperty()).then(Color.WHITE).otherwise(Color.BLACK)
            );

            text.fillProperty().bind(
                    Bindings.when(hoverProperty()).then(Color.BLACK).otherwise(Color.WHITE)
            );
            setOnMouseClicked(e -> action.run());
            getChildren().addAll(bg, text);
        }
    }
}