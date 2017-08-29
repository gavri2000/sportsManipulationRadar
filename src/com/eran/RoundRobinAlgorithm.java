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
        List<Pair<Team,Team>> pairList = possibleManipulatedWinnerPairs(team, tournamentGraph, listOfGroups, coalition);
        return listOfPossibleWinners;
    }

    private List<Pair<Team,Team>> possibleManipulatedWinnerPairs(Team team, int[][] tournamentGraph, List<Group> listOfGroups, List<Team> coalition) {
        List<Pair<Team,Team>> pairList = new ArrayList<>();
        Group groupOfTeam = getGroupOfTeam(team, listOfGroups);
        groupOfTeam.SortTeams();
        Team tm1 = groupOfTeam.getTeams().get(0);
        Team tm2 = groupOfTeam.getTeams().get(1);
        Team tm3 = groupOfTeam.getTeams().get(2);
        Team tm4 = groupOfTeam.getTeams().get(3);
        for (int t1 = 0; t1 <= 1; t1++) {
            for (int t2 = 0; t2 <=1 ; t2++) {
                for (int t3 = 0; t3 <=1 ; t3++) {
                    for (int t4 = 0; t4 <=1 ; t4++) {
                        // TODO: fix the last condition of all the if commands (tournamentGraph[tm#.getTeamNumber() - 1][team.getTeamNumber()] == 000)
                        if ((t1 == 1) && (coalition.contains(tm1)) && (tm1 != team) && (tournamentGraph[tm1.getTeamNumber() - 1][team.getTeamNumber()] == 000)) {
                            tm1.setScore(tm1.getScore() - 3);
                            team.setScore(team.getScore() + 3);
                        }
                        if ((t2 == 1) && (coalition.contains(tm2)) && (tm2 != team) && (tournamentGraph[tm2.getTeamNumber() - 1][team.getTeamNumber()] == 000)) {
                            tm2.setScore(tm2.getScore() - 3);
                            team.setScore(team.getScore() + 3);
                        }
                        if ((t3 == 1) && (coalition.contains(tm3)) && (tm1 != team) && (tournamentGraph[tm3.getTeamNumber() - 1][team.getTeamNumber()] == 000)) {
                            tm3.setScore(tm3.getScore() - 3);
                            team.setScore(team.getScore() + 3);
                        }
                        if ((t4 == 1) && (coalition.contains(tm4)) && (tm4 != team) && (tournamentGraph[tm4.getTeamNumber() - 1][team.getTeamNumber()] == 000)) {
                            tm4.setScore(tm4.getScore() - 3);
                            team.setScore(team.getScore() + 3);
                        }
                        groupOfTeam.SortTeams();
                        if ((team == groupOfTeam.getTeams().get(0) || team == groupOfTeam.getTeams().get(1)) && !pairList.contains(new Pair(groupOfTeam.getTeams().get(0), groupOfTeam.getTeams().get(1)))) {
                            pairList.add(new Pair(groupOfTeam.getTeams().get(0), groupOfTeam.getTeams().get(1)));
                            System.out.println(groupOfTeam.getTeams().get(0).getTeamNumber() + "," + groupOfTeam.getTeams().get(1).getTeamNumber());
                        }
                        initListOfGroupsScores(tournamentGraph, listOfGroups);
                    }
                }
            }
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
                listOfGroups.get(i).getTeams().get(j).setScore(0);
            }
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
