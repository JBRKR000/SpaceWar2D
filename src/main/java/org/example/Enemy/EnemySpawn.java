package org.example.Enemy;

import java.util.Random;

public class EnemySpawn extends EnemyEntity {
    public static int randomSpeed() {
        Random random = new Random();
        return random.nextInt(200) + 50;
    }

}
