package com.parabells.PTClient;

import com.parabells.PTGameObjects.SuperFigure;

import java.util.ArrayList;

public class Move extends DefaultCommand{
	public float x;
	public float y;
	public SuperFigure superFigure;
	public ArrayList<Integer> mobs;

	public Move(float x, float y, SuperFigure superFigure, ArrayList<Integer> mobs){
		name = "Move";
		this.x = x;
		this.y = y;
		this.superFigure = superFigure;
		this.mobs = mobs;
	}
}
