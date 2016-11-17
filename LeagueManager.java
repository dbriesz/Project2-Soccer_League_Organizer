import com.teamtreehouse.model.Player;
import com.teamtreehouse.model.Players;

public class LeagueManager {

  public static void main(String[] args) {
    Player[] players = Players.load();
    System.out.printf("There are currently %d registered players.%n", players.length);
    // Your code here!
  }

}
