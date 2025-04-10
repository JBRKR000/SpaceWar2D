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
import org.jetbrains.annotations.NotNull;

public class rocketPower implements EntityFactory {
    @Spawns("rocket")
    public Entity rocket(SpawnData data) {
        var velocity = new Point2D(1, -1);
        return getEntity(data, velocity);
    }
    @Spawns("rocket3")
    public Entity rocket3(SpawnData data) {
        var velocity = new Point2D(0, -1);
        return getEntity(data, velocity);
    }
    @Spawns("rocket2")
    public Entity rocket2(SpawnData data) {
        var velocity = new Point2D(-1, -1);
        return getEntity(data, velocity);
    }

    @NotNull
    private Entity getEntity(SpawnData data, Point2D velocity) {
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
            entity.rotateBy(90);
        });
        return entity;
    }
}
