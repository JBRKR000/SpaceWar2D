package org.example.Bonus;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.OffscreenCleanComponent;
import com.almasb.fxgl.dsl.components.ProjectileComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.util.Duration;
import org.example.Other.EntityType;

import static org.example.Player.PlayerEntity.p_x;
import static org.example.Player.PlayerEntity.p_y;

public class Lightning implements EntityFactory {

    private AnimationChannel idle, move;

    public Lightning() {
        Image lightningImage = FXGL.image("lightning.png");
        idle = new AnimationChannel(lightningImage, 5, 793 / 5, 600, Duration.seconds(0.75), 0, 4);
        move = new AnimationChannel(lightningImage, 5, 793 / 5, 600, Duration.seconds(0.75), 0, 4);
    }

    @Spawns("lightning")
    public Entity lightning(SpawnData data) {
        AnimatedTexture texture = new AnimatedTexture(idle);
        var velocity = new Point2D(0, 1);
        var entity = FXGL.entityBuilder(data)
                .type(EntityType.LIGHTNING)
                .scale(1, 1)
                .bbox(new HitBox(BoundingShape.box((double) 793 / 5, 597)))
                .with(new OffscreenCleanComponent())
                .with(new ProjectileComponent(velocity, 0))
                .collidable()
                .build();
        entity.getViewComponent().addChild(texture);
        entity.addComponent(new LightningAnimationComponent(texture, idle, move));
        FXGL.play("lightning_1.wav");
        entity.setOnActive(() -> {
            FXGL.run(()->{
                entity.setPosition(p_x - 42.5, p_y - 600);
            },Duration.seconds(0.01));
            FXGL.runOnce(() -> {

                FXGL.getGameWorld().removeEntity(entity);
            }, Duration.seconds(0.5));
        });

        return entity;
    }

    private static class LightningAnimationComponent extends com.almasb.fxgl.entity.component.Component {
        private AnimatedTexture texture;
        private AnimationChannel idle, move;

        public LightningAnimationComponent(AnimatedTexture texture, AnimationChannel idle, AnimationChannel move) {
            this.texture = texture;
            this.idle = idle;
            this.move = move;
        }

        @Override
        public void onUpdate(double tpf) {
            if (entity.getPosition().getY() > 0) {
                if (texture.getAnimationChannel() != move) {
                    texture.loopAnimationChannel(move);
                }
            } else {
                if (texture.getAnimationChannel() != idle) {
                    texture.loopAnimationChannel(idle);
                }
            }
        }
    }
}