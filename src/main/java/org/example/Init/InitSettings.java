package org.example.Init;
import com.almasb.fxgl.animation.Interpolators;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.SceneFactory;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.HealthIntComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import javafx.geometry.Point2D;
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
import org.example.Score.ScoreEntity;
import org.jetbrains.annotations.NotNull;
import java.util.Map;
import java.util.Random;
import static com.almasb.fxgl.dsl.FXGL.*;
import static com.almasb.fxgl.dsl.FXGLForKtKt.*;
import static com.sun.javafx.animation.TickCalculation.TICKS_PER_SECOND;


public class InitSettings extends GameApplication {

    private static int isDebugEnabled = 0;
    private Entity player;
    private int width = 800;
    private int height = 600;
    private int maxPlayers = 5;
    private int enemyCount = 0;
    private final BulletSpawner bulletSpawner = new BulletSpawner();

    public void initSettings(GameSettings settings) {
        settings.setWidth(width);
        settings.setHeight(height);
        settings.setGameMenuEnabled(true);
        settings.setTitle("Game App");
        settings.setVersion("0.2b");
        settings.setTicksPerSecond(TICKS_PER_SECOND / 100);
        settings.setMainMenuEnabled(true);
        settings.setSceneFactory(new SceneFactory() {
            @NotNull
            @Override
            public FXGLMenu newMainMenu() {

                return new org.example.MainMenu.MainMenu();
            }
        });

    }

    @Override
    protected void initPhysics() {
        onCollisionBegin(EntityType.PLAYER_BULLET, EntityType.ENEMY, (bullet, enemy) -> {
            var hp = enemy.getComponent(HealthIntComponent.class);

            if (hp.getValue() > 1) {
                bullet.removeFromWorld();
                hp.damage(1);
            } else {
                FXGL.play("enemy_boom.wav");
                enemy.removeFromWorld();
                bullet.removeFromWorld();
                enemyCount--;
                bulletSpawner.removeEnemy(enemy);
                FXGL.spawn("scoreText", new SpawnData(enemy.getX(), enemy.getY()).put("text", "+100"));
                FXGL.inc("score", +100);
            }
        });

        onCollisionBegin(EntityType.ENEMY_BULLET, EntityType.PLAYER, (bullet, player) -> {
            var hp = player.getComponent(HealthIntComponent.class);
            if (hp.getValue() > 1) {
                bullet.removeFromWorld();
                hp.damage(1);
                FXGL.play("player_gets_hit.wav");
            } else {
                FXGL.play("player_explodes.wav");
                player.removeFromWorld();
                bullet.removeFromWorld();

                FXGL.getGameController().gotoMainMenu();
                System.out.println();
                enemyCount = 0;
            }
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
            FXGL.play("shoot_player.wav");
            return Unit.INSTANCE;
        });
        onKeyDown(KeyCode.F3, () -> {
            isDebugEnabled++;
            System.out.println("DEBUG MODE IS ON");
            return Unit.INSTANCE;
        });

    }

    @Override
    protected void initGameVars(Map<String, Object> vars) {
        vars.put("score", 0);
        vars.put("lives", 3);
    }

    @Override
    protected void initGame() {
        Random random = new Random();
        FXGL.getSettings().setGlobalSoundVolume(0.1);
        FXGL.getGameWorld().addEntityFactory(new Entities());
        FXGL.getGameWorld().addEntityFactory(new EnemyEntity());
        FXGL.getGameWorld().addEntityFactory(new PlayerEntity());
        FXGL.getGameWorld().addEntityFactory(new BulletEntity());
        FXGL.getGameWorld().addEntityFactory(new ScoreEntity());
        FXGL.spawn("background");
        player = FXGL.spawn("player", (double) FXGL.getAppWidth() / 2 - 45, 500);
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

    @Override
    protected void initUI() {
        var text = FXGL.getUIFactoryService().newText("", 24);
        text.textProperty().bind(FXGL.getip("score").asString("Score: [%d]"));
        FXGL.getWorldProperties().addListener("score", (prev, now) -> {
            FXGL.animationBuilder()
                    .duration(Duration.seconds(0.5))
                    .interpolator(Interpolators.BOUNCE.EASE_OUT())
                    .repeat(2)
                    .autoReverse(true)
                    .scale(text)
                    .from(new Point2D(1, 1))
                    .to(new Point2D(1.2, 1.2))
                    .buildAndPlay();
        });
        FXGL.addUINode(text, 20, 50);
    }
}


