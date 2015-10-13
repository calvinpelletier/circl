package com.calvinpelletier.circl;

import org.w3c.dom.Text;

/**
 * Created by cpelletier on 9/26/15.
 */
public class TextNode extends Node {

    public String title = "";
    public String content = "";

    public TextNode(Coord position, int outlineColor) {
        super(position, outlineColor);
    }

}
