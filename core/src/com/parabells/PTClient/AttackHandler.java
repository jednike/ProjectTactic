package com.parabells.PTClient;

import com.parabells.PTGame.PTGame;
import com.parabells.PTGameWorld.GameWorld;

public class AttackHandler extends CommandHandler {

    public AttackHandler(PTGame game, GameWorld gameWorld){
        super(game, gameWorld);
    }

    @Override
    public void Handle(String text) {
        Attack command = game.json.fromJson(text, Attack.class);
        gameWorld.getMobs().get(command.attackerID).setAttackedMob(command.attackedID);
    }
}
