package com.teamtreehouse.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class Prompter {

    private Team mTeam;
    private BufferedReader mReader;
    private Queue<Player> mPlayerQueue;
    private Map<String, String> mMenu;

    public Prompter(Team team) {
        mTeam = team;
        mReader = new BufferedReader(new InputStreamReader(System.in));
        mPlayerQueue = new ArrayDeque<Player>();
        mMenu = new HashMap<String, String>();
        mMenu.put("create", "Create a new team");
    }

    private String promptAction() throws IOException {
        System.out.print("Your options are: %n");
        for (Map.Entry<String, String> option : mMenu.entrySet()) {
            System.out.printf("%s - %s %n",
                                option.getKey(),
                                option.getValue());
        }
        System.out.print("What do you want to do:  ");
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
                        Team team = createTeam();
                        System.out.printf("");
                        break;
                    case "quit":
                        System.out.println("Goodbye!");
                        break;
                }
            } catch (IOException ioe) {
                System.out.println("Problem with input");
                ioe.printStackTrace();
            }
        } while (!choice.equals("quit"));
    }

    public Team createTeam() throws IOException {
        System.out.print("Please enter a new team name: ");
        String teamName = mReader.readLine();
        System.out.print("Please enter the coach's name for the new team: ");
        String coach = mReader.readLine();
        return new Team();
    }
}
