package com.parabells.PTClient;

import com.parabells.PTGame.PTGame;
import com.parabells.PTGameObjects.Mob;
import com.parabells.PTGameWorld.GameWorld;

public class AddHandler extends CommandHandler {
    public AddHandler(PTGame game, GameWorld gameWorld) {
        super(game, gameWorld);
    }

    @Override
    public void Handle(String text) {
        Add command = game.json.fromJson(text, Add.class);
        gameWorld.addNewObject(command.id, command.x, command.y, command.radius, command.ownerID);
    }
}
