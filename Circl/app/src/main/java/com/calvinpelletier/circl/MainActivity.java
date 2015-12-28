package com.calvinpelletier.circl;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;


public class MainActivity extends ActionBarActivity {

    private PalaceView palace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    public void init()
    {
        setContentView(R.layout.activity_main);

        final RelativeLayout mainLayout = (RelativeLayout)findViewById(R.id.mainLayout);
        final RelativeLayout pvContainer = (RelativeLayout)findViewById(R.id.pvContainer);
        palace = new PalaceView(this);

        pvContainer.addView(palace);

        final MainActivity m = this;


        View hamburger = findViewById(R.id.hamburgerMenu);
        hamburger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OverlayMenu.fadeIn(m,mainLayout);
            }
        });

        View trash = findViewById(R.id.trashIcon);
        trash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                palace.deleteSelectedNode();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public PalaceView getPalace() {
        return palace;
    }
}
