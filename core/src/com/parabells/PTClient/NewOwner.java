package com.parabells.PTClient;

public class NewOwner extends DefaultCommand {
    public int id;
    public int ownerID;

    public NewOwner(int id, int ownerID){
        name = "NewOwner";
        this.id = id;
        this.ownerID = ownerID;
    }
}
