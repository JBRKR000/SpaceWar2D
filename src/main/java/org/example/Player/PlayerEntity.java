package org.example.Player;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import org.example.Other.EntityType;

import static com.almasb.fxgl.dsl.FXGL.entityBuilder;

public class PlayerEntity implements EntityFactory {
    @Spawns("player")
    public Entity newPlayer(SpawnData data) {
        return entityBuilder(data)
                .type(EntityType.PLAYER)
                .viewWithBBox("player.png")
                .with(new PlayerComponent())
                .collidable()
                .build();
    }
}
