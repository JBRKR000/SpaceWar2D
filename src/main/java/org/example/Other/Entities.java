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
}
