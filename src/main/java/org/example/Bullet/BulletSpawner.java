package org.example.Bullet;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import javafx.util.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BulletSpawner {
    Random random = new Random();
    private final double maxDelay = 2.5;
    private final List<Entity> enemies = new ArrayList<>();

    public BulletSpawner() {
    }


    public void addEnemy(Entity enemy) {
        enemies.add(enemy);
    }


    public void removeEnemy(Entity enemy) {
        enemies.remove(enemy);
    }

    public void spawnBulletsFromEnemies() {

        for (Entity enemy : enemies) {
            double delay = random.nextDouble() * maxDelay;
            FXGL.getGameTimer().runOnceAfter(() -> {
                if (FXGL.getGameWorld().getEntities().contains(enemy)) {
                    Entity bullet = FXGL.getGameWorld().create("enemy_bullet", new SpawnData(enemy.getX()+25, enemy.getY()+40).put("angle", 0));
                    bullet.setScaleX(1.1);
                    bullet.setScaleY(1.1);
                    FXGL.getGameWorld().addEntity(bullet);
                }
            }, Duration.seconds(delay));
        }
    }

}
