package com.parabells.PTGameObjects;

import com.badlogic.gdx.math.Circle;

/**
 * Class for planet
 */
public class Planet extends SuperFigure{
    private float timeToControl;

    /**
     * Constructor
     * @param x - xPosition
     * @param y - yPosition
     * @param radius - radius
     * @param hostName - player who owns the object
     */
    public Planet(int ID, float x, float y, float radius, float timeToControl, String hostName) {
        super(ID, x, y, radius, hostName);
        this.timeToControl = timeToControl;
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
}
