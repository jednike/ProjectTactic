package com.parabells.PTClient;

import com.parabells.PTGame.PTGame;
import com.parabells.PTGameWorld.GameWorld;

public abstract class CommandHandler {
	public PTGame game;
	public GameWorld gameWorld;
	
	public CommandHandler(PTGame game, GameWorld gameWorld){
		this.game = game;
		this.gameWorld = gameWorld;
	}
	
	abstract public void Handle(String text);
}