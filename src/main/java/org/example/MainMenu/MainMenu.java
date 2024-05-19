package org.example.MainMenu;

import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;
import com.almasb.fxgl.dsl.FXGL;
import javafx.beans.binding.Bindings;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.Objects;

public class MainMenu extends FXGLMenu {
    private MediaPlayer mediaPlayer;
    public MainMenu() {
        super(MenuType.MAIN_MENU);
        BackgroundSize backgroundSize = new BackgroundSize(1920, 1080, true, true, false, true);
        Image backgroundImage = new Image("assets/textures/background.jpg");
        BackgroundImage background = new BackgroundImage(backgroundImage,
                BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT,
                BackgroundPosition.DEFAULT, backgroundSize);
        getContentRoot().setBackground(new Background(background));
        Media media = new Media(Objects.requireNonNull(getClass().getResource("/assets/music/ChaseTheDestroyers.wav")).toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.setVolume(0.05);
        mediaPlayer.play();
        var button = new AsteroidsButton("START", () -> {
            mediaPlayer.stop();
            fireNewGame();
        });
        button.setTranslateX(FXGL.getAppWidth() / 2 - 200 / 2);
        button.setTranslateY(FXGL.getAppHeight() / 2 - 40 / 2);
        getContentRoot().getChildren().add(button);
    }

    private static class AsteroidsButton extends StackPane {
        public AsteroidsButton(String name, Runnable action) {
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
