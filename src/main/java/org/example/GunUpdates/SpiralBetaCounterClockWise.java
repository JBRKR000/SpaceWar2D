package org.example.GunUpdates;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import javafx.geometry.Point2D;
import javafx.util.Duration;

public class SpiralBetaCounterClockWise extends Component {

    private double angle = -7.5;
    private double centerX;
    private double centerY;
    private double speedY = 3;
    private double radius = 150;

    public SpiralBetaCounterClockWise(double centerX, double centerY) {
        this.centerX = centerX;
        this.centerY = centerY;
    }

    @Override
    public void onAdded() {
        FXGL.run(() -> {
            angle -= 0.05; // Przeciwny kierunek
            radius += 0.1;
            if (entity != null) {
                double x = centerX + radius * Math.cos(angle);
                double y = centerY + radius * Math.sin(angle) + speedY;
                entity.setPosition(new Point2D(x, y));
                centerY += speedY;
            }
        }, Duration.seconds(0.016));
    }
}
