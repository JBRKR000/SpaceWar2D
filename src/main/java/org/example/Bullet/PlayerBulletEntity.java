package org.example.Bullet;
import com.almasb.fxgl.dsl.components.*;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.entity.components.TimeComponent;
import javafx.geometry.Point2D;
import org.example.Other.EntityType;

import static com.almasb.fxgl.dsl.FXGL.*;


public class PlayerBulletEntity implements EntityFactory {


    @Spawns("player_bullet")
    public Entity playerBullet(SpawnData data) {
        Point2D dir = data.get("dir");

        var effectComponent = new EffectComponent();

        var entity = entityBuilder(data)
                .type(EntityType.PLAYER_BULLET)
                .scale(0.45,0.45)
                .viewWithBBox("bullet_5.png")
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

}
