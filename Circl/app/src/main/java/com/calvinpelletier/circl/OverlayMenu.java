package com.calvinpelletier.circl;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**
 * Created by wilson on 9/27/15.
 */
public class OverlayMenu {

    public static void fadeIn(final Context context, final RelativeLayout container)
    {
        LayoutInflater mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View view;
        final View fadeView = createFadeView(context);
        /* We inflate the xml which gives us a view */
        view = mInflater.inflate(R.layout.overlay_menu,null, false);

        container.addView(fadeView);
        container.addView(view);

        fadeIn(context, container, view);
        fadeIn(context,container,fadeView);

        Button closeButton = (Button)view.findViewById(R.id.buttonX);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fadeOut(context,container,view);
                fadeOut(context,container,fadeView);
            }
        });


    }

    private static void fadeIn(Context context, RelativeLayout container, final View view)
    {
        view.setVisibility(View.INVISIBLE);
        final Animation fadeInAnimation = AnimationUtils.loadAnimation(context, R.anim.fade_in);
        // Now Set your animation
        view.startAnimation(fadeInAnimation);
        view.startAnimation(fadeInAnimation);

        fadeInAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private static void fadeOut(Context context,RelativeLayout container,final View view)
    {
        Animation fadeOutAnimation = AnimationUtils.loadAnimation(context, R.anim.fade_out);
        // Now Set your animation
        view.startAnimation(fadeOutAnimation);

        fadeOutAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private static View createFadeView(Context context)
    {
        View view = new View(context);
        view.setBackgroundColor(Color.argb(196, 0, 0, 0));
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        view.setLayoutParams(params);

        view.setVisibility(View.INVISIBLE);

        return view;
    }
}
