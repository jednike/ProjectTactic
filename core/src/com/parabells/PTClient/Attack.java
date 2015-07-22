package com.parabells.PTClient;

public class Attack extends DefaultCommand{
    public int attackedID;
    public int attackerID;

    public Attack(int attackedID, int attackerID){
        name = "Attack";
        this.attackedID = attackedID;
        this.attackerID = attackerID;
    }
}
