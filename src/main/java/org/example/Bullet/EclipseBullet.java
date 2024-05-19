package org.example.Bullet;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.OffscreenCleanComponent;
import com.almasb.fxgl.dsl.components.ProjectileComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import javafx.geometry.Point2D;
import org.example.Other.EntityType;

import static com.almasb.fxgl.dsl.FXGL.entityBuilder;

public class EclipseBullet implements EntityFactory {
    @Spawns("eclipse_bullet")
    public Entity enemyBullet(SpawnData data) {
        var velocity = new Point2D(0, 1);
        var entity = entityBuilder(data)
                .type(EntityType.ENEMY_BULLET)
                .scale(0.45, 0.45)
                .viewWithBBox("bullet_2.png")
                .with(new OffscreenCleanComponent())
                .with(new ProjectileComponent(velocity, 250))
                .collidable()
                .build();

        entity.setOnActive(() -> {
            spawnTripleBurst(entity);
        });

        entity.addComponent(new EclipseBullet.EnemyBulletTracking());
        return entity;
    }

    private void spawnTripleBurst(Entity sourceEntity) {
        for (int i = 0; i < 3; i++) {
            Entity bullet = createBullet(sourceEntity, i);
            FXGL.getGameWorld().addEntity(bullet);
        }
    }

    private Entity createBullet(Entity sourceEntity, int index) {
        double angle = 45 + index * 45;
        Point2D velocityVector = new Point2D(Math.cos(Math.toRadians(angle)), Math.sin(Math.toRadians(angle))).multiply(1.2);

        var bullet = entityBuilder()
                .at(sourceEntity.getPosition())
                .type(EntityType.ENEMY_BULLET)
                .viewWithBBox("bullet_2.png")
                .with(new OffscreenCleanComponent())
                .with(new ProjectileComponent(velocityVector, 200))
                .collidable()
                .build();

        bullet.addComponent(new EclipseBullet.EnemyBulletTracking());

        return bullet;
    }

    private static class EnemyBulletTracking extends com.almasb.fxgl.entity.component.Component {
        @Override
        public void onUpdate(double tpf) {
//            entity.setRotation(0);
        }
    }
}
