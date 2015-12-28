package com.calvinpelletier.circl;

import java.util.ArrayList;

/**
 * Created by cleev on 12/26/15.
 */
public class ParentNode extends Node {

    private ArrayList<Node> children = new ArrayList<Node>();
    private boolean childrenHidden = true;

    public ParentNode(Coord position, int outlineColor, PalaceView palace) {
        super(position, outlineColor, palace);
        this.setLargeRadius();
    }

    public void onTap() {
        if (childrenHidden) {

        }
    }
}
