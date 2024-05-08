package org.example.Debug;

import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.example.Init.InitSettings;

public class DebugWindow {

    public static void show(InitSettings initSettings) {
        Stage stage = new Stage();
        stage.initModality(Modality.NONE);
        stage.initStyle(StageStyle.UTILITY);
        stage.setResizable(false);
        stage.setTitle("Debug Window");

        VBox vbox = new VBox(10);
        Label label = new Label("Available options:");
        CheckBox godModeCheckbox = new CheckBox("God Mode");
        CheckBox everyEnemyHas1HP = new CheckBox("Every Enemy Has 1 HP");

        godModeCheckbox.setOnAction(event -> {
            if (godModeCheckbox.isSelected()) {
                initSettings.toggleGodMode();
            } else {
                initSettings.toggleOffGodMode();
            }
        });
        everyEnemyHas1HP.setOnAction(event -> {
            if (everyEnemyHas1HP.isSelected()) {
                initSettings.toggleEveryEnemyHas1HP();
            } else {

            }
        });

        vbox.getChildren().addAll(label, godModeCheckbox);

        Scene scene = new Scene(vbox, 200, 150);
        stage.setScene(scene);
        stage.show();
    }
}
