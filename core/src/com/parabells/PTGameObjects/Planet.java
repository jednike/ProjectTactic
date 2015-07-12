package com.parabells.PTGameObjects;

import com.badlogic.gdx.graphics.Color;

/**
 * Class for planet
 */
public class Planet extends SuperFigure{
    private float timeToControl;
    private float timeToRespawn;
    private int numberMobs;

    /**
     * Constructor
     * @param x - xPosition
     * @param y - yPosition
     * @param radius - radius
     * @param hostName - player who owns the object
     */
    public Planet(int ID, float x, float y, float radius, float timeToControl, float timeToRespawn, int numberMobs, String hostName,  Color color) {
        super(ID, x, y, radius, hostName, color);
        this.timeToControl = timeToControl;
        this.timeToRespawn = timeToRespawn;
        this.numberMobs = numberMobs;
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

    /**
     * Getter for numbers mobs near planet
     * @return - number mobs near planet
     */
    public int getNumberMobs() {
        return numberMobs;
    }

    /**
     * Setter for numbers mobs near planet
     * @param numberMobs - numbers mobs near planet
     */
    public void setNumberMobs(int numberMobs) {
        this.numberMobs = numberMobs;
    }
}
