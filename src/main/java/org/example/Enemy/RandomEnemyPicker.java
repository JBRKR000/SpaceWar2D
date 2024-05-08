package org.example.Enemy;

import com.almasb.fxgl.entity.Entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomEnemyPicker {
    public static String picker(){
        Random random = new Random();
        List<String> enemyList = new ArrayList<String>();
        enemyList.add("void");
        enemyList.add("inferno");
        enemyList.add("striker");
        enemyList.add("eclipse");
        return enemyList.get(random.nextInt(enemyList.size()));
    }
}
