import org.academiadecodigo.bootcamp.scanners.menu.MenuInputScanner;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class Table implements Runnable {

    private HashMap<String, Integer> deck;
    private int tableScore = 0;
    private final int blackJack = 21;
    private final int tableMinBet = 16;
    private CopyOnWriteArrayList<ClientConnection> clientList;

    public Table() {
        this.clientList = new CopyOnWriteArrayList<>();
    }

    @Override
    public void run() {

        while (clientList.size() != 0) {

            try {
                Thread.sleep(5000);

                synchronized (clientList) {

                    welcomeMenu();
                    removePlayers();
                    setPlayerState();
                    gameRound();

                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void welcomeMenu() {

        String roundStart ="          _____\n" +
                "         |A .  | _____\n" +
                "         | /.\\ ||A ^  | _____\n" +
                "         |(_._)|| / \\ ||A _  | _____\n" +
                "         |  |  || \\ / || ( ) ||A_ _ |\n" +
                "         |____V||  .  ||(_'_)||( v )|\n" +
                "                |____V||  |  || \\ / |\n" +
                "                       |____V||  .  |\n" +
                "                              |____V|\n" +
                "               Let's Play Again        ";

        for (ClientConnection clientConnection : clientList) {

            clientConnection.getOut().println(roundStart);
            clientConnection.getOut().flush();
            int answer = lobbyMenu(clientConnection);

            if (answer == 2) {

                clientConnection.getOut().println("See you later Alligator :)");
                clientConnection.getOut().flush();

                try {
                    clientConnection.getClientSocket().close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            clientConnection.getOut().println("Game started");
            clientConnection.getOut().flush();
        }
    }

    public void removePlayers() {

        for (ClientConnection clientConnection : clientList) {
            if (clientConnection.getClientSocket().isClosed()) {
                clientList.remove(clientConnection);
                if (clientList.size() == 0) {
                    return;
                }
            }
        }
    }

    public int lobbyMenu(ClientConnection clientConnection) {

        String[] pChoice = {"Throw Cards", "Quit"};
        MenuInputScanner number = new MenuInputScanner(pChoice);
        number.setMessage("Ready to get some thrills?");
        int answer = clientConnection.getPrompt().getUserInput(number);
        return answer;
    }


    public void gameRound() {

        createDeck();
        giveCards();

        for (ClientConnection clientConnection : clientList) {
            if (clientConnection.win() || clientConnection.lost()) {
                continue;
            }

            while (clientConnection.isBetting()) {
                playerChoice(clientConnection);
            }
        }

        while (tableScore < tableMinBet) {
            tableHand();
        }

        for (ClientConnection clientConnection : clientList) {
            checkResults(clientConnection);
        }
        return;
    }

    public void checkResults(ClientConnection clientConnection) {

        if (clientConnection.lost() || clientConnection.win()) {
            return;
        }

        if (tableScore > blackJack) {
            statusMessage("You Win", clientConnection);
            return;
        }

        if (clientConnection.getScore() > tableScore) {
            statusMessage("You Win", clientConnection);
            return;
        }

        if (clientConnection.getScore() == tableScore) {
            statusMessage("Draw", clientConnection);
            return;
        }

        statusMessage("You Lost", clientConnection);
    }

    public void setPlayerState() {

        for (ClientConnection clientConnection : clientList) {
            clientConnection.resetScore();
            clientConnection.setBetting(true);
            clientConnection.setWin(false);
            clientConnection.setLose(false);
        }
        tableScore = 0;
    }

    public void createSuit(String suits) {
        for (int i = 2; i <= 10; i++) {
            deck.put(i + suits, i);
        }

        deck.put("Ace" + suits, 11);
        deck.put("Jack" + suits, 10);
        deck.put("Queen" + suits, 10);
        deck.put("King" + suits, 10);
    }

    public void createDeck() {

        deck = new HashMap<>();
        createSuit(" of Spades");
        createSuit(" of Hearts");
        createSuit(" of Clubs");
        createSuit(" of Diamonds");
    }

    public void giveCards() {

        try {
            tableHand();

            for (ClientConnection clientConnection : clientList) {
                clientConnection.getOut().println("Players 1st card");
                dealCardsToPlayer(clientConnection);
                Thread.sleep(500);

                clientConnection.getOut().println("Players 2nd card");
                dealCardsToPlayer(clientConnection);
                checkPlayerHand(clientConnection);
                Thread.sleep(1000);

            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void dealCardsToPlayer(ClientConnection clientConnection) {

        String receivedCard = getCard();
        clientConnection.getOut().println("Card: " + receivedCard);
        clientConnection.setScore(deck.get(receivedCard));
        getAceValuePlayer(receivedCard, clientConnection);
        clientConnection.getOut().println("Score " + clientConnection.getScore() + "\n");
        clientConnection.getOut().flush();
        deck.remove(receivedCard);
    }

    public void tableHand() {

        String receivedCard = getCard();
        tableScore += deck.get(receivedCard);
        getAceValueTable(receivedCard);
        deck.remove(receivedCard);

        for (ClientConnection clientConnection : clientList) {
            clientConnection.getOut().println("Table Card: " + receivedCard + "\n");
            clientConnection.getOut().flush();
        }
    }

    public String getCard() {

        int counter = 1;
        String receivedCard = "";
        int getRandomCard = (int) Math.ceil(Math.random() * deck.size());

        for (String card : deck.keySet()) {
            if (counter == getRandomCard) {
                receivedCard = card;
                break;
            }
            counter++;
        }
        return receivedCard;
    }

    public void getAceValuePlayer(String receivedCard, ClientConnection clientConnection) {
        if (clientConnection.getScore() > blackJack && receivedCard.contains("Ace")) {
            clientConnection.setScore(-10);
        }
    }

    public void getAceValueTable(String receivedCard) {
        if (tableScore > blackJack && receivedCard.contains("Ace")) {
            tableScore -= 10;
        }
    }

    public void playerChoice(ClientConnection clientConnection) {

        String[] pChoice = {"Hit", "Pass"};
        MenuInputScanner number = new MenuInputScanner(pChoice);
        number.setMessage("Choose your fate");
        int answer = clientConnection.getPrompt().getUserInput(number);
        playRound(answer, clientConnection);
    }

    public void playRound(int answer, ClientConnection clientConnection) {//Colocar a variavel no topo e não colocar os metudos a retornar

        if (answer == 1) {
            dealCardsToPlayer(clientConnection);
            checkPlayerHand(clientConnection);
        }

        if (answer == 2) {
            checkPlayerHand(clientConnection);
            clientConnection.setBetting(false);
        }

    }

    public void checkPlayerHand(ClientConnection clientConnection) {

        if (clientConnection.getScore() > blackJack) {
            statusMessage("You Lost", clientConnection);
            clientConnection.setBetting(false);
            clientConnection.setLose(true);
            return;
        }

        if (clientConnection.getScore() == blackJack) {
            statusMessage("BlackJack", clientConnection);
            clientConnection.setWin(true);
            clientConnection.setBetting(false);
        }
    }

    public void statusMessage(String status, ClientConnection clientConnection) {

        clientConnection.getOut().println("------------------");
        clientConnection.getOut().println("|" + status + "        |");
        clientConnection.getOut().println("|Player Score: " + clientConnection.getScore() + "|");
        clientConnection.getOut().println("|Table Score: " + tableScore + "  |");
        clientConnection.getOut().println("------------------");
        clientConnection.getOut().flush();
    }

    public void addPlayerToTable(Socket playerSocket) {
        clientList.add(new ClientConnection(playerSocket));

    }

    public CopyOnWriteArrayList<ClientConnection> getClientList() {
        return clientList;
    }
}