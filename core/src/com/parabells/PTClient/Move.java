package com.parabells.PTClient;

import java.util.ArrayList;

public class Move extends DefaultCommand{
	public float x;
	public float y;
	public int targetID;
	public ArrayList<Integer> mobs;

	public Move(float x, float y, int targetID, ArrayList<Integer> mobs){
		name = "Move";
		this.x = x;
		this.y = y;
		this.targetID = targetID;
		this.mobs = mobs;
	}
}
