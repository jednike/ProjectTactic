package com.parabells.PTClient;

import com.parabells.PTGameObjects.Mob;
import com.parabells.PTGameObjects.Planet;

import java.util.ArrayList;
import java.util.Map;

public class Setp extends DefaultCommand{
	public Map<Integer, Mob> mobs;
	public Map<Integer, Planet> planets;
	public int receiverID;

	public Setp(Map<Integer, Mob> mobs, Map<Integer, Planet> planets, int receiverID){
		name ="Setp";
		this.planets = planets;
		this.mobs = mobs;
		this.receiverID = receiverID;
	}
}
