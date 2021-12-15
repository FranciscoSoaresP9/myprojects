package org.academiadecodigo.loopeytunes.EpicGame;

import org.academiadecodigo.simplegraphics.graphics.Color;
import org.academiadecodigo.simplegraphics.graphics.Text;
import org.academiadecodigo.simplegraphics.pictures.Picture;

public class Field {
    private final Picture field = new Picture(10, 10, "src/org/academiadecodigo/loopeytunes/EpicGame/resources/Background.jpeg");
    private final Picture start = new Picture(493, 275, "src/org/academiadecodigo/loopeytunes/EpicGame/resources/Start-button.png");
    private final Picture gameOverScreen = new Picture(10, 10, "src/org/academiadecodigo/loopeytunes/EpicGame/resources/GameOver.png");
    private final Picture tazWins = new Picture(550, 420, "src/org/academiadecodigo/loopeytunes/EpicGame/resources/TazWins.png");
    private final Picture coyoteWins = new Picture(550, 270, "src/org/academiadecodigo/loopeytunes/EpicGame/resources/CoyoteWins.png");
    private final Picture tazLoses = new Picture(220, 350, "src/org/academiadecodigo/loopeytunes/EpicGame/resources/TazLoses.png");
    private final Picture coyoteLoses = new Picture(120, 250, "src/org/academiadecodigo/loopeytunes/EpicGame/resources/CoyoteLoses.png");
    private final Picture tazTie = new Picture(200, 380, "src/org/academiadecodigo/loopeytunes/EpicGame/resources/TazTie.png");
    private final Picture coyoteTie = new Picture(820, 160, "src/org/academiadecodigo/loopeytunes/EpicGame/resources/CoyoteTie.png");
    private final Picture menu = new Picture(10, 10, "src/org/academiadecodigo/loopeytunes/EpicGame/resources/Menu.png");
    public static Picture Boom = new Picture(370, 200, "src/org/academiadecodigo/loopeytunes/EpicGame/resources/boom.png");

    public void generateField() {
        field.draw();
    }

    public void generateStart() {
        menu.draw();
    }

    public void hideRules() {
        menu.delete();
    }

    public void playerOneVictory() {
        gameOverScreen.draw();
        tazWins.grow(40, 60);
        tazWins.draw();
        coyoteLoses.grow(-50, -80);
        coyoteLoses.draw();
        Text shadow = new Text(643, 292, "TAZ WINS!!");
        Text winner = new Text(640, 290, "TAZ WINS!!");
        drawText(winner, shadow, 280, 60, Color.YELLOW);


    }

    public void playerTwoVictory() {
        gameOverScreen.draw();
        coyoteWins.grow(-20, -60);
        coyoteWins.draw();
        tazLoses.draw();
        Text shadow = new Text(643, 292, "COYOTE WINS!!");
        Text winner = new Text(640, 290, "COYOTE WINS!!");
        drawText(winner, shadow, 280, 60, Color.YELLOW);

    }

    public void tie() {
        gameOverScreen.draw();
        tazTie.draw();
        coyoteTie.grow(-60, -140);
        coyoteTie.draw();
        Text shadow = new Text(643, 292, "IT'S A TIE!!");
        Text winner = new Text(640, 290, "IT'S A TIE!!");
        drawText(winner, shadow, 280, 60, Color.YELLOW);

    }

    public void drawText(Text text, Text textShadow, int growCol, int growRow, Color color) {
        textShadow.setColor(Color.BLACK);
        text.setColor(color);
        textShadow.grow(growCol, growRow);
        text.grow(growCol, growRow);
        textShadow.draw();
        text.draw();

    }

    public void hideStart() {
        start.delete();
    }

}