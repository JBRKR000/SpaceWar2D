package org.example.GunUpdates;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import javafx.geometry.Point2D;
import javafx.util.Duration;

public class SpiralBetaClockWise extends Component {

    private double angle = 0;
    private double centerX;
    private double centerY;
    private double speedY = 2; // Stała prędkość w dół
    private double radius = 200; // Początkowy promień

    public SpiralBetaClockWise(double centerX, double centerY) {
        this.centerX = centerX;
        this.centerY = centerY;
    }

    @Override
    public void onAdded() {
        FXGL.run(() -> {
            angle += 0.05; // Obrót zgodnie z ruchem wskazówek zegara
            radius += 0.5;
            if(entity != null){
                double x = centerX + radius * Math.cos(angle);
                double y = centerY + radius * Math.sin(angle) + speedY;
                entity.setPosition(new Point2D(x, y));
                centerY += speedY;
            }
        }, Duration.seconds(0.016));
    }

    @Override
    public void onUpdate(double tpf) {

    }
}