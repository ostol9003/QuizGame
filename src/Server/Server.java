package Server;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static final int serverPort = 3000; // if You change port, remember to change also in Helper.ConnectToServer()
    ServerSocket serverSocket;

    public Server() {
        try {
            serverSocket = new ServerSocket(serverPort);
            System.out.println("Server is working");
        } catch (IOException e) {
            System.out.println("Unable to create server socket");
            System.out.println(e);
            System.exit(1);
        }
    }

    public static void main(String[] args) throws IOException {
        Server server = new Server();
        server.run();
        server.serverSocket.close();
    }

    void run() throws IOException {

        while (true) {
            Socket socket = serverSocket.accept();
            new ClientSession(socket).start();
        }

    }

}
