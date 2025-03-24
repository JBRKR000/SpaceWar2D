package org.example.GunUpdates;

import com.almasb.fxgl.entity.component.Component;
import javafx.geometry.Point2D;

public class SpiralComponent extends Component {
    private double angle = 0;
    private double angleSpeed;
    private double delta;

    public SpiralComponent(double angleSpeed, double delta) {
        this.angleSpeed = angleSpeed;
        this.delta = delta;
    }

    @Override
    public void onUpdate(double tpf) {
        angle += tpf * angleSpeed;
        double x = Math.cos(angle) * delta;
        double y = Math.sin(angle) * 100;
        entity.translate(new Point2D(x, y).multiply(tpf));
    }
}