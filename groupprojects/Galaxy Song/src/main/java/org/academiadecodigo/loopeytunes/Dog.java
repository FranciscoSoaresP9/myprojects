package org.academiadecodigo.loopeytunes;

public class Dog {
    private int height;
    private int energy;
    private String purpose;
    private int lifespan;

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public void setLifespan(int lifespan) {
        this.lifespan = lifespan;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public int getEnergy() {
        return energy;
    }

    public int getLifespan() {
        return lifespan;
    }

    public int getHeight() {
        return height;
    }

    public String getPurpose() {
        return purpose;
    }
}
