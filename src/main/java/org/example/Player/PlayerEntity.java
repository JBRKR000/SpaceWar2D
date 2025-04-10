package org.example.Player;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.HealthIntComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.ui.ProgressBar;
import javafx.util.Duration;
import org.example.Other.EntityType;
import static com.almasb.fxgl.dsl.FXGL.entityBuilder;

public class PlayerEntity implements EntityFactory {
    private static final int MAX_HP = 1;
    public static int currentHP = MAX_HP;
    public static int hp_;
    public static double p_x;
    public static double p_y;


    @Spawns("player")
    public Entity newPlayer(SpawnData data) {
        var hp = new HealthIntComponent(currentHP); // Używamy aktualnej wartości punktów życia
        var hpView = new ProgressBar(false);
        hpView.setMaxValue(currentHP);
        hpView.setWidth(50);
        hpView.setTranslateY(60);
        hpView.setTranslateX(-4);
        hpView.currentValueProperty().bind(hp.valueProperty());

        Entity entity = entityBuilder(data)
                .type(EntityType.PLAYER)
                .bbox(new HitBox(BoundingShape.box(64,58)))
//                .viewWithBBox("player.png")
                .with(hp)
                .with(new PlayerComponent())
                .collidable()
                .build();
        FXGL.run(()->{
            p_x = entity.getX();
            p_y = entity.getY();
        }, Duration.seconds(0.001));
        return entity;
    }
}


