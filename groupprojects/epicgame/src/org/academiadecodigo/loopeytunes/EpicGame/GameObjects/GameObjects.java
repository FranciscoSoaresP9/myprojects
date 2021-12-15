package org.academiadecodigo.loopeytunes.EpicGame.GameObjects;

import org.academiadecodigo.simplegraphics.pictures.Picture;

public class GameObjects {
    protected int score;
    protected Picture body;
    protected int col;
    protected int row;

    public GameObjects(int score) {
        this.score = score;
    }

    public void changePic(int col, int row, String src) {
        if (body != null) {
            body.delete();
        }
        this.col = col;
        this.row = row;
        body = new Picture(col, row, src);
        body.draw();
    }
public void bodyDelete(){
        body.delete();
}
    public Picture getPicture() {
        return body;
    }

    public int getScore() {
        return score;
    }
}
