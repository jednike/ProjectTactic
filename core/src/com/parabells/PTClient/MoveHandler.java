package com.parabells.PTClient;

import com.parabells.PTGame.PTGame;
import com.parabells.PTGameWorld.GameWorld;

public class MoveHandler extends CommandHandler {

	public MoveHandler(PTGame game, GameWorld gameWorld) {
		super(game, gameWorld);
	}

	@Override
	public void Handle(String text) {
		Move command = game.json.fromJson(text, Move.class);

		gameWorld.setSelectedID(command.mobs);
		gameWorld.moveToPoint(command.x, command.y, command.targetID, false);
	}
}
