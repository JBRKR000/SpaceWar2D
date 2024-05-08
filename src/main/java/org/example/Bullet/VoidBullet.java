package org.example.Bullet;

import com.almasb.fxgl.dsl.components.OffscreenCleanComponent;
import com.almasb.fxgl.dsl.components.ProjectileComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import javafx.geometry.Point2D;
import org.example.Other.EntityType;

import static com.almasb.fxgl.dsl.FXGL.entityBuilder;

public class VoidBullet implements EntityFactory {
    @Spawns("void_bullet")
    public Entity enemyBullet(SpawnData data) {
        var velocity = new Point2D(0,1);
        var entity = entityBuilder(data)
                .type(EntityType.ENEMY_BULLET)
                .scale(0.45,0.45)
                .viewWithBBox("bullet_6.png")
                .with(new OffscreenCleanComponent())
                .with(new ProjectileComponent(velocity, 150))
                .collidable()
                .build();

        entity.setOnActive(() -> {
            // TODO EFFECTS

        });
        entity.addComponent(new VoidBullet.EnemyBulletTracking());
        return entity;
    }
    private static class EnemyBulletTracking extends com.almasb.fxgl.entity.component.Component {
        @Override
        public void onUpdate(double tpf) {
            entity.setRotation(0);
        }
    }
}
