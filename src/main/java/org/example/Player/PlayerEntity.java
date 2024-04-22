package org.example.Player;

import com.almasb.fxgl.dsl.components.HealthIntComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.ui.ProgressBar;
import org.example.Other.EntityType;

import static com.almasb.fxgl.dsl.FXGL.entityBuilder;

public class PlayerEntity implements EntityFactory {

    private static final int MAX_HP = 10;


    @Spawns("player")
    public Entity newPlayer(SpawnData data) {
        var hp = new HealthIntComponent(MAX_HP);
        var hpView = new ProgressBar(false);
        hpView.setMaxValue(MAX_HP);
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
