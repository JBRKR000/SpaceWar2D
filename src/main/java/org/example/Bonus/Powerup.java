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

public class Powerup implements EntityFactory {
    @Spawns("powerup")
    public Entity powerup(SpawnData data) {
        var velocity = new Point2D(0, 1);
        var entity = entityBuilder(data)
                .type(EntityType.POWERUP)
                .scale(0.8, 0.8)
                .viewWithBBox("powerup.png")
                .with(new OffscreenCleanComponent())
                .with(new ProjectileComponent(velocity, 200))
                .collidable()
                .build();
        entity.setOnActive(() -> {
            entity.rotateBy(-180);
        });
        return entity;
    }
}
