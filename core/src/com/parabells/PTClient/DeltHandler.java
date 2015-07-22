package com.parabells.PTClient;

import com.parabells.PTGame.PTGame;
import com.parabells.PTGameWorld.GameWorld;

public class DeltHandler extends CommandHandler {

    public DeltHandler(PTGame game, GameWorld gameWorld){
        super(game, gameWorld);
    }

    @Override
    public void Handle(String text){
        Delt command = game.json.fromJson(text, Delt.class);
    }
}
