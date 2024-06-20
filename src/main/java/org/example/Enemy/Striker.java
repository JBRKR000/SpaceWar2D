package org.example.Enemy;

import com.almasb.fxgl.dsl.components.HealthIntComponent;
import com.almasb.fxgl.dsl.components.RandomMoveComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.ui.ProgressBar;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import org.example.Other.EntityType;

import java.util.Random;

import static com.almasb.fxgl.dsl.FXGL.*;

public class Striker implements EntityFactory {
    public static Point2D pos;
    private static final int MAX_HP = 100;
    @Spawns("striker")
    public Entity newEnemy(SpawnData data) {

        var hp = new HealthIntComponent(MAX_HP);
        var hpView = new ProgressBar(false);
        hpView.setMaxValue(MAX_HP);
        hpView.setWidth(85);
        hpView.setTranslateY(90);
        hpView.currentValueProperty().bind(hp.valueProperty());
        Entity entity = entityBuilder(data)
                .type(EntityType.ENEMY)
                .viewWithBBox("enemy_3.png")
                .view(hpView)
                .with(hp)
                .with(new RandomMoveComponent(new Rectangle2D(0, 0, getAppWidth(), ((double) getAppHeight() /2)),100))
                .collidable()
                .build();
        entity.addComponent(new EnemySetAngle());
        pos = new Point2D(entity.getX(),entity.getY());
        return entity;
    }
    private static class EnemySetAngle extends com.almasb.fxgl.entity.component.Component {
        @Override
        public void onUpdate(double tpf) {
            entity.setRotation(0);
        }
    }
    public static Point2D getPosOfEnemy() {
        return pos;
    }
}
