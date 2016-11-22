package com.teamtreehouse.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class Prompter {
    public Team mTeam;
    public Player mPlayer;
    private List<Player> mAllPlayers;
    private List<Team> mTeams;
    private BufferedReader mReader;
    private Queue<Player> mPlayerQueue;
    private Map<String, String> mMenu;

    public Prompter(Player[] players) {
        mAllPlayers = new ArrayList<>();
        Collections.addAll(mAllPlayers, players);
        mTeams = new ArrayList<>();
        mReader = new BufferedReader(new InputStreamReader(System.in));
        mPlayerQueue = new ArrayDeque<Player>();
        mMenu = new HashMap<String, String>();
        mMenu.put("create", "Create a new team");
        mMenu.put("add", "Add a player to a team");
        mMenu.put("quit", "Exit the program");
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
                    case "create":
                        createTeam();
                        break;
                    case "add":
                        mTeam = promptForTeam();
                        mPlayer = promptToAddPlayer();
                        mTeam.addPlayer(mPlayer);
                        System.out.printf("Added %s to team %s.%n", mPlayer.getPlayerName(), mTeam.getTeamName());
                        break;
                    case "quit":
                        System.out.println("Goodbye!");
                        break;
                    default:
                        System.out.printf("Unknown choice:  '%s'. Try again.  %n%n%n",
                                choice);
                }
            } catch (IOException ioe) {
                System.out.println("Problem with input");
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
        System.out.printf("Added %s to the list of teams with %s as its coach.%n", teamName, coachName);
    }

    private int promptForTeamIndex(List<Team> teams) throws IOException {
        int counter = 1;
        for (Team team : teams) {
            System.out.printf("%d.)  %s %n", counter, team.getTeamName());
            counter++;
        }
        System.out.print("Choose the team you would like to add a player to:  ");
        String optionAsString = mReader.readLine();
        int choice = Integer.parseInt(optionAsString.trim());
        return choice - 1;
    }

    private int promptForPlayerIndex(List<Player> players) throws IOException {
        int counter = 1;
        for (Player player : players) {
            System.out.printf("%d.)  %s %n", counter, player.getPlayerName());
            counter++;
        }
        System.out.printf("Choose the player you would like to add to team %s:  ", mTeam.getTeamName());
        String optionAsString = mReader.readLine();
        int choice = Integer.parseInt(optionAsString.trim());
        return choice - 1;
    }

    private Team promptForTeam() throws IOException {
        int index = promptForTeamIndex(mTeams);
        return mTeams.get(index);
    }

    public Player promptToAddPlayer() throws IOException {
        int index = promptForPlayerIndex(mAllPlayers);
        return mAllPlayers.get(index);
    }
}