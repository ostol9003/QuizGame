package Server;

import Model.Player;

import java.io.*;
import java.net.Socket;

import static Model.Player.*;

public class ClientSession extends Thread {


    private final Socket socket;

    public ClientSession(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        String msg;
        try {
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while ((msg = in.readLine()) != null) {
                //System.out.println(msg); // printing message from client

                if ("top5".equals(msg)) {       // client asking about top 5 players
                    synchronized (players) {       // synchronized becouse 2 players can try to use at the same time
                        readPlayers();
                        sortPlayers();

                        StringBuilder sb = new StringBuilder(); // creating s
                        for (int i = 0; i < 5; i++) {
                            if (i < players.size()) {
                                sb.append(players.get(i).getName() + "#" + players.get(i).getScore());
                                sb.append("@");
                            } else {
                                sb.append("---------");
                                sb.append("@");
                            }
                        }
                        // System.out.println(sb.toString());       //printing string (top 5 list in format "player1#score@player2#score@..."
                        out.println(sb);
                        players.clear();  // this little thing caused a lot of problems, we forgot to clear the list and it loaded players from the file so many times, how many times the player pressed top5
                    }
                } else if (msg.contains("#")) {     // client sending player name with score in format "name#score"
                    String[] lines = msg.split("#");
                    Player player = new Player(lines[0], Integer.parseInt(lines[1]));
                    addToHistory(player);
                } else out.println("Invalid request");
            }


        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public void addToHistory(Player player) {       // append to file
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter("src/Server/playersHistory.csv", true));
            out.write(player.getName() + ";" + player.getScore());
            out.newLine();
            out.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public void readPlayers() {      // reading players from file
        try (BufferedReader in = new BufferedReader(new FileReader("src/Server/playersHistory.csv"))) {
            String playerStr;
            String[] playerData;
            while ((playerStr = in.readLine()) != null) {
                playerData = playerStr.split(";");
                Player player = new Player(playerData[0], Integer.parseInt(playerData[1]));
                addPlayer(player);
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }

}

