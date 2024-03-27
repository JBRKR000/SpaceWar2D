package org.example;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.dsl.FXGL;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.util.Duration;

import java.awt.*;

import static com.almasb.fxgl.dsl.FXGLForKtKt.getAppHeight;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getAppWidth;

public class InitSettings extends GameApplication {
    Duration FPS = new Duration(1.0/60.0);
    private int width = 800;
    private int height = 600;
    private int enemyCount = 0;
    private final int MAX_ENEMIES = 10;
    public void initSettings(GameSettings settings){
        settings.setWidth(width);
        settings.setHeight(height);
        settings.setGameMenuEnabled(true);
        //settings.setCloseConfirmation(true);
        settings.setTitle("Game App");

    }
    public int getWidthScreen(){
        return width;
    }




    @Override
    protected void initGame() { //initial entities & more
        FXGL.getGameWorld().addEntityFactory(new Entities()); //dodaje wszystkie entity do świata
        //ENEMY BLUE - BLUEBERRY
            FXGL.run(()->{
                if(enemyCount < MAX_ENEMIES){
                    FXGL.spawn("enemy", FXGLMath.randomPoint(
                            new Rectangle2D(0,0,getAppWidth(),(double) getAppHeight() /2)
                    )); //SPAWN W RÓŻNYCH MIEJSCACH
                    enemyCount++;
                }
            }, Duration.seconds(1));

    }
}
