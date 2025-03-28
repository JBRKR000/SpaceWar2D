package org.example.Enemy;

import com.almasb.fxgl.core.asset.AssetType;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.HealthIntComponent;
import com.almasb.fxgl.dsl.components.ProjectileComponent;
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
import javafx.animation.Animation;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.util.Duration;
import org.example.Other.EntityType;
import static com.almasb.fxgl.dsl.FXGL.*;


public class Fighter implements EntityFactory {

    public static Point2D pos;
    private static final int MAX_HP = 450;
    private AnimationChannel move;


    public Fighter() {
        Image enemy_moving = FXGL.image("fighter.png");
        move = new AnimationChannel(enemy_moving, 4, 320/4, 138/2, Duration.seconds(1), 0, 7);

    }

    @Spawns("fighter")
    public Entity newEnemy(SpawnData data) {
        var hp = new HealthIntComponent(MAX_HP);
        var hpView = new ProgressBar(false);
        hpView.setMaxValue(MAX_HP);
        hpView.setWidth(85);
        hpView.setTranslateY(90);
        hpView.currentValueProperty().bind(hp.valueProperty());
        AnimatedTexture texture = new AnimatedTexture(move);
        Entity entity = entityBuilder(data)
                .type(EntityType.ENEMY)
                .bbox(new HitBox(BoundingShape.box(80, 69)))
                .scale(0.5, 0.5)
                .view(hpView)
                .with(hp)
                .with(new RandomMoveComponent(new Rectangle2D(0, 0, getAppWidth(), ((double) getAppHeight() / 2)), 0))
                .collidable()
                .build();
        entity.getViewComponent().addChild(texture);
        entity.addComponent(new EnemySetAngle(texture, move));
        texture.loopAnimationChannel(move);
        pos = new Point2D(entity.getX(), entity.getY());

        FXGL.run(() -> {
            try {
                if (entity.hasComponent(RandomMoveComponent.class)) {
                    int randomspeed = FXGL.random(0, 0);
                    entity.getComponent(RandomMoveComponent.class).setMoveSpeed(randomspeed);
                }
            } catch (Exception e) {
                //IGNORE
            }


        }, Duration.seconds(5));

        entity.setOnActive(() -> {
            FXGL.run(()->{
                if(FXGL.random(0, 100) < 65) {
                    entity.setPosition(FXGL.random(0, getAppWidth() - 80), FXGL.random(0, getAppHeight() / 2));
                }
            }, Duration.seconds(FXGL.random(1,2)));

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

