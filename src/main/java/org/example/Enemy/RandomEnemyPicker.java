package org.example.Enemy;

import com.almasb.fxgl.entity.Entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.example.Init.InitSettings.enemiesToDestroy;
import static org.example.Init.InitSettings.wave;

public class RandomEnemyPicker {
    public static String picker(){
        String pick = null;
        Random random = new Random();

        if(wave <= 3){
            enemiesToDestroy = 8;
            List<String> enemyList1to3 = new ArrayList<>();
            enemyList1to3.add("inferno");
            enemyList1to3.add("striker");
            pick =  enemyList1to3.get(random.nextInt(enemyList1to3.size()));
        }
        if(wave > 3 && wave <= 6){
            enemiesToDestroy = 6;
            List<String> enemyList4to6 = new ArrayList<>();
            enemyList4to6.add("eclipse");
            enemyList4to6.add("void");
            pick = enemyList4to6.get(random.nextInt(enemyList4to6.size()));
        }
        if(wave > 6 && wave < 10){
            enemiesToDestroy = 7;
            List<String> enemyList4to6 = new ArrayList<>();
            enemyList4to6.add("eclipse");
            enemyList4to6.add("void");
            enemyList4to6.add("inferno");
            enemyList4to6.add("striker");
            pick = enemyList4to6.get(random.nextInt(enemyList4to6.size()));
        }
        if(wave == 10){
            enemiesToDestroy = 1;
            List<String> enemyList = new ArrayList<>();
            enemyList.add("boss_1");
            pick = enemyList.get(random.nextInt(enemyList.size()));
        }
        return pick;


    }
}
