package com.calvinpelletier.circl;

/**
 * Created by Calvin Pelletier on 9/26/15.
 */
public class Connection {

    private String label = "";
    private Node nodeA;
    private Node nodeB;

    public Connection(Node nodeA, Node nodeB) {
        this.nodeA = nodeA;
        this.nodeB = nodeB;
    }

    public Connection(Node nodeA, Node nodeB, String label) {
        this.nodeA = nodeA;
        this.nodeB = nodeB;
        this.label = label;
    }

    public void changeLabel(String newLabel) {
        this.label = newLabel;
    }

    public Node getNodeA() {
        return this.nodeA;
    }

    public Node getNodeB() {
        return this.nodeB;
    }
}
