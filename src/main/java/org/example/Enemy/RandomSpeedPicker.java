package org.example.Enemy;

import com.almasb.fxgl.dsl.FXGL;
import javafx.util.Duration;

import java.util.concurrent.atomic.AtomicInteger;

public class RandomSpeedPicker {
    public static int randomSpeed() {
        AtomicInteger randomspeed = new AtomicInteger();
        FXGL.run(() -> {
            randomspeed.set(FXGL.random(80, 200));
        }, Duration.seconds(0.5));
        return randomspeed.get();
    }
}
