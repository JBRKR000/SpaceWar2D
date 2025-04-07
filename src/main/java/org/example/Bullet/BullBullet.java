package org.example.Bullet;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.OffscreenCleanComponent;
import com.almasb.fxgl.dsl.components.ProjectileComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import javafx.geometry.Point2D;
import javafx.util.Duration;
import org.example.Other.EntityType;

public class BullBullet implements EntityFactory {

    @Spawns("bull_bullet")
    public Entity newBullet(SpawnData data) {
        Point2D target = FXGL.getGameWorld().getSingleton(EntityType.PLAYER).getPosition();
        Point2D direction = target.subtract(data.getX(), data.getY()).normalize();

       var entity = FXGL.entityBuilder(data)
                .view("bull_bullet.png")
                .type(EntityType.ENEMY_BULLET)
                .bbox(new HitBox(BoundingShape.circle(5)))
                .with(new ProjectileComponent(direction, 500))
                .with(new OffscreenCleanComponent())
                .collidable()
                .build();
        entity.setOnActive(() -> {
            FXGL.run(() -> {
               entity.setPosition(target.getX(), target.getY() - 100);
            }, Duration.seconds(1));
        });

        return entity;
    }


    }
