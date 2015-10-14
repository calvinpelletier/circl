package com.calvinpelletier.circl;

import android.content.Context;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wilson on 10/13/15.
 */
public class User implements java.io.Serializable {
    private transient Context ctx;
    private List<Node> nodes;

    public User(Context ctx)
    {
        this.ctx = ctx;
        this.nodes = new ArrayList<Node>();
    }

    public void addNode(Node n)
    {
        this.nodes.add(n);
    }

    public int getNumNodes()
    {
        return nodes.size();
    }

    public Node getNode(int idx)
    {
        return nodes.get(idx);
    }

    public void storeData() throws IOException
    {
        SaveData dataStorageEngine = new SaveData(this.ctx);
        dataStorageEngine.save(this);
    }

    public static User getData(Context ctx) throws IOException, ClassNotFoundException
    {
        SaveData dataStorageEngine = new SaveData(ctx);
        return dataStorageEngine.load();
    }
}
