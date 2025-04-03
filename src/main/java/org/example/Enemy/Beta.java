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

public class Beta implements EntityFactory {

    public static Point2D pos;
    private static final int MAX_HP = 450;
    private AnimationChannel move;
    public boolean betaisMoving = true;


    public Beta() {
        Image enemy_moving = FXGL.image("beta.png");
        move = new AnimationChannel(enemy_moving, 4, 480/4, 300/3, Duration.seconds(2), 0, 11);

    }

    @Spawns("beta")
    public Entity newEnemy(SpawnData data) {
        var hp = new HealthIntComponent(MAX_HP);
        var hpView = new ProgressBar(false);
        hpView.setMaxValue(MAX_HP);
        hpView.setWidth(85);
        hpView.setTranslateY(110);
        hpView.setTranslateX(20);
        hpView.currentValueProperty().bind(hp.valueProperty());
        AnimatedTexture texture = new AnimatedTexture(move);
        Entity entity = entityBuilder(data)
                .type(EntityType.ENEMY)
                .bbox(new HitBox(BoundingShape.box((double) 480 /4, 100)))
                .scale(0.5, 0.5)
                .view(hpView)
                .with(hp)
                .with(new RandomMoveComponent(new Rectangle2D(0, 0, getAppWidth(), ((double) getAppHeight() / 2)), 200))
                .collidable()
                .build();
        entity.getViewComponent().addChild(texture);
        entity.addComponent(new Beta.EnemySetAngle(texture, move));
        texture.loopAnimationChannel(move);
        pos = new Point2D(entity.getX(), entity.getY());

        entity.setOnActive(()->{

            FXGL.run(()->{
                if(entity.hasComponent(RandomMoveComponent.class)){
                        if(FXGL.random(0,3) > 1){
                            entity.getComponent(RandomMoveComponent.class).setMoveSpeed(0);
                            betaisMoving = false;
                        }else {
                            entity.getComponent(RandomMoveComponent.class).setMoveSpeed(200);
                            betaisMoving = true;
                        }
                }
            },Duration.seconds(1));
        });


        return entity;
    }

    private static class EnemySetAngle extends Component {
        private AnimatedTexture texture;
        private AnimationChannel idle, move;

        public EnemySetAngle(AnimatedTexture texture, AnimationChannel move) {
            this.texture = texture;
            this.move = move;
        }
        @Override
        public void onUpdate(double tpf) {
            entity.setRotation(0);
        }
    }
}
