package org.academiadecodigo.loopeytunes.EpicGame.GameObjects;

import org.academiadecodigo.loopeytunes.EpicGame.Field;
import org.academiadecodigo.loopeytunes.EpicGame.Game;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardEvent;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardHandler;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class Character extends GameObjects implements KeyboardHandler {

    private boolean reachedForFood;
    private int lastPicCounter;

    public Character() {
        super(0);
    }

    public void getsAlive(String[] animation) {
        int size = animation.length;
        int r = lastPicCounter % size;
        if (r < size-1) {
            changePic(col, row, animation[r + 1]);
            lastPicCounter++;
            return;
        }
        changePic(col,row,animation[0]);
        lastPicCounter++;
    }


    public void eat(int score, int col, int row, String path) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        changePic(col, row, path);

        switch (Game.getFoodItem().getType()) {
            case BOMB:
                Field.Boom.draw();
                Game.soundEffects(Game.bombSound);
                if (this.getScore() > 0) {
                    this.score = 0;
                }
                break;

            default:
                this.score += score;
        }
        Game.getFoodItem().getsEaten();


    }

    public boolean hasReachedForFood() {
        return reachedForFood;
    }


    @Override
    public void keyPressed(KeyboardEvent keyboardEvent) {
        if (keyboardEvent.getKey() == KeyboardEvent.KEY_A) {
            reachedForFood = true;
            try {
                Game.soundEffects(Game.eatSound);
            } catch (LineUnavailableException e) {
                e.printStackTrace();
            } catch (UnsupportedAudioFileException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (!Game.getFoodItem().wasEaten() && Game.isGameOn()) {
                try {
                    eat(Game.getFoodItem().score, 230, 350, "src/org/academiadecodigo/loopeytunes/EpicGame/resources/Taz1.png");
                } catch (UnsupportedAudioFileException e) {
                    e.printStackTrace();
                } catch (LineUnavailableException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        if (keyboardEvent.getKey() == KeyboardEvent.KEY_K) {
            reachedForFood = true;
            try {
                Game.soundEffects(Game.eatSound);
            } catch (LineUnavailableException e) {
                e.printStackTrace();
            } catch (UnsupportedAudioFileException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (!Game.getFoodItem().wasEaten() && Game.isGameOn()) {
                try {
                    eat(Game.getFoodItem().score, 680, 350, "src/org/academiadecodigo/loopeytunes/EpicGame/resources/Coyote1.png");
                } catch (UnsupportedAudioFileException e) {
                    e.printStackTrace();
                } catch (LineUnavailableException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    @Override
    public void keyReleased(KeyboardEvent keyboardEvent) {
        if (keyboardEvent.getKey() == KeyboardEvent.KEY_A) {
            Timer timer = new Timer();
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    if (Game.isGameOn()) {
                        changePic(230, 350, "src/org/academiadecodigo/loopeytunes/EpicGame/resources/Taz2.2.png");
                    }
                }
            };
            timer.schedule(task, 300);
            reachedForFood = false;

        }

        if (keyboardEvent.getKey() == KeyboardEvent.KEY_K) {
            Timer timer = new Timer();
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    if (Game.isGameOn()) {
                        changePic(870, 350, "src/org/academiadecodigo/loopeytunes/EpicGame/resources/Coyote2.png");

                    }
                }
            };
            timer.schedule(task, 300);
            reachedForFood = false;

        }
    }
}
