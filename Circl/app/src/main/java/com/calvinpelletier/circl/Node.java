package com.calvinpelletier.circl;

import android.graphics.Color;

import java.util.ArrayList;

/**
 * Created by Calvin Pelletier on 9/26/15.
 */



public class Node implements java.io.Serializable {

    private int outlineColor = Color.BLACK;
    private Coord position;
    private static int STD_RADIUS = 50;
    private int radius = STD_RADIUS;
    private PalaceView palace;
    private boolean hidden = false;

    public Node(Coord position, int outlineColor, PalaceView palace) {
        this.position = position;
        this.outlineColor = outlineColor;
        this.palace = palace;
    }

    public Coord getPosition() {
        return this.position;
    }

    public void setPosition(Coord newPosition) {
        this.position = newPosition;
    }

    public void setPosition(float x, float y) {
        this.position.x = x;
        this.position.y = y;
    }

    public int getOutline()
    {
        return this.outlineColor;
    }

    public void setOutline(int newColor) {
        this.outlineColor = newColor;
    }

    public int getRadius()
    {
        return this.radius;
    }

    public int getSqrRadius() {
        return this.radius * this.radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public PalaceView getPalace() {
        return palace;
    }

    public boolean getHidden() {
        return this.hidden;
    }

    public void setHidden(boolean value) {
        this.hidden = value;
    }

    //TODO: remove this when users can't place a generic node
    public void onTap() {}

}
