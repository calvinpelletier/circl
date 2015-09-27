package com.calvinpelletier.circl;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

public class PalaceView extends View {

    private ScaleGestureDetector mScaleDetector;
    private float mScaleFactor = 1.f;

    PVGestureController gestureController;

    public PalaceView(Context context)
    {
        super(context);

        mScaleDetector = new ScaleGestureDetector(context, new ScaleListener());
        gestureController = new PVGestureController(400,400);
    }

    private Canvas canvas;

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.save();
        canvas.scale(mScaleFactor, mScaleFactor);
        canvas.translate(gestureController.getTranslateX(), gestureController.getTranslateY());

        // Just drawing a blue circle for the time being, so I can test panning and zooming
        //Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        //p.setColor(Color.BLUE);
        //canvas.drawCircle(50, 50, 200, p);

        Node n1 = new Node(new Coord(100,100), Color.YELLOW);
        Node n2 = new Node(new Coord(490,120), Color.GREEN);
        Node n3 = new Node(new Coord(400,500), Color.MAGENTA);
        Node n4 = new Node(new Coord(200,300), Color.RED);

        drawNode(canvas,n1);
        drawNode(canvas,n2);
        drawNode(canvas,n3);
        drawNode(canvas,n4);

        drawConnection(canvas,n2,n3);
        drawConnection(canvas,n1,n3);
        drawConnection(canvas,n1,n4);

        canvas.restore();
    }

    // r should be somewhere in between 50 and 100 for now.
    private void drawNode(Canvas canvas,Node n)
    {
        Paint nodePaint = new Paint();
        nodePaint.setColor(Color.rgb(0,49,94));
        canvas.drawCircle(n.getPosition().x, n.getPosition().y, n.getRadius(), nodePaint);


        nodePaint = new Paint();
        nodePaint.setStyle(Paint.Style.STROKE);
        nodePaint.setStrokeWidth(5.0f);
        nodePaint.setColor(n.getOutline());
        canvas.drawCircle(n.getPosition().x, n.getPosition().y, n.getRadius(), nodePaint);
    }

    private void drawConnection(Canvas canvas,Node n1,Node n2)
    {
        int x1 = n1.getPosition().x;
        int y1 = n1.getPosition().y;
        int x2 = n2.getPosition().x;
        int y2 = n2.getPosition().y;

        int r1 = n1.getRadius();
        int r2 = n2.getRadius();

        int kx = 1;
        int ky = 1;
        if(x1 > x2)
            kx = -1;
        if(y1 > y2)
            ky = -1;

        // Time for fun trig
        double theta = Math.atan(Math.abs(y2-y1)/Math.abs(x2-x1));

        float realX1 = x1+kx*r1*(float)Math.cos(theta);
        float realY1 = y1+ky*r1*(float)Math.sin(theta);

        float realX2 = x2-kx*r2*(float)Math.cos(theta);
        float realY2 = y2-ky*r2*(float)Math.sin(theta);

        Paint connectionPaint = new Paint();
        connectionPaint.setColor(Color.rgb(0,49,94));
        connectionPaint.setStrokeWidth(5.0f);
        canvas.drawLine(realX1,realY1,realX2,realY2,connectionPaint);

        /*Paint dotPaint = new Paint();
        dotPaint.setColor(Color.RED);
        canvas.drawCircle(realX1,realY1,4,dotPaint);
        canvas.drawCircle(realX2,realY2,4,dotPaint);*/



    }

    public boolean onTouchEvent(MotionEvent ev)
    {
        gestureController.onTouchEvent(ev);
        mScaleDetector.onTouchEvent(ev);
        invalidate();

        return true;
    }

    // http://stackoverflow.com/questions/5216658/pinch-zoom-for-custom-view
    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            mScaleFactor *= detector.getScaleFactor();

            // Don't let the object get too small or too large.
            mScaleFactor = Math.max(0.1f, Math.min(mScaleFactor, 5.0f));

            return true;
        }
    }

}
