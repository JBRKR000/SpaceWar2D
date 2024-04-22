package org.example.Init;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;
import kotlin.Unit;
import org.example.Bullet.BulletEntity;
import org.example.Bullet.BulletSpawner;
import org.example.Enemy.EnemyEntity;
import org.example.Other.Entities;
import org.example.Other.EntityType;
import org.example.Player.PlayerComponent;
import org.example.Player.PlayerEntity;

import java.util.Random;

import static com.almasb.fxgl.dsl.FXGL.*;
import static com.almasb.fxgl.dsl.FXGLForKtKt.*;
import static com.sun.javafx.animation.TickCalculation.TICKS_PER_SECOND;
import static org.example.Enemy.EnemyEntity.getPosOfEnemy;

public class InitSettings extends GameApplication {

    private Entity player;
    private int width = 800;
    private int height = 600;
    private int maxPlayers = 5;
    private int enemyCount = 0;
    private final BulletSpawner bulletSpawner = new BulletSpawner();

    public void initSettings(GameSettings settings){
        settings.setWidth(width);
        settings.setHeight(height);
        settings.setGameMenuEnabled(true);
        settings.setTitle("Game App");
        settings.setVersion("0.2b");
        settings.setTicksPerSecond(TICKS_PER_SECOND/100);
        settings.setMainMenuEnabled(false);

    }

    @Override
    protected void initPhysics() {
        onCollisionBegin(EntityType.PLAYER_BULLET, EntityType.ENEMY, (bullet, enemy) -> {
            enemy.removeFromWorld();
            bullet.removeFromWorld();
            enemyCount--;
            bulletSpawner.removeEnemy(enemy);
        });
    }

    @Override
    protected void initInput() {
        onKey(KeyCode.RIGHT, () -> {
            player.getComponent(PlayerComponent.class).moveRight();
            return Unit.INSTANCE;
        });

        onKey(KeyCode.LEFT, () -> {
            player.getComponent(PlayerComponent.class).moveLeft();
            return Unit.INSTANCE;
        });
        onKey(KeyCode.UP, () -> {
            player.getComponent(PlayerComponent.class).moveDown();
            return Unit.INSTANCE;
        });
        onKey(KeyCode.DOWN, () -> {
            player.getComponent(PlayerComponent.class).moveUp();
            return Unit.INSTANCE;
        });
        onKeyDown(KeyCode.SPACE, () -> {
            player.getComponent(PlayerComponent.class).shoot();
            return Unit.INSTANCE;
        });
    }

    @Override
    protected void initGame() {
        Random random = new Random();
        FXGL.getSettings().setGlobalSoundVolume(0.1);
        FXGL.getGameWorld().addEntityFactory(new Entities());
        FXGL.getGameWorld().addEntityFactory(new EnemyEntity());
        FXGL.getGameWorld().addEntityFactory(new PlayerEntity());
        FXGL.getGameWorld().addEntityFactory(new BulletEntity());
        FXGL.spawn("background");
        player = FXGL.spawn("player", (double)FXGL.getAppWidth()/2-45, 500);
        //SPAWN ENEMY
        run(() -> {
            if (enemyCount < maxPlayers) {
                Entity enemy = FXGL.getGameWorld().create("enemy", new SpawnData(200, 100).put("angle", 0));
                spawnWithScale(enemy, Duration.seconds(0)).angleProperty().set(0);
                enemyCount++;
                bulletSpawner.addEnemy(enemy);
            }
        }, Duration.seconds(3));

        run(bulletSpawner::spawnBulletsFromEnemies, Duration.seconds(1));
    }

}


