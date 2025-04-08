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
import org.example.GunUpdates.SpiralComponent;
import org.example.GunUpdates.ZigZagComponent;
import org.example.Other.EntityType;

public class CoreBullet implements EntityFactory {

    @Spawns("core_bullet")
    public Entity newBullet(SpawnData data) {
        var velocity = new Point2D(0,1);
        var entity = FXGL.entityBuilder(data)
                .viewWithBBox("core_bullet.png")
                .type(EntityType.ENEMY_BULLET)
                .with(new ProjectileComponent(velocity, 1000))
                .with(new ZigZagComponent(10000,10))
                .with(new OffscreenCleanComponent())
                .collidable()
                .build();
        entity.setOnActive(() -> {

        });

        return entity;
    }


}
