package com.parabells.PTClient;

public class Add extends DefaultCommand {
    public float x;
    public float y;
    public float radius;
    public int id;
    public int ownerID;

    public Add(int id, int ownerID, float x, float y, float radius){
        name = "Add";
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.id = id;
        this.ownerID = ownerID;
    }
}
