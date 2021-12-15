import org.academiadecodigo.bootcamp.Prompt;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientConnection {

    private Socket clientSocket;
    private int score = 0;
    private PrintWriter out;
    private Prompt prompt;
    private boolean isBetting = true;
    private boolean win = false;
    private boolean lose = false;

    public ClientConnection(Socket clientSocket) {

        this.clientSocket = clientSocket;
        try {
            out = new PrintWriter(clientSocket.getOutputStream());
            out.println();

            prompt = new Prompt(clientSocket.getInputStream(), new PrintStream(clientSocket.getOutputStream(), true));
        } catch (IOException e) {
            Server.LOGGER.info("ERROR WITH CONNECTION");
        }
    }

    public Prompt getPrompt() {
        return prompt;
    }

    public PrintWriter getOut() {
        return out;
    }

    public Socket getClientSocket() {
        return clientSocket;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int value) {

        this.score = score + value;
    }

    public void setBetting(boolean isBetting) {
        this.isBetting = isBetting;
    }

    public boolean win() {
        return win;
    }

    public void setWin(boolean win){
        this.win=win;
    }

    public boolean lost() {
        return lose;
    }

    public void setLose(boolean lose) {
        this.lose = lose;
    }

    public boolean isBetting() {
        return isBetting;
    }

    public void resetScore() {
        score = 0;
    }
}
