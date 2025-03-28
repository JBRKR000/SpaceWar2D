package org.example.Bullet;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.OffscreenCleanComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import javafx.geometry.Point2D;
import org.example.GunUpdates.CircularMovementComponent;
import org.example.Other.EntityType;

import static com.almasb.fxgl.dsl.FXGL.entityBuilder;
import static org.example.Enemy.Fighter.pos;

public class FighterBullet implements EntityFactory {
    @Spawns("fighter_bullet")
    public Entity enemyBullet(SpawnData data) {
        Point2D center = new Point2D(data.getX(), data.getY());
        double radius = 200;
        double speed = 5;
        double verticalSpeed = 250;
        Entity firstEntity = null;

        for (int i = 0; i < 4; i++) {
            double angle = i * (Math.PI / 2);
            double x = center.getX() + radius * Math.cos(angle);
            double y = center.getY() + radius * Math.sin(angle);

            var entity = entityBuilder(new SpawnData(x, y))
                    .type(EntityType.ENEMY_BULLET)
                    .scale(0.3, 0.3)
                    .viewWithBBox("fighter_bullet.png")
                    .with(new OffscreenCleanComponent())
                    .with(new CircularMovementComponent(center, radius, speed, verticalSpeed))
                    .collidable()
                    .build();

            entity.setOnActive(() -> {
                // Additional logic if needed
            });

            entity.addComponent(new EnemyBulletTracking());
            FXGL.getGameWorld().addEntity(entity);

            // Store the first entity
            if (firstEntity == null) {
                firstEntity = entity;
            }
        }

        // Return the first entity as the result
        return firstEntity;
    }


    private static class EnemyBulletTracking extends com.almasb.fxgl.entity.component.Component {
        @Override
        public void onUpdate(double tpf) {
            entity.setRotation(0);
        }
    }
}