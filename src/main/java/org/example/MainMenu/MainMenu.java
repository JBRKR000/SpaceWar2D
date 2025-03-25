package org.example.MainMenu;

import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;
import com.almasb.fxgl.dsl.FXGL;
import javafx.beans.binding.Bindings;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import javafx.scene.text.Text;
import org.example.Score.HighScoreManager;
import javafx.scene.text.Font;

import java.awt.*;
import java.io.IOException;
import java.util.List;

public class MainMenu extends FXGLMenu {
    private final HighScoreManager highScoreManager = new HighScoreManager();

    public MainMenu() throws IOException, FontFormatException {
        super(MenuType.MAIN_MENU);
        double appWidth = 1920;
        double appHeight = 1080;
        var bgImage = new Image("assets/textures/background4.jpg");
        var bgView = new ImageView(bgImage);

        Font customFont = Font.loadFont("assets/fonts/f.ttf", 128);
        bgView.setFitWidth(appWidth);
        bgView.setFitHeight(appHeight);
        getContentRoot().getChildren().add(bgView);
       
        Text title = new Text("Space Invaders");
        title.setFont(customFont);
        title.setFill(Color.BLUEVIOLET);
        title.setTranslateX(appWidth / 2 - title.getLayoutBounds().getWidth() / 2);
        title.setTranslateY(appHeight / 4);
        
        var startButton = new MenuButton("START", this::fireNewGame);
        var highScoresButton = new MenuButton("HIGH SCORES", this::showHighScores);
        var exitButton = new MenuButton("EXIT", this::fireExit);
        startButton.setTranslateX(appWidth / 2 - 100);
        startButton.setTranslateY(appHeight / 2 - 50);
        highScoresButton.setTranslateX(appWidth / 2 - 100);
        highScoresButton.setTranslateY(appHeight / 2);
        exitButton.setTranslateX(appWidth / 2 - 100);
        exitButton.setTranslateY(appHeight / 2 + 50);
        
        getContentRoot().getChildren().add(title);
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
            bg.setStroke(Color.BLACK);

            var text = FXGL.getUIFactoryService().newText(name, Color.WHITE, 18);

            bg.fillProperty().bind(
                    Bindings.when(hoverProperty()).then(Color.WHITE).otherwise(Color.BLACK)
            );

            text.fillProperty().bind(
                    Bindings.when(hoverProperty()).then(Color.BLACK).otherwise(Color.WHITE)
            );
            setOnMouseClicked(e -> {
                action.run();
                FXGL.play("menu_click.wav");

            });
            getChildren().addAll(bg, text);
        }
    }
}