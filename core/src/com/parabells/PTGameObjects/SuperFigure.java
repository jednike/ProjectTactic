package com.parabells.PTGameObjects;

import com.badlogic.gdx.math.Circle;

/**
 * Parent class for game objects
 */
public class SuperFigure {
    private Circle figure;
    private float stepX, stepY;
    private float newX, newY;
    private SuperFigure target;
    private String hostName;
    private Boolean isSelected;
    private Boolean isMove;
    public int ID;

    /**
     * Constructor
     * @param x - xPosition
     * @param y - yPosition
     * @param radius - radius
     * @param hostName - player who owns the object
     */
    public SuperFigure(int ID, float x, float y, float radius, String hostName){
        this.ID = ID;
        figure = new Circle(x, y, radius);
        isSelected = false;
        isMove = false;
        this.hostName = hostName;
    }

    /**
     * Getter for circle
     * @return - circle
     */
    public Circle getFigure() {
        return figure;
    }

    /**
     * Getter for player's name
     * @return - player's name
     */
    public String getHostName() {
        return hostName;
    }

    /**
     * Setter for player's name
     * @param hostName - player's name
     */
    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    /**
     * Setter for isSelelcted
     * @param isSelected - if an object is selected
     */
    public void setIsSelected(Boolean isSelected) {
        this.isSelected = isSelected;
    }

    /**
     * Getter for isSelected
     * @return - if an object is selected
     */
    public Boolean getIsSelected() {
        return isSelected;
    }

    /**
     * Getter for step x
     * @return - step x
     */
    public float getStepX() {
        return stepX;
    }

    /**
     * Setter for step x
     * @param stepX - step x
     */
    public void setStepX(float stepX) {
        this.stepX = stepX;
    }

    /**
     * Getter for step y
     * @return - step y
     */
    public float getStepY() {
        return stepY;
    }

    /**
     * Setter for step y
     * @param stepY - step y
     */
    public void setStepY(float stepY) {
        this.stepY = stepY;
    }

    /**
     * Getter for isMove
     * @return - true if moving
     */
    public Boolean getIsMove() {
        return isMove;
    }

    /**
     * Setter for isMove
     * @param isMove - move or not move
     */
    public void setIsMove(Boolean isMove) {
        this.isMove = isMove;
    }

    /**
     * Getter for target x position
     * @return - target x position
     */
    public float getNewX() {
        return newX;
    }

    /**
     * Setter for target x position
     * @param newX - target x position
     */
    public void setNewX(float newX) {
        this.newX = newX;
    }

    /**
     * Getter for target y position
     * @return - target y position
     */
    public float getNewY() {
        return newY;
    }

    /**
     * Setter for target y position
     * @param newY - target y position
     */
    public void setNewY(float newY) {
        this.newY = newY;
    }

    /**
     * Setter for new target
     * @param target - new target
     */
    public void setTarget(SuperFigure target) {
        this.target = target;
    }

    /**
     * Getter for current target
     * @return - current target
     */
    public SuperFigure getTarget() {
        return target;
    }

    /**
     * Getter for ID
     * @return - ID
     */
    public int getID() {
        return ID;
    }
}
