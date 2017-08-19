package com.eran;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

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
        List<String> coalitionList = null;
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
                coalitionList = Arrays.asList(coalition);
                break;
            }
        }

        CslAlgorithm cslAlgorithm = new CslAlgorithm();
        String Vw;

        System.out.println("Tournament possible winners: ");
        for (int i = 1; i <= numOfTeams; i++) {
            Vw = String.valueOf(i);
            System.out.println("V" + Vw + ": " + cslAlgorithm.CSLAlgorithm(Vw, cupTree, tournamentGraph, coalitionList));
        }

        System.out.println("Consolation brackets possible winners: ");
        for (int i = 1; i <= numOfTeams; i++) {
            Vw = String.valueOf(i);
            System.out.println("V" + Vw + ": " + cslAlgorithm.findPossibleLosersForConsolation(Vw, tournamentGraph, coalitionList));
        }
    }
}
