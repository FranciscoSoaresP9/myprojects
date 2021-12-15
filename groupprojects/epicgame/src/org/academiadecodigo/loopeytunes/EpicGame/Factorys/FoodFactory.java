package org.academiadecodigo.loopeytunes.EpicGame.Factorys;

import org.academiadecodigo.loopeytunes.EpicGame.GameObjects.Food;
import org.academiadecodigo.loopeytunes.EpicGame.GameObjects.FoodType;


public class FoodFactory {

    public static Food makeFood() {
        switch (FoodType.chooseType()) {
            case GOOD:
                return new Food(20, "src/org/academiadecodigo/loopeytunes/EpicGame/resources/Avocado.png", FoodType.GOOD);
            case BAD:
                return new Food(-10, "src/org/academiadecodigo/loopeytunes/EpicGame/resources/poop.png", FoodType.BAD);
            case BOMB:
                return new Food(0, "src/org/academiadecodigo/loopeytunes/EpicGame/resources/bomb.png", FoodType.BOMB);
            default:
                return new Food(60, "src/org/academiadecodigo/loopeytunes/EpicGame/resources/SuperJuice.png", FoodType.SPECIAL);//most multiply to number of clicks
        }


    }
}