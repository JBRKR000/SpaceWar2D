package org.example.Init;
import com.almasb.fxgl.animation.Interpolators;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.SceneFactory;
import com.almasb.fxgl.audio.Music;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.HealthIntComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;
import kotlin.Unit;
import org.example.Bonus.BonusSpawner;
import org.example.Bonus.CoinBonus;
import org.example.Bonus.HealthBonus;
import org.example.Bonus.Powerup;
import org.example.Bullet.*;
import org.example.Debug.DebugWindow;
import org.example.Enemy.Eclipse;
import org.example.Enemy.Inferno;
import org.example.Enemy.Striker;
import org.example.Enemy.Void;
import org.example.GunUpdates.GunUpdateEntities;
import org.example.MainMenu.MainMenu;
import org.example.Other.Entities;
import org.example.Other.EntityType;
import org.example.Player.PlayerComponent;
import org.example.Player.PlayerEntity;
import org.example.Score.ScoreEntity;
import org.jetbrains.annotations.NotNull;

import java.nio.channels.FileLock;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import static com.almasb.fxgl.dsl.FXGL.*;
import static com.almasb.fxgl.dsl.FXGLForKtKt.*;
import static com.sun.javafx.animation.TickCalculation.TICKS_PER_SECOND;
import static org.example.Enemy.RandomEnemyPicker.picker;


public class InitSettings extends GameApplication {

    public static boolean isDebugEnabled = false;
    private Entity player;
    private int godmode = 0;
    public static int wave = 1;  // Current wave
    public static int enemiesToDestroy = 10;  // Enemies to defeat per wave (adjustable)
    private int enemiesDefeated = 0;
    private int enemyCount = 0;
    private final BulletSpawner bulletSpawner = new BulletSpawner();
    private Music backgroundMusic;
    private int maxPlayers = 5;
    public String enemyType;
    public static boolean isCoinSpawned = false;
    public static boolean isHealthSpawned = false;
    public static boolean isEnemySpawned = false;
    public static boolean isPowerupSpawned = false;
    public int bonus;
    private boolean spacebarPressed = false;
    public static int powerup = 0;


    public void initSettings(GameSettings settings) {
        settings.setWidth(800);
        settings.setHeight(600);
        settings.setGameMenuEnabled(true);
        settings.setTitle("Game App");
        settings.setVersion("0.2b");
        settings.setTicksPerSecond(TICKS_PER_SECOND / 100);
        settings.setMainMenuEnabled(true);
        settings.setSceneFactory(new SceneFactory() {
            @NotNull
            @Override
            public FXGLMenu newMainMenu() {
                return new MainMenu();
            }
        });
    }
    public void toggleGodMode() {
        godmode = 1;
        System.out.println("GODMODE IS ENABLED");
    }
    public void toggleOffGodMode() {
        System.out.println("GODMODE IS DISABLED");
        godmode = 0;
    }

    @Override
    protected void initPhysics() {
        AtomicInteger randomHealth = new AtomicInteger();
        run(()->{
            if(wave < 3){
                randomHealth.set(FXGL.random(1, 7));
            }
            if(wave >= 3 && wave < 8){
                randomHealth.set(FXGL.random(1, 10));
            }
            if(wave > 8 && wave < 11){
                randomHealth.set(FXGL.random(1, 12));
            }

        },Duration.seconds(0.1));


        AtomicInteger randomCoin = new AtomicInteger();
        run(()->{
            if(wave < 3){
                randomCoin.set(FXGL.random(1, 5));
            }
            if(wave >= 3 && wave < 8){
                randomCoin.set(FXGL.random(1, 8));
            }
            if(wave > 8 && wave < 11){
                randomCoin.set(FXGL.random(1, 10));
            }

        },Duration.seconds(0.1));

        AtomicInteger randomPowerUp = new AtomicInteger();
        run(()->{
            if(wave < 3){
                randomPowerUp.set(FXGL.random(1, 6));
            }
            if(wave >= 3 && wave < 8){
                randomPowerUp.set(FXGL.random(1, 4));
            }
            if(wave > 8 && wave < 11){
                randomPowerUp.set(FXGL.random(1, 8));
            }

        },Duration.seconds(0.1));

        onCollisionBegin(EntityType.PLAYER_BULLET, EntityType.ENEMY, (bullet, enemy) -> {
            var hp = enemy.getComponent(HealthIntComponent.class);

            if (hp.getValue() > 1) {
                bullet.removeFromWorld();
                hp.damage(1);
                FXGL.play("enemy_hit.wav");
            } else {
                FXGL.play("enemy_boom.wav");
                enemy.removeFromWorld();
                bullet.removeFromWorld();
                enemiesDefeated++;
                enemyCount--;
                bulletSpawner.removeEnemy(enemy);
                FXGL.spawn("scoreText", new SpawnData(enemy.getX(), enemy.getY()).put("text", "+100"));
                FXGL.inc("score", +100);
                isEnemySpawned = false;

                    if(randomHealth.get() == 2 && randomCoin.get() != 1) {
                        FXGL.spawn("health_bonus", new SpawnData(enemy.getX(), enemy.getY()));
                        isHealthSpawned = true;
                    }
                    if(randomCoin.get() == 1 && randomHealth.get() != 2) {
                        FXGL.spawn("coin_bonus", new SpawnData(enemy.getX(), enemy.getY()));
                        isCoinSpawned = true;
                    }
                    if(randomPowerUp.get() == 3) {

                        FXGL.spawn("powerup", new SpawnData(enemy.getX(), enemy.getY()));
                        isPowerupSpawned = true;
                    }
            }
        });

        onCollisionBegin(EntityType.ENEMY_BULLET, EntityType.PLAYER, (bullet, player) -> {
            if(godmode == 0) {
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
                    wave = 1;
                }
            }
        });

        onCollisionBegin(EntityType.HEALTH, EntityType.PLAYER, (health, player) -> {
            if(godmode == 0) {
                var hp = player.getComponent(HealthIntComponent.class);
                if (hp.getValue() > 20) {
                    health.removeFromWorld();
                    isHealthSpawned = false;
                }else{
                    health.removeFromWorld();
                    isHealthSpawned = false;
                    hp.setValue(hp.getValue()+2);
                    FXGL.spawn("scoreText", new SpawnData(health.getX(), health.getY()).put("text", "+15"));
                    FXGL.inc("score", +15);
                    FXGL.play("health.wav");
                }
            }
        });
        onCollisionBegin(EntityType.COIN, EntityType.PLAYER, (coin, player) -> {
            if(godmode == 0) {
                    coin.removeFromWorld();
                    isCoinSpawned = false;
                    FXGL.spawn("scoreText", new SpawnData(coin.getX(), coin.getY()).put("text", "+" + bonus));
                    FXGL.inc("score", +bonus);
                    FXGL.play("coin.wav");

            }
        });

        onCollisionBegin(EntityType.POWERUP, EntityType.PLAYER, (powerup, player) -> {
            if(godmode == 0) {
                InitSettings.powerup++;
                powerup.removeFromWorld();
                isPowerupSpawned = false;
                FXGL.play("powerup.wav");

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
            if(!spacebarPressed){
                spacebarPressed = true;
                FXGL.run(()->{
                    player.getComponent(PlayerComponent.class).shoot();
                    FXGL.play("shoot_player.wav");
                },Duration.seconds(0.3));
            }
            return Unit.INSTANCE;
        });
        onKeyDown(KeyCode.F3, () -> {
            FXGL.getGameController().pauseEngine();
            System.out.println("ENGINE IS PAUSED");
            return Unit.INSTANCE;
        });
        onKeyDown(KeyCode.F4, () -> {
            System.out.println("ENGINE IS RESUMED");
            FXGL.getGameController().resumeEngine();
            return Unit.INSTANCE;
        });
        onKeyDown(KeyCode.F5, () -> {
            System.out.println("DEBUG WINDOW IS OPEN");
            DebugWindow.show(this);
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
        FXGL.getGameWorld().addEntityFactory(new PlayerEntity());
        FXGL.getGameWorld().addEntityFactory(new PlayerBulletEntity());
        FXGL.getGameWorld().addEntityFactory(new ScoreEntity());
        FXGL.getGameWorld().addEntityFactory(new Eclipse());
        FXGL.getGameWorld().addEntityFactory(new Inferno());
        FXGL.getGameWorld().addEntityFactory(new Striker());
        FXGL.getGameWorld().addEntityFactory(new Void());
        FXGL.getGameWorld().addEntityFactory(new EclipseBullet());
        FXGL.getGameWorld().addEntityFactory(new StrikerBullet());
        FXGL.getGameWorld().addEntityFactory(new VoidBullet());
        FXGL.getGameWorld().addEntityFactory(new VoidLaser());
        FXGL.getGameWorld().addEntityFactory(new InfernoBullet());
        FXGL.getGameWorld().addEntityFactory(new HealthBonus());
        FXGL.getGameWorld().addEntityFactory(new CoinBonus());
        FXGL.getGameWorld().addEntityFactory(new GunUpdateEntities());
        FXGL.getGameWorld().addEntityFactory(new Powerup());


        backgroundMusic = FXGL.getAssetLoader().loadMusic("CosmicConquest.mp3");
        backgroundMusic.getAudio().setVolume(0.06);
        backgroundMusic.getAudio().play();

        FXGL.spawn("background");
        player = FXGL.spawn("player", (double) FXGL.getAppWidth() / 2 - 45, 500);


        FXGL.getGameTimer().runOnceAfter(() -> FXGL.getDialogService().showMessageBox("Wave " + wave + " Started!"), Duration.seconds(1));

        run(() -> {

            if (enemiesDefeated < enemiesToDestroy) {
                if (enemyCount < maxPlayers && enemiesDefeated - enemyCount < maxPlayers) {
                    String picker = picker();
                    Entity enemy = FXGL.getGameWorld().create(picker, new SpawnData(200, 100).put("angle", 0));
                    spawnWithScale(enemy, Duration.seconds(0)).angleProperty().set(0);
                    enemyCount++;
                    bulletSpawner.addEnemy(enemy, picker);
                    isEnemySpawned = true;
                    enemyType = picker();
                    bonus = BonusSpawner.bonusSpawner(enemyType);
                }
            } else {
                if ((FXGL.getGameWorld().getEntitiesByType(EntityType.COIN).isEmpty())&&FXGL.getGameWorld().getEntitiesByType(EntityType.HEALTH).isEmpty() &&
                        FXGL.getGameWorld().getEntitiesByType(EntityType.ENEMY).isEmpty() && FXGL.getGameWorld().getEntitiesByType(EntityType.POWERUP).isEmpty()) {
                    FXGL.getGameTimer().runOnceAfter(() -> FXGL.getDialogService().showMessageBox("Wave " + (wave - 1) + " Completed!"), Duration.seconds(0));
                    try{
                        FXGL.getGameWorld().getEntitiesByType(EntityType.ENEMY, EntityType.ENEMY_BULLET).forEach(Entity::removeFromWorld);
                    }catch (Exception e){
                        System.out.println("No enemies left");
                    }
                    enemiesDefeated = 0;
                    enemyCount = 0;
                    wave++;
                }
            }


        }, Duration.seconds(1));
        run(bulletSpawner::spawnBulletsFromEnemies, Duration.seconds(3));

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

    public void toggleEveryEnemyHas1HP() {
    }


}


