package com.parabells.PTGameObjects;

import com.badlogic.gdx.math.Circle;

public class Mob extends SuperFigure {
    private float HP;

    public Mob(float x, float y, float radius, float HP, String hostName) {
        super(x, y, radius, hostName);
        this.HP = HP;
    }

    public float getHP() {
        return HP;
    }
}
