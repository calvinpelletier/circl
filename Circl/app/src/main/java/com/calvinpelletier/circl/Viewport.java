package com.calvinpelletier.circl;

import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

/**
 * Created by Calvin on 10/15/15.
 */

//viewport is what the user sees on their screen
public class Viewport {

    private PalaceView palace; //palace is the entire canvas

    private final double sameTouchLocationTolerance = 20.0;

    private Coord position = new Coord(0.f, 0.f); //viewport position relative to canvas
    private Coord touchStart = new Coord(0.f, 0.f);
    private Coord positionOld = new Coord(0.f, 0.f);
    private float scale = 1.f; //viewport scale (pinch and zoom)
    private boolean placingNode = false;
    private boolean creatingConnection = false;
    private Node nodeHeldDown = null;

    public Viewport(PalaceView palace) {
        this.palace = palace;
    }

    public Coord getPosition() {
        return position;
    }

    public float getScale() {
        return scale;
    }

    public void setPlacingNode(boolean placingNode) {
        this.placingNode = placingNode;
    }

    public void setNodeHeldDown() {
        nodeHeldDown = tappedNode;
    }
    public Node getNodeHeldDown() {
        return nodeHeldDown;
    }

    public boolean inStandardMode() {
        return !placingNode && !creatingConnection && (nodeHeldDown == null);
    }
    public void returnToStandardMode() {
        placingNode = false;
        creatingConnection = false;
        nodeHeldDown = null;
    }

    public void startAddConnection() {
        this.creatingConnection = true;
    }

    public Coord viewportToPalaceCoord(Coord viewportCoord) {
        Coord ret = new Coord((float)viewportCoord.x * scale + position.x, (float)viewportCoord.y * scale + position.y);
        return ret;
    }

    public Coord palaceToViewportCoord(Coord palaceCoord) {
        Coord ret = new Coord((float)(palaceCoord.x - position.x)/scale, (float)(palaceCoord.y - position.y)/scale);
        return ret;
    }

    //~~~USER INTERACTION~~~
    private Node tappedNode = null;
    private Node firstNodeInConnection = null;
    public void onTouchEvent(MotionEvent ev) {
        switch(ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                for (int i = 0; i < palace.nodeArray.size(); i++) {
                    if (distance(palace.nodeArray.get(i).getPosition(), viewportToPalaceCoord(new Coord(ev.getX(),ev.getY()))) < palace.nodeArray.get(i).getRadius()) {
                        tappedNode = palace.nodeArray.get(i);
                    }
                }
                touchStart.x = ev.getX();
                touchStart.y = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                if (nodeHeldDown != null && nodeHeldDown == tappedNode) {
                    if (!tooCloseToANode(viewportToPalaceCoord(new Coord(ev.getX(), ev.getY())), nodeHeldDown)) {
                        nodeHeldDown.setPosition(viewportToPalaceCoord(new Coord(ev.getX(), ev.getY())));
                    }
                } else {
                    position.x = constrainX(touchStart.x - ev.getX() + positionOld.x);
                    position.y = constrainY(touchStart.y - ev.getY() + positionOld.y);
                }
                break;
            case MotionEvent.ACTION_UP:
                //if the tap was released in the same place that it started
                if (distance(touchStart, new Coord(ev.getX(), ev.getY())) < sameTouchLocationTolerance) {
                    if (nodeHeldDown != null) {
                        if (tappedNode == null) {
                            nodeHeldDown = null;
                            palace.nodeNotHeldDown();
                        }
                    } else if (placingNode) {
                        if (tappedNode == null) {
                            if (!tooCloseToANode(viewportToPalaceCoord(new Coord(ev.getX(), ev.getY())), null)) {
                                placingNode = false;
                                palace.addNode(viewportToPalaceCoord(new Coord(ev.getX(), ev.getY())));
                            }
                        }
                    } else if (creatingConnection) {
                        if (tappedNode != null) {
                            //check whether we're ready to set the first node or second
                            if (firstNodeInConnection == null) {
                                firstNodeInConnection = tappedNode;
                                palace.startAddConnection2();
                            } else {
                                palace.addConnection(firstNodeInConnection, tappedNode);
                                firstNodeInConnection = null;
                                creatingConnection = false;
                            }
                        }
                    } else {
                        if (tappedNode != null) {
                            System.out.println("Tapped node: " + tappedNode); //TODO: change this when we can open nodes
                        }
                    }
                }
                tappedNode = null;
                positionOld.x = position.x;
                positionOld.y = position.y;
                break;
        }
    }

    public void onScale(ScaleGestureDetector detector) {
        scale *= detector.getScaleFactor();

        // Don't let the object get too small or too large.
        scale = Math.max(0.1f, Math.min(scale, 5.0f));
    }
    //~~~~~~

    //~~~PRIVATE HELPER FUNCTIONS~~~
    // Makes sure viewport X falls between 0 and width
    private float constrainX(float x)
    {
        return Math.min(palace.width,Math.max(x,0));
    }

    // Makes sure viewport Y falls between 0 and height
    private float constrainY(float y)
    {
        return Math.min(palace.height,Math.max(y,0));
    }

    private double distance(Coord coord1, Coord coord2) {
        return Math.sqrt(Math.pow(coord1.x - coord2.x, 2) + Math.pow(coord1.y - coord2.y, 2));
    }

    private boolean tooCloseToANode(Coord pos, Node ignore) {
        boolean temp = false;
        for (int i = 0; i < palace.nodeArray.size(); i++) {
            if ((distance(palace.nodeArray.get(i).getPosition(), pos) < (palace.nodeArray.get(i).getRadius() * 2)+10) && (palace.nodeArray.get(i) != ignore)) {
                temp = true;
            }
        }
        return temp;
    }
    //~~~~~~
}
