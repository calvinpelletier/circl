package com.calvinpelletier.circl;

import android.view.MotionEvent;
import android.view.ScaleGestureDetector;

/**
 * Created by Calvin on 10/15/15.
 */

//viewport is what the user sees on their screen
public class Viewport {

    private PalaceView palace; //palace is the entire canvas

    private final double sameTouchLocationTolerance = 10.0;

    private Coord position = new Coord(0.f, 0.f); //viewport position relative to canvas
    private Coord touchStart = new Coord(0.f, 0.f);
    private Coord positionOld = new Coord(0.f, 0.f);
    private float scale = 1.f; //viewport scale (pinch and zoom)
    private boolean placingNode = false;

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
                position.x = constrainX(touchStart.x - ev.getX() + positionOld.x);
                position.y = constrainY(touchStart.y - ev.getY() + positionOld.y);
                break;
            case MotionEvent.ACTION_UP:
                for (int i = 0; i < palace.nodeArray.size(); i++) {
                    if (distance(palace.nodeArray.get(i).getPosition(), viewportToPalaceCoord(new Coord(ev.getX(),ev.getY()))) < palace.nodeArray.get(i).getRadius()) {
                        if (palace.nodeArray.get(i) == tappedNode) {
                            System.out.println("Tapped node at index: " + i);
                        }
                    }
                }
                if (placingNode && tappedNode==null && (distance(touchStart, new Coord(ev.getX(), ev.getY())) < sameTouchLocationTolerance)) {
                    boolean temp = true;
                    for (int i = 0; i < palace.nodeArray.size(); i++) {
                        if (distance(palace.nodeArray.get(i).getPosition(), new Coord(ev.getX(),ev.getY())) < (palace.nodeArray.get(i).getRadius() * 2)) {
                            temp = false;
                        }
                    }
                    if (temp) {
                        placingNode = false;
                        palace.addNode(viewportToPalaceCoord(new Coord(ev.getX(), ev.getY())));
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
    //~~~~~~
}
