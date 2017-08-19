package com.eran;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 05/06/2017.
 */
public class Tree {
    Node root;
    int level,cLevel;
    public static List<Node> myList;

    Tree()
    {
        root=null;
        level=0;
        cLevel=level-1;
        myList = new ArrayList<>();
    }

    protected void add(int data,Tree mytree)
    {
        if(root==null)
        {
            root=new Node(data);
            myList.add(root);
            return;
        }
        Node node=mytree.myList.get(0);
        if(root!=null)
        {
            if(node.left==null)
            {
                node.left=new Node(data);
                mytree.myList.add(node.left);
                return;
            }
            else
            {
                node.right=new Node(data);
                mytree.myList.add(node.right);
            }
            if(node.left!=null & node.right!=null)
            {
                mytree.myList.remove(0);
            }
        }
    }

    public Node getRoot () {
        return root;
    }
}
