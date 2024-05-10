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
import com.almasb.fxgl.input.Input;
import com.almasb.fxgl.input.MouseTrigger;
import com.almasb.fxgl.input.Trigger;
import com.almasb.fxgl.input.TriggerListener;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import kotlin.Unit;
import org.example.Bullet.*;
import org.example.Debug.DebugWindow;
import org.example.Enemy.Eclipse;
import org.example.Enemy.Inferno;
import org.example.Enemy.Striker;
import org.example.Enemy.Void;
import org.example.MainMenu.MainMenu;
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
import static org.example.Enemy.RandomEnemyPicker.picker;


public class InitSettings extends GameApplication {

    public static boolean isDebugEnabled = false;
    private Entity player;
    private int godmode = 0;
    private int wave = 1;
    private int enemiesToDestroy = 10;
    private int enemiesDefeated = 0;
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
        onCollisionBegin(EntityType.PLAYER_BULLET, EntityType.ENEMY, (bullet, enemy) -> {
            var hp = enemy.getComponent(HealthIntComponent.class);

            if (hp.getValue() > 1) {
                bullet.removeFromWorld();
                hp.damage(1);
            } else {
                FXGL.play("enemy_boom.wav");
                enemy.removeFromWorld();
                bullet.removeFromWorld();
                enemiesDefeated++;
                enemyCount--;
                bulletSpawner.removeEnemy(enemy);
                FXGL.spawn("scoreText", new SpawnData(enemy.getX(), enemy.getY()).put("text", "+100"));
                FXGL.inc("score", +100);
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
                }
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
        double diff = 1.5;
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
        FXGL.getGameWorld().addEntityFactory(new InfernoBullet());

        FXGL.spawn("background");
        player = FXGL.spawn("player", (double) FXGL.getAppWidth() / 2 - 45, 500);
        //SPAWN ENEMY
        run(() -> {

            if (enemiesDefeated < enemiesToDestroy) {
                if (enemyCount < maxPlayers && enemiesDefeated - enemyCount < maxPlayers) {
                    String picker = picker();
                    Entity enemy = FXGL.getGameWorld().create(picker, new SpawnData(200, 100).put("angle", 0));
                    spawnWithScale(enemy, Duration.seconds(0)).angleProperty().set(0);
                    enemyCount++;
                    bulletSpawner.addEnemy(enemy, picker);
                }
            } else {
                if (enemyCount == 0) {
                    var waveCompletedText = FXGL.getUIFactoryService().newText("Wave Completed!", Color.WHITE, 36);
                    waveCompletedText.setTranslateX(100);
                    waveCompletedText.setTranslateY(100);
                    //FXGL.getGameScene().addUINode(waveCompletedText);
                    FXGL.getGameController().gotoMainMenu();
                    System.out.println("WAVE " + wave + " COMPLETED!");
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


