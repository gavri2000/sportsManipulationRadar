package com.eran;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

import static com.eran.Main.*;

/**
 * Created by user on 23/08/2017.
 */
public class RoundRobinAlgorithm {
    public List<Team> possibleWinners(Team team, List<Group> listOfGroups, int [][] tournamentGraph, List<Team> coalition) {
        List<Team> listOfPossibleWinners = new ArrayList<>();

        // fill the scores array by the strengths from tournament graph
        int[][] listOfGroupsScores = new int[NUM_OF_GROUPS][NUM_OF_TEAMS];
        initListOfGroupsScores(tournamentGraph, listOfGroups, listOfGroupsScores);
        List<Pair<Team,Team>> pairList = possibleManipulatedWinnerPairs(team, tournamentGraph, listOfGroups, listOfGroupsScores);

        return listOfPossibleWinners;
    }

    private List<Pair<Team,Team>> possibleManipulatedWinnerPairs(Team team, int[][] tournamentGraph, List<Group> listOfGroups, int[][] listOfGroupsScores) {
        List<Pair<Team,Team>> pairList = new ArrayList<>();
        Group groupOfTeam = getGroupOfTeam(team, listOfGroups);


        return pairList;
    }

    private Group getGroupOfTeam(Team team, List<Group> listOfGroups) {
        for (Group group: listOfGroups) {
            if (group.getTeams().contains(team)) {
                return group;
            }
        }
        return null;
    }


    public void initListOfGroupsScores(int[][] tournamentGraph, List<Group> listOfGroups, int[][] listOfGroupsScores) {
        for (int i = 0; i < NUM_OF_GROUPS; i++) {
            for (int j = 0; j < NUM_OF_TEAMS; j++) {
                for (int k = j + 1; k < NUM_OF_TEAMS; k++) {
                    Team team1 = listOfGroups.get(i).getTeams().get(j);
                    Team team2 = listOfGroups.get(i).getTeams().get(k);

                    if (tournamentGraph[team1.getTeamNumber()-1][team2.getTeamNumber()-1] == 1) {
                        listOfGroupsScores[i][j] += SCORE_FOR_WINNING_MATCH;
                    } else {
                        listOfGroupsScores[i][k] += SCORE_FOR_WINNING_MATCH;
                    }
                }
            }
        }
    }

}
