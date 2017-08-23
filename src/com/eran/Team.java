package com.eran;

/**
 * Created by user on 23/08/2017.
 */
public class Team {
    private int teamNumber;
    private int score;

    public Team(int teamNumber, int score) {
        this.teamNumber = teamNumber;
        this.score = score;
    }

    public int getTeamNumber() {
        return teamNumber;
    }

    public void setTeamNumber(int teamNumber) {
        this.teamNumber = teamNumber;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
