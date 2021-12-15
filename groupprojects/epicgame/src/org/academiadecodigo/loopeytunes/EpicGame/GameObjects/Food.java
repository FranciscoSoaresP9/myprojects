package org.academiadecodigo.loopeytunes.EpicGame.GameObjects;

public class Food extends GameObjects {
    private boolean eaten;
    private FoodType type;

    public Food(int score, String path, FoodType type) {
        super(score);
        changePic(550, 300, path);
        this.type = type;
        body.grow(-50, -50);
        eaten = false;
    }

    public void getsEaten() {
        this.eaten = true;
        body.delete();
    }

    public boolean wasEaten() {
        return eaten;
    }

    public FoodType getType() {
        return type;
    }
}
