package com.parabells.PTGameWorld;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.parabells.PTGame.PTGame;
import com.parabells.PTGameObjects.Mob;
import com.parabells.PTGameObjects.Planet;
import com.parabells.PTGameObjects.SuperFigure;
import com.parabells.PTHelpers.Circle;
import com.parabells.PTHelpers.Utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

/**
 * Base game methods class
 */
public class GameWorld {private PTGame game;
    private Stack<Vector2> bulletFrom, bulletTo;
    private ArrayList<Planet> planets;
    private ArrayList<Mob> mobs;
    private ArrayList<Integer> selectedID;
    private int playerID;
    private boolean isSelected;
    private float mobRadius, HP, reloadTime, attackRadius, damage;
    private float planetRadius, timeToControl, timeToRespawn;
    private int size = 20;

    /**
     * Constructor
     * @param game - super game class
     */
    public GameWorld(PTGame game){
        this.game = game;
        bulletFrom = new Stack<Vector2>();
        bulletTo = new Stack<Vector2>();
        isSelected = false;
        //Mob's variables
        mobRadius = 5*game.getScreenWidth()/1024;
        HP = 100;
        reloadTime = 5;
        attackRadius = 50*game.getScreenWidth()/1024;
        damage = 50;

        //Planet's variables
        planetRadius = 30*game.getScreenWidth()/1024;
        timeToControl = 5;
        timeToRespawn = 0.01f;

        playerID = 1;
        startGame();
    }

    /**
     * Start actions(Server part!)
     */
    private void startGame(){
        planets = new ArrayList<Planet>();
        selectedID = new ArrayList<Integer>();
        planets.add(new Planet(0, game.getScreenWidth()/10, game.getScreenHeight()/2, planetRadius, timeToControl, timeToRespawn, 1));
        planets.add(new Planet(1, game.getScreenWidth()/2, game.getScreenHeight()/2, planetRadius, timeToControl, timeToRespawn, 2));
        planets.add(new Planet(2, 9 * game.getScreenWidth() / 10, game.getScreenHeight() / 2, planetRadius, timeToControl, timeToRespawn, Utils.NEUTRAL_OWNER_ID));
        mobs = new ArrayList<Mob>();
        for (int i = 0; i < planets.size(); i++){
            addMobsToPlanet(i);
        }
    }

    /**
     * Generate mobs on planet(Server part!)
     * @param numberPlanet - size planet's
     */
    private void addMobsToPlanet(int numberPlanet){
        for(int i = 0; i < size; i++){
            double angle = (2*Math.PI)/size*i;
            float radius = planets.get(numberPlanet).getFigure().radius + 2*mobRadius;
            float posX = (float) (planets.get(numberPlanet).getFigure().x + radius*Math.cos(angle));
            float posY = (float) (planets.get(numberPlanet).getFigure().y + radius*Math.sin(angle));
            mobs.add(new Mob(mobs.size(), posX, posY, mobRadius, HP, reloadTime, attackRadius, damage, planets.get(numberPlanet).getOwnerID()));
        }
    }

    public float getReloadTime() {
        return reloadTime;
    }

    public float getTimeToRespawn() {
        return timeToRespawn;
    }

    public float getTimeToControl() {
        return timeToControl;
    }

    public int getPlayerID() {
        return playerID;
    }

    /**
     * Getter for stack's mob
     * @return - stack's mob(for render)
     */
    public List<Mob> getMobs() {
        return mobs;
    }

    /**
     * Getter for stack planets
     * @return - stack's planets(for render)
     */
    public List<Planet> getPlanets() {
        return planets;
    }

    /**
     * Update method
     * @param delta - delta time
     */
    public void update(float delta) {
        for (Planet planet : planets) {
            planet.update(this, delta);
            if(planet.isNewMobRespawn()){
                respawnToPlanet(planet);
            }
            planet.setInvader(whoIsInvader(planet));
        }
        Iterator<Mob> iter = mobs.iterator();
        while (iter.hasNext()) {
            Mob mob = iter.next();
            if(mob.isRemove()){
                iter.remove();
            } else {
                mob.update(this, delta);
            }
        }
    }

    private void respawnToPlanet(Planet planet){
        float radius = planet.getFigure().radius + 2*mobRadius;
        boolean isAdded = false;
        int number = 0;
        while(!isAdded) {
            if(number%size==0){
                radius += 4*mobRadius;
            }
            isAdded = true;
            double angle = (2 * Math.PI) / size * number;
            float posX = (float) (planet.getFigure().x + radius * Math.cos(angle));
            float posY = (float) (planet.getFigure().y + radius * Math.sin(angle));
            for(Mob mob: mobs){
                if(mob.getFigure().overlaps(new Circle(posX, posY, mobRadius))){
                    number++;
                    isAdded = false;
                    break;
                }
            }
            if(isAdded) {
                mobs.add(new Mob(mobs.size(), posX, posY, mobRadius, HP, reloadTime, attackRadius, damage, planet.getOwnerID()));
            }
        }

    }

    /**
     * Getter for mass shot(start)
     * @return - mass shot
     */
    public Stack<Vector2> getBulletFrom() {
        return bulletFrom;
    }

    /**
     * Getter for mass shot(end)
     * @return - mass shot
     */
    public Stack<Vector2> getBulletTo() {
        return bulletTo;
    }

    /**
     * If near planet only one type enemy return his name
     * @param planet - our planet
     * @return - enemy name
     */
    private int whoIsInvader(Planet planet){
        int invader = Utils.NEUTRAL_OWNER_ID;
        for (Mob mob: mobs){
            if(mob.getFigure().overlaps(new Circle(planet.getFigure().x, planet.getFigure().y, 2 * planet.getFigure().radius))){
                if(mob.getOwnerID() == planet.getOwnerID()){
                    return Utils.NEUTRAL_OWNER_ID;
                }
                if(invader == Utils.NEUTRAL_OWNER_ID){
                    invader = mob.getOwnerID();
                } else if(invader != mob.getOwnerID()){
                    return Utils.NEUTRAL_OWNER_ID;
                }
            }
        }
        return invader;
    }

    /**
     * Move object to free point or follow for target
     * @param newX - target position x
     * @param newY - target position y
     * @param target - target
     */
    private void moveToPoint(float newX, float newY, SuperFigure target, boolean itIsMine){
        for(Mob mob: mobs){
            if(itIsMine) {
                selectedID.add(mob.getID());
                if (mob.getIsSelected()) {
                    mob.setNextPosition(newX, newY, target);
                }
            } else {
                if(selectedID.contains(mob.getID())){
                    mob.setNextPosition(newX, newY,target);
                }
            }
        }
        for(Planet planet: planets){
            if(itIsMine) {
                selectedID.add(planet.getID());
                if (planet.getIsSelected()) {
                    planet.setNextPosition(newX, newY, target);
                }
            } else {
                if(selectedID.contains(planet.getID())){
                    planet.setNextPosition(newX, newY,target);
                }
            }
        }
        if(!itIsMine) {
            selectedID.clear();
        }
        isSelected = false;
    }

    /**
     * Click on game field
     * @param screenX - screen position click X
     * @param screenY - screen position click Y
     */
    public void clickOnWorld(int screenX, int screenY) {
        screenY = (int) (game.getScreenHeight() - screenY);
        Circle helpCircle = new Circle(screenX, screenY, 2);
        if(!checkOnTouch(helpCircle)){
            if(isSelected) {
                moveToPoint(screenX, screenY, null, true);
            }
        }
    }

    /**
     * Check on hit on object
     * @param helpCircle - help figure for detect on hit
     */
    private boolean checkOnTouch(Circle helpCircle){
        boolean isSelect = false;
        for(Mob mob: mobs){
            if(mob.getFigure().overlaps(helpCircle)) {
                if (mob.getOwnerID() == playerID) {
                    isSelected = true;
                    mob.setIsSelected(true);
                } else {
                    if (isSelected) {
                        moveToPoint(mob.getFigure().x, mob.getFigure().y, mob, true);
                    }
                }
                isSelect = true;
            }
        }
        for (Planet planet: planets){
            if(planet.getFigure().overlaps(helpCircle)){
                if (planet.getOwnerID() == playerID) {
                    isSelected = true;
                    planet.setIsSelected(true);
                } else{
                    if(isSelected) {
                        moveToPoint(planet.getFigure().x, planet.getFigure().y, planet, true);
                    }
                }
                isSelect = true;
            }
        }
        return isSelect;
    }
}
