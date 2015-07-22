package com.parabells.PTGameObjects;

import com.badlogic.gdx.graphics.Color;
import com.parabells.PTGameWorld.GameWorld;
import com.parabells.PTHelpers.Utils;

/**
 * Class for planet
 */
public class Planet extends SuperFigure{
    private float timeToControl;
    private float timeToRespawn;
    private int invader;
    private boolean isNewOwner;
    private boolean isNewMobRespawn;

    /**
     * Constructor
     * @param x - xPosition
     * @param y - yPosition
     * @param radius - radius
     * @param ownerID - player who owns the object
     */
    public Planet(float x, float y, float radius, float timeToControl, float timeToRespawn, int ownerID) {
        super(x, y, radius, ownerID);
        this.timeToControl = timeToControl;
        this.timeToRespawn = timeToRespawn;
        isNewMobRespawn = false;
        isNewOwner = false;
    }

    @Override
    public void update(GameWorld gameWorld, float delta) {
        super.update(gameWorld, delta);
    }

    public boolean isNewOwner() {
        return isNewOwner;
    }
}
