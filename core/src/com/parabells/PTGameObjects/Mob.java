package com.parabells.PTGameObjects;

import com.parabells.PTGameWorld.GameWorld;

/**
 * Class for mob(point)
 */
public class Mob extends SuperFigure {
    private float HP;
    private float damage;
    private float reloadTime;
    private float atackRadius;
    private boolean isRemove;
    private int attackedMob;

    /**
     * Constructor
     * @param x - xPosition
     * @param y - yPosition
     * @param radius - radius
     * @param HP - HP's mob
     * @param ownerID - owner id
     */
    public Mob(float x, float y, float radius, float HP, float reloadTime, float atackRadius, float damage, int ownerID) {
        super(x, y, radius, ownerID);
        this.HP = HP;
        this.damage = damage;
        this.atackRadius = atackRadius;
        attackedMob = -1;
    }

    @Override
    public void update(GameWorld gameWorld, float delta) {
        super.update(gameWorld, delta);
    }

    public void setAttackedMob(int attackerMob) {
        this.attackedMob = attackerMob;
    }

    public int getAttackedMob() {
        return attackedMob;
    }
}
