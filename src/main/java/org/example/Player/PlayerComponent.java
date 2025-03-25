package org.example.Player;

import com.almasb.fxgl.core.math.Vec2;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import javafx.scene.image.Image;
import javafx.util.Duration;

import java.util.concurrent.atomic.AtomicReference;

import static com.almasb.fxgl.dsl.FXGLForKtKt.spawn;
import static org.example.Init.InitSettings.powerupCounter;

public class PlayerComponent extends Component {
    private AtomicReference<Double> shootInterval = new AtomicReference<>(0.9);
    private AnimatedTexture texture;
    private AnimationChannel idle, moveLeft, moveRight;
    private double prevX, prevY;

    public PlayerComponent() {
        Image image = FXGL.image("player_ship.png");
        idle = new AnimationChannel(image, 8, 512 / 8, 175 / 3, Duration.seconds(1), 0, 2);
        moveLeft = new AnimationChannel(image, 8, 512 / 8, 175 / 3, Duration.seconds(1), 4, 8);
        moveRight = new AnimationChannel(image, 8, 512 / 8, 175 / 3, Duration.seconds(1), 8, 11);
        texture = new AnimatedTexture(idle);
        FXGL.run(this::updateShootInterval, Duration.seconds(0.1));
    }

    @Override
    public void onAdded() {
        entity.getViewComponent().addChild(texture);
        prevX = entity.getX();
        prevY = entity.getY();
    }

    @Override
    public void onUpdate(double tpf) {
        if (entity.getX() != prevX || entity.getY() != prevY) {
        } else {
            texture.loopAnimationChannel(idle);
        }
        prevX = entity.getX();
        prevY = entity.getY();
    }

    public void moveLeft() {
        if (entity.getX() > 10) {
            entity.translate(-10, 0);
            if (texture.getAnimationChannel() != moveLeft) {
                texture.loopAnimationChannel(moveLeft);
            }
        }
    }

    public void moveRight() {
        if (entity.getX() < FXGL.getAppWidth() - 45) {
            entity.translate(10, 0);
            if (texture.getAnimationChannel() != moveRight) {
                texture.loopAnimationChannel(moveRight);
            }
        }
    }

    public void moveUp() {
        if (entity.getY() < FXGL.getAppHeight() - 50) {
            entity.translate(0, 10);
        }
    }

    public void moveDown() {
        if (entity.getY() > (double) FXGL.getAppHeight() / 2) {
            entity.translate(0, -10);
        }
    }

    public void shoot() {
        FXGL.run(this::spawnShoot, Duration.seconds(shootInterval.get()));
    }

    //HOW FAST THE PLAYER CAN SHOOT
    private void updateShootInterval() {
        if (powerupCounter <= 4) {
            shootInterval.set(0.35);
        } else if (powerupCounter == 5) {
            shootInterval.set(0.35);
        } else {
            shootInterval.set(0.35);
        }
    }

    //SPAWN BULLETS DEPENDING ON THE POWERUP LEVEL
    public void spawnShoot() {
        var dir = Vec2.fromAngle(entity.getRotation() - 90);
        switch (powerupCounter) {
            case 1:
                spawn("lvl1", new SpawnData(entity.getX() - 20, entity.getY() - 30).put("dir", dir.toPoint2D()));
                spawn("lvl1", new SpawnData(entity.getX() + 5, entity.getY() - 30).put("dir", dir.toPoint2D()));
                FXGL.play("shoot_player.wav");
                break;
            case 2:
                spawn("lvl2", new SpawnData(entity.getX() - 20, entity.getY() - 30).put("dir", dir.toPoint2D()));
                spawn("lvl2", new SpawnData(entity.getX() - 7.5, entity.getY() - 30).put("dir", dir.toPoint2D()));
                spawn("lvl2", new SpawnData(entity.getX() + 5, entity.getY() - 30).put("dir", dir.toPoint2D()));
                FXGL.play("shoot_player.wav");
                break;
            case 3:
                spawn("lvl3", new SpawnData(entity.getX() + 15, entity.getY() - 30).put("dir", dir.toPoint2D()));
                FXGL.play("b1.wav");
                break;
            case 4:
                spawn("lvl4", new SpawnData(entity.getX() + 15, entity.getY() - 30).put("dir", dir.toPoint2D()));
                FXGL.play("b1.wav");
                break;
            case 5:
                spawn("lvl5", new SpawnData(entity.getX() + 15, entity.getY() - 30).put("dir", dir.toPoint2D()));
                FXGL.play("b2.wav");
                break;
            case 6:
                spawn("lvl6", new SpawnData(entity.getX() + 15, entity.getY() - 30).put("dir", dir.toPoint2D()));
                FXGL.play("b2.wav");
                break;
            case 7:
                spawn("lvl7", new SpawnData(entity.getX() + 15, entity.getY() - 30).put("dir", dir.toPoint2D()));
                FXGL.play("b3.wav");
                break;
            case 8:
                spawn("lvl8", new SpawnData(entity.getX() + 15, entity.getY() - 30).put("dir", dir.toPoint2D()));
                FXGL.play("b3.wav");
                break;
            case 9:
                spawn("lvl7", new SpawnData(entity.getX() + 15, entity.getY() - 30).put("dir", dir.toPoint2D()));
                spawn("lvl5", new SpawnData(entity.getX() + 15, entity.getY() - 30).put("dir", dir.toPoint2D()));
                FXGL.play("b3.wav");
                break;
            case 10:
                spawn("lvl7", new SpawnData(entity.getX() + 15, entity.getY() - 30).put("dir", dir.toPoint2D()));
                spawn("lvl6", new SpawnData(entity.getX() + 15, entity.getY() - 30).put("dir", dir.toPoint2D()));
                FXGL.play("b3.wav");
                break;
        }
    }

    public void usePowerRocket() {
        var dir = Vec2.fromAngle(entity.getRotation());
        spawn("rocket", new SpawnData(entity.getX() - 10, entity.getY()).put("dir", dir.toPoint2D()));
        spawn("rocket", new SpawnData(entity.getX() + 50, entity.getY()).put("dir", dir.toPoint2D()));
    }
}