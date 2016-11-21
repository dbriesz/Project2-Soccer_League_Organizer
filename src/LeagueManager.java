import com.teamtreehouse.model.Player;
import com.teamtreehouse.model.Players;
import com.teamtreehouse.model.Prompter;
import com.teamtreehouse.model.Team;

import java.io.IOException;

public class LeagueManager {

  public static void main(String[] args) throws IOException {
    Player[] players = Players.load();
    System.out.printf("There are currently %d registered players.%n", players.length);
    // Your code here!
    Team team = new Team();
    Prompter prompter = new Prompter(team);
    try {
        prompter.createTeam();
    } catch (IOException e) {
      e.printStackTrace();
    }


  }

}
