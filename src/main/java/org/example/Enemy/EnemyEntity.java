package org.example.Enemy;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.HealthIntComponent;
import com.almasb.fxgl.dsl.components.ProjectileComponent;
import com.almasb.fxgl.dsl.components.RandomMoveComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.ui.ProgressBar;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.paint.Color;
import org.example.Other.EntityType;

import java.util.Random;

import static com.almasb.fxgl.dsl.FXGL.*;
import static org.example.Enemy.EnemySpawn.randomSpeed;

public class EnemyEntity implements EntityFactory {
    public static Point2D pos;
    @Spawns("enemy")
    public Entity newEnemy(SpawnData data) {
        final String[] enemyTextures = {"enemy_1.png", "enemy_2.png", "enemy_3.png", "enemy_4.png"};
        Random random = new Random();
        int randomIndex = random.nextInt(enemyTextures.length);
        String randomTexture = enemyTextures[randomIndex];
        var hp = new HealthIntComponent(2);
        var hpView = new ProgressBar(false);
        hpView.setFill(Color.LIGHTGREEN);
        hpView.setMaxValue(2);
        hpView.setWidth(85);
        hpView.setTranslateY(90);
        hpView.currentValueProperty().bind(hp.valueProperty());
        Entity entity = entityBuilder(data)
                .type(EntityType.ENEMY)
                .viewWithBBox(randomTexture)
                //.view(hpView)
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