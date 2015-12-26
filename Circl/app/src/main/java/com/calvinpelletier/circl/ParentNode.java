package com.calvinpelletier.circl;

import java.util.ArrayList;

/**
 * Created by cleev on 12/26/15.
 */
public class ParentNode extends Node {

    private ArrayList<Node> children = new ArrayList<Node>();

    public ParentNode(Coord position, int outlineColor) {
        super(position, outlineColor);
        this.setLargeRadius();
    }
}
