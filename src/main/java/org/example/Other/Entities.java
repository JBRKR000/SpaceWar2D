package org.example.Other;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;

public class Entities implements EntityFactory {

    @Spawns("background")
    public Entity background(SpawnData data) {
        return FXGL.entityBuilder(data)
                .view("background.jpg")
                .scale(0.5, 1)
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

}
