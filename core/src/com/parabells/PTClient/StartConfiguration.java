package com.parabells.PTClient;

import java.util.ArrayList;

public class StartConfiguration extends DefaultCommand{
	public ArrayList<Add> add;
	public int receiverID;
	public int levelWidth;
	public int levelHeight;

	public StartConfiguration(ArrayList<Add> add, int receiverID, int levelWidth, int levelHeight){
		name ="StartConfiguration";
		this.add = add;
		this.levelWidth = levelWidth;
		this.levelHeight = levelHeight;
		this.receiverID = receiverID;
	}
}
