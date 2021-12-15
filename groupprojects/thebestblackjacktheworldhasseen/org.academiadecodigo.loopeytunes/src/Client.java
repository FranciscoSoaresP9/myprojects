import java.io.*;
import java.net.Socket;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Client {

    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    public static void main(String[] args) {
        try {
            String host = args[0];
            int port = 9002;
            Client client = new Client(host, port);
            client.start();
        }catch (ArrayIndexOutOfBoundsException e){
            Server.LOGGER.info("PROTOCOL: filename.jar <IP ADRESS>");
        }
        Server.LOGGER.info("CONNECTING...");
    }

    public Client(String host, int port) {

        try {
            socket = new Socket(host, port);
            out = new PrintWriter(socket.getOutputStream());
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            Server.LOGGER.info("Error getting Server port/IP");
        }
    }

    public void start() {

        while (this.socket.isBound()) {
            receive();
        }
        exit();
    }

    public void exit() {

        try {
            this.out.close();
            this.socket.close();
        } catch (IOException e) {
            Server.LOGGER.info("Error while disconnecting, your progress will be saved");
        }
        System.exit(0);
    }

    public void send() {

        int n = 0;
        Scanner scanner = new Scanner(System.in);

        try {
            n = scanner.nextInt();
            out.println(n);
            out.flush();
        } catch (InputMismatchException e) {
            System.out.println("Invalid Input");
            send();
        }
    }

    public void receive() {

        try {
            String message = in.readLine();

            if (message.contains("Alligator")) {
                exit();
            }

            if (message.contains("Pass") || message.contains("Quit")) {
                System.out.println(message);
                send();

            } else {
                System.out.println(message);
            }
        } catch (IOException e) {
            System.out.println("The server disconnected");

            try {
                in.close();
                out.close();
                socket.close();
                System.exit(0);
            } catch (IOException ioException) {
                Server.LOGGER.info("Error while disconnecting, your progress will be saved");
            }
        }
    }
}
