package org.example.GunUpdates;

import com.almasb.fxgl.entity.component.Component;
import javafx.geometry.Point2D;

public class ZigZagComponent extends Component {
    private double angle = 0;
    private double speed;
    private double amplitude;

    public ZigZagComponent(double speed, double amplitude) {
        this.speed = speed;
        this.amplitude = amplitude;
    }

    @Override
    public void onUpdate(double tpf) {
        angle += tpf * speed;
        double x = Math.sin(angle) * amplitude;
        entity.translate(new Point2D(x, -1).multiply(tpf * 300));
    }
}