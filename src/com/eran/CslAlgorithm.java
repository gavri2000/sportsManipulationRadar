package com.eran;

import java.util.*;

/**
 * Created by user on 05/06/2017.
 */
public class CslAlgorithm {

    private Set<Node> quarterFinalLeftLosers = new HashSet<>();
    private Set<Node> quarterFinalRightLosers = new HashSet<>();
    private Set<Node> semiFinalLeftLosers = new HashSet<>();
    private Set<Node> semiFinalRightLosers = new HashSet<>();
    private Set<Node> quarterFinalConsolationLeftWinners = new HashSet<>();
    private Set<Node> quarterFinalConsolationRightWinners = new HashSet<>();
    private Set<Node> semiFinalConsolationLeftWinners = new HashSet<>();
    private Set<Node> semiFinalConsolationRightWinners = new HashSet<>();
    private Set<Node> finalConsolationWinners = new HashSet<>();

    /**
     *
     * @param c - a Node in the cup tree
     * @param T - a tournament graph represented by a matrix
     * @param C - list of teams in the coalition (teams that can throw games)
     * @param level - the current tree level (the root level is zero)
     * @return Returns the set of possible c.winners of the cup tree via manipulation of
     *         the tournament by the coalition
     */
    private List<Node> possibleWinners(Node c, String arrivedFrom, int [][] T, List<String> C, int level) {
        List<Node> winnersList = new ArrayList<>();
        if (c.getLeft() == null && c.getRight() == null){ // means c is actually a leaf
            winnersList.add(c);
            return winnersList;
        }
        
        c.setLeftWinners(possibleWinners(c.getLeft(),"left", T, C, level+1));
        c.setRightWinners(possibleWinners(c.getRight(), "right", T, C, level+1));

        for(Node Vi: c.getLeftWinners()) {
            for (Node Vj: c.getRightWinners()) {
                if (isExist(T, Vi.data, Vj.data) || (C.contains(Integer.toString(Vj.data)))) {
                    c.winners.add(Vi);

                    // the loser team will be inserted to the leftLosers (of quarter final
                    // for level 2 or for semi final for level 1)
                    addNodeToCorrectLosersList(arrivedFrom, level, Vj);

                }
            }
        }
        for (Node Vj: c.getRightWinners()) {
            for (Node Vi: c.getLeftWinners()) {
                if (isExist(T, Vj.data, Vi.data) || (C.contains(Integer.toString(Vi.data)))) {
                    c.winners.add(Vj);

                    // the loser team will be inserted to the rightLosers (of quarter final
                    // for level 2 or for semi final for level 1)
                    addNodeToCorrectLosersList(arrivedFrom, level, Vi);
                }
            }
        }

        return c.getWinners();
    }

    private void addNodeToCorrectLosersList(String arrivedFrom, int level, Node v) {
        if (level == 2) {
            if (arrivedFrom.equals("left")) {
                quarterFinalLeftLosers.add(v);
            } else {
                quarterFinalRightLosers.add(v);
            }
        } else {
            if (level == 1) {
                if (arrivedFrom.equals("left")) {
                    semiFinalLeftLosers.add(v);
                }
                else {
                    semiFinalRightLosers.add(v);
                }
            }
        }
    }

    /**
     *
     * @param Vw - a team to check if it can win via manipulations
     * @param c - cup tree
     * @param T - a tournament graph represented by a matrix
     * @param C - list of teams in the coalition (teams that can throw games)
     * @return true if Vw can win via manipulations, false otherwise
     */
    public boolean CSLAlgorithm (String Vw,Tree c,int[][] T, List<String> C) {
        List<Node> winners;
        winners = possibleWinners(c.getRoot(),"root", T, C, 0);

        for (Node winner: winners) {
            if (winner.data == Integer.parseInt(Vw)) {
                return true;
            }
        }
        return false;
    }

    public boolean isExist(int [][] T, int v1, int v2) {
        if (T[v1-1][v2-1] == 1) {
            return true;
        }
        return false;
    }


    public boolean findPossibleLosersForConsolation (String Vw, int[][] T, List<String> C){

        // find the winners of consolations bracket quarter-final - left side teams against right side teams
        for(Node Vi: quarterFinalLeftLosers) {
            for (Node Vj: quarterFinalRightLosers) {
                if (isExist(T, Vi.data, Vj.data) || (C.contains(Integer.toString(Vj.data)))) {
                    quarterFinalConsolationLeftWinners.add(Vi);
                }
            }
        }

        for(Node Vj: quarterFinalRightLosers) {
            for (Node Vi: quarterFinalLeftLosers) {
                if (isExist(T, Vj.data, Vi.data) || (C.contains(Integer.toString(Vi.data)))) {
                    quarterFinalConsolationRightWinners.add(Vj);
                }
            }
        }

        // find the winners of consolations bracket quarter-final left teams against semi-final left teams
        for(Node Vi: quarterFinalConsolationLeftWinners) {
            for (Node Vj: semiFinalLeftLosers) {
                if (isExist(T, Vi.data, Vj.data) || (C.contains(Integer.toString(Vj.data)))) {
                    semiFinalConsolationLeftWinners.add(Vi);
                }
            }
        }

        for(Node Vj: semiFinalLeftLosers) {
            for (Node Vi: quarterFinalConsolationLeftWinners) {
                if (isExist(T, Vj.data, Vi.data) || (C.contains(Integer.toString(Vi.data)))) {
                    semiFinalConsolationLeftWinners.add(Vj);
                }
            }
        }

        // find the winners of consolations bracket quarter-final right teams against semi-final right teams
        for(Node Vi: quarterFinalConsolationRightWinners) {
            for (Node Vj: semiFinalRightLosers) {
                if (isExist(T, Vi.data, Vj.data) || (C.contains(Integer.toString(Vj.data)))) {
                    semiFinalConsolationRightWinners.add(Vi);
                }
            }
        }

        for(Node Vj: semiFinalRightLosers) {
            for (Node Vi: quarterFinalConsolationRightWinners) {
                if (isExist(T, Vj.data, Vi.data) || (C.contains(Integer.toString(Vi.data)))) {
                    semiFinalConsolationRightWinners.add(Vj);
                }
            }
        }

        // finally, find the winners of the whole consolation bracket
        for(Node Vi: semiFinalConsolationRightWinners) {
            for (Node Vj: semiFinalConsolationLeftWinners) {
                if (isExist(T, Vi.data, Vj.data) || (C.contains(Integer.toString(Vj.data)))) {
                    finalConsolationWinners.add(Vi);
                }
            }
        }

        for(Node Vj: semiFinalConsolationLeftWinners) {
            for (Node Vi: semiFinalConsolationRightWinners) {
                if (isExist(T, Vj.data, Vi.data) || (C.contains(Integer.toString(Vi.data)))) {
                    finalConsolationWinners.add(Vj);
                }
            }
        }

        for (Node winner: finalConsolationWinners) {
            if (winner.data == Integer.parseInt(Vw)) {
                return true;
            }
        }
        return false;
    }
}
