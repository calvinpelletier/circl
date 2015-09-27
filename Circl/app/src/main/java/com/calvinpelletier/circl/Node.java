package com.calvinpelletier.circl;

import android.graphics.Color;

import java.util.ArrayList;

/**
 * Created by Calvin Pelletier on 9/26/15.
 */
public class Node implements java.io.Serializable {

    private ArrayList<Connection> connectionArray = new ArrayList<Connection>();
    private int outlineColor = Color.BLACK;
    private Coord position;
    private final int radius = 50;

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

    public void setPosition(int x, int y) {
        this.position.x = x;
        this.position.y = y;
    }

    public void setOutline(int newColor) {
        this.outlineColor = newColor;
    }
}
