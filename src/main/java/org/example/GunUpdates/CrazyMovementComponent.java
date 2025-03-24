package org.example.GunUpdates;

import com.almasb.fxgl.entity.component.Component;
import javafx.geometry.Point2D;

import java.util.Random;

public class CrazyMovementComponent extends Component {
    private double speed;
    private Random random = new Random();
    private double changeDirectionTime = 0;

    public CrazyMovementComponent(double speed) {
        this.speed = speed;
    }

    @Override
    public void onUpdate(double tpf) {
        changeDirectionTime -= tpf;
        if (changeDirectionTime <= 0) {
            changeDirectionTime = random.nextDouble() * 0.5; // Change direction every 0.5 seconds
            double angle = random.nextDouble() * 2 * Math.PI;
            double x = Math.cos(angle) * speed;
            double y = Math.sin(angle) * speed;
            entity.setProperty("velocity", new Point2D(x, y));
        }

        Point2D velocity = entity.getObject("velocity");
        entity.translate(velocity.multiply(tpf));
    }
}