import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

public class Server {

    private Table table;
    private Socket socket;
    private ServerSocket serverSocket;
    private final ExecutorService tablesThreads = Executors.newFixedThreadPool(6);
    private final ExecutorService sortUsers = Executors.newFixedThreadPool(1);
    public static final Logger LOGGER = Logger.getLogger(Server.class.getName());

    public Server() {

        try {
            this.table = new Table();
            serverSocket = new ServerSocket(9002);
        } catch (IOException e) {
            LOGGER.info("Server couldn't connect");
        }
    }

    public static void main(String[] args) {

        Server server = new Server();
        server.listening();
    }

    public void listening() {

        while (true) {
            LOGGER.info("Waiting for connections...");
            try {
                socket = serverSocket.accept();
                sortUsers.submit(new StartTable());

            } catch (IOException e) {
                LOGGER.info("Client connection failed!");
            }
        }
    }

    private class StartTable implements Runnable {

        @Override
        public void run() {

            synchronized (table.getClientList()) {

                if (table.getClientList().size() == 0) {
                    table.addPlayerToTable(socket);
                    System.out.println("Accepted new table");
                    tablesThreads.submit(table);

                } else {
                    table.addPlayerToTable(socket);
                    System.out.println("Added player to table");
                }
            }
        }
    }
}