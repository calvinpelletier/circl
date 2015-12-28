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
    private static int LARGE_RADIUS = 250;
    private int radius = STD_RADIUS;

    public Node(Coord position, int outlineColor) {
        this.position = position;
        this.outlineColor = outlineColor;
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

}
