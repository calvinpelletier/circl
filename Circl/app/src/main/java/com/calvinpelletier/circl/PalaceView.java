package com.calvinpelletier.circl;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

// The overarching view that will use a Canvas to display the "mind palace"
public class PalaceView extends View {

    // A bunch of constants for drawing nodes and connections
    private final int FILL_COLOR = Color.rgb(0,49,94);
    private final float STROKE_WIDTH = 5.f;

    private final Paint nodePaintFill = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint nodePaintStroke = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint connectionPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    // Used for managing zoom
    private ScaleGestureDetector mScaleDetector;
    private float mScaleFactor = 1.f;

    // Used for managing panning
    PVGestureController gestureController;

    public PalaceView(Context context)
    {
        super(context);

        mScaleDetector = new ScaleGestureDetector(context, new ScaleListener());
        gestureController = new PVGestureController(400,400);

        nodePaintFill.setColor(FILL_COLOR);

        connectionPaint.setColor(FILL_COLOR);
        connectionPaint.setStrokeWidth(STROKE_WIDTH);

        nodePaintStroke.setStyle(Paint.Style.STROKE);
        nodePaintStroke.setStrokeWidth(STROKE_WIDTH);
    }

    private Canvas canvas;

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.save();
        canvas.scale(mScaleFactor, mScaleFactor);
        canvas.translate(gestureController.getTranslateX(), gestureController.getTranslateY());

        Node n1 = new Node(new Coord(100,100), Color.YELLOW);
        Node n2 = new Node(new Coord(490,120), Color.GREEN);
        Node n3 = new Node(new Coord(400,500), Color.MAGENTA);
        Node n4 = new Node(new Coord(200,300), Color.RED);

        drawNode(canvas,n1);
        drawNode(canvas,n2);
        drawNode(canvas,n3);
        drawNode(canvas,n4);

        drawConnection(canvas, n2, n3);
        drawConnection(canvas, n1, n3);
        drawConnection(canvas,n1,n4);

        canvas.restore();
    }

    private void drawNode(Canvas canvas,Node n)
    {
        canvas.drawCircle(n.getPosition().x, n.getPosition().y, n.getRadius(), nodePaintFill);

        nodePaintStroke.setColor(n.getOutline());
        canvas.drawCircle(n.getPosition().x, n.getPosition().y, n.getRadius(), nodePaintStroke);
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

        canvas.drawLine(realX1,realY1,realX2,realY2,connectionPaint);
    }

    public boolean onTouchEvent(MotionEvent ev)
    {
        gestureController.onTouchEvent(ev);
        mScaleDetector.onTouchEvent(ev);
        invalidate();

        return true;
    }

    // http://stackoverflow.com/questions/5216658/pinch-zoom-for-custom-view
    // this facilitates scaling the canvas when a user "pinches"
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
