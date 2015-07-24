package com.parabells.PTClient;

import java.util.ArrayList;

public class DeltaUpdate extends DefaultCommand {
    public ArrayList<Integer> id;
    public ArrayList<Float> xPosition;
    public ArrayList<Float> yPosition;

    public DeltaUpdate(ArrayList<Integer> id, ArrayList<Float> xPosition, ArrayList<Float> yPosition){
        name = "DeltaUpdate";
        this.id = id;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
    }
}
