package com.teamtreehouse.model;

import java.util.ArrayList;
import java.util.List;

public class Team {

    private String mTeamName;
    private String mCoachName;
    private List<Player> mTeam;

    public Team(String teamName, String coachName) {
        mTeamName = teamName;
        mCoachName = coachName;
        mTeam = new ArrayList<Player>();
    }

    public void addPlayer(Player player) {
        mTeam.add(player);
    }


}