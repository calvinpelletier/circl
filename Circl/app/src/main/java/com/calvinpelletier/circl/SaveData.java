package com.calvinpelletier.circl;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;

/**
 * Created by Saketh on 9/26/15.
 */
public class SaveData {

    private String filename;
    private Context ctx;
    private FileOutputStream fos;
    private ObjectOutputStream oos;
    private FileInputStream fis;
    private ObjectInputStream ois;

    public SaveData() {
        this.ctx = null;
        this.filename = "test";
        this.fos = null;
        this.oos = null;
        this.fis = null;
        this.ois = null;
    }

    public void saveNode(Node node) throws FileNotFoundException, IOException {
        try {
            this.fos = this.ctx.openFileOutput(this.filename, this.ctx.MODE_PRIVATE);
            this.oos = new ObjectOutputStream(this.fos);
            this.oos.writeObject(node);
            oos.close();
            this.fos.close();
        }
    }

    public void loadNode(Node node) throws FileNotFoundException, StreamCorruptedException, IOException, ClassNotFoundException{
        try {
            this.fis = this.ctx.openFileInput(this.filename);
            this.ois = new ObjectInputStream(this.fis);
            Node restart = (Node) this.ois.readObject();
            this.ois.close();
            this.fis.close();
        }

    }



}
