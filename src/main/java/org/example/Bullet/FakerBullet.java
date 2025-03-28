package org.example.Bullet;

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

public class FakerBullet implements EntityFactory {

    @Spawns("faker_bullet")
    public Entity enemyBullet(SpawnData data) {
        Point2D startPosition = new Point2D(data.getX(), data.getY());
        Point2D velocity = new Point2D(0, 1);
        var entity = entityBuilder(data)
                .type(EntityType.FAKER_BULLET)
                .viewWithBBox("faker_bullet.png")
                .with(new OffscreenCleanComponent())
                .with(new ProjectileComponent(velocity, 400))
                .collidable()
                .build();

        entity.setOnActive(() -> {
            FXGL.run(() -> {
                entity.setScaleX(entity.getScaleX() * 1.1);
                entity.setScaleY(entity.getScaleY() * 1.1);
            }, Duration.seconds(0.1));

            FXGL.runOnce(() -> {
                createExplosion(entity);
                entity.removeFromWorld();
            }, Duration.seconds(1.5));
        });

        entity.addComponent(new EnemyBulletTracking());
        return entity;
    }

    private void createExplosion(Entity entity) {
        Point2D center = entity.getPosition();
        double speed = 200;
        for (int i = 0; i < 8; i++) {
            double angle = i * (Math.PI / 4);
            Point2D velocity = new Point2D(Math.cos(angle), Math.sin(angle));
            var smallBullet = entityBuilder(new SpawnData(center.getX(), center.getY()))
                    .type(EntityType.FAKER_BULLET)
                    .viewWithBBox("faker_bullet.png")
                    .with(new OffscreenCleanComponent())
                    .with(new ProjectileComponent(velocity, speed))
                    .collidable()
                    .build();
            FXGL.getGameWorld().addEntity(smallBullet);
        }
    }

    private static class EnemyBulletTracking extends com.almasb.fxgl.entity.component.Component {
        @Override
        public void onUpdate(double tpf) {
            entity.setRotation(0);
        }
    }
}