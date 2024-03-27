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

import java.awt.*;

import static org.example.AddedLogicClass.randomSpawnVector;



public class Entities implements EntityFactory {
    InitSettings initSettings = new InitSettings();
    @Spawns("enemy")
    public Entity newEnemy(SpawnData data) {
        Point2D velocity = new Point2D(randomSpawnVector(), 0);
        Entity entity = FXGL.entityBuilder(data)
                .view("enemy_1.png")
                .scale(0.025, 0.025)
                .collidable()
                .with(new ProjectileComponent(velocity, 200)) // PORUSZANIE SIĘ PO WEKTORZE X,Y
                .buildAndAttach();
        if (velocity.getX() < 0) {
            entity.setRotation(-1);
        }
        entity.addComponent(new EnemyControl());
        return entity;
    }



    private static class EnemyControl extends com.almasb.fxgl.entity.component.Component {
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

}
