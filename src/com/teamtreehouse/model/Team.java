package com.teamtreehouse.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Team implements Comparable {

    private String mTeamName;
    private String mCoachName;
    private ArrayList<Player> mPlayers;

    public Team(String teamName, String coachName) {
        mTeamName = teamName;
        mCoachName = coachName;
        mPlayers = new ArrayList<Player>();
    }

    public void addPlayer(Player player) {
        mPlayers.add(player);
        Collections.sort(mPlayers);
    }

    public void removePlayer(Player player) {
        mPlayers.remove(player);
        Collections.sort(mPlayers);
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
        return mPlayers;
    }

    public int getSize() {
        return mPlayers.size();
    }
}