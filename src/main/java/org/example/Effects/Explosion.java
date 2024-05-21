package org.example.Effects;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import javafx.scene.image.Image;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

import static com.almasb.fxgl.dsl.FXGL.entityBuilder;
import static com.almasb.fxgl.dsl.FXGL.runOnce;

public class Explosion implements EntityFactory {

    private AnimatedTexture createAnimation() {
        List<Image> frames = new ArrayList<>();
        for (int i = 0; i <= 11; i++) {
            String filename = String.format("assets/textures/frame_%02d_delay-0.1s.png", i);
            frames.add(new Image(filename));
        }
        AnimationChannel animationChannel = new AnimationChannel(frames, Duration.seconds(0.8));
        AnimatedTexture animatedTexture = new AnimatedTexture(animationChannel);
        animatedTexture.play();
        runOnce(animatedTexture::stop,Duration.seconds(0.8));
        return animatedTexture;
    }

    private AnimatedTexture createAnimation2() {
        List<Image> frames = new ArrayList<>();
        for (int i = 0; i <= 7; i++) { // Pętla od 1 do 8
            String filename = String.format("assets/textures/frame_%d_delay-0.1s.gif", i); // Formatowanie bez zera wiodącego
            frames.add(new Image(filename));
        }
        AnimationChannel animationChannel = new AnimationChannel(frames, Duration.seconds(0.6));
        AnimatedTexture animatedTexture = new AnimatedTexture(animationChannel);
        animatedTexture.play();
        FXGL.runOnce(animatedTexture::stop, Duration.seconds(0.6));
        return animatedTexture;
    }

    @Spawns("explosion")
    public Entity enemyBullet(SpawnData data) {
        Entity entity =  entityBuilder(data)
                .scale(0.5,0.5)
                .view(createAnimation())
                .collidable()
                .buildAndAttach();
        FXGL.runOnce(entity::removeFromWorld, Duration.seconds(0.8));
        return entity;
    }
    @Spawns("light_explosion")
    public Entity enemyBullet2(SpawnData data) {
        Entity entity =  entityBuilder(data)
                .scale(0.3,0.3)
                .view(createAnimation2())
                .collidable()
                .buildAndAttach();
        FXGL.runOnce(entity::removeFromWorld, Duration.seconds(0.6));
        return entity;
    }
}