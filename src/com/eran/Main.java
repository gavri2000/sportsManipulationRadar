package com.eran;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static final int NUM_OF_GROUPS = 2;
    public static final int NUM_OF_TEAMS = 4;
    public static final int SCORE_FOR_WINNING_MATCH = 3;

    public static void main(String[] args) throws FileNotFoundException {

        List<String> mylist;
        String inputFile = "C:\\Users\\user\\IdeaProjects\\sportsManipulationRadar\\src\\com\\eran\\data.txt";
        try {
            mylist = Utils.buildDataListFromFile(inputFile);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        // builds the cup tree
        Tree cupTree = new Tree();
        int numOfTeams = Integer.parseInt(mylist.get(0));

        for (int i = 0; i < numOfTeams - 1; i++) {
            cupTree.add(-1, cupTree);
        }
        for (int i = 1; i <= numOfTeams; i++) {
            cupTree.add(i, cupTree);
        }

        // builds the tournamentGraph dynamically
        int tournamentGraph[][] = new int[numOfTeams][numOfTeams];
        String[] teams;
        String[] coalition;
        List<String> coalitionListCsl = null;
        List<Team> coalitionList = new ArrayList<>();



        int v1, v2;

        // the first line in the file should be "teams strengths" so we start from the next line
        for (int i = 1; i < mylist.size(); i++) {
            if (!mylist.get(i).equals("coalition")) {
                teams = mylist.get(i).split("\\s");
                v1 = Integer.parseInt(teams[0]) - 1;
                v2 = Integer.parseInt(teams[1]) - 1;
                tournamentGraph[v1][v2] = 1;
            } else {
                // builds the coalition
                coalition = mylist.get(i + 1).split("\\s");
                coalitionListCsl = Arrays.asList(coalition);
                break;
            }
        }

        for (String team:coalitionListCsl) {
            coalitionList.add(new Team(Integer.parseInt(team), 0));
        }


        CslAlgorithm cslAlgorithm = new CslAlgorithm();
        String Vw;

        System.out.println("Tournament possible winners: ");
        for (int i = 1; i <= numOfTeams; i++) {
            Vw = String.valueOf(i);
            System.out.println("V" + Vw + ": " + cslAlgorithm.CSLAlgorithm(Vw, cupTree, tournamentGraph, coalitionListCsl));
        }

        System.out.println("Consolation brackets possible winners: ");
        for (int i = 1; i <= numOfTeams; i++) {
            Vw = String.valueOf(i);
            System.out.println("V" + Vw + ": " + cslAlgorithm.findPossibleLosersForConsolation(Vw, tournamentGraph, coalitionListCsl));
        }

        // build the Groups for world cup.. 9 Groups 4 teams in each.
        List<Group> listOfGroups = new ArrayList<>();
        initListOfGroups(listOfGroups);

        RoundRobinAlgorithm roundRobinAlgorithm = new RoundRobinAlgorithm();
        Team team = new Team(1, 0);

        System.out.println("Round Robin possible winners: ");
        for (int i = 1; i <= numOfTeams; i++) {
            team.setTeamNumber(i);
            System.out.println("team" + team.getTeamNumber() + ": " + roundRobinAlgorithm.possibleWinners(team, listOfGroups, tournamentGraph, coalitionList));
        }

    }




    /**
     *
     * @param listOfGroups
     */
    private static void initListOfGroups(List <Group> listOfGroups) {
        for (int i = 0; i < NUM_OF_GROUPS; i++) {
            for (int j = 0; j < NUM_OF_TEAMS; j++) {
                listOfGroups.get(i).getTeams().get(j).setTeamNumber(i * NUM_OF_TEAMS + j + 1);
            }
        }
    }
}
