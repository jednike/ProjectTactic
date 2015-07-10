package com.parabells.PTGameObjects;

/**
 * Class for mob(point)
 */
public class Mob extends SuperFigure {
    private float HP;

    /**
     * Constructor
     * @param x - xPosition
     * @param y - yPosition
     * @param radius - radius
     * @param HP - HP's mob
     * @param hostName - player's name
     */
    public Mob(float x, float y, float radius, float HP, String hostName) {
        super(x, y, radius, hostName);
        this.HP = HP;
    }

    /**
     * Getter for HP mob
     * @return - current HP
     */
    public float getHP() {
        return HP;
    }
}
