package org.example.GunUpdates;

import com.almasb.fxgl.entity.component.Component;
import javafx.geometry.Point2D;

public class CircularMovementComponent extends Component {
    private Point2D center;
    private double radius;
    private double angle;
    private double speed;
    private double verticalSpeed;  // Downward speed
    private double verticalOffset; // Keep track of the downward displacement

    public CircularMovementComponent(Point2D center, double radius, double speed, double verticalSpeed) {
        this.center = center;
        this.radius = radius;
        this.speed = speed;
        this.angle = 0;
        this.verticalSpeed = verticalSpeed;
        this.verticalOffset = 0; // Start with no downward displacement
    }

    @Override
    public void onUpdate(double tpf) {
        // Update the angle for circular motion
        angle += speed * tpf;

        // Calculate the new x and y positions based on the circular movement
        double x = center.getX() + radius * Math.cos(angle);
        double y = center.getY() + radius * Math.sin(angle);

        // Apply the vertical offset for downward movement
        verticalOffset += verticalSpeed * tpf;  // Increase the downward displacement

        // Combine the circular movement with the downward movement
        y += verticalOffset;

        // Update the entity's position
        entity.setPosition(x, y);
    }
}
