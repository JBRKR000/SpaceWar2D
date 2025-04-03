package org.example.Bullet;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import javafx.geometry.Point2D;
import javafx.util.Duration;
import org.example.GunUpdates.SpiralBetaClockWise;
import org.example.GunUpdates.SpiralBetaCounterClockWise;
import org.example.Other.EntityType;

import static com.almasb.fxgl.dsl.FXGLForKtKt.entityBuilder;

public class BetaBullet implements EntityFactory {

    @Spawns("betaBullet")
    public Entity newBetaBullet(SpawnData data) {
        Point2D startPosition = data.get("startPosition");

        var bulletClockwise = entityBuilder(data)
                .scale(0.75, 0.75)
                .viewWithBBox("beta_bullet.png")
                .type(EntityType.ENEMY_BULLET)

                .with(new SpiralBetaCounterClockWise(startPosition.getX()-50, startPosition.getY()+165))
                .collidable()
                .build();
        bulletClockwise.setOnActive(() -> {
            FXGL.run(() -> {
                if (bulletClockwise.getPosition().getY() > FXGL.getAppHeight()) {
                    bulletClockwise.removeFromWorld();
                }
            }, Duration.seconds(0.1));
        });
        return bulletClockwise;
    }

    @Spawns("betaBullet2")
    public Entity newBetaBullet2(SpawnData data) {
        Point2D startPosition = data.get("startPosition");
        var bulletCounterClockwise = entityBuilder(data)
                .scale(0.75, 0.75)
                .viewWithBBox("beta_bullet.png")
                .type(EntityType.ENEMY_BULLET)
                .with(new SpiralBetaClockWise(startPosition.getX()+200, startPosition.getY()+150))
                .collidable()
                .build();
        bulletCounterClockwise.setOnActive(() -> {
            FXGL.run(() -> {
                if (bulletCounterClockwise.getPosition().getY() > FXGL.getAppHeight()) {
                    bulletCounterClockwise.removeFromWorld();
                }
            }, Duration.seconds(0.1));
        });
        return bulletCounterClockwise;
    }

}