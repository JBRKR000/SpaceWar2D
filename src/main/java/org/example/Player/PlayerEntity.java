package org.example.Player;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.HealthIntComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.ui.ProgressBar;
import javafx.scene.input.KeyCode;
import kotlin.Unit;
import org.example.Other.EntityType;

import static com.almasb.fxgl.dsl.FXGL.entityBuilder;
import static com.almasb.fxgl.dsl.FXGLForKtKt.onKeyDown;

public class PlayerEntity implements EntityFactory {

    private static final int MAX_HP = 10;
    private static final int DEBUG_HP = -1;
    private int currentHP = MAX_HP;


    @Spawns("player")
    public Entity newPlayer(SpawnData data) {
        var hp = new HealthIntComponent(currentHP); // Używamy aktualnej wartości punktów życia
        var hpView = new ProgressBar(false);
        hpView.setMaxValue(currentHP);
        hpView.setWidth(50);
        hpView.setTranslateY(60);
        hpView.setTranslateX(13);
        hpView.currentValueProperty().bind(hp.valueProperty());
        Entity entity = entityBuilder(data)
                .type(EntityType.PLAYER)
                .viewWithBBox("player.png")
                .with(hp)
                .view(hpView)
                .with(new PlayerComponent())
                .collidable()
                .build();
        return entity;
    }




}
