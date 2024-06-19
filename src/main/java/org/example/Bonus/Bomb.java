package org.example.Bonus;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.OffscreenCleanComponent;
import com.almasb.fxgl.dsl.components.ProjectileComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import javafx.geometry.Point2D;
import javafx.util.Duration;
import org.example.Other.EntityType;

import static com.almasb.fxgl.dsl.FXGL.entityBuilder;
import static com.almasb.fxgl.dsl.FXGL.runOnce;

public class Bomb implements EntityFactory {

    @Spawns("Bomb")
    public Entity bomb(SpawnData data) {
        var velocity = new Point2D(0, -1);
        var entity = entityBuilder(data)
                .type(EntityType.BOMB)
                .scale(0.05, 0.05)
                .viewWithBBox("bomb.png")
                .with(new OffscreenCleanComponent())
                .with(new ProjectileComponent(velocity, 400))
                .collidable()
                .build();
        entity.setOnActive(() -> {
            runOnce(() ->{
                FXGL.getGameWorld().removeEntity(entity);
                spawnCircle(entity);
            }, Duration.seconds(0.8));
            runOnce(() ->{
                spawnCircle(entity);
            }, Duration.seconds(1));
            runOnce(() ->{
                spawnCircle(entity);
            }, Duration.seconds(1.2));
        });

        return entity;
    }

    private void spawnCircle(Entity sourceEntity) {
        for (int i = 0; i < 10; i++) {
            Entity bullet = createBullet(sourceEntity, i);
            FXGL.getGameWorld().addEntity(bullet);
        }
    }
    private Entity createBullet(Entity sourceEntity, int index) {
        double angle = 45 + index * 45;
        Point2D velocityVector = new Point2D(Math.cos(Math.toRadians(angle)), Math.sin(Math.toRadians(angle))).multiply(1.2);

        return entityBuilder()
                .at(sourceEntity.getPosition())
                .scale(0.02, 0.02)
                .type(EntityType.BOMB)
                .viewWithBBox("bomb.png")
                .with(new OffscreenCleanComponent())
                .with(new ProjectileComponent(velocityVector, 400))
                .collidable()
                .build();
    }
}
