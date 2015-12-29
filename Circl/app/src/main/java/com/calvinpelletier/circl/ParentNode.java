package com.calvinpelletier.circl;

import java.util.ArrayList;

/**
 * Created by cleev on 12/26/15.
 */
public class ParentNode extends Node {

    private ArrayList<Node> children = new ArrayList<Node>();
    private boolean childrenHidden = true;
    private static final int STD_RADIUS = 50;
    private int largeRadius = 250;

    public ParentNode(Coord position, int outlineColor, PalaceView palace) {
        super(position, outlineColor, palace);
        this.setRadius(largeRadius);
    }

    public void onTap() {
        if (childrenHidden) {
            this.setRadius(STD_RADIUS);
            this.childrenHidden = false;
            for (Node node : children) {
                node.setHidden(false);
            }
        } else {
            this.setRadius(largeRadius);
            this.childrenHidden = true;
            for (Node node : children) {
                node.setHidden(true);
            }
        }
    }

    public void addChild(Node child) {
        children.add(child);
        child.setHidden(childrenHidden);
        recalculateChildPositions();
    }

    private void recalculateChildPositions() {
        int innerRadius = this.largeRadius - STD_RADIUS/2;
        double minSeparation = Math.acos(4 * (STD_RADIUS + 5) * (STD_RADIUS + 5) / ((double)(-2 * innerRadius * innerRadius)) + 1); //10 is a buffer
        double maxSeparation = minSeparation * 2;
        int nNodesCutoff0 = (int)(Math.PI * 2 / maxSeparation);
        int nNodesCutoff1 = (int)(Math.PI * 2 / minSeparation);
        double upRadians = Math.PI * 3 / 2;
        System.out.println(minSeparation);
        System.out.println(nNodesCutoff1);
        if (children.size() == 0) {
            return;
        } else if (children.size() <= nNodesCutoff0) {
            for (int i = 0; i < children.size(); i++) {
                children.get(i).setPosition((float)(this.getPosition().x + Math.cos((upRadians + maxSeparation * i) % (Math.PI * 2)) * innerRadius),
                                            (float)(this.getPosition().y + Math.sin((upRadians + maxSeparation * i) % (Math.PI * 2)) * innerRadius));
            }
        } else if (children.size() <= nNodesCutoff1) {
            double separation = maxSeparation + (minSeparation - maxSeparation) * (children.size() - nNodesCutoff0) / (nNodesCutoff1 - nNodesCutoff0);
            for (int i = 0; i < children.size(); i++) {
                children.get(i).setPosition((float)(this.getPosition().x + Math.cos((upRadians + separation * i) % (Math.PI * 2)) * innerRadius),
                        (float)(this.getPosition().y + Math.sin((upRadians + separation * i) % (Math.PI * 2)) * innerRadius));
            }
        } else {
            double separation = Math.PI * 2 / children.size();
            this.largeRadius = (int)(STD_RADIUS/2 + Math.sqrt(4 * (STD_RADIUS + 5) * (STD_RADIUS + 5) / (2 * (1 - Math.cos(separation)))));
            innerRadius = this.largeRadius - STD_RADIUS/2;
            for (int i = 0; i < children.size(); i++) {
                children.get(i).setPosition((float)(this.getPosition().x + Math.cos((upRadians + separation * i) % (Math.PI * 2)) * innerRadius),
                        (float)(this.getPosition().y + Math.sin((upRadians + separation * i) % (Math.PI * 2)) * innerRadius));
            }
        }
    }
}
