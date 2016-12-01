import com.teamtreehouse.model.Player;
import com.teamtreehouse.model.Players;
import com.teamtreehouse.model.Prompter;

import java.io.IOException;

public class LeagueManager {

    public static void main(String[] args) throws IOException {

        Player[] players = Players.load();
        Prompter prompter = new Prompter(Players.load());
        System.out.printf("There are currently %d registered players.%n", players.length);
        prompter.run();
    }
}
