package org.example.Bullet;

import com.almasb.fxgl.dsl.components.*;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.entity.components.TimeComponent;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import org.example.Other.EntityType;

import static com.almasb.fxgl.dsl.FXGL.*;


public class BulletEntity implements EntityFactory {



    @Spawns("player_bullet")
    public Entity playerBullet(SpawnData data) {
        Point2D dir = data.get("dir");

        var effectComponent = new EffectComponent();

        var entity = entityBuilder(data)
                .type(EntityType.PLAYER_BULLET)
                .scale(0.45,0.45)
                .viewWithBBox("shoot_1.png")
                .with(new ProjectileComponent(dir, 300))
                .with(new OffscreenCleanComponent())
                .with(new TimeComponent())
                .with(effectComponent)
                .collidable()
                .build();

        entity.setOnActive(() -> {
            // TODO EFFECTS
        });
        return entity;
    }
    @Spawns("enemy_bullet")
    public Entity enemyBullet(SpawnData data) {
        var velocity = new Point2D(0,1);
        var entity = entityBuilder(data)
                .type(EntityType.ENEMY_BULLET)
                .scale(0.45,0.45)
                .viewWithBBox("shoot_2.png")
                .with(new OffscreenCleanComponent())
                .with(new ProjectileComponent(velocity, 150))
                .collidable()
                .build();

        entity.setOnActive(() -> {
            // TODO EFFECTS

        });
        entity.addComponent(new EnemyBulletTracking());
        return entity;
    }
    private static class EnemyBulletTracking extends com.almasb.fxgl.entity.component.Component {
        @Override
        public void onUpdate(double tpf) {
            entity.setRotation(0);
        }
    }

}
