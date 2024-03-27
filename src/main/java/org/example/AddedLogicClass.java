package org.example;

import java.util.Random;

public class AddedLogicClass {
    public static int randomSpawnVector(){ //GENERATES -1 OR 1
        Random random = new Random();
        int randomPoint = random.nextInt(3) - 1;
        if(randomPoint==0){
            randomPoint = 1;
        }
        return randomPoint;
    }



}
