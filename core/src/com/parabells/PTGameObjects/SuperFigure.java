package com.parabells.PTGameObjects;

import com.parabells.PTGameWorld.GameWorld;
import com.parabells.PTHelpers.Circle;

/**
 * Parent class for game objects
 */
public class SuperFigure {
    private Circle figure;
    private float newX, newY;
    private int targetID;
    private int ownerID;
    private boolean isSelected;
    private boolean isMove;

    /**
     * Constructor
     * @param x - xPosition
     * @param y - yPosition
     * @param radius - radius
     * @param ownerID - player who owns the object
     */
    public SuperFigure(float x, float y, float radius, int ownerID){
        this.ownerID = ownerID;
        figure = new Circle(x, y, radius);
        isSelected = false;
        isMove = false;
        targetID = -1;
    }

    public void update(GameWorld gameWorld, float delta){
        for(Mob mob: gameWorld.getMobs().values()) {
            if(mob.getOwnerID() == ownerID){
                if(mob != this && figure.overlaps(mob.getFigure())){
                    if(mob.getFigure().x >= figure.x){
                        mob.getFigure().x++;
                    } else if(mob.getFigure().x < figure.x){
                        mob.getFigure().x--;
                    }
                    if(mob.getFigure().y >= figure.y){
                        mob.getFigure().y++;
                    } else if(mob.getFigure().y < figure.y){
                        mob.getFigure().y--;
                    }
                }
            }
        }
        if (isMove) {
            if(targetID == -1) {
                if (!figure.overlaps(new Circle(newX, newY, gameWorld.getPlanetRadius()) )) {
                    figure.x += (newX - figure.x)/200;
                    figure.y += (newY - figure.y)/200;
                } else {
                    isMove = false;
                }
            } else {
                Circle targetFigure = gameWorld.getPlanets().containsKey(targetID)?gameWorld.getPlanets().get(targetID).getFigure():gameWorld.getMobs().get(targetID).getFigure();
                if (targetFigure != null && !figure.overlaps(targetFigure)) {
                    figure.x += (targetFigure.x - figure.x) / 200;
                    figure.y += (targetFigure.y - figure.y) / 200;
                } else {
                    isMove = false;
                }
            }
        }
    }

    public void setNextPosition(float newX, float newY, int targetID){
        if(isSelected){
            isSelected = false;
            this.targetID = targetID;
            if(targetID == -1) {
                this.newX = newX;
                this.newY = newY;
            } else {
                this.targetID = targetID;
            }
            isMove = true;
        }
    }

    /**
     * Getter for circle
     * @return - circle
     */
    public Circle getFigure() {
        return figure;
    }

    public int getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(int ownerID) {
        this.ownerID = ownerID;
    }

    /**
     * Setter for isSelelcted
     * @param isSelected - if an object is selected
     */
    public void setIsSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    /**
     * Getter for isSelected
     * @return - if an object is selected
     */
    public boolean getIsSelected() {
        return isSelected;
    }
}
