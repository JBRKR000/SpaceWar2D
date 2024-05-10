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

public class StrikerBullet implements EntityFactory {
    @Spawns("striker_bullet")
    public Entity enemyBullet(SpawnData data) {
        var entity = entityBuilder(data)
                .type(EntityType.ENEMY_BULLET)
                .scale(0.45, 0.45)
                .viewWithBBox("bullet_4.png")
                .with(new OffscreenCleanComponent())
                .collidable()
                .build();

        entity.setOnActive(() -> {
            spawnDoubleShoot(entity);
        });

        entity.addComponent(new StrikerBullet.EnemyBulletTracking());

        return entity;
    }

    private void spawnDoubleShoot(Entity sourceEntity) {
        for (int i = 0; i < 2; i++) {
            Entity bullet = createBullet(sourceEntity, i);
            FXGL.getGameWorld().addEntity(bullet);
        }
    }

    private Entity createBullet(Entity sourceEntity, int index) {
        var velocity = new Point2D(0, 1);
        var bullet = entityBuilder()
                .at(sourceEntity.getPosition().add(index * 30, 0))
                .type(EntityType.ENEMY_BULLET)
                .viewWithBBox("bullet_4.png")
                .with(new OffscreenCleanComponent())
                .with(new ProjectileComponent(velocity, 100))
                .collidable()
                .build();

        bullet.addComponent(new StrikerBullet.EnemyBulletTracking());

        return bullet;
    }

    private static class EnemyBulletTracking extends com.almasb.fxgl.entity.component.Component {
        @Override
        public void onUpdate(double tpf) {
            entity.setRotation(0);
        }
    }
}
