package com.teamtreehouse.model;

import java.util.ArrayList;
import java.util.List;

public class Team implements Comparable {

    private String mTeamName;
    private String mCoachName;
    private List<Player> mTeam;
    private List<Team> mTeams;

    public Team(String teamName, String coachName) {
        mTeamName = teamName;
        mCoachName = coachName;
        mTeam = new ArrayList<Player>();
    }

    public void addPlayer(Player player) {
        mTeam.add(player);
    }

    public void removePlayer(Player player) {
        mTeam.remove(player);
    }

    public String getTeamName() { return mTeamName; }

    @Override
    public int compareTo(Object obj) {
        Team other = (Team) obj;
        if (equals(other)) {
            return 0;
        }
        return mTeamName.compareTo(other.mTeamName);
    }

    public List<Player> getAllPlayers() {
        return mTeam;
    }
}