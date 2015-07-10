package com.parabells.PTGameObjects;

/**
 * Class for mob(point)
 */
public class Mob extends SuperFigure {
    private float HP;
    private float damage;

    /**
     * Constructor
     * @param x - xPosition
     * @param y - yPosition
     * @param radius - radius
     * @param HP - HP's mob
     * @param hostName - player's name
     */
    public Mob(int ID, float x, float y, float radius, float HP, float damage, String hostName) {
        super(ID, x, y, radius, hostName);
        this.HP = HP;
        this.damage = damage;
    }

    /**
     * Getter for HP mob
     * @return - current HP
     */
    public float getHP() {
        return HP;
    }

    /**
     * Getter for damage
     * @return - gamage
     */
    public float getDamage() {
        return damage;
    }
}
