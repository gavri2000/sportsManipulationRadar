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
        initListOfGroupsScores(tournamentGraph, listOfGroups);
        List<Pair<Team,Team>> pairList = possibleManipulatedWinnerPairs(team, tournamentGraph, listOfGroups);
        return listOfPossibleWinners;
    }

    private List<Pair<Team,Team>> possibleManipulatedWinnerPairs(Team team, int[][] tournamentGraph, List<Group> listOfGroups) {
        List<Pair<Team,Team>> pairList = new ArrayList<>();
        Group groupOfTeam = getGroupOfTeam(team, listOfGroups);
        groupOfTeam.SortTeams();

        //TODO:  instead of the following if, we need to find if there was manipulates
        /*
            for (int t1 = 0; t1 <= 1; t1++) {
                for (int t2 = 0; t2 <=1 ; t2++) {
                    for (int t3 = 0; t3 <=1 ; t3++) {
                        for (int t4 = 0; t4 <=1 ; t4++) {
                            if (t1 == 1 && teams[0] is in coalision && teams[0] is not "our team" && teams[0] is better than "our team")
                            {
                                teams[0].score -= 3;
                                "our team".score += 3;
                            }
                            if (t2 == 1 && teams[1] is in coalision && teams[1] is not "our team" && teams[1] is better than "our team")
                            {
                                teams[1].score -= 3;
                                "our team".score += 3;
                            }
                            ...
                            teams.sort();
                            if winnersPair dont contains the pair <teams[0], teams[1]> && "our team" is inside that pair, add it.
                        }
                    }
                }
            }
         */
        //the following if is a private case of the previous comment, and will be removed after the prev comment will be coded
        if (groupOfTeam.getTeams().get(0) == team || groupOfTeam.getTeams().get(1) == team) {
            pairList.add(new Pair<>(groupOfTeam.getTeams().get(0), groupOfTeam.getTeams().get(1)));
        }
        return pairList;
    }

    private Group getGroupOfTeam(Team comparedTeam, List<Group> listOfGroups) {
        for (Group group: listOfGroups) {
            for (Team team:group.getTeams()) {
                if (team.getTeamNumber() == comparedTeam.getTeamNumber()) {
                    return group;
                }
            }
        }
        return null;
    }


    public void initListOfGroupsScores(int[][] tournamentGraph, List<Group> listOfGroups) {
        for (int i = 0; i < NUM_OF_GROUPS; i++) {
            for (int j = 0; j < NUM_OF_TEAMS; j++) {
                for (int k = j + 1; k < NUM_OF_TEAMS; k++) {
                    Team team1 = listOfGroups.get(i).getTeams().get(j);
                    Team team2 = listOfGroups.get(i).getTeams().get(k);

                    if (tournamentGraph[team1.getTeamNumber()-1][team2.getTeamNumber()-1] == 1) {
                        listOfGroups.get(i).getTeams().get(j).setScore(listOfGroups.get(i).getTeams().get(j).getScore() + SCORE_FOR_WINNING_MATCH);
                    } else {
                        listOfGroups.get(i).getTeams().get(k).setScore(listOfGroups.get(i).getTeams().get(k).getScore() + SCORE_FOR_WINNING_MATCH);
                    }
                }
            }
        }
    }

}
