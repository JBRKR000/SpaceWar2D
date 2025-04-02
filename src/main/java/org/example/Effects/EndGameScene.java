package org.example.Effects;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.scene.SubScene;
import javafx.animation.FadeTransition;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;
import org.example.Init.InitSettings;

import static org.example.Init.InitSettings.*;

public class EndGameScene extends SubScene {
    public EndGameScene(int finalScore) {
        Image backgroundImage = new Image("/assets/textures/background.jpg");
        ImageView backgroundView = new ImageView(backgroundImage);
        backgroundView.setFitWidth(FXGL.getAppWidth());
        backgroundView.setFitHeight(FXGL.getAppHeight());

        Text message = new Text("Gratulacje! Ukończyłeś grę!");
        Text message2 = new Text("Twój wynik: " + finalScore);
        Text thankYouMessage = new Text("Dziękujemy za przejście wersji demo. Mamy nadzieję, że się spodobało!");

        message.setFill(Color.WHITE);
        message2.setFill(Color.WHITE);
        thankYouMessage.setFill(Color.WHITE);

        message.setFont(Font.font("Comic Sans MS", 24));
        message2.setFont(Font.font("Comic Sans MS", 24));
        thankYouMessage.setFont(Font.font("Comic Sans MS", 24));

        message2.setTextAlignment(TextAlignment.CENTER);
        thankYouMessage.setTextAlignment(TextAlignment.CENTER);

        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(2), message);
        FadeTransition fadeTransition2 = new FadeTransition(Duration.seconds(2), message2);
        FadeTransition fadeTransition3 = new FadeTransition(Duration.seconds(2), thankYouMessage);

        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.setCycleCount(1);
        fadeTransition.play();

        fadeTransition2.setFromValue(0);
        fadeTransition2.setToValue(1);
        fadeTransition2.setCycleCount(1);
        fadeTransition2.play();

        fadeTransition3.setFromValue(0);
        fadeTransition3.setToValue(1);
        fadeTransition3.setCycleCount(1);
        fadeTransition3.play();

        Button mainMenuButton = new Button("Powrót do menu głównego");
        mainMenuButton.setStyle("-fx-background-color: #ff0000; -fx-text-fill: white; -fx-font-size: 16px;");
        mainMenuButton.setOnAction(e -> {
            FXGL.getGameController().gotoMainMenu();
            FXGL.getAudioPlayer().stopMusic(endgameMusic);
            currentMusic = FXGL.getAssetLoader().loadMusic("CosmicConquest.mp3");
            wave = 1;
            FXGL.getAudioPlayer().playMusic(InitSettings.currentMusic);
        });

        VBox layout = new VBox(10, message, message2, thankYouMessage, mainMenuButton);
        layout.setAlignment(Pos.CENTER);
        layout.setTranslateX((double) FXGL.getAppWidth() / 2 - 1000);
        layout.setTranslateY((double) FXGL.getAppHeight() / 2 - 650);

        StackPane root = new StackPane(backgroundView, layout);
        root.setAlignment(Pos.CENTER);
        getContentRoot().getChildren().add(root);
    }
}
