package org.example.GunUpdates;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.EffectComponent;
import com.almasb.fxgl.dsl.components.OffscreenCleanComponent;
import com.almasb.fxgl.dsl.components.ProjectileComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.entity.components.TimeComponent;
import javafx.geometry.Point2D;
import org.example.Other.EntityType;

import static com.almasb.fxgl.dsl.FXGL.entityBuilder;

public class GunUpdateEntities implements EntityFactory {

    @Spawns("lvl2")
    public Entity lvl2(SpawnData data) {
        Point2D dir = new Point2D(0, -1);
        var entity = entityBuilder(data)
                .type(EntityType.PLAYER_BULLET)
                .scale(0.45, 0.45)
                .viewWithBBox("bullet_5.png")
                .with(new OffscreenCleanComponent())
                .with(new ProjectileComponent(dir, 300))
                .collidable()
                .build();

        entity.setOnActive(() -> {
            spawnDoubleShoot(entity);
        });
        return entity;
    }

    private void spawnDoubleShoot(Entity sourceEntity) {
        for (int i = 0; i < 1; i++) {
            Entity bullet = createdoubleBullet(sourceEntity, i);
            FXGL.getGameWorld().addEntity(bullet);
        }
    }

    private Entity createdoubleBullet(Entity sourceEntity, int index) {
        var velocity = new Point2D(0, -1);  // Kierunek w górę
        var bullet = entityBuilder()
                .at(sourceEntity.getPosition().add(index * 30 - 15, -30))
                .scale(0.45, 0.45)
                .type(EntityType.PLAYER_BULLET)
                .viewWithBBox("bullet_5.png")
                .with(new OffscreenCleanComponent())
                .with(new ProjectileComponent(velocity, 300))  // Ustawienie prędkości
                .collidable()
                .build();
        return bullet;
    }
    @Spawns("lvl3")
    public Entity lvl3(SpawnData data) {
        Point2D dir = new Point2D(0, -1);
        var entity = entityBuilder(data)
                .type(EntityType.PLAYER_BULLET)
                .scale(0.45, 0.45)
                .viewWithBBox("bullet_5.png")
                .with(new OffscreenCleanComponent())
                .with(new ProjectileComponent(dir, 300))
                .collidable()
                .build();

        entity.setOnActive(() -> {
            spawnTripleShoot(entity);
        });
        return entity;
    }
    private void spawnTripleShoot(Entity sourceEntity) {
        for (int i = 0; i < 2; i++) {
            Entity bullet = createTripleBullet(sourceEntity, i);
            FXGL.getGameWorld().addEntity(bullet);
        }
    }

    private Entity createTripleBullet(Entity sourceEntity, int index) {
        var velocity = new Point2D(0, -1);  // Kierunek w górę
        var bullet = entityBuilder()
                .at(sourceEntity.getPosition().add(index * 30 - 15, -30))
                .scale(0.45, 0.45)
                .type(EntityType.PLAYER_BULLET)
                .viewWithBBox("bullet_5.png")
                .with(new OffscreenCleanComponent())
                .with(new ProjectileComponent(velocity, 300))  // Ustawienie prędkości
                .collidable()
                .build();
        return bullet;
    }

    @Spawns("lvl4")
    public Entity playerBullet(SpawnData data) {
        Point2D dir = data.get("dir");
        var effectComponent = new EffectComponent();
        var entity = entityBuilder(data)
                .type(EntityType.PLAYER_BULLET)
                .scale(0.8,0.8)
                .viewWithBBox("bullet_lvl_4.png")
                .with(new ProjectileComponent(dir, 300))
                .with(new OffscreenCleanComponent())
                .with(new TimeComponent())
                .with(effectComponent)
                .collidable()
                .build();
        entity.setOnActive(() -> {
            spawnTripleBurst_lvl4(entity);
        });
        return entity;
    }
    private void spawnTripleBurst_lvl4(Entity sourceEntity) {
        for (int i = 0; i < 2; i++) {
            Entity bullet = createBullet_lvl4(sourceEntity, i);
            FXGL.getGameWorld().addEntity(bullet);
        }
    }

    private Entity createBullet_lvl4(Entity sourceEntity, int index) {
        double angle = -97 + index * 15;
        Point2D velocityVector = new Point2D(Math.cos(Math.toRadians(angle)), Math.sin(Math.toRadians(angle))).multiply(1.2);

        var bullet = entityBuilder()
                .at(sourceEntity.getPosition())
                .scale(0.8,0.8)
                .type(EntityType.PLAYER_BULLET)
                .viewWithBBox("bullet_lvl_4.png")
                .with(new OffscreenCleanComponent())
                .with(new ProjectileComponent(velocityVector, 300))
                .collidable()
                .build();
        return bullet;
    }

    @Spawns("lvl5")
    public Entity playerBulletentity(SpawnData data) {
        Point2D dir = data.get("dir");
        var effectComponent = new EffectComponent();
        var entity = entityBuilder(data)
                .type(EntityType.PLAYER_BULLET)
                .scale(0.8,0.8)
                .viewWithBBox("bullet_lvl_4.png")
                .with(new ProjectileComponent(dir, 300))
                .with(new OffscreenCleanComponent())
                .with(new TimeComponent())
                .with(effectComponent)
                .collidable()
                .build();
        entity.setOnActive(() -> {
            spawnTripleBurst_lvl5(entity);
        });
        return entity;
    }
    private void spawnTripleBurst_lvl5(Entity sourceEntity) {
        for (int i = 0; i < 5; i++) {
            Entity bullet = createBullet_lvl5(sourceEntity, i);
            FXGL.getGameWorld().addEntity(bullet);
        }
    }

    private Entity createBullet_lvl5(Entity sourceEntity, int index) {
        double angle = -118 + index * 15;
        Point2D velocityVector = new Point2D(Math.cos(Math.toRadians(angle)), Math.sin(Math.toRadians(angle))).multiply(1.2);

        var bullet = entityBuilder()
                .at(sourceEntity.getPosition())
                .scale(0.8,0.8)
                .type(EntityType.PLAYER_BULLET)
                .viewWithBBox("bullet_lvl_4.png")
                .with(new OffscreenCleanComponent())
                .with(new ProjectileComponent(velocityVector, 300))
                .collidable()
                .build();
        return bullet;
    }



}
