package com.calvinpelletier.circl;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by wilson on 10/15/15.
 */
public class TextEditor {
    private RelativeLayout sideBar;
    private MainActivity context;

    private boolean editable = false;

    private EditText titleEditor;
    private EditText editor;

    // The sidebar editor toggling buttons, to switch between editing windows
    private TextView content_sb;
    private TextView style_sb;
    private TextView settings_sb;
    private TextView delete_sb;

    // The layouts for each of the four "modes"
    private RelativeLayout contentLayout;
    private RelativeLayout styleLayout;
    private RelativeLayout settingsLayout;
    private RelativeLayout deleteLayout;

    public TextEditor(MainActivity context)
    {
        this.context = context;
    }

    public void open(TextNode textNode)
    {
        context.setContentView(R.layout.text_editor);
        this.sideBar = (RelativeLayout)context.findViewById(R.id.sideBar);

        this.titleEditor = (EditText)context.findViewById(R.id.editText);
        this.editor = (EditText)context.findViewById(R.id.editor);

        this.content_sb = (TextView)context.findViewById(R.id.content_sb);
        this.style_sb = (TextView)context.findViewById(R.id.style_sb);
        this.settings_sb = (TextView)context.findViewById(R.id.settings_sb);
        this.delete_sb = (TextView)context.findViewById(R.id.delete_sb);

        this.contentLayout = (RelativeLayout)context.findViewById(R.id.contentLayout);
        this.styleLayout = (RelativeLayout)context.findViewById(R.id.styleLayout);
        this.settingsLayout = (RelativeLayout)context.findViewById(R.id.settingsLayout);
        this.deleteLayout = (RelativeLayout)context.findViewById(R.id.deleteLayout);

        titleEditor.setText(textNode.title);
        editor.setText(textNode.content);

        content_sb.setOnClickListener(displayContentEditor);
        style_sb.setOnClickListener(displayStyleEditor);
        settings_sb.setOnClickListener(displaySettingsEditor);
        delete_sb.setOnClickListener(displayDeleteEditor);

        // On edit click, slide in - for now i'm just gonna do it

        Button editButton = (Button)context.findViewById(R.id.button6);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editable)
                    slideOutSideBar();
                else
                    slideInSideBar();
            }
        });
    }

    private View.OnClickListener displayContentEditor = new View.OnClickListener() {
        @Override
        public void onClick(final View v) {
            contentLayout.setVisibility(View.VISIBLE);
            styleLayout.setVisibility(View.GONE);
            settingsLayout.setVisibility(View.GONE);
            deleteLayout.setVisibility(View.GONE);
        }
    };

    private View.OnClickListener displayStyleEditor = new View.OnClickListener() {
        @Override
        public void onClick(final View v) {
            contentLayout.setVisibility(View.GONE);
            styleLayout.setVisibility(View.VISIBLE);
            settingsLayout.setVisibility(View.GONE);
            deleteLayout.setVisibility(View.GONE);
        }
    };

    private View.OnClickListener displaySettingsEditor = new View.OnClickListener() {
        @Override
        public void onClick(final View v) {
            contentLayout.setVisibility(View.GONE);
            styleLayout.setVisibility(View.GONE);
            settingsLayout.setVisibility(View.VISIBLE);
            deleteLayout.setVisibility(View.GONE);
        }
    };

    private View.OnClickListener displayDeleteEditor = new View.OnClickListener() {
        @Override
        public void onClick(final View v) {
            contentLayout.setVisibility(View.GONE);
            styleLayout.setVisibility(View.GONE);
            settingsLayout.setVisibility(View.GONE);
            deleteLayout.setVisibility(View.VISIBLE);
        }
    };

    private void makeEditable(EditText e)
    {
        e.setClickable(true);
        e.setCursorVisible(true);
        e.setFocusable(true);
        e.setFocusableInTouchMode(true);
    }

    private void makeNotEditable(EditText e)
    {
        e.setClickable(false);
        e.setCursorVisible(false);
        e.setFocusable(false);
        e.setFocusableInTouchMode(false);

        e.clearFocus();

    }

    private void slideInSideBar()
    {
        sideBar.setVisibility(View.INVISIBLE);
        final Animation fadeInAnimation = AnimationUtils.loadAnimation(context, R.anim.slidein_editor);
        // Now Set your animation
        sideBar.startAnimation(fadeInAnimation);

        fadeInAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                sideBar.setVisibility(View.VISIBLE);
                editable = true;

                makeEditable(titleEditor);
                makeEditable(editor);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private void slideOutSideBar()
    {
        final Animation fadeOutAnimation = AnimationUtils.loadAnimation(context, R.anim.slideout_editor);
        // Now Set your animation
        sideBar.startAnimation(fadeOutAnimation);

        fadeOutAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                sideBar.setVisibility(View.GONE);
                editable = false;
                makeNotEditable(titleEditor);
                makeNotEditable(editor);

                // Automatically hide the keyboard in case they had it open
                // If we don't do this, they can still edit the content
                InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(editor.getWindowToken(), 0);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}
