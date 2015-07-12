package com.parabells.PTCommands;

public class MovM extends DefaultCommand {
    public float x;
    public float y;
    public int[] mobs;

    public MovM(float x, float y, int[] mobs){
        this.x = x;
        this.y = y;
        this.mobs = mobs;
    }
}
