package org.example.Bonus;
import com.almasb.fxgl.dsl.components.OffscreenCleanComponent;
import com.almasb.fxgl.dsl.components.ProjectileComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import javafx.geometry.Point2D;
import org.example.Other.EntityType;
import static com.almasb.fxgl.dsl.FXGL.entityBuilder;

public class CoinBonus implements EntityFactory {
    @Spawns("coin_bonus")
    public Entity enemyBullet(SpawnData data) {
        var velocity = new Point2D(0, 1);
        var entity = entityBuilder(data)
                .type(EntityType.COIN)
                .scale(0.45, 0.45)
                .viewWithBBox("coin.png")
                .with(new OffscreenCleanComponent())
                .with(new ProjectileComponent(velocity, 100))
                .collidable()
                .build();
        entity.setOnActive(() -> {
        });
        return entity;
    }
}
