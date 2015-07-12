package com.parabells.PTCommands;

public class MovM extends DefaultCommand {
    public int x;
    public int y;
    public int[] mobs;

    public MovM(int x, int y, int[] mobs){
        this.x = x;
        this.y = y;
        this.mobs = mobs;
    }
}
