package org.example.Bullet;

import com.almasb.fxgl.dsl.components.OffscreenCleanComponent;
import com.almasb.fxgl.dsl.components.ProjectileComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import javafx.geometry.Point2D;
import org.example.Other.EntityType;

import static com.almasb.fxgl.dsl.FXGL.entityBuilder;
import static org.example.Player.PlayerEntity.p_x;
import static org.example.Player.PlayerEntity.p_y;

public class InfernoBullet implements EntityFactory {
    @Spawns("inferno_bullet")
    public Entity enemyBullet(SpawnData data) {
        double destX = p_x;
        double destY = p_y;

        Point2D startPosition = new Point2D(data.getX(), data.getY()); // Pozycja początkowa pocisku
        Point2D targetPosition = new Point2D(p_x, p_y); // Pozycja celu
        Point2D velocity = targetPosition.subtract(startPosition).normalize().multiply(400); // Obliczenie wektora prędkości
        var entity = entityBuilder(data)
                .type(EntityType.ENEMY_BULLET)
                .viewWithBBox("bullet_3.png")
                .with(new OffscreenCleanComponent())
                .with(new ProjectileComponent(velocity, 400))
                .collidable()
                .build();

        entity.setOnActive(() -> {
            // TODO EFFECTS

        });
        entity.addComponent(new InfernoBullet.EnemyBulletTracking());
        return entity;
    }
    private static class EnemyBulletTracking extends com.almasb.fxgl.entity.component.Component {
        @Override
        public void onUpdate(double tpf) {
            entity.setRotation(0);
        }
    }
}
