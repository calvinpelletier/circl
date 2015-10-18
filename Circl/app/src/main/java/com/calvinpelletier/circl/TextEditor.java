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

    private Button editButton;
    private Button closeButton;

    // The sidebar editor toggling buttons, to switch between editing windows
    private TextView content_sb;
    private TextView style_sb;
    private TextView settings_sb;
    private TextView delete_sb;

    private TextView [] layoutModeButtons;

    // The layouts for each of the four "modes"
    private RelativeLayout contentLayout;
    private RelativeLayout styleLayout;
    private RelativeLayout settingsLayout;
    private RelativeLayout deleteLayout;

    private RelativeLayout [] layoutModes;

    private TextView saveButton;

    public TextEditor(MainActivity context)
    {
        this.context = context;
    }

    public void open(TextNode textNode)
    {
        context.setContentView(R.layout.text_editor);
        this.sideBar = (RelativeLayout)context.findViewById(R.id.sideBar);

        this.titleEditor = (EditText)context.findViewById(R.id.contentTitle);
        this.editor = (EditText)context.findViewById(R.id.editor);

        this.content_sb = (TextView)context.findViewById(R.id.content_sb);
        this.style_sb = (TextView)context.findViewById(R.id.style_sb);
        this.settings_sb = (TextView)context.findViewById(R.id.settings_sb);
        this.delete_sb = (TextView)context.findViewById(R.id.delete_sb);

        layoutModeButtons = new TextView[4];
        layoutModeButtons[0] = content_sb;
        layoutModeButtons[1] = style_sb;
        layoutModeButtons[2] = settings_sb;
        layoutModeButtons[3] = delete_sb;

        this.contentLayout = (RelativeLayout)context.findViewById(R.id.contentLayout);
        this.styleLayout = (RelativeLayout)context.findViewById(R.id.styleLayout);
        this.settingsLayout = (RelativeLayout)context.findViewById(R.id.settingsLayout);
        this.deleteLayout = (RelativeLayout)context.findViewById(R.id.deleteLayout);

        layoutModes = new RelativeLayout[4];

        layoutModes[0] = contentLayout;
        layoutModes[1] = styleLayout;
        layoutModes[2] = settingsLayout;
        layoutModes[3] = deleteLayout;


        content_sb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayLayout(0);
            }
        });

        style_sb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayLayout(1);
            }
        });

        settings_sb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayLayout(2);
            }
        });

        delete_sb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayLayout(3);
            }
        });

        // On edit click, slide in - for now i'm just gonna do it

        editButton = (Button)context.findViewById(R.id.editButton);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               slideInSideBar();
            }
        });

        saveButton = (TextView)context.findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slideOutSideBar();
            }
        });


        closeButton = (Button)context.findViewById(R.id.closeButton);
        // At some point, we'll have an on click listener to close the editor.



        if(textNode != null)
        {
            titleEditor.setText(textNode.title);
            editor.setText(textNode.content);
        }
        else
        {
            titleEditor.setText("");
            editor.setText("");
            slideInSideBar();
        }
    }

    private void displayLayout(int layoutIdx)
    {
        for(int i = 0;i < 4;i++)
        {
            RelativeLayout currLayout = layoutModes[i];
            TextView currToggleButton = layoutModeButtons[i];
            if(i == layoutIdx)
            {
                // Display this
                currLayout.setVisibility(View.VISIBLE);
                currToggleButton.setBackgroundResource(R.color.blue);
            }
            else
            {
                // Hide this
                currLayout.setVisibility(View.GONE);
                currToggleButton.setBackgroundResource(R.color.lightBlue);
            }
        }
    }

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
        sideBar.setVisibility(View.VISIBLE);
        editable = true;

        makeEditable(titleEditor);
        makeEditable(editor);
        editButton.setVisibility(View.GONE);
        closeButton.setVisibility(View.GONE);
        /*
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
                editButton.setVisibility(View.GONE);
                closeButton.setVisibility(View.GONE);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        */
    }

    private void slideOutSideBar()
    {
        sideBar.setVisibility(View.GONE);
        editable = false;
        makeNotEditable(titleEditor);
        makeNotEditable(editor);

        // Automatically hide the keyboard in case they had it open
        // If we don't do this, they can still edit the content
        InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editor.getWindowToken(), 0);

        editButton.setVisibility(View.VISIBLE);
        closeButton.setVisibility(View.VISIBLE);

        // also set to main content view.
        displayLayout(0);
    }
        /*
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

                editButton.setVisibility(View.VISIBLE);
                closeButton.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
    */
}
