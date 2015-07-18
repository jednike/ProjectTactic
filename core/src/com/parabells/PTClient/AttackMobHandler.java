package com.parabells.PTClient;

import com.parabells.PTGame.PTGame;
import com.parabells.PTGameWorld.GameWorld;

public class AttackMobHandler extends CommandHandler{

	public AttackMobHandler(PTGame game, GameWorld gameWorld) {
		super(game, gameWorld);
	}

	@Override
	public void Handle(String text) {
		System.out.println("Command attack by mob:" + text);
	}
}
