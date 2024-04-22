package org.example.Score;

import com.almasb.fxgl.animation.Interpolators;
import com.almasb.fxgl.dsl.components.ExpireCleanComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import javafx.geometry.Point2D;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGL.*;

public class ScoreEntity implements EntityFactory {
    @Spawns("scoreText")
    public Entity newScoreText(SpawnData data) {
        String text = data.get("text");
        var e = entityBuilder(data)
                .view(getUIFactoryService().newText(text, 24))
                .with(new ExpireCleanComponent(Duration.seconds(0.66)).animateOpacity())
                .build();

        animationBuilder()
                .duration(Duration.seconds(0.66))
                .interpolator(Interpolators.EXPONENTIAL.EASE_OUT())
                .translate(e)
                .from(new Point2D(data.getX(), data.getY()))
                .to(new Point2D(data.getX(), data.getY() - 30))
                .buildAndPlay();

        return e;
    }
}
