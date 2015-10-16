package com.calvinpelletier.circl;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import java.util.ArrayList;

// The overarching view that will use a Canvas to display the "mind palace"
public class PalaceView extends View {

    private MainActivity mainActivity;

    private Viewport viewport; //viewport is what the user sees on their screen

    public int width = 400;
    public int height = 400;

    // A bunch of constants for drawing nodes, connections, and instructions
    private final int FILL_COLOR = Color.rgb(0,49,94);
    private final float STROKE_WIDTH = 5.f;
    private final Paint nodePaintFill = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint nodePaintStroke = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint connectionPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    // Used for managing zoom
    private ScaleGestureDetector mScaleDetector;

    //temporary storage for nodes and connections
    public ArrayList<Node> nodeArray = new ArrayList<Node>();
    public ArrayList<Connection> connectionArray = new ArrayList<Connection>();

    public PalaceView(Context context)
    {
        super(context);

        mainActivity = (MainActivity) context;

        viewport = new Viewport(this);

        nodePaintFill.setColor(FILL_COLOR);
        connectionPaint.setColor(FILL_COLOR);
        connectionPaint.setStrokeWidth(STROKE_WIDTH);
        nodePaintStroke.setStyle(Paint.Style.STROKE);
        nodePaintStroke.setStrokeWidth(STROKE_WIDTH);

        mScaleDetector = new ScaleGestureDetector(context, new ScaleListener());
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        //adds nodes for debugging purposes
        tempInitialization();
    }

    //~~~MENU FUNCTIONS~~~
    public void startAddNode() {
        viewport.setPlacingNode(true);
        mainActivity.findViewById(R.id.tapToPlaceNode).setVisibility(View.VISIBLE);
        mainActivity.findViewById(R.id.hamburgerMenu).setVisibility(View.GONE);
    }
    public void addNode(Coord pos) {
        mainActivity.findViewById(R.id.tapToPlaceNode).setVisibility(View.GONE);
        mainActivity.findViewById(R.id.hamburgerMenu).setVisibility(View.VISIBLE);
        nodeArray.add(new Node(pos, Color.BLACK));
        invalidate();
    }

    public void startAddConnection() {
        viewport.startAddConnection();
        mainActivity.findViewById(R.id.tapFirstNode).setVisibility(View.VISIBLE);
        mainActivity.findViewById(R.id.hamburgerMenu).setVisibility(View.GONE);
    }
    public void startAddConnection2() {
        mainActivity.findViewById(R.id.tapFirstNode).setVisibility(View.GONE);
        mainActivity.findViewById(R.id.tapSecondNode).setVisibility(View.VISIBLE);
    }
    public void addConnection(Node node1, Node node2) {
        mainActivity.findViewById(R.id.tapSecondNode).setVisibility(View.GONE);
        mainActivity.findViewById(R.id.hamburgerMenu).setVisibility(View.VISIBLE);
        if (node1 == node2) {
            return;
        }
        for (int i = 0; i < connectionArray.size(); i++) {
            Node a = connectionArray.get(i).getNodeA();
            Node b = connectionArray.get(i).getNodeB();
            if ((a == node1 && b == node2) || (a == node2 && b == node1)) { //check that a connection doesn't already exist
                return;
            }
        }
        connectionArray.add(new Connection(node1, node2));
        invalidate();
    }
    //~~~~~~

    //~~~DRAWING FUNCTIONS~~~
    private Canvas canvas;
    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.save();
        canvas.scale(viewport.getScale(), viewport.getScale());
        canvas.translate(-1 * viewport.getPosition().x, -1 * viewport.getPosition().y);

        for (int i = 0; i < nodeArray.size(); i++) {
            drawNode(canvas, nodeArray.get(i));
        }
        for (int i = 0; i < connectionArray.size(); i++) {
            drawConnection(canvas, connectionArray.get(i).getNodeA(), connectionArray.get(i).getNodeB());
        }

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
        float x1 = n1.getPosition().x;
        float y1 = n1.getPosition().y;
        float x2 = n2.getPosition().x;
        float y2 = n2.getPosition().y;

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

        canvas.drawLine(realX1, realY1, realX2, realY2, connectionPaint);
    }
    //~~~~~~

    //~~~USER INTERACTION~~~
    public boolean onTouchEvent(MotionEvent ev)
    {
        viewport.onTouchEvent(ev); //viewport handles most of the interaction
        mScaleDetector.onTouchEvent(ev);
        invalidate();
        return true;
    }

    // http://stackoverflow.com/questions/5216658/pinch-zoom-for-custom-view
    // this facilitates scaling the canvas when a user "pinches"
    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            viewport.onScale(detector); //viewport handles most of the interaction
            return true;
        }
    }
    //~~~~~~

    //TODO: remove when user can add their own nodes. for debugging purposes only
    private void tempInitialization() {
        Node n1 = new Node(new Coord(100,100), Color.YELLOW);
        Node n2 = new Node(new Coord(490,120), Color.GREEN);
        Node n3 = new Node(new Coord(400,500), Color.MAGENTA);
        Node n4 = new Node(new Coord(200,300), Color.RED);
        nodeArray.add(n1);
        nodeArray.add(n2);
        nodeArray.add(n3);
        nodeArray.add(n4);
    }
}
