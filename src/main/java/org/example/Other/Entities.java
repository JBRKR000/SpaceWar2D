package org.example.Other;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;

import static org.example.Init.InitSettings.wave;

public class Entities implements EntityFactory {

    @Spawns("background")
    public Entity background(SpawnData data) {
        return FXGL.entityBuilder(data)
                .view("background.jpg")
                .scale(1, 1)
                .zIndex(-100)
                .neverUpdated()
                .build();

    }
    @Spawns("background2")
    public Entity background2(SpawnData data) {
        return FXGL.entityBuilder(data)
                .view("background2.jpg")
                .scale(0.4,0.4)
                .build();
    }
    @Spawns("background3")
    public Entity background3(SpawnData data) {
        return FXGL.entityBuilder(data)
                .view("background3.jpg")
                .scale(0.5, 0.6)
                .build();
    }
    @Spawns("background4")
    public Entity background4(SpawnData data) {
        return FXGL.entityBuilder(data)
                .view("background4.jpg")
                .scale(0.5, 0.5)
                .build();
    }
    @Spawns("background5")
    public Entity background5(SpawnData data) {
        return FXGL.entityBuilder(data)
                .view("background5.jpg")
                .scale(0.4, 0.4)
                .build();
    }


    public static void pickBackground(){
        if(wave == 1 || wave == 2){
            FXGL.getGameWorld().spawn("background");
        }
        if(wave == 3 || wave == 4){
            FXGL.getGameWorld().spawn("background2");
        }
        if(wave == 5 || wave == 6){
            FXGL.getGameWorld().spawn("background3");
        }
        if(wave == 7 || wave == 8){
            FXGL.getGameWorld().spawn("background4");
        }
        if(wave == 9 || wave == 10){
            FXGL.getGameWorld().spawn("background5");
        }
    }
}
