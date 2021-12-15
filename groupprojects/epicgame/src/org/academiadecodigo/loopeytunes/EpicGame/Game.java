package org.academiadecodigo.loopeytunes.EpicGame;

import org.academiadecodigo.loopeytunes.EpicGame.Factorys.FoodFactory;
import org.academiadecodigo.loopeytunes.EpicGame.GameObjects.Character;
import org.academiadecodigo.loopeytunes.EpicGame.GameObjects.Food;
import org.academiadecodigo.loopeytunes.EpicGame.GameObjects.FoodType;
import org.academiadecodigo.simplegraphics.graphics.Color;
import org.academiadecodigo.simplegraphics.graphics.Text;
import org.academiadecodigo.simplegraphics.keyboard.Keyboard;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardEvent;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardEventType;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardHandler;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;


public class Game {
    private static final int MAXDELAY = 2000;
    private static final int MINDELAY = 1000;
    private static final int FOOD_TOTAL = 10;
    private static final String musicPath = "src/org/academiadecodigo/loopeytunes/EpicGame/resources/LooneyMusic.wav";
    private static final String winMusicPath = "src/org/academiadecodigo/loopeytunes/EpicGame/resources/WinSound.wav";
    public static final String eatSound = "src/org/academiadecodigo/loopeytunes/EpicGame/resources/EatSound.wav";
    public static final String bombSound = "src/org/academiadecodigo/loopeytunes/EpicGame/resources/bombSound.wav";
    private final Field field;
    private static boolean gameOn = false;
    private static Food foodItem = new Food(0, "src/org/academiadecodigo/loopeytunes/EpicGame/resources/Transparent.png", FoodType.GOOD);
    private static Character taz;
    private static Character coyote;
    private static String[] tazAnimation = new String[]{"src/org/academiadecodigo/loopeytunes/EpicGame/resources/Taz2.1.png", "src/org/academiadecodigo/loopeytunes/EpicGame/resources/Taz2.2.png", "src/org/academiadecodigo/loopeytunes/EpicGame/resources/Taz2.3.png", "src/org/academiadecodigo/loopeytunes/EpicGame/resources/Taz2.2.png"};
    private static String[] coyoteAnimation = new String[]{"src/org/academiadecodigo/loopeytunes/EpicGame/resources/coyote2.png", "src/org/academiadecodigo/loopeytunes/EpicGame/resources/coyote2.2.png"};
    private Text scorePlayer1;
    private Text scorePlayer1Shadow;
    private Text scorePlayer2;
    private Text scorePlayer2Shadow;
    private Clip clip;

    public Game(Field field) {
        this.field = field;
    }

    public void start() throws InterruptedException, UnsupportedAudioFileException, LineUnavailableException, IOException {
        playersCreation();

        while (!gameOn) {
            Thread.sleep(MINDELAY);
        }
        field.hideRules();
        this.field.hideStart();
        startEngine();

    }

    public void startEngine() throws InterruptedException, UnsupportedAudioFileException, LineUnavailableException, IOException {
        commandsOn();
        scoreAppear();

        for (int i = 0; i < FOOD_TOTAL; i++) {
            playersAppearance();
            scoreUpdate();

            animation();

            foodItem = FoodFactory.makeFood();

            Thread.sleep(MAXDELAY / 2);
            animation();
            foodItem.getPicture().delete();
            if (Field.Boom != null) {
                Field.Boom.delete();
            }
        }
        gameOn = false;
        gameOver();
    }

    public void animation() throws InterruptedException {
        int counter = 0;
        while (!(taz.hasReachedForFood() || coyote.hasReachedForFood()) && counter < 5) {
            taz.getsAlive(tazAnimation);
            coyote.getsAlive(coyoteAnimation);
            Thread.sleep(200);
            counter++;

        }
    }


    public void playersCreation() {
        taz = new Character();
        coyote = new Character();
    }

    public void playersAppearance() {
        taz.changePic(230, 350, "src/org/academiadecodigo/loopeytunes/EpicGame/resources/Taz2.2.png");
        coyote.changePic(870, 350, "src/org/academiadecodigo/loopeytunes/EpicGame/resources/coyote2.png");
    }

    public void commandsOn() {
        Keyboard kbPlayer1 = new Keyboard(taz);
        KeyboardEvent aPressed = new KeyboardEvent();
        KeyboardEvent aDpressed = new KeyboardEvent();
        setCommands(aPressed, KeyboardEvent.KEY_A, KeyboardEventType.KEY_PRESSED);
        setCommands(aDpressed, KeyboardEvent.KEY_A, KeyboardEventType.KEY_RELEASED);

        Keyboard kbPlayer2 = new Keyboard(coyote);
        KeyboardEvent kPressed = new KeyboardEvent();
        KeyboardEvent kDpressed = new KeyboardEvent();
        setCommands(kPressed, KeyboardEvent.KEY_K, KeyboardEventType.KEY_PRESSED);
        setCommands(kDpressed, KeyboardEvent.KEY_K, KeyboardEventType.KEY_RELEASED);

        kbPlayer1.addEventListener(aPressed);
        kbPlayer1.addEventListener(aDpressed);
        kbPlayer2.addEventListener(kPressed);
        kbPlayer2.addEventListener(kDpressed);

    }

    public void setCommands(KeyboardEvent keyboardEventName, int keyboardEvent, KeyboardEventType keyboardEventType) {
        keyboardEventName.setKey(keyboardEvent);
        keyboardEventName.setKeyboardEventType(keyboardEventType);

    }

    public void scoreAppear() {
        scorePlayer1Shadow = new Text(93, 51, "Score: " + taz.getScore());
        scorePlayer1 = new Text(90, 50, "Score: " + taz.getScore());
        scorePlayer2Shadow = new Text(1153, 51, "Score: " + coyote.getScore());
        scorePlayer2 = new Text(1150, 50, "Score: " + coyote.getScore());

        field.drawText(scorePlayer1, scorePlayer1Shadow, 40, 15, Color.YELLOW);
        field.drawText(scorePlayer2, scorePlayer2Shadow, 40, 15, Color.YELLOW);

    }

    public void scoreUpdate() {
        scorePlayer1.setText("Score: " + taz.getScore());
        scorePlayer1Shadow.setText("Score: " + taz.getScore());
        scorePlayer2.setText("Score: " + coyote.getScore());
        scorePlayer2Shadow.setText("Score: " + coyote.getScore());
    }

    private void gameOver() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        music(winMusicPath);
        if (taz.getScore() > coyote.getScore()) {
            field.playerOneVictory();
            return;
        }

        if (taz.getScore() == coyote.getScore()) {
            field.tie();
            return;
        }

        field.playerTwoVictory();
    }


    public static Food getFoodItem() {
        return foodItem;
    }

    public void gameOn() {
        gameOn = true;
    }

    public static boolean isGameOn() {
        return gameOn;
    }


    public void music() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        File music = new File(musicPath);
        AudioInputStream audio = AudioSystem.getAudioInputStream(music);
        clip = AudioSystem.getClip();
        clip.open(audio);
        clip.start();
    }

    public static void soundEffects(String soundPath) throws LineUnavailableException, UnsupportedAudioFileException, IOException {
        File music = new File(soundPath);
        AudioInputStream audio = AudioSystem.getAudioInputStream(music);
        Clip clip = AudioSystem.getClip();
        clip.open(audio);
        clip.start();
        Timer timer = new Timer();
        TimerTask tas = new TimerTask() {
            @Override
            public void run() {
                clip.stop();
            }
        };
    }

    public void music(String musicPath) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        clip.stop();
        File music = new File(musicPath);
        AudioInputStream audio = AudioSystem.getAudioInputStream(music);
        Clip clip = AudioSystem.getClip();
        clip.open(audio);
        clip.start();
    }

}
