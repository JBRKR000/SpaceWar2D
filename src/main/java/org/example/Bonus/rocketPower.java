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

public class rocketPower implements EntityFactory {
    @Spawns("rocket")
    public Entity rocket(SpawnData data) {
        var velocity = new Point2D(0, -1);
        var entity = FXGL.entityBuilder(data)
                .type(EntityType.ROCKET)
                .scale(1, 1)
                .rotate(90)
                .viewWithBBox("rocket.png")
                .with(new OffscreenCleanComponent())
                .with(new ProjectileComponent(velocity, 400))
                .collidable()
                .build();
        entity.setOnActive(() -> {
        });
        return entity;
    }
}
