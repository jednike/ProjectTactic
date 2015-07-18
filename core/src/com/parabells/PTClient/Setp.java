package com.parabells.PTClient;

import com.parabells.PTGameObjects.Mob;
import com.parabells.PTGameObjects.Planet;

import java.util.ArrayList;

public class Setp extends DefaultCommand{
	public ArrayList<Mob> mobs;
	public ArrayList<Planet> planets;
	public int receiverID;

	public Setp(ArrayList<Mob> mobs, ArrayList<Planet> planets, int receiverID){
		name ="Setp";
		this.planets = planets;
		this.mobs = mobs;
		this.receiverID = receiverID;
	}
}
