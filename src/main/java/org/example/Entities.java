package org.example;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.ProjectileComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.sun.javafx.geom.Shape;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import java.util.Random;

import static org.example.AddedLogicClass.randomSpawnVector;

public class Entities implements EntityFactory {
    private static final double TRACKING_UPDATE_INTERVAL = 0.0000000000000000000000001;

    static int DEFAULT_ENEMY_LIFE = 100;

    private boolean allySpawned = false;

    public double enemyPosX;
    public double enemyPosY;


    public double enemyUpdateX;
    public double enemyUpdateY;
    public double playerUpdateX;
    public double playerUpdateY;
    public double bullet_enemy_UpdateX;
    public double bullet_enemy_UpdateY;
    public double bullet_player_UpdateX;
    public double bullet_player_UpdateY;


    private int randomSpeed() {
        Random random = new Random();
        return random.nextInt(451) + 50;
    }

    private final String[] enemyTextures = {"enemy_1.png", "enemy_2.png", "enemy_3.png", "enemy_4.png"};
    private final double[][] enemyScales = {{0.1, 0.1}, {0.1, 0.1}, {0.2, 0.2}, {0.05, 0.05}};


    @Spawns("enemy")
    public Entity spawnEnemy(SpawnData data) {
        int textureIndex = new Random().nextInt(enemyTextures.length);
        String texture = enemyTextures[textureIndex];
        double[] scale = enemyScales[textureIndex];

        Point2D velocity = new Point2D(randomSpawnVector(), 0);
        Entity entity = FXGL.entityBuilder(data)
                .view(texture)
                .type(EntityType.ENEMY)
                .scale(scale[0], scale[1])
                .collidable()
                .with(new ProjectileComponent(velocity, randomSpeed()))
                .buildAndAttach();

        FXGL.run(() -> {
            int newSpeed = randomSpeed();
            entity.getComponent(ProjectileComponent.class).setSpeed(newSpeed);
            if (entity.getRotation() == -180 || entity.getRotation() == 180) {
                entity.setRotation(0);
            }
        }, Duration.seconds(0.5));

        if (velocity.getX() < 0) {
            entity.setRotation(-1);
        }
        enemyPosX = entity.getX();
        enemyPosY = entity.getY();

        entity.addComponent(new EnemyControlOffScreen());
        entity.addComponent(new EnemyShoot(randomShootInterval()));

        spawnTrackingRectangle(entity);

        return entity;
    }

    private static class EnemyControlOffScreen extends com.almasb.fxgl.entity.component.Component {
        @Override
        public void onUpdate(double tpf) {
            Point2D newPos = entity.getPosition();
            if (newPos.getX() <= 0 || newPos.getX() >= (FXGL.getAppWidth())) {
                entity.getComponent(ProjectileComponent.class).setSpeed(-entity.getComponent(ProjectileComponent.class).getSpeed());
                if (newPos.getX() < 0 || newPos.getX() >= FXGL.getAppWidth()) {
                    entity.setRotation(-1);
                }
            }
            
        }
    }

    private void spawnTrackingRectangle_PlayerShip(Entity enemy) {
        double width = 50; // Szerokość prostokąta
        double height = 55; // Wysokość prostokąta

        Rectangle trackingRectangle = new Rectangle(width, height, Color.TRANSPARENT);
        trackingRectangle.setStroke(Color.BLUE);
        trackingRectangle.setStrokeWidth(2);

        Entity rectangleEntity = FXGL.entityBuilder()
                .view(trackingRectangle)
                .buildAndAttach();

        rectangleEntity.setPosition(enemy.getPosition());

        FXGL.run(() -> updateTrackingPosition_for_playerShip(rectangleEntity, enemy), Duration.seconds(TRACKING_UPDATE_INTERVAL*0.0000000001));
    }

    private void spawnTrackingRectangle(Entity enemy) {
        double width = 50; // Szerokość prostokąta
        double height = 60; // Wysokość prostokąta

        Rectangle trackingRectangle = new Rectangle(width, height, Color.TRANSPARENT);
        trackingRectangle.setStroke(Color.RED);
        trackingRectangle.setStrokeWidth(2);

        Entity rectangleEntity = FXGL.entityBuilder()
                .view(trackingRectangle)
                .buildAndAttach();

        rectangleEntity.setPosition(enemy.getPosition());

        FXGL.run(() -> updateTrackingPosition_for_Enemy(rectangleEntity, enemy), Duration.seconds(TRACKING_UPDATE_INTERVAL*0.0000000001));
    }
    private void spawnTrackingRectangle_Bullet_Enemy(Entity bullet) {
        double width = 15; // Szerokość prostokąta
        double height = 45; // Wysokość prostokąta

        Rectangle trackingRectangle = new Rectangle(width, height, Color.TRANSPARENT);
        trackingRectangle.setStroke(Color.WHITE);
        trackingRectangle.setStrokeWidth(2);

        Entity rectangleEntity = FXGL.entityBuilder()
                .view(trackingRectangle)
                .buildAndAttach();

        rectangleEntity.setPosition(bullet.getX() - width / 2, bullet.getY() - height / 2);

        FXGL.run(() -> updateTrackingPosition(rectangleEntity, bullet), Duration.seconds(TRACKING_UPDATE_INTERVAL*0.0000000001));
    }
    private void spawnTrackingRectangle_Bullet_Player(Entity bullet) {
        double width = 15; // Szerokość prostokąta
        double height = 45; // Wysokość prostokąta

        Rectangle trackingRectangle = new Rectangle(width, height, Color.TRANSPARENT);
        trackingRectangle.setStroke(Color.GREEN);
        trackingRectangle.setStrokeWidth(2);

        Entity rectangleEntity = FXGL.entityBuilder()
                .view(trackingRectangle)
                .buildAndAttach();

        rectangleEntity.setPosition(bullet.getX(), bullet.getY());

        FXGL.run(() -> updateTrackingPosition_for_Player(rectangleEntity, bullet), Duration.seconds(TRACKING_UPDATE_INTERVAL*0.0000000001));
    }
    private void updateTrackingPosition_for_Enemy(Entity rectangleEntity, Entity enemy) {
        double enemyX = enemy.getX() + 10;
        double enemyY = enemy.getY();
        rectangleEntity.setPosition(enemyX, enemyY);
        bullet_enemy_UpdateX = enemyX;
        bullet_player_UpdateY = enemyY;
    }
    private void updateTrackingPosition_for_Player(Entity rectangleEntity, Entity enemy) {
        double playerX = enemy.getX();
        double playerY = enemy.getY()-40;
        rectangleEntity.setPosition(playerX, playerY);
        bullet_player_UpdateX = playerX;
        bullet_player_UpdateY = playerY;

    }
    private void updateTrackingPosition(Entity rectangleEntity, Entity enemy) {
        rectangleEntity.setPosition(enemy.getX(), enemy.getY());
        enemyUpdateX = enemy.getX();
        enemyUpdateY = enemy.getY();
    }
    private void updateTrackingPosition_for_playerShip(Entity rectangleEntity, Entity enemy) {
        double playerShipX = enemy.getX() + 12.5;
        double playerShipY = enemy.getY() + 5;
        rectangleEntity.setPosition(playerShipX, playerShipY);
        playerUpdateX = playerShipX;
        playerUpdateY = playerShipY;
    }



    private void checkCollision(Entity rectangleEntity, Entity enemy) {
        if(bullet_enemy_UpdateX == playerUpdateX && bullet_player_UpdateY == playerUpdateY){
            System.out.println("KOLIZJA!!!");
        }
    }




    @Spawns("simpleLaser")
    public Entity spawnLaser(SpawnData data) {
        Point2D velocity = new Point2D(0, 1);
        Entity entity = FXGL.entityBuilder(data)
                .view("shoot_2.png")
                .scale(0.0675, 0.0675)
                .collidable()
                .with(new ProjectileComponent(velocity, 150))
                .rotate(-90)
                .buildAndAttach();
        spawnTrackingRectangle_Bullet_Enemy(entity);
        return entity;
    }

    private static class EnemyShoot extends com.almasb.fxgl.entity.component.Component {
        private double timeSinceLastSpawn = 0.0;
        private double spawnInterval;

        public EnemyShoot(double spawnInterval) {
            this.spawnInterval = spawnInterval;
        }

        @Override
        public void onUpdate(double tpf) {
            timeSinceLastSpawn += tpf;
            if (timeSinceLastSpawn >= spawnInterval) {
                double X = entity.getX() + 25;
                double Y = entity.getY() + 25;
                FXGL.spawn("simpleLaser", X, Y);
                timeSinceLastSpawn = 0.0;
                spawnInterval = randomShootInterval();
            }
        }
    }

    private static double randomShootInterval() {
        Random random = new Random();
        return random.nextDouble() * 2 + 1;
    }

    @Spawns("player")
    public Entity newPlayer(SpawnData data) {
        Entity entity = FXGL.entityBuilder(data)
                .view("user_ship.png")
                .collidable()
                .scale(0.0675, 0.0675)
                .with(new PlayerComponent())
                .build();
        spawnTrackingRectangle_PlayerShip(entity);
        return entity;
    }

    @Spawns("bullet")
    public Entity bullet(SpawnData data) {
        Point2D dir = data.get("dir");
        Entity entity = FXGL.entityBuilder(data)
                .view("shoot_1.png")
                .collidable()
                .type(EntityType.BULLET)
                .with(new ProjectileComponent(dir, 200))
                .scale(0.0675, 0.0675)
                .build();
        spawnTrackingRectangle_Bullet_Player(entity);
        return entity;
    }

    @Spawns("background")
    public Entity background(SpawnData data) {
        return FXGL.entityBuilder(data)
                .view("background.jpg")
                .scale(0.5, 1)
                .build();
    }

}
