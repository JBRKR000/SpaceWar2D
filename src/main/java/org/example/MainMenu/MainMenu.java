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
        double appWidth = FXGL.getAppWidth();
        double appHeight = FXGL.getAppHeight();
        BackgroundSize backgroundSize = new BackgroundSize(appWidth, appHeight, false, false, false, false);
        Image backgroundImage = new Image("assets/textures/background3.jpg");
        BackgroundImage background = new BackgroundImage(backgroundImage,
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT, backgroundSize);
        Media media = new Media(Objects.requireNonNull(getClass().getResource("/assets/music/CosmicConquest.mp3")).toString());
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
        getContentRoot().setBackground(new Background(background));
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