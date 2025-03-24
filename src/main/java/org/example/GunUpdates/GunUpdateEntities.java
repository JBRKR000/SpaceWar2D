package org.example.GunUpdates;

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

public class GunUpdateEntities implements EntityFactory {

    @Spawns("lvl1")
    public Entity lvl1(SpawnData data) {
        Point2D dir = new Point2D(0, -1);
        return entityBuilder(data)
                .type(EntityType.PLAYER_BULLET)
                .scale(0.45, 0.45)
                .viewWithBBox("bullet_5.png")
                .with(new OffscreenCleanComponent())
                .with(new ProjectileComponent(dir, 300))
                .collidable()
                .build();
    }

    @Spawns("lvl2")
    public Entity lvl2(SpawnData data) {
        Point2D dir = new Point2D(0, -1);
        return entityBuilder(data)
                .type(EntityType.PLAYER_BULLET)
                .scale(0.45, 0.45)
                .viewWithBBox("bullet_5.png")
                .with(new OffscreenCleanComponent())
                .with(new ProjectileComponent(dir, 300))
                .collidable()
                .build();
    }

    @Spawns("lvl3")
    public Entity lvl3(SpawnData data) {
        Point2D dir1 = new Point2D(0, -1);
        Point2D dir2 = new Point2D(-1, -1).normalize();
        Point2D dir3 = new Point2D(1, -1).normalize();

        Entity bullet1 = entityBuilder(data)
                .type(EntityType.PLAYER_BULLET)
                .scale(0.8, 0.8)
                .viewWithBBox("bullet_lvl_4.png")
                .with(new OffscreenCleanComponent())
                .with(new ProjectileComponent(dir1, 300))
                .collidable()
                .build();

        Entity bullet2 = entityBuilder(data)
                .type(EntityType.PLAYER_BULLET)
                .scale(0.8, 0.8)
                .viewWithBBox("bullet_lvl_4.png")
                .with(new OffscreenCleanComponent())
                .with(new ProjectileComponent(dir2, 300))
                .collidable()
                .build();

        Entity bullet3 = entityBuilder(data)
                .type(EntityType.PLAYER_BULLET)
                .scale(0.8, 0.8)
                .viewWithBBox("bullet_lvl_4.png")
                .with(new OffscreenCleanComponent())
                .with(new ProjectileComponent(dir3, 300))
                .collidable()
                .build();

        FXGL.getGameWorld().addEntities(bullet1, bullet2, bullet3);

        return bullet1;
    }

    @Spawns("lvl4")
    public Entity lvl4(SpawnData data) {
        Point2D dir1 = new Point2D(0, -1);
        Point2D dir2 = new Point2D(-1, -1).normalize();
        Point2D dir3 = new Point2D(1, -1).normalize();
        Point2D dir4 = new Point2D(-1, -2).normalize();
        Point2D dir5 = new Point2D(1, -2).normalize();

        Entity bullet1 = entityBuilder(data)
                .type(EntityType.PLAYER_BULLET)
                .scale(0.8, 0.8)
                .viewWithBBox("bullet_lvl_4.png")
                .with(new OffscreenCleanComponent())
                .with(new ProjectileComponent(dir1, 300))
                .collidable()
                .build();

        Entity bullet2 = entityBuilder(data)
                .type(EntityType.PLAYER_BULLET)
                .scale(0.8, 0.8)
                .viewWithBBox("bullet_lvl_4.png")
                .with(new OffscreenCleanComponent())
                .with(new ProjectileComponent(dir2, 300))
                .collidable()
                .build();

        Entity bullet3 = entityBuilder(data)
                .type(EntityType.PLAYER_BULLET)
                .scale(0.8, 0.8)
                .viewWithBBox("bullet_lvl_4.png")
                .with(new OffscreenCleanComponent())
                .with(new ProjectileComponent(dir3, 300))
                .collidable()
                .build();

        Entity bullet4 = entityBuilder(data)
                .type(EntityType.PLAYER_BULLET)
                .scale(0.8, 0.8)
                .viewWithBBox("bullet_lvl_4.png")
                .with(new OffscreenCleanComponent())
                .with(new ProjectileComponent(dir4, 300))
                .collidable()
                .build();

        Entity bullet5 = entityBuilder(data)
                .type(EntityType.PLAYER_BULLET)
                .scale(0.8, 0.8)
                .viewWithBBox("bullet_lvl_4.png")
                .with(new OffscreenCleanComponent())
                .with(new ProjectileComponent(dir5, 300))
                .collidable()
                .build();

        FXGL.getGameWorld().addEntities(bullet1, bullet2, bullet3, bullet4, bullet5);

        return bullet1;
    }
    @Spawns("lvl5")
    public Entity lvl5(SpawnData data) {
        Point2D dir = new Point2D(0, -1).normalize();
        Point2D dir2 = new Point2D(0, -1).normalize();
        Entity bullet = entityBuilder(data)
                .type(EntityType.PLAYER_BULLET)
                .scale(0.8, 0.8)
                .viewWithBBox("b1.png")
                .with(new OffscreenCleanComponent())
                .with(new ProjectileComponent(dir, 450))
                .with(new SpiralComponent(5, 300))
                .collidable()
                .build();
        Entity bullet2 = entityBuilder(data)
                .type(EntityType.PLAYER_BULLET)
                .scale(0.8, 0.8)
                .viewWithBBox("b1.png")
                .with(new OffscreenCleanComponent())
                .with(new ProjectileComponent(dir2, 450))
                .with(new SpiralComponent(5, -300))
                .collidable()
                .build();

        FXGL.getGameWorld().addEntities(bullet, bullet2);

        return bullet;
    }
    @Spawns("lvl6")
    public Entity lvl6(SpawnData data) {
        Point2D dir = new Point2D(0, -1).normalize();
        Point2D dir2 = new Point2D(0, -1).normalize();
        Entity bullet = entityBuilder(data)
                .type(EntityType.PLAYER_BULLET)
                .scale(0.8, 0.8)
                .viewWithBBox("b1.png")
                .with(new OffscreenCleanComponent())
                .with(new ProjectileComponent(dir, 450))
                .with(new SpiralComponent(5, 300))
                .collidable()
                .build();
        Entity bullet2 = entityBuilder(data)
                .type(EntityType.PLAYER_BULLET)
                .scale(0.8, 0.8)
                .viewWithBBox("b1.png")
                .with(new OffscreenCleanComponent())
                .with(new ProjectileComponent(dir2, 450))
                .with(new SpiralComponent(5, -300))
                .collidable()
                .build();
        Entity bullet3 = entityBuilder(data)
                .type(EntityType.PLAYER_BULLET)
                .scale(0.8, 0.8)
                .viewWithBBox("b1.png")
                .with(new OffscreenCleanComponent())
                .with(new ProjectileComponent(dir2, 450))
                .with(new SpiralComponent(5, -800))
                .collidable()
                .build();
        Entity bullet4 = entityBuilder(data)
                .type(EntityType.PLAYER_BULLET)
                .scale(0.8, 0.8)
                .viewWithBBox("b1.png")
                .with(new OffscreenCleanComponent())
                .with(new ProjectileComponent(dir2, 450))
                .with(new SpiralComponent(5, 800))
                .collidable()
                .build();
        FXGL.getGameWorld().addEntities(bullet, bullet2, bullet3, bullet4);

        return bullet;
    }
    @Spawns("lvl7")
    public Entity lvl7(SpawnData data) {
        Point2D dir = new Point2D(0, -1).normalize();
        Entity bullet = entityBuilder(data)
                .type(EntityType.PLAYER_BULLET)
                .scale(1, 1)
                .viewWithBBox("b2.png")
                .with(new OffscreenCleanComponent())
                .with(new ProjectileComponent(dir, 300))
                .with(new CrazyMovementComponent(100))
                .collidable()
                .build();
        Entity bullet2 = entityBuilder(data)
                .type(EntityType.PLAYER_BULLET)
                .scale(1, 1)
                .viewWithBBox("b2.png")
                .with(new OffscreenCleanComponent())
                .with(new ProjectileComponent(dir, 300))
                .with(new CrazyMovementComponent(75))
                .collidable()
                .build();
        Entity bullet3 = entityBuilder(data)
                .type(EntityType.PLAYER_BULLET)
                .scale(1, 1)
                .viewWithBBox("b2.png")
                .with(new OffscreenCleanComponent())
                .with(new ProjectileComponent(dir, 300))
                .with(new CrazyMovementComponent(125))
                .collidable()
                .build();
        Entity bullet4 = entityBuilder(data)
                .type(EntityType.PLAYER_BULLET)
                .scale(1, 1)
                .viewWithBBox("b2.png")
                .with(new OffscreenCleanComponent())
                .with(new ProjectileComponent(dir, 300))
                .with(new CrazyMovementComponent(130))
                .collidable()
                .build();

        FXGL.getGameWorld().addEntities(bullet, bullet2, bullet3, bullet4);
        return bullet;
    }
    @Spawns("lvl8")
    public Entity lvl8(SpawnData data) {
        Point2D dir = new Point2D(0, -1).normalize();
        Entity bullet = entityBuilder(data)
                .type(EntityType.PLAYER_BULLET)
                .scale(1, 1)
                .viewWithBBox("b2.png")
                .with(new OffscreenCleanComponent())
                .with(new ProjectileComponent(dir, 300))
                .with(new CrazyMovementComponent(100))
                .collidable()
                .build();
        Entity bullet2 = entityBuilder(data)
                .type(EntityType.PLAYER_BULLET)
                .scale(1, 1)
                .viewWithBBox("b2.png")
                .with(new OffscreenCleanComponent())
                .with(new ProjectileComponent(dir, 300))
                .with(new CrazyMovementComponent(75))
                .collidable()
                .build();
        Entity bullet3 = entityBuilder(data)
                .type(EntityType.PLAYER_BULLET)
                .scale(1, 1)
                .viewWithBBox("b2.png")
                .with(new OffscreenCleanComponent())
                .with(new ProjectileComponent(dir, 300))
                .with(new CrazyMovementComponent(125))
                .collidable()
                .build();
        Entity bullet4 = entityBuilder(data)
                .type(EntityType.PLAYER_BULLET)
                .scale(1, 1)
                .viewWithBBox("b2.png")
                .with(new OffscreenCleanComponent())
                .with(new ProjectileComponent(dir, 300))
                .with(new CrazyMovementComponent(130))
                .collidable()
                .build();
        Entity bullet5 = entityBuilder(data)
                .type(EntityType.PLAYER_BULLET)
                .scale(1, 1)
                .viewWithBBox("b2.png")
                .with(new OffscreenCleanComponent())
                .with(new ProjectileComponent(dir, 300))
                .with(new CrazyMovementComponent(130))
                .collidable()
                .build();
        Entity bullet6 = entityBuilder(data)
                .type(EntityType.PLAYER_BULLET)
                .scale(1, 1)
                .viewWithBBox("b2.png")
                .with(new OffscreenCleanComponent())
                .with(new ProjectileComponent(dir, 300))
                .with(new CrazyMovementComponent(130))
                .collidable()
                .build();

        FXGL.getGameWorld().addEntities(bullet, bullet2, bullet3, bullet4, bullet5, bullet6);
        return bullet;
    }
}