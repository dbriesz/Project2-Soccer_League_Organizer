package com.teamtreehouse.model;

import java.util.ArrayList;
import java.util.List;

public class Team {

    private List<Player> mTeam;

    public Team() {
        mTeam = new ArrayList<Player>();
    }

    public void addPlayer(Player player) {
        mTeam.add(player);
    }
}