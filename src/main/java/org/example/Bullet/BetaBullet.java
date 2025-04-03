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

        // Tworzenie pierwszego pocisku z ruchem zgodnym z ruchem wskazówek zegara
        var bulletClockwise = entityBuilder(data)
                .scale(0.75, 0.75)
                .viewWithBBox("beta_bullet.png")
                .type(EntityType.ENEMY_BULLET)
                .at(startPosition)
                .with(new SpiralBetaClockWise(startPosition.getX(), startPosition.getY()))
                .collidable()
                .build();

        var bulletCounterClockwise = entityBuilder(data)
                .scale(0.75, 0.75)
                .viewWithBBox("beta_bullet.png")
                .type(EntityType.ENEMY_BULLET)
                .at(startPosition)
                .with(new SpiralBetaCounterClockWise(startPosition.getX(), startPosition.getY()))
                .collidable()
                .build();

        bulletClockwise.setOnActive(() -> {
            FXGL.run(() -> {
                if (bulletClockwise.getPosition().getY() > FXGL.getAppHeight()) {
                    bulletClockwise.removeFromWorld();
                }
            }, Duration.seconds(0.1));
        });

        bulletCounterClockwise.setOnActive(() -> {
            FXGL.run(() -> {
                if (bulletCounterClockwise.getPosition().getY() > FXGL.getAppHeight()) {
                    bulletCounterClockwise.removeFromWorld();
                }
            }, Duration.seconds(0.1));
        });

        return bulletClockwise;
    }
}