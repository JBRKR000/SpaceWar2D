package org.example.Enemy;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.example.Init.InitSettings.enemiesToDestroy;
import static org.example.Init.InitSettings.wave;

public class RandomEnemyPicker {
    public static String picker() {
        String pick = null;
        Random random = new Random();


        List<String> level1Enemies = List.of("striker", "inferno");
        List<String> level2Enemies = List.of("core", "eclipse", "void");
        List<String> level3Enemies = List.of("beta", "faker");
        List<String> level5Enemies = List.of("bull", "fighter");

        if (wave <= 3) {
            enemiesToDestroy = 8;
            List<String> enemyList1to3 = new ArrayList<>();
            enemyList1to3.addAll(level1Enemies);
            enemyList1to3.addAll(level2Enemies);
            pick = enemyList1to3.get(random.nextInt(enemyList1to3.size()));
        } else if (wave > 3 && wave <= 6) {
            enemiesToDestroy = 6;
            List<String> enemyList4to6 = new ArrayList<>();
            enemyList4to6.addAll(level2Enemies);
            enemyList4to6.addAll(level3Enemies);
            pick = enemyList4to6.get(random.nextInt(enemyList4to6.size()));
        } else if (wave > 6 && wave < 10) {
            enemiesToDestroy = 7;
            List<String> enemyList7to9 = new ArrayList<>();
            enemyList7to9.addAll(level2Enemies);
            enemyList7to9.addAll(level3Enemies);
            enemyList7to9.addAll(level5Enemies);
            pick = enemyList7to9.get(random.nextInt(enemyList7to9.size()));
        } else if (wave == 10) {
            enemiesToDestroy = 1;
            pick = "boss_1";
        }

        return pick;
    }
}