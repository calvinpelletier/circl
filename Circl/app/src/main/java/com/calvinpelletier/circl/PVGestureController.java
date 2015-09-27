package com.calvinpelletier.circl;

import android.view.MotionEvent;

import java.util.ArrayList;

// Manages user panning in PalaceView.java
public class PVGestureController {

    private int width, height;

    private float startX = 0f;
    private float startY = 0f;

    private float translateX = 0f;
    private float translateY = 0f;

    private float previousTranslateX = 0f;
    private float previousTranslateY = 0f;

    private Node tappedNode = null;

    public PVGestureController(int width, int height)
    {
        this.width = width;
        this.height = height;
    }

    public void onTouchEvent(MotionEvent ev, ArrayList<Node> nodeArray)
    {
        switch(ev.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                for (int i = 0; i < nodeArray.size(); i++) {
                    if (distance(nodeArray.get(i).getPosition(), new Coord(ev.getX(),ev.getY())) < nodeArray.get(i).getRadius()) {
                        tappedNode = nodeArray.get(i);
                    }
                }
                startX = ev.getX()-previousTranslateX;
                startY = ev.getY()-previousTranslateY;
                translateX = ev.getX()-startX;
                translateY = ev.getY()-startY;
                break;
            case MotionEvent.ACTION_MOVE:
                translateX = constrainX(ev.getX() - startX);
                translateY = constrainY(ev.getY()-startY);
                break;
            case MotionEvent.ACTION_UP:
                for (int i = 0; i < nodeArray.size(); i++) {
                    if (distance(nodeArray.get(i).getPosition(), new Coord(ev.getX(),ev.getY())) < nodeArray.get(i).getRadius()) {
                        if (nodeArray.get(i) == tappedNode) {
                            //node has been tapped
                        }
                    }
                }
                tappedNode = null;

                previousTranslateX = translateX;
                previousTranslateY = translateY;
                break;
        }
    }

    // Makes sure translateX falls between 0 and width
    private float constrainX(float x)
    {
        return Math.min(0,Math.max(x,-width));
    }

    // Makes sure translateY falls between 0 and height
    private float constrainY(float y)
    {
        return Math.min(0,Math.max(y,-height));
    }


    public float getTranslateX()
    {
        return translateX;
    }

    public float getTranslateY()
    {
        return translateY;
    }

    private double distance(Coord coord1, Coord coord2) {
        return Math.sqrt(Math.pow(coord1.x - coord2.x, 2) + Math.pow(coord1.y + coord2.y, 2));
    }
}
