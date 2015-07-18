package com.parabells.PTClient;

import com.parabells.PTGame.PTGame;
import com.parabells.PTGameWorld.GameWorld;

public class PingHandler extends CommandHandler {

	public PingHandler(PTGame game, GameWorld gameWorld) {
		super(game, gameWorld);
	}

	@Override
	public void Handle(String text) {
		PingCommand command = game.json.fromJson(text, PingCommand.class);
		//gameWorld.setPing(command.From);
	}

}
