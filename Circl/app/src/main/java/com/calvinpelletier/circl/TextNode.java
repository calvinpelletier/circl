package com.calvinpelletier.circl;

/**
 * Created by cpelletier on 9/26/15.
 */

// Object for any node with *text* associated with it
public class TextNode extends Node {

    public String title = "";
    public String content = "";

    public TextNode(Coord position, int outlineColor, String title, String content) {
        super(position, outlineColor);
        this.title = title;
        this.content = content;
    }

    public void onTap() {
        TextEditor editor = new TextEditor((MainActivity)palace.getContext());
        editor.open(this,true);
    }
}
