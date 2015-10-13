package com.calvinpelletier.circl;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wilson on 10/13/15.
 */
public class User {
    private List<Node> nodes;

    public User()
    {
        this.nodes = new ArrayList<Node>();
    }

    public void addNode(Node n)
    {
        this.nodes.add(n);
    }

    public int getNumNodes()
    {
        return nodes.size();
    }

    public Node getNode(int idx)
    {
        return nodes.get(idx);
    }
}
