package org.academiadecodigo.loopeytunes.EpicGame;

import org.academiadecodigo.simplegraphics.keyboard.Keyboard;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardEvent;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardEventType;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardHandler;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public class Menu implements KeyboardHandler {
    private final Field field = new Field();
    private final Game game = new Game(field);


    public Menu() throws InterruptedException, UnsupportedAudioFileException, IOException, LineUnavailableException {
        game.music();
        field.generateField();
        field.generateStart();
        commandsOn();
        game.start();
    }

    public void commandsOn() {
        Keyboard kbStart = new Keyboard(this);
        KeyboardEvent spacePress = new KeyboardEvent();
        spacePress.setKey(KeyboardEvent.KEY_SPACE);
        spacePress.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
        kbStart.addEventListener(spacePress);


    }

    @Override
    public void keyPressed(KeyboardEvent keyboardEvent) {

        if (keyboardEvent.getKey() == KeyboardEvent.KEY_SPACE) {

            game.gameOn();
        }
    }

    @Override
    public void keyReleased(KeyboardEvent keyboardEvent) {

    }
}
