package com.eran;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by user on 05/06/2017.
 */
public class Node {
    int data;
    Node left,right;
    List<Node> winners;
    List<Node> leftWinners;
    List<Node> rightWinners;



    Node(int data)
    {
        left = null;
        right = null;
        this.data = data;
        winners = new ArrayList<>();
        leftWinners = Collections.emptyList();
        rightWinners = Collections.emptyList();
    }

    public Node getLeft () {
        return left;
    }

    public Node getRight () {
        return right;
    }

    public List<Node> getWinners () {
        return winners;
    }

    public List<Node> getLeftWinners () {
        return leftWinners;
    }

    public List<Node> getRightWinners () {
        return rightWinners;
    }

    public void setWinners(List<Node> winners) {
        this.winners = winners;
    }

    public void setLeftWinners(List<Node> leftWinners) {
        this.leftWinners = leftWinners;
    }

    public void setRightWinners(List<Node> rightWinners) {
        this.rightWinners = rightWinners;
    }
}
