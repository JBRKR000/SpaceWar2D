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


public class Eclipse implements EntityFactory {

    public static Point2D pos;
    private static final int MAX_HP = 125;
    private AnimationChannel idle, move;


    public Eclipse() {
        Image enemy_idle = FXGL.image("enemy_1_idle_6x1.png");
        Image enemy_moving = FXGL.image("enemy_1_moving_512x60.png");
        idle = new AnimationChannel(enemy_idle, 6, 85, 60, Duration.seconds(1), 0, 5);
        move = new AnimationChannel(enemy_moving, 6, 80, 60, Duration.seconds(2), 0, 5);

    }

    @Spawns("eclipse")
    public Entity newEnemy(SpawnData data) {
        var hp = new HealthIntComponent(MAX_HP);
        var hpView = new ProgressBar(false);
        hpView.setMaxValue(MAX_HP);
        hpView.setWidth(85);
        hpView.setTranslateY(90);
        hpView.currentValueProperty().bind(hp.valueProperty());
        AnimatedTexture texture = new AnimatedTexture(idle);
        Entity entity = entityBuilder(data)
                .type(EntityType.ENEMY)
                .bbox(new HitBox(BoundingShape.box(85, 60)))
                .scale(0.5, 0.5)
                .view(hpView)
                .with(hp)
                .with(new RandomMoveComponent(new Rectangle2D(0, 0, getAppWidth(), ((double) getAppHeight() / 2)), 100))
                .collidable()
                .build();
        entity.getViewComponent().addChild(texture);
        entity.addComponent(new EnemySetAngle(texture, idle, move));
        pos = new Point2D(entity.getX(), entity.getY());

        FXGL.run(() -> {
            try {
                if (entity.hasComponent(RandomMoveComponent.class)) {
                    int randomspeed = FXGL.random(0, 100);
                    entity.getComponent(RandomMoveComponent.class).setMoveSpeed(randomspeed);
                }
            } catch (Exception e) {
                //IGNORE
            }


        }, Duration.seconds(5));

        return entity;
    }

    private static class EnemySetAngle extends Component {
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
}

