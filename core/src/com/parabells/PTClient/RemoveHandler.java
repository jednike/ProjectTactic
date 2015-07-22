package com.parabells.PTClient;

import com.parabells.PTGame.PTGame;
import com.parabells.PTGameWorld.GameWorld;

public class RemoveHandler extends CommandHandler {

    public RemoveHandler(PTGame game, GameWorld gameWorld) {
        super(game, gameWorld);
    }

    @Override
    public void Handle(String text) {
        Remove command = game.json.fromJson(text, Remove.class);
        gameWorld.removeObject(command.id);
    }
}
