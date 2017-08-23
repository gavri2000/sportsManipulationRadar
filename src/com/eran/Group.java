package com.eran;

import java.util.List;

/**
 * Created by user on 23/08/2017.
 */
public class Group {
    private int groupNumber;
    private List<Team> teams;

    public int getGroupNumber() {
        return groupNumber;
    }

    public void setGroupNumber(int groupNumber) {
        this.groupNumber = groupNumber;
    }

    public List<Team> getTeams() {
        return teams;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }

    public Group(int groupNumber, List<Team> teams) {

        this.groupNumber = groupNumber;
        this.teams = teams;
    }

    public void SortTeams() {
        teams.sort(Team::compareTo);
    }
}
