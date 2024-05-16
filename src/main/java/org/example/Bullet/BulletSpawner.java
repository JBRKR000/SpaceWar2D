package org.example.Bullet;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.RandomMoveComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.time.LocalTimer;
import com.sun.jdi.Method;
import javafx.util.Duration;
import org.example.Enemy.Inferno;

import java.util.*;

public class BulletSpawner {
    Random random = new Random();
    private final double maxDelay = 2.5;
    private final Map<Entity, String> enemies = new HashMap<>();

    public BulletSpawner() {
    }


    public void addEnemy(Entity enemy, String type) {
        enemies.put(enemy,type);
    }


    public void removeEnemy(Entity enemy) {
        enemies.remove(enemy);
    }

    public void spawnHealthBonus() {

    }


    public void spawnBulletsFromEnemies() {
        for (Entity enemy : enemies.keySet()) {
            double delay = FXGL.random(0.5,3); //JAK SZYBKO MAJĄ PRZECIWNICY STRZELAĆ
            FXGL.getGameTimer().runOnceAfter(() -> {
                String type = enemies.get(enemy);
                if (type != null && type.equals("inferno")) {
                    RandomMoveComponent moveComponent = enemy.getComponent(RandomMoveComponent.class);
                    if (moveComponent != null && moveComponent.getMoveSpeed() == 0) {
                        Entity bullet = FXGL.getGameWorld().create("inferno_bullet", new SpawnData(enemy.getX() + 25, enemy.getY() + 40).put("angle", 0));
                        FXGL.play("INFERNO.wav");
                        bullet.setScaleX(1.1);
                        bullet.setScaleY(1.1);
                        FXGL.getGameWorld().addEntity(bullet);
                    }
                }
                if (type != null && type.equals("void")) {
                    if (FXGL.getGameWorld().getEntities().contains(enemy)) {
                        RandomMoveComponent moveComponent = enemy.getComponent(RandomMoveComponent.class);
                        int random = FXGL.random(1,2);
                        if(random == 1 && (moveComponent != null && moveComponent.getMoveSpeed() == 0)) {
                            Entity bullet = FXGL.getGameWorld().create("void_laser", new SpawnData(enemy.getX() + 19, enemy.getY() + 40).put("angle", 0));
                            FXGL.play("void_laser.wav");
                            bullet.setScaleX(1.1);
                            bullet.setScaleY(1.1);
                            FXGL.getGameWorld().addEntity(bullet);
                            try{
                                FXGL.run(()->{
                                    if(FXGL.getGameWorld().getEntities().contains(bullet)) {
                                        FXGL.getGameWorld().removeEntity(bullet);
                                    }
                                },Duration.seconds(1));
                            }catch (Exception e){
                                System.out.println("Failed to remove bullet");
                            }
                        }else{
                            Entity bullet = FXGL.getGameWorld().create("void_bullet", new SpawnData(enemy.getX() + 25, enemy.getY() + 40).put("angle", 0));
                            FXGL.play("void_bullet.wav");
                            bullet.setScaleX(1.1);
                            bullet.setScaleY(1.1);
                            FXGL.getGameWorld().addEntity(bullet);
                        }
                    }
                }
                if (type != null && type.equals("striker")) {
                    if (FXGL.getGameWorld().getEntities().contains(enemy)) {
                        Entity bullet = FXGL.getGameWorld().create("striker_bullet", new SpawnData(enemy.getX() + 25, enemy.getY() + 40).put("angle", 0));
                        FXGL.play("STRIKER.wav");
                        bullet.setScaleX(1.1);
                        bullet.setScaleY(1.1);
                        FXGL.getGameWorld().addEntity(bullet);
                        bullet.setVisible(false);
                    }
                }
                if (type != null && type.equals("eclipse")) {
                    if (FXGL.getGameWorld().getEntities().contains(enemy)) {
                        Entity bullet = FXGL.getGameWorld().create("eclipse_bullet", new SpawnData(enemy.getX() + 25, enemy.getY() + 40).put("angle", 0));
                        FXGL.play("ECLIPSE.wav");
                        bullet.setScaleX(1.1);
                        bullet.setScaleY(1.1);
                        FXGL.getGameWorld().addEntity(bullet);

                    }
                }
            }, Duration.seconds(delay));
        }
    }

}
