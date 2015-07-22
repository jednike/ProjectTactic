package com.parabells.PTClient;

import com.parabells.PTGame.PTGame;
import com.parabells.PTGameWorld.GameWorld;

public class NewOwnerHandler extends CommandHandler {
    public NewOwnerHandler(PTGame game, GameWorld gameWorld) {
        super(game, gameWorld);
    }

    @Override
    public void Handle(String text) {
        NewOwner command = game.json.fromJson(text, NewOwner.class);
        gameWorld.getPlanets().get(command.id).setOwnerID(command.ownerID);
    }
}
