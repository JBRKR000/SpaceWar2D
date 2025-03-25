package org.example.Bonus;

import com.almasb.fxgl.dsl.components.OffscreenCleanComponent;
import com.almasb.fxgl.dsl.components.ProjectileComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import javafx.geometry.Point2D;
import org.example.Other.EntityType;

import static com.almasb.fxgl.dsl.FXGL.entityBuilder;

public class BonusDrop implements EntityFactory {

    @Spawns("bonus_drop")
    public Entity bonus_drop(SpawnData data) {
        var velocity = new Point2D(0, 1);
        var entity = entityBuilder(data)
                .type(EntityType.BONUS)
                .scale(.8 ,.8)
                .viewWithBBox("bonus.png")
                .with(new OffscreenCleanComponent())
                .with(new ProjectileComponent(velocity, 250))
                .collidable()
                .build();
        entity.setOnActive(() -> {
            entity.rotateBy(-90);
        });
        return entity;
    }
}
