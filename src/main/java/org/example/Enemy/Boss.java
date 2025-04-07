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
import javafx.util.Duration;
import org.example.Other.EntityType;

import static com.almasb.fxgl.dsl.FXGL.*;

public class Boss implements EntityFactory {

    private AnimationChannel move;

    public Boss(){
        move = new AnimationChannel(FXGL.image("boss2.png"), 3,  900/3, 496/2, Duration.seconds(1), 0, 5);
    }

    int MAX_HP = 5000;
    @Spawns("boss_1")
    public Entity boss_1(SpawnData data) {
        var hp = new HealthIntComponent(MAX_HP);
        var hpView = new ProgressBar(false);
        hpView.setMaxValue(MAX_HP);
        hpView.setWidth(300);
        hpView.setTranslateY(350);
        hpView.currentValueProperty().bind(hp.valueProperty());
        AnimatedTexture texture = new AnimatedTexture(move);
        texture.loopAnimationChannel(move); // Dodanie uruchomienia animacji
        Entity entity = entityBuilder(data)
                .type(EntityType.ENEMY)
                .bbox(new HitBox(BoundingShape.box((double) 900 /3, (double) 496 /3)))
                .view(hpView)
                .with(hp)
                .with(new RandomMoveComponent(new Rectangle2D(0, 0, getAppWidth(), ((double) getAppHeight() /2)), 35))
                .collidable()
                .build();
        entity.getViewComponent().addChild(texture);
        entity.addComponent(new Boss.EnemySetAngle(texture, move));

        FXGL.run(()->{
            try{
                if(entity.hasComponent(RandomMoveComponent.class)){
                    if(FXGL.random(0,2) == 0){
                        int randomspeed = FXGL.random(0,150);
                        entity.getComponent(RandomMoveComponent.class).setMoveSpeed(randomspeed);
                    }else{
                        entity.setPosition(FXGL.random(250, FXGL.getAppWidth() - 250), FXGL.random(0, FXGL.getAppHeight()/ 2));
                    }

                }
            }catch (Exception e){
                //IGNORE
            }
        }, Duration.seconds(1));

        return entity;
    }
    private static class EnemySetAngle extends Component {
        private AnimatedTexture texture;
        private AnimationChannel move;
        public EnemySetAngle(AnimatedTexture texture, AnimationChannel move) {
            this.texture = texture;
            this.move = move;
            texture.loopAnimationChannel(move);
            texture.setScaleX(1.25);
            texture.setScaleY(1.25);
        }

        @Override
        public void onUpdate(double tpf) {
            entity.setRotation(0);
        }
    }
}
