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

    /**
     * Time for grab planet
     * @return - left time
     */
    public float getTimeToControl() {
        return timeToControl;
    }

    /**
     * Time for grab planet
     * @param timeToControl - time to grab
     */
    public void setTimeToControl(float timeToControl) {
        this.timeToControl = timeToControl;
    }

    /**
     * Getter for time of respawn mob on planet
     * @return - time of respawn
     */
    public float getTimeToRespawn() {
        return timeToRespawn;
    }

    /**
     * Setter for time of respawn mob on planet
     * @param timeToRespawn - new time of respawn
     */
    public void setTimeToRespawn(float timeToRespawn) {
        this.timeToRespawn = timeToRespawn;
    }

    public boolean isNewMobRespawn() {
        return isNewMobRespawn;
    }

    public void setInvader(int invader) {
        this.invader = invader;
    }

    public boolean isNewOwner() {
        return isNewOwner;
    }
}
