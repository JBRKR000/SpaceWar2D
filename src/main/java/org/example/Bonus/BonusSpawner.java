package org.example.Bonus;

import java.util.Objects;

public class BonusSpawner {
    public static int bonusSpawner(String enemyType){
        int howMany = 0;
        switch (enemyType) {
            case "eclipse":
                howMany = 17;
                break;
            case "striker":
                howMany = 9;
                break;
            case "inferno":
                howMany = 21;
                break;
            case "void":
                howMany = 11;
                break;
            default:
                howMany = 10;
                break;
        }

        return howMany;
    }
}
