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
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;



public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final RelativeLayout mainLayout = (RelativeLayout)findViewById(R.id.mainLayout);
        final RelativeLayout pvContainer = (RelativeLayout)findViewById(R.id.pvContainer);
        PalaceView p = new PalaceView(this);

        pvContainer.addView(p);


        View hamburger = findViewById(R.id.hamburgerMenu);
        hamburger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater mInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View view;
                final View fadeView = createFadeView();
                /* We inflate the xml which gives us a view */
                view = mInflater.inflate(R.layout.overlay_menu,null, false);

                mainLayout.addView(fadeView);
                mainLayout.addView(view);

                Button closeButton = (Button)view.findViewById(R.id.buttonX);
                closeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mainLayout.removeView(view);
                        mainLayout.removeView(fadeView);
                    }
                });



            }
        });
    }

    private View createFadeView()
    {
        View view = new View(this);
        view.setBackgroundColor(Color.argb(196, 0, 0, 0));
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        view.setLayoutParams(params);

        return view;
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
}
