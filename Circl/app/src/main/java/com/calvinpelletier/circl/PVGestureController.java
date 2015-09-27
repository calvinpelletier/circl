package com.calvinpelletier.circl;

import android.view.MotionEvent;

/**
 * Created by wilson on 9/26/15.
 */
public class PVGestureController {
    private float startX = 0f;
    private float startY = 0f;

    private float translateX = 0f;
    private float translateY = 0f;

    private float previousTranslateX = 0f;
    private float previousTranslateY = 0f;

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
                translateX = ev.getX()-startX;
                translateY = ev.getY()-startY;
                break;
            case MotionEvent.ACTION_UP:
                previousTranslateX = translateX;
                previousTranslateY = translateY;
                break;
        }
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
