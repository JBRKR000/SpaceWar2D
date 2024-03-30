package org.example;
import com.almasb.fxgl.core.math.Vec2;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.component.Component;
import static com.almasb.fxgl.dsl.FXGLForKtKt.spawn;
public class PlayerComponent extends Component {
    public void moveLeft(){
        entity.translate(-10, 0);
    }
    public void moveRight(){
        entity.translate(10, 0);
    }
    public void moveUp(){
        entity.translate(0, 10);
    }
    public void moveDown(){
        entity.translate(0, -10);
    }
    public void shoot(){
        Vec2 dir = Vec2.fromAngle(entity.getRotation()-90);
        spawn("bullet",new SpawnData(entity.getX()+30,entity.getY()+25).put("dir",dir.toPoint2D()));
    }


}
