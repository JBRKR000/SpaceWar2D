package org.example.Other;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import javafx.geometry.Point2D;

public class StarComponent extends Component {
    private Point2D velocity;
    public StarComponent(Point2D velocity) {
        this.velocity = velocity;
    }
    @Override
    public void onUpdate(double tpf) {
        entity.translate(velocity.multiply(tpf));
        if (entity.getX() > FXGL.getAppWidth()) {
            entity.setX(0);
        }
        if (entity.getY() > FXGL.getAppHeight()) {
            entity.setY(0);
        }
    }
}
