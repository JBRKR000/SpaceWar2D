package org.example.Player;
import com.almasb.fxgl.core.math.Vec2;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.component.Component;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGLForKtKt.spawn;
public class PlayerComponent extends Component {
    public void moveLeft(){
        if(entity.getX() > 10){
            entity.translate(-10, 0);
        }
    }
    public void moveRight(){
        if(entity.getX() < FXGL.getAppWidth() - 45){
            entity.translate(10, 0);
        }

    }
    public void moveUp(){
        if(entity.getY() < FXGL.getAppHeight() - 50){
            entity.translate(0, 10);
        }

    }
    public void moveDown(){
        if(entity.getY() > (double) FXGL.getAppHeight() /2){
            entity.translate(0, -10);
        }
    }
    public void shoot(){
        Vec2 dir = Vec2.fromAngle(entity.getRotation()-90);
        spawn("player_bullet",new SpawnData(entity.getX()-20,entity.getY()-30).put("dir",dir.toPoint2D()));
    }

}
