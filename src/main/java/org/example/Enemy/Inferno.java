package org.example.Enemy;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.HealthIntComponent;
import com.almasb.fxgl.dsl.components.RandomMoveComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.ui.ProgressBar;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.util.Duration;
import org.example.Other.EntityType;

import java.util.Random;

import static com.almasb.fxgl.dsl.FXGL.*;

public class Inferno implements EntityFactory {
    public boolean shoot = false;
    public static Point2D pos;
    private static final int MAX_HP = 7;
    @Spawns("inferno")
    public Entity newEnemy(SpawnData data) {

        var hp = new HealthIntComponent(MAX_HP);
        var hpView = new ProgressBar(false);
        hpView.setMaxValue(MAX_HP);
        hpView.setWidth(85);
        hpView.setTranslateY(90);
        hpView.currentValueProperty().bind(hp.valueProperty());
        Entity entity = entityBuilder(data)
                .type(EntityType.ENEMY)
                .viewWithBBox("enemy_2.png")
                .view(hpView)
                .with(hp)
                .with(new RandomMoveComponent(new Rectangle2D(0, 0, getAppWidth(), ((double) getAppHeight() /2)),0))
                .collidable()
                .build();
        entity.addComponent(new Inferno.EnemySetAngle());
        pos = new Point2D(entity.getX(),entity.getY());
        FXGL.run(()->{
            try{
                if(entity.getComponent(RandomMoveComponent.class) != null){
                    Random random = new Random();
                    int randomspeed = random.nextInt(2) * 300;
                    shoot = randomspeed != 300;
                    entity.getComponent(RandomMoveComponent.class).setMoveSpeed(randomspeed);
                }
            }catch (Exception e){
                //
            }
        }, Duration.seconds(2));

        return entity;
    }
    private static class EnemySetAngle extends com.almasb.fxgl.entity.component.Component {
        @Override
        public void onUpdate(double tpf) {
            entity.setRotation(0);
        }
    }
    public boolean isShooting() {
        return shoot;
    }
}
