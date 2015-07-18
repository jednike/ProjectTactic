package com.parabells.PTClient;

import com.parabells.PTGameObjects.Mob;
import com.parabells.PTGameObjects.Planet;

import java.util.ArrayList;

public class Delt extends DefaultCommand{
	public ArrayList<Mob> mobs;
	public ArrayList<Planet> planets;

	public Delt(ArrayList<Mob> mobs, ArrayList<Planet> planets){
		name ="Delt";
		this.mobs = mobs;
		this.planets = planets;
	}
}
