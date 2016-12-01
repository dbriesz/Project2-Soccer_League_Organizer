package com.teamtreehouse.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Prompter {
    private List<Player> mAllPlayers;
    private List<Team> mTeams;
    private BufferedReader mReader;
    private Map<String, String> mMenu;

    public Prompter(Player[] players) {
        mAllPlayers = new ArrayList<>();
        Collections.addAll(mAllPlayers, players);
        Collections.sort(mAllPlayers);
        mTeams = new ArrayList<>();
        mReader = new BufferedReader(new InputStreamReader(System.in));
        mMenu = new HashMap<String, String>();
        mMenu.put("add", "Add a player to a team");
        mMenu.put("balance", "View League Balance Report");
        mMenu.put("create", "Create a new team");
        mMenu.put("height", "View report of a team grouped by height");
        mMenu.put("quit", "Exit the program");
        mMenu.put("remove", "Remove a player from a team");
        mMenu.put("roster", "Print out a team roster");
    }

    private String promptAction() throws IOException {
        System.out.println("Your options are:");
        for (Map.Entry<String, String> option : mMenu.entrySet()) {
            System.out.printf("%n%s - %s %n",
                                option.getKey(),
                                option.getValue());
        }
        System.out.print("\nWhat do you want to do:  ");
        String choice = mReader.readLine();
        return choice.trim().toLowerCase();
    }

    public void run() {
        String choice = "";
        do {
            try {
                choice = promptAction();
                switch (choice) {
                    case "add":
                        Team team = promptForTeam();
                        if (checkTeamSize(team)) {
                            System.out.println("%nSorry, teams are allowed a maximum 11 players.%n%n");
                            break;
                        }
                        Player player = promptForPlayer();
                        team.addPlayer(player);
                        System.out.printf("%nAdded %s to team %s.%n%n", player.getPlayerInfo(), team.getTeamName());
                        break;
                    case "balance":
                        balanceReport();
                        break;
                    case "create":
                        if (checkTeamMax()) {
                            System.out.println("%nSorry, the team limit has been reached - no new teams may be created.%n%n");
                            break;
                        }
                        createTeam();
                        break;
                    case "height":
                        team = promptForTeam();
                        heightReport(team);
                        break;
                    case "remove":
                        team = promptForTeam();
                        player = promptByTeam(team);
                        team.removePlayer(player);
                        System.out.printf("%nRemoved %s from team %s.%n%n", player.getPlayerInfo(), team.getTeamName());
                        break;
                    case "roster":
                        team = promptForTeam();
                        printTeamRoster(team);
                        break;
                    case "quit":
                        System.out.println("Goodbye!");
                        break;
                    default:
                        System.out.printf("%nUnknown choice:  '%s'. Try again.  %n%n",
                                choice);
                }
            } catch (IOException ioe) {
                System.out.println("%nProblem with input%n%n");
                ioe.printStackTrace();
            }
        } while (!choice.equals("quit"));
    }

    public void createTeam() throws IOException {
        System.out.print("Please enter a new team name: ");
        String teamName = mReader.readLine();
        System.out.print("Please enter the coach's name for the new team: ");
        String coachName = mReader.readLine();
        Team team = new Team(teamName, coachName);
        mTeams.add(team);
        Collections.sort(mTeams);
        System.out.printf("Added %s to the list of teams with %s as its coach.%n%n", teamName, coachName);
    }

    private int promptForTeamIndex(List<Team> teams) throws IOException {
        int counter = 1;
        int choice = -1;
        for (Team team : teams) {
            System.out.printf("%d.)  %s %n", counter, team.getTeamName());
            counter++;
        }

        do {
            try {
                System.out.print("Select the team:  ");
                String optionAsString = mReader.readLine();
                choice = Integer.parseInt(optionAsString.trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input.  Please enter a number.");
            }

        } while (choice > teams.size() || choice < 1);

        return choice - 1;
    }

    private int promptForPlayerIndex(List<Player> players) throws IOException {
        int counter = 1;
        int choice = -1;
        for (Player player : players) {
            System.out.printf("%d.)  %s %n", counter, player.getPlayerInfo());
            counter++;
        }

        do {
            try {
                System.out.printf("Select a player:  ");
                String optionAsString = mReader.readLine();
                choice = Integer.parseInt(optionAsString.trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input.  Please enter a number.");
            }
        } while (choice > players.size() || choice < 1);

        return choice - 1;
    }

    private Team promptForTeam() throws IOException {
        int index = promptForTeamIndex(mTeams);
        return mTeams.get(index);
    }

    public Player promptForPlayer() throws IOException {
        int index = promptForPlayerIndex(getAvailablePlayers());
        return getAvailablePlayers().get(index);
    }

    public Player promptByTeam(Team team) throws IOException {
        int index = promptForPlayerIndex(team.getAllPlayers());
        return team.getAllPlayers().get(index);
    }

    public void heightReport(Team team) {
        Collections.sort(team.getAllPlayers(), new Comparator<Player>(){

            @Override
            public int compare(Player o1, Player o2) {
                return Integer.compare(o1.getHeightInInches(), o2.getHeightInInches());
            }
        });

        System.out.printf("Height report for %s%n%n", team.getTeamName());
        int counter = 1;
        for (Player player : team.getAllPlayers()) {
            System.out.printf("%d.)  %s %n", counter, player.getPlayerInfo());
            counter++;
        }
        System.out.println();
    }

        public void balanceReport() {
            System.out.println("League Balance Report\n");
            Map<Team, Integer> experiencedPlayerCounts = new HashMap<Team, Integer>();

            for (Team team : mTeams) {
                int exp = 0;
                for (Player player : team.getAllPlayers()) {
                    if (player.isPreviousExperience()) {
                        exp++;
                    }
                }
                experiencedPlayerCounts.put(team, exp);
            }

            for (Team team : mTeams) {
                int experiencedCount = experiencedPlayerCounts.get(team);
                int inexperiencedCount = team.getSize() - experiencedCount;
                float avg = (((float) experiencedCount / team.getSize()) * 100);
                System.out.printf("%s, Experienced Players: %d, Inexperienced Players: %d, " +
                                "Average Experience Level: %.1f%%%n",
                                team.getTeamName(), experiencedCount, inexperiencedCount, avg);
            }

            Map<Integer, Integer> countsByHeight = new HashMap<Integer, Integer>();
            for (Team team : mTeams) {
                int counter = 0;
                for (Player player : team.getAllPlayers()) {
                    int heightValue = player.getHeightInInches();
                    Integer heightCounts = countsByHeight.get(heightValue);
                    if (heightCounts == null) {
                        heightCounts = 0;
                    }
                    heightCounts++;
                    countsByHeight.put(heightValue, heightCounts);
                }

                for (Map.Entry<Integer, Integer> entry : countsByHeight.entrySet()) {
                    System.out.printf("%nTeam: %s  Height: %d  Number of Players: %d%n", team.getTeamName(), entry.getKey(), entry.getValue());
                }
            }
            System.out.println();
        }

        public void printTeamRoster(Team team){
            System.out.printf("Team roster for %s%n%n", team.getTeamName());
            int counter = 1;
            for (Player player : team.getAllPlayers()) {
                System.out.printf("%d.)  %s %n", counter, player.getPlayerInfo());
                counter++;
            }
            System.out.println();
        }

        public boolean checkTeamSize(Team team){
            int counter = 0;
            for (Player player : team.getAllPlayers()) {
                counter++;
            }
            if (counter > 11) {
                return true;
            } else {
                return false;
            }
        }

        public boolean checkTeamMax() {
            if (mTeams.size() >= 3) {
                return true;
            } else {
                return false;
            }
        }

        public List<Player> getAvailablePlayers() {
            Set<Player> availablePlayers = new TreeSet<>();
            availablePlayers.addAll(mAllPlayers);
            for (Team team : mTeams) {
                availablePlayers.removeAll(team.getAllPlayers());
            }
            return new ArrayList<Player>(availablePlayers);
        }
}