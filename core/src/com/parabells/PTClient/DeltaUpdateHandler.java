package com.parabells.PTClient;

import com.parabells.PTGame.PTGame;
import com.parabells.PTGameWorld.GameWorld;

public class DeltaUpdateHandler extends CommandHandler {
    public DeltaUpdateHandler(PTGame game, GameWorld gameWorld) {
        super(game, gameWorld);
    }

    @Override
    public void Handle(String text) {
        DeltaUpdate command = game.json.fromJson(text, DeltaUpdate.class);
        gameWorld.deltaUpdate(command.id, command.xPosition, command.yPosition);
    }
}
