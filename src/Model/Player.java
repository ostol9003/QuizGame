package Model;

import java.util.ArrayList;
import java.util.Comparator;

public class Player {
    public static ArrayList<Player> players = new ArrayList<>();
    private final String name;
    private final int score;


    public Player(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public static void addPlayer(Player player) {
        players.add(player);
    }

    public static void sortPlayers() {
        players.sort(Comparator.comparingInt(Player::getScore).reversed());
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }
}
