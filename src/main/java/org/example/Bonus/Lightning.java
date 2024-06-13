package org.example.Bonus;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.OffscreenCleanComponent;
import com.almasb.fxgl.dsl.components.ProjectileComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import javafx.geometry.Point2D;
import org.example.Other.EntityType;

public class Lightning implements EntityFactory {
    @Spawns("lightning")
    public Entity lightning(SpawnData data) {
        var velocity = new Point2D(0, 1);
        var entity = FXGL.entityBuilder(data)
                .type(EntityType.LIGHTNING)
                .scale(1, 0.5)
                .viewWithBBox("lightning.png")
                .with(new OffscreenCleanComponent())
                .with(new ProjectileComponent(velocity, 0))
                .collidable()
                .build();
        entity.setOnActive(() -> {
        });
        return entity;
    }
}
