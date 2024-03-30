package org.example;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import javafx.geometry.Rectangle2D;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;
import kotlin.Unit;
import static com.almasb.fxgl.dsl.FXGLForKtKt.*;
import static com.sun.javafx.animation.TickCalculation.TICKS_PER_SECOND;
public class InitSettings extends GameApplication {
    private boolean spacePressed = false;
    private Entity player;
    private int width = 800;
    private int height = 600;
    private int enemyCount = 0;
    private final int MAX_ENEMIES = 10;
    private boolean allySpawned = false;
    public void initSettings(GameSettings settings){
        settings.setWidth(width);
        settings.setHeight(height);
        settings.setGameMenuEnabled(true);
        settings.setTitle("Game App");
        settings.setVersion("0.1b");
        settings.setTicksPerSecond(TICKS_PER_SECOND/150);
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
            spacePressed = true;
            FXGL.run(() -> {
                player.getComponent(PlayerComponent.class).shoot();
                spacePressed = false;
            }, Duration.seconds(0.25));
                return  Unit.INSTANCE;});
    }
    @Override
    protected void initGame() {
        FXGL.getGameWorld().addEntityFactory(new Entities());
        spawn("background");

        player = FXGL.spawn("player", (double)getAppWidth()/2-45, 500);
        spawnEnemies();

    }
    private void spawnEnemies() {
        FXGL.run(() -> {
            if (enemyCount < MAX_ENEMIES) {
                FXGL.spawn("enemy", FXGLMath.randomPoint(
                        new Rectangle2D(0, 0, getAppWidth(), (double) getAppHeight() / 2)
                ));
                enemyCount++;
            }
        }, Duration.seconds(1));
    }
    public void spawn_shoot_1(){
                FXGL.spawn("enemy", FXGLMath.randomPoint(
                        new Rectangle2D(0, 0, getAppWidth(), (double) getAppHeight() / 2)
                ));
                enemyCount++;

    }
}
