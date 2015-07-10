package com.parabells.PTGameObjects;

import com.badlogic.gdx.math.Circle;

public class SuperFigure {
    private Circle figure;
    private float stepX, stepY;
    private float newX, newY;
    private String hostName;
    private Boolean isSelected;
    private Boolean isMove;

    public SuperFigure(float x, float y, float radius, String hostName){
        figure = new Circle(x, y, radius);
        isSelected = false;
        isMove = false;
        this.hostName = hostName;
    }

    public Circle getFigure() {
        return figure;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public void setIsSelected(Boolean isSelected) {
        this.isSelected = isSelected;
    }

    public Boolean getIsSelected() {
        return isSelected;
    }

    public float getStepX() {
        return stepX;
    }

    public void setStepX(float stepX) {
        this.stepX = stepX;
    }

    public float getStepY() {
        return stepY;
    }

    public void setStepY(float stepY) {
        this.stepY = stepY;
    }

    public Boolean getIsMove() {
        return isMove;
    }

    public void setIsMove(Boolean isMove) {
        this.isMove = isMove;
    }

    public float getNewX() {
        return newX;
    }

    public void setNewX(float newX) {
        this.newX = newX;
    }

    public float getNewY() {
        return newY;
    }

    public void setNewY(float newY) {
        this.newY = newY;
    }
}
