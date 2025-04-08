package org.example.Init;
import com.almasb.fxgl.animation.Interpolators;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.GameView;
import com.almasb.fxgl.app.scene.SceneFactory;
import com.almasb.fxgl.audio.*;
import com.almasb.fxgl.core.math.Vec2;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.HealthIntComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.ui.ProgressBar;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Duration;
import kotlin.Unit;
import org.example.Bonus.*;
import org.example.Bullet.*;
import org.example.Debug.Console;
import org.example.Effects.EndGameScene;
import org.example.Effects.Explosion;
import org.example.Enemy.*;
import org.example.Enemy.Void;
import org.example.GunUpdates.GunUpdateEntities;
import org.example.MainMenu.MainMenu;
import org.example.Other.Entities;
import org.example.Other.EntityType;
import org.example.Player.PlayerComponent;
import org.example.Player.PlayerEntity;
import org.example.Score.HighScoreManager;
import org.example.Score.ScoreEntity;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import static com.almasb.fxgl.dsl.FXGL.*;
import static com.almasb.fxgl.dsl.FXGLForKtKt.*;
import static com.almasb.fxgl.dsl.FXGLForKtKt.spawn;
import static com.sun.javafx.animation.TickCalculation.TICKS_PER_SECOND;
import static org.example.Enemy.RandomEnemyPicker.picker;
import static org.example.Player.PlayerEntity.hp_;




public class InitSettings extends GameApplication {
    private UserAction toggleConsoleAction;
    private Console console;
    public static boolean isDebugEnabled = false;
    private Entity player;
    private static boolean godmode = false;
    public static int wave = 1;
    public static int enemiesToDestroy = 10;
    public static int enemiesDefeated = 0;
    public static int enemyCount = 0;
    private final BulletSpawner bulletSpawner = new BulletSpawner();
    private int maxPlayers = 5;
    public String enemyType;
    public static boolean isCoinSpawned = false;
    public static boolean isHealthSpawned = false;
    public static boolean isEnemySpawned = false;
    public static boolean isPowerupSpawned = false;
    public int bonus;
    public static boolean spacebarPressed = false;
    public static Integer powerup = 0;
    public static Integer powerupCounter = 1;
    private static boolean hitsoundEnabled = false;
    private final HighScoreManager highScoreManager = new HighScoreManager();
    private static final int SHAKE_POWER = 5;
    private static double VOLUME = 0.20;
    public static Music currentMusic;
    private static List<String> waves_musicList;
    private int previousWave = 0;
    public static Music endgameMusic;
    private boolean isimmune = false;




    private void checkWaveChange() {
        if (wave != previousWave) {
            onWaveChange();
            previousWave = wave;
        }
    }
    private void endGameStateWithVictory() {
        endgameMusic = FXGL.getAssetLoader().loadMusic("victory_music.wav");
        int finalScore = FXGL.geti("score");
        currentMusic.getAudio().stop();
        endgameMusic.getAudio().stop();
        endgameMusic.getAudio().setVolume(0.5);
        endgameMusic.getAudio().setLooping(true);
        endgameMusic.getAudio().play();
        FXGL.getSceneService().pushSubScene(new EndGameScene(finalScore));
        FXGL.getDialogService().showInputBox("Gratulacje! Wpisz swój nick", playerName -> {
            highScoreManager.writeHighScore(playerName, finalScore);
        });
    }
    private void endGameState() {
        int finalScore = FXGL.geti("score");
        currentMusic.getAudio().stop();
        String message = "Game Over!\n" +
                "Your score: " + finalScore + "\n" +
                "Wave reached: " + wave + "\n" +
                "Enter your name to save high score";
        FXGL.play("death_music.wav");
        FXGL.getDialogService().showInputBox(message, playerName -> {
            highScoreManager.writeHighScore(playerName, finalScore);
            FXGL.getGameController().gotoMainMenu();
        });
    }


    public static void setWave(int newWave) {
        wave = newWave;
        System.out.println("Wave set to " + wave);

    }

    public void initSettings(GameSettings settings) {
        settings.setWidth(1920);
        settings.setHeight(1080);
        settings.setGameMenuEnabled(true);
        settings.setTitle("SpaceInvaders");
        settings.setVersion("1.3.25.12.a");
        settings.setFullScreenAllowed(true);
        settings.setFullScreenFromStart(true);
        settings.setAppIcon("icon.png");
        settings.setCloseConfirmation(false); // TODO: Change to true
        settings.setEntityPreloadEnabled(true);
        settings.setTicksPerSecond(TICKS_PER_SECOND / 100); // 60FPS locked
        settings.setMainMenuEnabled(true);
        settings.setSceneFactory(new SceneFactory() {
            @NotNull
            @Override
            public FXGLMenu newMainMenu() {
                try {
                    return new MainMenu();
                } catch (IOException | FontFormatException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        waves_musicList = List.of("boss_music.wav", "ShadowsFromTheStars.mp3", "music_1.wav", "TerminatorBattle.mp3", "music_2.mp3", "music_3.wav", "music_4.mp3");

    }

    public static void toggleGodMode() {
        godmode = !godmode;
        System.out.println("God mode: " + (godmode ? "enabled" : "disabled"));
    }




    private static String currentMusicFile = "";
    private static void playMusicForWave(int wave) {
        String musicFile = "";
        switch (wave) {
            case 1:
            case 2:
                musicFile = waves_musicList.get(4);
                VOLUME = 0.20;
                break;
            case 3:
            case 4:
                musicFile = waves_musicList.get(2);
                VOLUME = 0.10;
                break;
            case 5:
            case 6:
                musicFile = waves_musicList.get(3);
                VOLUME = 0.20;
                break;
            case 7:
                musicFile = waves_musicList.get(5);
                VOLUME = 0.10;
                break;
            case 8:
                musicFile = waves_musicList.get(1);
                VOLUME = 0.10;
                break;
            case 9:
                musicFile = waves_musicList.get(6);
                VOLUME = 0.20;
                break;
            case 10:
                musicFile = waves_musicList.get(0);
                VOLUME = 0.20;
                break;
        }

        if (!musicFile.equals(currentMusicFile)) {
            if (currentMusic != null) {
                FXGL.getAudioPlayer().stopMusic(currentMusic);
            }
            currentMusic = FXGL.getAssetLoader().loadMusic(musicFile);
            FXGL.getAudioPlayer().loopMusic(currentMusic);
            currentMusic.getAudio().setVolume(VOLUME);
            currentMusicFile = musicFile;
        }
    }







    @Override
    protected void initPhysics() {
        // ------ SECTION ABOUT RANDOMNESS OF BONUSES ------
        AtomicInteger randomHealth = new AtomicInteger();
        run(() -> {
            if (wave < 3) {
                randomHealth.set(FXGL.random(1, 7));
            }
            if (wave >= 3 && wave < 8) {
                randomHealth.set(FXGL.random(1, 10));
            }
            if (wave > 8 && wave < 11) {
                randomHealth.set(FXGL.random(1, 12));
            }

        }, Duration.seconds(0.1));


        AtomicInteger randomCoin = new AtomicInteger();
        run(() -> {
            if (wave < 3) {
                randomCoin.set(FXGL.random(1, 5));
            }
            if (wave >= 3 && wave < 8) {
                randomCoin.set(FXGL.random(1, 8));
            }
            if (wave > 8 && wave < 11) {
                randomCoin.set(FXGL.random(1, 10));
            }

        }, Duration.seconds(0.1));

        AtomicInteger randomPowerUp = new AtomicInteger();
        run(() -> {
            if (wave < 3) {
                randomPowerUp.set(FXGL.random(1, 6));
            }
            if (wave >= 3 && wave < 8) {
                randomPowerUp.set(FXGL.random(1, 4));
            }
            if (wave > 8 && wave < 11) {
                randomPowerUp.set(FXGL.random(1, 8));
            }

        }, Duration.seconds(0.1));

        // ------ SECTION ABOUT RANDOMNESS OF BONUSES ------

        onCollisionBegin(EntityType.PLAYER_BULLET, EntityType.ENEMY, (bullet, enemy) -> {
            var hp = enemy.getComponent(HealthIntComponent.class);

            if (hp.getValue() > 1) {

                if(wave == 10){
                   float random =  FXGL.random(0,1000);
                    if(random > 350 && random < 375){
                        FXGL.spawn("health_bonus", new SpawnData(enemy.getX(), enemy.getY()));
                        isHealthSpawned = true;
                    }
                }

                bullet.removeFromWorld();
                switch (powerupCounter) {
                    case 1:
                        hp.damage(20);
                        break;
                    case 2:
                        hp.damage(17);
                        break;
                    case 3:
                        hp.damage(15);
                        break;
                    case 4:
                        hp.damage(18);
                        break;
                    case 5:
                        hp.damage(16);
                        break;
                    case 6:
                        hp.damage(14);
                        break;
                    case 7:
                        hp.damage(10);
                        break;
                    case 8:
                        hp.damage(9);
                        break;
                    case 9:
                        hp.damage(8);
                        break;
                    case 10:
                        hp.damage(7);
                        break;
                    default:
                        break;
                }


                if (!hitsoundEnabled) {
                    FXGL.play("enemy_hit.wav");
                    hitsoundEnabled = true;
                    FXGL.runOnce(() -> hitsoundEnabled = false, Duration.seconds(0.05));
                }

                FXGL.spawn("light_explosion", new SpawnData(enemy.getX() + 30 + FXGL.random(-10, 20), enemy.getY() + 20 + FXGL.random(10, 20)));
            } else {
                FXGL.play("enemy_boom.wav");
                FXGL.getGameScene().getViewport().shake(SHAKE_POWER, 0.01);
                if (FXGL.random(1, 2) == 1) { //TODO: Change to smaller %
                    FXGL.spawn("bonus_drop", new SpawnData(enemy.getX(), enemy.getY()));
                }
                enemy.removeFromWorld();
                bullet.removeFromWorld();
                enemiesDefeated++;
                enemyCount--;
                bulletSpawner.removeEnemy(enemy);
                FXGL.spawn("scoreText", new SpawnData(enemy.getX(), enemy.getY()).put("text", "+100"));
                FXGL.spawn("explosion", new SpawnData(enemy.getX(), enemy.getY()));
                FXGL.inc("score", +100);
                isEnemySpawned = false;


                if (randomHealth.get() == 2 && randomCoin.get() != 1) {
                    FXGL.spawn("health_bonus", new SpawnData(enemy.getX(), enemy.getY()));
                    isHealthSpawned = true;
                }
                if (randomCoin.get() == 1 && randomHealth.get() != 2) {
                    FXGL.spawn("coin_bonus", new SpawnData(enemy.getX(), enemy.getY()));
                    isCoinSpawned = true;
                }
                if (randomPowerUp.get() == 3) {
                    FXGL.spawn("powerup", new SpawnData(enemy.getX(), enemy.getY()));
                    isPowerupSpawned = true;
                }
            }
        });

        onCollisionBegin(EntityType.ENEMY_BULLET, EntityType.PLAYER, (bullet, player) -> {
            if (!godmode && !isimmune) {
                try {
                    var hp = player.getComponent(HealthIntComponent.class);
                    hp_ = hp.getValue();
                    if (hp.getValue() > 1) {
                        bullet.removeFromWorld();
                        hp.damage(1);
                        FXGL.play("userhit.wav");
                        FXGL.getGameScene().getViewport().shake(SHAKE_POWER, 0.01);

                        isimmune = true;
                        blinkPlayer(player);
                        FXGL.runOnce(() -> isimmune = false, Duration.seconds(2));
                    } else {
                        FXGL.play("player_explodes.wav");
                        player.removeFromWorld();
                        bullet.removeFromWorld();
                        endGameState();
                        enemyCount = 0;
                        wave = 1;
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        });
        onCollisionBegin(EntityType.FAKER_BULLET, EntityType.PLAYER, (faker_bullet, player) -> {
            if (!godmode && !isimmune) {
                try {
                    var hp = player.getComponent(HealthIntComponent.class);
                    hp_ = hp.getValue();
                    if (hp.getValue() > 1) {
                        faker_bullet.removeFromWorld();
                        hp.damage(1);
                        FXGL.play("userhit.wav");
                        isimmune = true;
                        blinkPlayer(player);
                        FXGL.runOnce(() -> isimmune = false, Duration.seconds(2));
                        var playerComponent = player.getComponent(PlayerComponent.class);
                        playerComponent.setMovementSpeed(2);
                        FXGL.runOnce(() -> playerComponent.setMovementSpeed(10), Duration.seconds(3));
                        FXGL.play("slow.wav");
                    } else {
                        FXGL.play("player_explodes.wav");
                        player.removeFromWorld();
                        faker_bullet.removeFromWorld();
                        endGameState();
                        enemyCount = 0;
                        wave = 1;
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        });

        onCollisionBegin(EntityType.HEALTH, EntityType.PLAYER, (health, player) -> {
            if (!godmode) {
                var hp = player.getComponent(HealthIntComponent.class);

                if (hp.getValue() > 20) {
                    health.removeFromWorld();
                    isHealthSpawned = false;
                } else {
                    health.removeFromWorld();
                    isHealthSpawned = false;
                    hp.setValue(hp.getValue() + 2);
                    FXGL.spawn("scoreText", new SpawnData(health.getX(), health.getY()).put("text", "+15"));
                    FXGL.inc("score", +15);
                    FXGL.play("health.wav");
                    hp_ = hp.getValue();
                }
            }
        });
        onCollisionBegin(EntityType.COIN, EntityType.PLAYER, (coin, player) -> {
            if (!godmode) {
                coin.removeFromWorld();
                isCoinSpawned = false;
                FXGL.spawn("scoreText", new SpawnData(coin.getX(), coin.getY()).put("text", "+" + bonus));
                FXGL.inc("score", +bonus);
                FXGL.play("coin.wav");

            }
        });
        onCollisionBegin(EntityType.BONUS, EntityType.PLAYER, (bonus, player) -> {
            bonus.removeFromWorld();
            FXGL.play("bonus2.wav");
            switch (FXGL.random(0, 2)) {
                case 0:
                    var dir = Vec2.fromAngle(player.getRotation());
                    spawn("rocket", new SpawnData(player.getX() - 10, player.getY()).put("dir", dir.toPoint2D()));
                    spawn("rocket", new SpawnData(player.getX() - 75, player.getY()).put("dir", dir.toPoint2D()));
                    spawn("rocket2", new SpawnData(player.getX() + 50, player.getY()).put("dir", dir.toPoint2D()));
                    spawn("rocket2", new SpawnData(player.getX() + 110, player.getY()).put("dir", dir.toPoint2D()));
                    spawn("rocket3", new SpawnData(player.getX() + 5, player.getY()).put("dir", dir.toPoint2D()));
                    spawn("rocket3", new SpawnData(player.getX() + 35, player.getY()).put("dir", dir.toPoint2D()));
                    break;
                case 1:
                    FXGL.spawn("lightning", new SpawnData(player.getX(), player.getY()));
                    break;
                case 2:
                    FXGL.spawn("Bomb", new SpawnData(player.getX() - 325, player.getY() - 325));
                    break;
            }

        });
        onCollisionBegin(EntityType.LIGHTNING, EntityType.ENEMY, (lightning, enemy) -> {
            var hp = enemy.getComponent(HealthIntComponent.class);
            if (hp.getValue() > 1) {
                hp.damage(hp.getValue() / 2);
                FXGL.spawn("explosion", new SpawnData(enemy.getX(), enemy.getY()));
                FXGL.play("enemy_boom.wav");
                FXGL.getGameScene().getViewport().shake(SHAKE_POWER, 0.01);
            } else {
                enemy.removeFromWorld();
                enemiesDefeated++;
                enemyCount--;
                FXGL.spawn("scoreText", new SpawnData(enemy.getX(), enemy.getY()).put("text", "+39"));
                FXGL.inc("score", +39);
                FXGL.spawn("explosion", new SpawnData(enemy.getX(), enemy.getY()));
                FXGL.play("enemy_boom.wav");
                FXGL.getGameScene().getViewport().shake(SHAKE_POWER, 0.01);
                bulletSpawner.removeEnemy(enemy);
                isEnemySpawned = false;
            }

        });
        onCollisionBegin(EntityType.ROCKET, EntityType.ENEMY, (rocket, enemy) -> {
            var hp = enemy.getComponent(HealthIntComponent.class);
            if (hp.getValue() > 1) {
                hp.damage(100);
                rocket.removeFromWorld();
                FXGL.spawn("explosion", new SpawnData(enemy.getX(), enemy.getY()));
                FXGL.play("enemy_hit.wav");
            } else {
                enemy.removeFromWorld();
                enemiesDefeated++;
                enemyCount--;
                FXGL.spawn("scoreText", new SpawnData(enemy.getX(), enemy.getY()).put("text", "+45"));
                FXGL.inc("score", +45);
                FXGL.spawn("explosion", new SpawnData(enemy.getX(), enemy.getY()));
                FXGL.play("enemy_boom.wav");
                FXGL.getGameScene().getViewport().shake(SHAKE_POWER, 0.01);
                bulletSpawner.removeEnemy(enemy);
                isEnemySpawned = false;
            }

        });

        onCollisionBegin(EntityType.BOMB, EntityType.ENEMY, (bomb, enemy) -> {
            var hp = enemy.getComponent(HealthIntComponent.class);

            if (hp.getValue() > 1) {
                bomb.removeFromWorld();
                hp.damage(100);
                if (!hitsoundEnabled) {
                    FXGL.play("enemy_hit.wav");
                    hitsoundEnabled = true;
                    FXGL.runOnce(() -> hitsoundEnabled = false, Duration.seconds(0.05));
                }
                FXGL.spawn("light_explosion", new SpawnData(enemy.getX() + 30 + FXGL.random(-10, 20), enemy.getY() + 20 + FXGL.random(10, 20)));
            } else {
                FXGL.play("enemy_boom.wav");
                FXGL.getGameScene().getViewport().shake(SHAKE_POWER, 0.01);
                enemy.removeFromWorld();
                bomb.removeFromWorld();
                enemiesDefeated++;
                enemyCount--;
                bulletSpawner.removeEnemy(enemy);
                FXGL.spawn("scoreText", new SpawnData(enemy.getX(), enemy.getY()).put("text", "+100"));
                FXGL.spawn("explosion", new SpawnData(enemy.getX(), enemy.getY()));

                FXGL.inc("score", +100);
                isEnemySpawned = false;

                if (randomHealth.get() == 2 && randomCoin.get() != 1) {
                    FXGL.spawn("health_bonus", new SpawnData(enemy.getX(), enemy.getY()));
                    isHealthSpawned = true;
                }
                if (randomCoin.get() == 1 && randomHealth.get() != 2) {
                    FXGL.spawn("coin_bonus", new SpawnData(enemy.getX(), enemy.getY()));
                    isCoinSpawned = true;
                }
                if (randomPowerUp.get() == 3) {

                    FXGL.spawn("powerup", new SpawnData(enemy.getX(), enemy.getY()));
                    isPowerupSpawned = true;
                }
            }
        });

        onCollisionBegin(EntityType.POWERUP, EntityType.PLAYER, (powerup, player) -> {
            if (!godmode) {
                InitSettings.powerup++;
                if (InitSettings.powerup % 3 == 0 && InitSettings.powerup != 0) {
                    FXGL.play("bonus2.wav");
                    if (powerupCounter < 10) {
                        powerupCounter++;
                        InitSettings.powerup = 0;
                        System.out.println(powerupCounter);
                    }
                }
                powerup.removeFromWorld();
                isPowerupSpawned = false;
                FXGL.play("bonus.wav");
            }

        });

    }

    @Override
    protected void initInput() {
        onKey(KeyCode.D, () -> {
            player.getComponent(PlayerComponent.class).moveRight();
            return Unit.INSTANCE;
        });
        onKey(KeyCode.A, () -> {
            player.getComponent(PlayerComponent.class).moveLeft();
            return Unit.INSTANCE;
        });
        onKey(KeyCode.W, () -> {
            player.getComponent(PlayerComponent.class).moveDown();
            return Unit.INSTANCE;
        });
        onKey(KeyCode.S, () -> {
            player.getComponent(PlayerComponent.class).moveUp();
            return Unit.INSTANCE;
        });
        onKeyDown(KeyCode.SPACE, () -> {
            if (!spacebarPressed) {
                spacebarPressed = true;
                player.getComponent(PlayerComponent.class).shoot();
            }
            return Unit.INSTANCE;
        });
    }

    private void checkPowerup() {

    }

    @Override
    protected void initGameVars(Map<String, Object> vars) {
        vars.put("score", 0);
        vars.put("waveText", 1);
        vars.put("enemyCount", 0);
    }


    @Override
    protected void initGame() {

        run(this::checkWaveChange, Duration.seconds(1));



        addBackground();
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
        FXGL.getGameWorld().addEntityFactory(new Boss());
        FXGL.getGameWorld().addEntityFactory(new Explosion());
        FXGL.getGameWorld().addEntityFactory(new rocketPower());
        FXGL.getGameWorld().addEntityFactory(new Lightning());
        FXGL.getGameWorld().addEntityFactory(new Bomb());
        FXGL.getGameWorld().addEntityFactory(new BonusDrop());
        FXGL.getGameWorld().addEntityFactory(new Fighter());
        FXGL.getGameWorld().addEntityFactory(new FighterBullet());
        FXGL.getGameWorld().addEntityFactory(new Faker());
        FXGL.getGameWorld().addEntityFactory(new FakerBullet());
        FXGL.getGameWorld().addEntityFactory(new Beta());
        FXGL.getGameWorld().addEntityFactory(new BetaBullet());
        FXGL.getGameWorld().addEntityFactory(new Bull());
        FXGL.getGameWorld().addEntityFactory(new BullBullet());
        FXGL.getGameWorld().addEntityFactory(new Core());
        FXGL.getGameWorld().addEntityFactory(new CoreBullet());
        player = FXGL.spawn("player", (double) FXGL.getAppWidth() / 2 - 45, 500);

        FXGL.getGameTimer().runOnceAfter(() -> {
            FXGL.getDialogService().showMessageBox("Wave " + wave + " Started!");

            FXGL.play("dialog.wav");
        }, Duration.seconds(0.5));

        run(() -> {

            if (wave != 10) {
                maxPlayers = FXGL.random(5, 7);
                if (enemiesDefeated < enemiesToDestroy) {
                    if (enemyCount < maxPlayers && enemiesDefeated - enemyCount < maxPlayers) {
                        String picker = picker();
                        Entity enemy = FXGL.getGameWorld().create(picker, new SpawnData(FXGL.random(0, FXGL.getAppWidth() - 20), 100).put("angle", 0));
                        spawnWithScale(enemy, Duration.seconds(0)).angleProperty().set(0);
                        enemyCount++;
                        bulletSpawner.addEnemy(enemy, picker);
                        isEnemySpawned = true;
                        enemyType = picker();
                        bonus = BonusSpawner.bonusSpawner(enemyType);

                    }
                } else {
                    if ((FXGL.getGameWorld().getEntitiesByType(EntityType.COIN).isEmpty()) && FXGL.getGameWorld().getEntitiesByType(EntityType.HEALTH).isEmpty() &&
                            FXGL.getGameWorld().getEntitiesByType(EntityType.ENEMY).isEmpty() && FXGL.getGameWorld().getEntitiesByType(EntityType.POWERUP).isEmpty() && FXGL.getGameWorld().getEntitiesByType(EntityType.BONUS).isEmpty()) {
                        FXGL.getGameTimer().runOnceAfter(() -> FXGL.getDialogService().showMessageBox("Wave " + (wave - 1) + " Completed!"), Duration.seconds(0));
                        try {
                            FXGL.getGameWorld().getEntitiesByType(EntityType.ENEMY, EntityType.ENEMY_BULLET).forEach(Entity::removeFromWorld);
                        } catch (Exception e) {
                            System.out.println("No enemies left");
                        }

                        addBackground();
                        enemiesDefeated = 0;
                        enemyCount = 0;
                        FXGL.inc("waveText", +1);
                        wave++;
                    }
                }
            } else {
                enemiesToDestroy = 2;
                maxPlayers = 2;
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
                    if ((FXGL.getGameWorld().getEntitiesByType(EntityType.COIN).isEmpty()) && FXGL.getGameWorld().getEntitiesByType(EntityType.HEALTH).isEmpty() &&
                            FXGL.getGameWorld().getEntitiesByType(EntityType.ENEMY).isEmpty() && FXGL.getGameWorld().getEntitiesByType(EntityType.POWERUP).isEmpty()) {
                        FXGL.getGameTimer().runOnceAfter(() -> FXGL.getDialogService().showMessageBox("Wave " + (wave - 1) + " Completed!"), Duration.seconds(0));
                        try {
                            FXGL.getGameWorld().getEntitiesByType(EntityType.ENEMY, EntityType.ENEMY_BULLET).forEach(Entity::removeFromWorld);
                        } catch (Exception e) {
                            System.out.println("No enemies left");
                        }
                        enemiesDefeated = 0;
                        enemyCount = 0;
                        endGameStateWithVictory();
                    }
                }
            }


        }, Duration.seconds(1));
        run(bulletSpawner::spawnBulletsFromEnemies, Duration.seconds(3));


        run(bulletSpawner::spawnBulletForBoss, Duration.seconds(FXGL.random(0.05, 1.5)));



    }

    private void onWaveChange() {
        playMusicForWave(wave);
    }

    private void initializeConsole() {
        console = new Console();
        console.setVisible(false);
        FXGL.addUINode(console, 10, 10);

        if (toggleConsoleAction == null) { // Dodajemy akcję tylko raz
            toggleConsoleAction = new UserAction("Toggle Console") {
                @Override
                protected void onActionBegin() {
                    console.setVisible(!console.isVisible());
                }
            };

            FXGL.getInput().addAction(toggleConsoleAction, KeyCode.F1);
        }
    }

    @Override
    protected void initUI() {
        var text = FXGL.getUIFactoryService().newText("", 15);
        text.textProperty().bind(FXGL.getip("score").asString("Score: %d"));
        FXGL.getWorldProperties().addListener("score", (prev, now) -> {
            FXGL.animationBuilder()
                    .duration(Duration.seconds(0.2))
                    .interpolator(Interpolators.BOUNCE.EASE_OUT())
                    .repeat(1)
                    .autoReverse(true)
                    .scale(text)
                    .from(new Point2D(1, 1))
                    .to(new Point2D(1.2, 1.2))
                    .buildAndPlay();
        });
        FXGL.addUINode(text, 1800, 50);

        var wavetext = FXGL.getUIFactoryService().newText("", 15);
        wavetext.textProperty().bind(FXGL.getip("waveText").asString("Wave: %d"));
        FXGL.getWorldProperties().addListener("waveText", (prev, now) -> {
            FXGL.animationBuilder()
                    .duration(Duration.seconds(0.2))
                    .interpolator(Interpolators.BOUNCE.EASE_OUT())
                    .repeat(1)
                    .autoReverse(true)
                    .scale(wavetext)
                    .from(new Point2D(1, 1))
                    .to(new Point2D(1.2, 1.2))
                    .buildAndPlay();
        });
        FXGL.addUINode(wavetext, 1801, 70);


        ProgressBar powerUpLoad = new ProgressBar(false);
        powerUpLoad.setWidth(200);
        powerUpLoad.setHeight(10);
        powerUpLoad.setRotate(-90);
        powerUpLoad.setFill(Color.LIGHTBLUE);
        powerUpLoad.setLabelVisible(false);
        powerUpLoad.setLabelFill(Color.LIGHTBLUE);
        powerUpLoad.setMaxValue(3);
        FXGL.run(() -> {
            powerUpLoad.setCurrentValue(powerup);

        }, Duration.seconds(0.1));
        FXGL.addUINode(powerUpLoad, -75, 600);

        Image powerUpImage = new Image("/assets/textures/powerup.png");
        ImageView powerUpView = new ImageView(powerUpImage);
        powerUpView.setFitHeight(30);
        powerUpView.setFitWidth(30);
        powerUpView.setRotate(-90);
        FXGL.addUINode(powerUpView, 9.5, 675);
        FXGL.run(() -> {
            if (powerup == 1) {
                powerUpView.setY(powerUpView.getFitWidth() - 70);
            } else if (powerup == 2) {
                powerUpView.setY(powerUpView.getFitWidth() - 140);
            } else if (powerup == 0) {
                powerUpView.setY(0);
            }

        }, Duration.seconds(0.1));

        // Health bar
        ProgressBar healthBar = new ProgressBar(false);
        healthBar.setWidth(200);
        healthBar.setHeight(10);
        healthBar.setRotate(-90);
        healthBar.setFill(Color.GREEN);
        healthBar.setLabelVisible(false);
        healthBar.setLabelFill(Color.LIGHTBLUE);
        healthBar.setMaxValue(20);
        hp_ = 20;
        FXGL.run(() -> {
            healthBar.setCurrentValue(hp_);

        }, Duration.seconds(0.1));
        FXGL.addUINode(healthBar, -75, 850);


        Image healthImage = new Image("/assets/textures/health.png");
        ImageView healthView = new ImageView(healthImage);
        healthView.setFitHeight(30);
        healthView.setFitWidth(30);
        healthView.setRotate(90);
        FXGL.addUINode(healthView, 10, 840);
        Timeline pulsateTimeline = new Timeline(
                new KeyFrame(Duration.ZERO,
                        new KeyValue(healthView.scaleXProperty(), 1),
                        new KeyValue(healthView.scaleYProperty(), 1)
                ),
                new KeyFrame(Duration.seconds(0.5),
                        new KeyValue(healthView.scaleXProperty(), 1.2),
                        new KeyValue(healthView.scaleYProperty(), 1.2)
                ),
                new KeyFrame(Duration.seconds(1),
                        new KeyValue(healthView.scaleXProperty(), 1),
                        new KeyValue(healthView.scaleYProperty(), 1)
                )
        );
        pulsateTimeline.setCycleCount(Animation.INDEFINITE);
        pulsateTimeline.setAutoReverse(true);


        FXGL.run(() -> {
            if (healthBar.getCurrentValue() < (20 * 0.30)) {
                healthBar.setFill(Color.RED);
                if (pulsateTimeline.getStatus() != Animation.Status.RUNNING) {
                    pulsateTimeline.play();
                    AudioPlayer audioPlayer = new AudioPlayer();
                    Path heartbeat = Paths.get("src/main/resources/assets/sounds/HeartBeat.wav");
                    try {
                        URL url = heartbeat.toUri().toURL();
                        Audio audio = audioPlayer.loadAudio(AudioType.SOUND, url, false);
                        audio.setVolume(0.1);
                        audio.play();
                        FXGL.runOnce(audio::stop, Duration.seconds(3));
                    } catch (MalformedURLException e) {
                        throw new RuntimeException(e);
                    }
                }
            } else {
                healthBar.setFill(Color.GREEN);
                pulsateTimeline.stop();
                healthView.setScaleX(1);
                healthView.setScaleY(1);
            }
        }, Duration.seconds(0.1));


        Image additional = new Image("/assets/textures/Addition.png");
        ImageView additionalview = new ImageView(additional);
        additionalview.setFitHeight(45);
        additionalview.setFitWidth(45);
        additionalview.setRotate(0);
        FXGL.addUINode(additionalview, 15, 1000);
        Text fpsText;
        // Dodaj tekst FPS do UI
        fpsText = new Text();
        fpsText.setTranslateX(10); // Ustaw pozycję X
        fpsText.setTranslateY(50); // Ustaw pozycję Y
        fpsText.setFill(Color.WHITE); // Ustaw kolor tekstu
        fpsText.setStyle("-fx-font-size: 20px;"); // Ustaw styl tekstu
        FXGL.addUINode(fpsText);

        // Aktualizuj tekst FPS co klatkę
        FXGL.getGameTimer().runAtInterval(() -> {
            double fps = 1 / tpf();
            fpsText.setText("FPS: " + fps);
        }, Duration.seconds(0.1));

    }


    public void toggleEveryEnemyHas1HP() {
    }

    private void addBackground() {
        String backgroundPath1 = "assets/textures/background.jpg";
        String backgroundPath2 = "assets/textures/background2.jpg";
        String backgroundPath3 = "assets/textures/background3.jpg";
        String backgroundPath4 = "assets/textures/background4.jpg";
        String backgroundPath5 = "assets/textures/background5.jpg";
        URL backgroundUrl = null;
        switch (wave) {
            case 1:
                backgroundUrl = ResourceLoader.getResource(backgroundPath1);
                break;
            case 2:
            case 3:
                backgroundUrl = ResourceLoader.getResource(backgroundPath2);
                break;
            case 4:
            case 5:
            case 6:
                backgroundUrl = ResourceLoader.getResource(backgroundPath3);
                break;
            case 7:
            case 8:
            case 9:
                backgroundUrl = ResourceLoader.getResource(backgroundPath4);
                break;
            case 10:
                backgroundUrl = ResourceLoader.getResource(backgroundPath5);
                break;
        }

        if (backgroundUrl == null) {
            throw new IllegalArgumentException("Invalid URL: Resource not found");
        }
        ImageView background = new ImageView(new Image(backgroundUrl.toExternalForm()));
        double margin = 100;
        background.setFitWidth(FXGL.getAppWidth() + margin * 2);
        background.setFitHeight(FXGL.getAppHeight() + margin * 2);
        background.setTranslateX(-margin);
        background.setTranslateY(-margin);
        FXGL.getGameScene().addGameView(new GameView(background, Integer.MIN_VALUE));
    }
    private void blinkPlayer(Entity player) {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(0.1), new KeyValue(player.opacityProperty(), 0)),
                new KeyFrame(Duration.seconds(0.2), new KeyValue(player.opacityProperty(), 1))
        );
        timeline.setCycleCount(10);
        timeline.setAutoReverse(true);
        timeline.play();
    }
}

