package org.example;
import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.ProjectileComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import javafx.geometry.Point2D;
import javafx.geometry.Point3D;
import javafx.util.Duration;

import java.awt.*;
import java.util.Random;
import java.util.Timer;

import static org.example.AddedLogicClass.randomSpawnVector;



public class Entities implements EntityFactory {

    public double EnemyPosX;

    private int randomSpeed(){ //generates random speed for entity
        Random random = new Random();
        return random.nextInt(451)+50;
    }
    private final String[] enemyTextures = {"enemy_1.png", "enemy_2.png", "enemy_3.png", "enemy_4.png"}; //enemy textures
    private final double[][] enemyScales = {{0.025, 0.025}, {0.05, 0.05}, {0.12, 0.12}, {0.12, 0.12}}; //scales for enemies
    @Spawns("enemy")
    public Entity spawnEnemy(SpawnData data) {
        int textureIndex = new Random().nextInt(enemyTextures.length);
        String texture = enemyTextures[textureIndex];
        double[] scale = enemyScales[textureIndex];

        Point2D velocity = new Point2D(randomSpawnVector(), 0);
        Entity entity = FXGL.entityBuilder(data)
                .view(texture)
                .scale(scale[0], scale[1])
                .collidable()
                .with(new ProjectileComponent(velocity,randomSpeed()))
                .buildAndAttach();

        FXGL.run(()->{
           int newSpeed = randomSpeed();
           entity.getComponent(ProjectileComponent.class).setSpeed(newSpeed);
            System.out.println("New speed: " + newSpeed);
            System.out.println("New Rotation:"+ entity.getRotation());
            System.out.println("New Vector:"+ entity.getX());
            EnemyPosX = entity.getX();
            if(entity.getRotation()==-180||entity.getRotation()==180){
                entity.setRotation(0);
            }
        }, Duration.seconds(0.5));

        if (velocity.getX() < 0) {
            entity.setRotation(-1);
        }
        entity.addComponent(new EnemyControlOffScreen());


        return entity;
    }
    private static class EnemyControlOffScreen extends com.almasb.fxgl.entity.component.Component {
        @Override
        public void onUpdate(double tpf) {
            Point2D newPos = entity.getPosition();
            if(newPos.getX() <= 0 || newPos.getX() >= (FXGL.getAppWidth())){
                System.out.println("Off-screen!");
                entity.getComponent(ProjectileComponent.class).setSpeed(-entity.getComponent(ProjectileComponent.class).getSpeed());
                if(newPos.getX()<0||newPos.getX() >= FXGL.getAppWidth()){
                    entity.setRotation(-1);
                }
            }
        }
    }

    @Spawns("simpleLaser")
    public Entity spawnLaser(SpawnData data){
        Point2D velocity = new Point2D(0, 1);
        Entity entity = FXGL.entityBuilder(data)
                .view("brick.png")
                .scale(0.00625,0.00625)
                .collidable()
                .with(new ProjectileComponent(velocity,150))
                .buildAndAttach();

        return entity;
    }




}
