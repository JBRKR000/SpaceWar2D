package org.example.Player;

import com.almasb.fxgl.core.math.Vec2;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.component.Component;
import javafx.util.Duration;

import java.util.concurrent.atomic.AtomicReference;

import static com.almasb.fxgl.dsl.FXGLForKtKt.spawn;
import static org.example.Init.InitSettings.powerupCounter;

public class PlayerComponent extends Component {
    private AtomicReference<Double> shootInterval = new AtomicReference<>(0.9);

    public PlayerComponent() {
        FXGL.run(this::updateShootInterval, Duration.seconds(0.1));
    }

    public void moveLeft() {
        if (entity.getX() > 10) {
            entity.translate(-10, 0);
        }
    }

    public void moveRight() {
        if (entity.getX() < FXGL.getAppWidth() - 45) {
            entity.translate(10, 0);
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
        FXGL.run(this::spawnShoot
        ,Duration.seconds(shootInterval.get()));
    }

    private void updateShootInterval() {
        if (powerupCounter < 4) {
            shootInterval.set(0.4);
        } else if (powerupCounter == 5) {
            shootInterval.set(0.3);
        } else if (powerupCounter >= 6) {
            shootInterval.set(0.3);
        }
    }

    public void spawnShoot() {
        var dir = Vec2.fromAngle(entity.getRotation() - 90);
        switch (powerupCounter) {
            case 1:
                spawn("player_bullet", new SpawnData(entity.getX() - 20, entity.getY() - 30).put("dir", dir.toPoint2D()));
                FXGL.play("shoot_player.wav");
                break;
            case 2:
                spawn("lvl2", new SpawnData(entity.getX() - 20, entity.getY() - 30).put("dir", dir.toPoint2D()));
                FXGL.play("shoot_player.wav");
                break;
            case 3:
                spawn("lvl3", new SpawnData(entity.getX() - 20, entity.getY() - 30).put("dir", dir.toPoint2D()));
                FXGL.play("shoot_player.wav");
                break;
            case 4:
                spawn("lvl4", new SpawnData(entity.getX() + 5, entity.getY() - 30).put("dir", dir.toPoint2D()));
                FXGL.play("player_bullet_lvl_4.wav");
                break;
            case 5:
                spawn("lvl5", new SpawnData(entity.getX() + 5, entity.getY() - 30).put("dir", dir.toPoint2D()));
                FXGL.play("player_bullet_lvl_4.wav");
                break;
        }
    }
    public void usePowerRocket(){
        var dir = Vec2.fromAngle(entity.getRotation()+90);
        //spawn("lightning", new SpawnData(entity.getX(), entity.getY()-470).put("dir", dir.toPoint2D()));
        //spawn("rocket", new SpawnData(entity.getX() + 20, entity.getY() - 28).put("dir", dir.toPoint2D()));
        //spawn("rocket", new SpawnData(entity.getX() - 18, entity.getY() - 28).put("dir", dir.toPoint2D()));
        //spawn("Bomb", new SpawnData(entity.getX()-350, entity.getY()-375).put("dir", dir.toPoint2D()));
    }
}
