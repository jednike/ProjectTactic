package com.parabells.PTGameObjects;

import com.badlogic.gdx.math.Circle;

public class Planet extends SuperFigure{
    private float timeToControl;

    public Planet(float x, float y, float radius, String hostName) {
        super(x, y, radius, hostName);
    }

    public float getTimeToControl() {
        return timeToControl;
    }

    public void setTimeToControl(float timeToControl) {
        this.timeToControl = timeToControl;
    }
}
