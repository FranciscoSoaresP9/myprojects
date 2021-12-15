package org.academiadecodigo.loopeytunes.EpicGame.GameObjects;

public enum FoodType {
    GOOD,
    BOMB,//lost all points
    SPECIAL,//click to win
    BAD;

    public static FoodType chooseType() {
      double odd = Math.random();
        if (odd<0.5){
            return GOOD;
        }
        if(odd>=0.5 &&odd<0.7){
            return BAD;
        }
        if(odd>=0.7&& odd<0.85){
            return BOMB;
        }
        return SPECIAL;
    }
}
