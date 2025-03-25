package org.example.Enemy;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.HealthIntComponent;
import com.almasb.fxgl.dsl.components.RandomMoveComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import com.almasb.fxgl.ui.ProgressBar;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.util.Duration;
import org.example.Other.EntityType;

import static com.almasb.fxgl.dsl.FXGL.*;

public class Striker implements EntityFactory {
    public static Point2D pos;
    private static final int MAX_HP = 100;

    private AnimationChannel idle1, move1;
    private AnimationChannel idle2, move2;

    public Striker(){
        Image enemy_idle1 = FXGL.image("enemy_3.png");
        Image enemy_idle2 = FXGL.image("variant2.png");
        Image enemy_moving1 = FXGL.image("enemy_3.png");
        Image enemy_moving2 = FXGL.image("variant2.png");
        idle1 = new AnimationChannel(enemy_idle1, 5,  44, 34, Duration.seconds(1), 0, 3);
        move1 = new AnimationChannel(enemy_moving1, 5, 44, 34, Duration.seconds(1), 0, 3);
        idle2 = new AnimationChannel(enemy_idle2, 5,  44, 34, Duration.seconds(1), 0, 2);
        move2 = new AnimationChannel(enemy_moving2, 5, 44, 34, Duration.seconds(1), 0, 3);
    }



    @Spawns("striker")
    public Entity newEnemy(SpawnData data) {

        var hp = new HealthIntComponent(MAX_HP);
        var hpView = new ProgressBar(false);
        hpView.setMaxValue(MAX_HP);
        hpView.setWidth(85);
        hpView.setTranslateY(55);
        hpView.setTranslateX(-15);
        hpView.currentValueProperty().bind(hp.valueProperty());
        AnimatedTexture texture1 = new AnimatedTexture(idle1);
        AnimatedTexture texture2 = new AnimatedTexture(idle2);
        texture2.setScaleX(1.25);
        texture2.setScaleY(1.25);
        texture1.setScaleX(1.25);
        texture1.setScaleY(1.25);
        Entity entity = entityBuilder(data)
                .type(EntityType.ENEMY)
                .bbox(new HitBox(BoundingShape.box(55, 42.5)))
                .view(hpView)
                .with(hp)
                .with(new RandomMoveComponent(new Rectangle2D(0, 0, getAppWidth(), ((double) getAppHeight() /2)),100))
                .collidable()
                .build();
        switch (FXGL.random(1,2)){
            case 1:
                entity.addComponent(new EnemySetAngle(texture1, idle1, move1));
                entity.getViewComponent().addChild(texture1);
                break;
            case 2:
                entity.addComponent(new EnemySetAngle(texture2, idle2, move2));
                entity.getViewComponent().addChild(texture2);
                break;
        }
        pos = new Point2D(entity.getX(),entity.getY());
        return entity;


    }
    static class EnemySetAngle extends Component {
        private AnimatedTexture texture;
        private AnimationChannel idle, move;

        public EnemySetAngle(AnimatedTexture texture, AnimationChannel idle, AnimationChannel move) {
            this.texture = texture;
            this.idle = idle;
            this.move = move;
        }
        @Override
        public void onUpdate(double tpf) {
            if (!entity.getPosition().equals(Point2D.ZERO)) {
                if (texture.getAnimationChannel() != move) {
                    texture.loopAnimationChannel(move);
                }
            } else {
                if (texture.getAnimationChannel() != idle) {
                    texture.loopAnimationChannel(idle);
                }
            }
            entity.setRotation(0);
        }
    }
    public static Point2D getPosOfEnemy() {
        return pos;
    }
}
