package com.parabells.PTClient;

import com.parabells.PTGameObjects.Mob;
import com.parabells.PTGameObjects.Planet;

import java.util.ArrayList;
import java.util.Map;

public class Delt extends DefaultCommand{
	public Map<Integer, Mob> mobs;
	public Map<Integer, Planet> planets;

	public Delt(Map<Integer, Mob> mobs, Map<Integer, Planet> planets){
		name ="Delt";
		this.mobs = mobs;
		this.planets = planets;
	}
}
