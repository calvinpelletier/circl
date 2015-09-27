package com.calvinpelletier.circl;

import android.view.MotionEvent;

// Manages user panning in PalaceView.java
public class PVGestureController {

    private int width, height;

    private float startX = 0f;
    private float startY = 0f;

    private float translateX = 0f;
    private float translateY = 0f;

    private float previousTranslateX = 0f;
    private float previousTranslateY = 0f;

    public PVGestureController(int width, int height)
    {
        this.width = width;
        this.height = height;
    }

    public void onTouchEvent(MotionEvent ev)
    {
        switch(ev.getAction())
        {
            case MotionEvent.ACTION_DOWN:
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
}
