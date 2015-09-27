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

    private enum Mode
    {
        NONE,
        PANNING
    }

    public PalaceView(Context context)
    {
        super(context);

        mScaleDetector = new ScaleGestureDetector(context, new ScaleListener());
        gestureController = new PVGestureController();
    }

    private Canvas canvas;

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.save();
        canvas.scale(mScaleFactor, mScaleFactor);
        canvas.translate(gestureController.getTranslateX(),gestureController.getTranslateY());

        // Just drawing a blue circle for the time being, so I can test panning and zooming
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setColor(Color.BLUE);
        canvas.drawCircle(50, 50, 200, p);


        canvas.restore();
    }

    // http://stackoverflow.com/questions/25941097/android-pinch-zoom-and-panning-for-canvas-something-doesnt-work
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
