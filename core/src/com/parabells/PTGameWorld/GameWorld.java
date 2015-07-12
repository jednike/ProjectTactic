package com.parabells.PTGameWorld;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.parabells.PTCommands.MovM;
import com.parabells.PTHelpers.Circle;
import com.parabells.PTGame.PTGame;
import com.parabells.PTGameObjects.Mob;
import com.parabells.PTGameObjects.Planet;
import com.parabells.PTGameObjects.SuperFigure;

import java.util.HashMap;
import java.util.Stack;

/**
 * Base game methods class
 */
public class GameWorld {private PTGame game;
    private Stack<Vector2> bulletFrom, bulletTo;
    private String messageText;
    private Stack<Planet> planets;
    private Stack<Mob> mobs;
    private String playerName;
    private int mobSelected, planetSelected;
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
        //Mob's variables
        mobRadius = 5;
        HP = 100;
        reloadTime = 5;
        attackRadius = 50;
        damage = 10;

        //Planet's variables
        planetRadius = 30;
        timeToControl = 5;
        timeToRespawn = 2;

        playerName = "PLAYER1";
        startGame();
    }

    /**
     * Start actions(Server part!)
     */
    private void startGame(){
        planets = new Stack<Planet>();
        mobSelected = 0;
        planetSelected = 0;
        planets.push(new Planet(0, game.getScreenWidth()/10, game.getScreenHeight()/2, planetRadius, timeToControl, timeToRespawn, 0, "PLAYER1", Color.GREEN));
        planets.push(new Planet(1, game.getScreenWidth()/2, game.getScreenHeight()/2, planetRadius, timeToControl, timeToRespawn, 0, "NEUTRAL", Color.BLUE));
        planets.push(new Planet(2, 9*game.getScreenWidth()/10, game.getScreenHeight()/2, planetRadius, timeToControl, timeToRespawn, 0, "PLAYER2", Color.RED));
        mobs = new Stack<Mob>();
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
            mobs.push(new Mob(mobs.size(), posX, posY, mobRadius, HP, reloadTime, attackRadius, damage, planets.get(numberPlanet).getHostName(), planets.get(numberPlanet).getColor()));
        }
    }

    /**
     * Getter for stack's mob
     * @return - stack's mob(for render)
     */
    public Stack<Mob> getMobs() {
        return mobs;
    }

    /**
     * Getter for stack planets
     * @return - stack's planets(for render)
     */
    public Stack<Planet> getPlanets() {
        return planets;
    }

    /**
     * Update method
     * @param delta - delta time
     */
    public void update(float delta) {
        for (Planet planet: planets){
            if(!planet.getHostName().equals("NEUTRAL")) {
                if (planet.getTimeToRespawn() >= 0) {
                    planet.setTimeToRespawn(planet.getTimeToRespawn() - delta);
                } else {
                    respawnToPlanet(planet);
                    planet.setTimeToRespawn(timeToRespawn);
                }
            }
            String invader = whoIsInvader(planet);
            if(!invader.equals("NOBODY")){
                if(!planet.getHostName().equals("NEUTRAL")) {
                    if (planet.getTimeToControl() >= 0) {
                        planet.setTimeToControl(planet.getTimeToControl() - delta);
                    } else {
                        if(planet.getHostName().equals(playerName) && planet.getIsSelected()){
                            planetSelected--;
                        }
                        planet.setIsMove(false);
                        planet.setHostName("NEUTRAL");
                        planet.setColor(Color.BLUE);
                        planet.setTimeToControl(5);
                    }
                } else {
                    if (planet.getTimeToControl() >= 0) {
                        planet.setTimeToControl(planet.getTimeToControl() - delta);
                    } else {
                        planet.setHostName(invader);
                        planet.setColor(Color.GREEN);
                        planet.setTimeToControl(5);
                    }
                }
            } else {
                planet.moving();
            }
        }
        for (Mob mob: mobs){
            if(mob.getReloadTime() >= 0){
                mob.setReloadTime(mob.getReloadTime() - delta);
            } else{
                for(Mob attackMob: mobs){
                    if(attackMob.getFigure().overlaps(new Circle(mob.getFigure().x, mob.getFigure().y, mob.getAtackRadius())) && !attackMob.getHostName().equals(mob.getHostName())) {
                        bulletFrom.push(new Vector2(mob.getFigure().x, mob.getFigure().y));
                        bulletTo.push(new Vector2(attackMob.getFigure().x, attackMob.getFigure().y));
                        attackMob.setHP(attackMob.getHP() - mob.getDamage());
                        mob.setReloadTime(reloadTime);
                        if(attackMob.getHP() < 0){
                            if(mob.getHostName().equals(playerName) && mob.getIsSelected()){
                                mobSelected--;
                            }
                            mobs.remove(attackMob);
                            return;
                        }
                    }
                }
            }
            mob.moving();
        }
    }

    private void respawnToPlanet(Planet planet){
        float radius = planet.getFigure().radius + 2*mobRadius;
        Boolean isAdded = false;
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
                mobs.push(new Mob(mobs.size(), posX, posY, mobRadius, HP, reloadTime, attackRadius, damage, planet.getHostName(), planet.getColor()));
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
    private String whoIsInvader(Planet planet){
        String invader = "";
        for (Mob mob: mobs){
            if(mob.getFigure().overlaps(new Circle(planet.getFigure().x, planet.getFigure().y, 2*planet.getFigure().radius))){
                if(mob.getHostName().equals(planet.getHostName())){
                    return "NOBODY";
                }
                if(invader.equals("")){
                    invader = mob.getHostName();
                } else if(!invader.equals(mob.getHostName())){
                    return "NOBODY";
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
    private void moveToPoint(float newX, float newY, SuperFigure target, Boolean itIsMine){
        int selected[] = new int[mobSelected];
        Boolean isFirst = true;
        int i = -1;
        if(itIsMine) {
            String command;
            if (target == null) {
                command = "MovM" + (int) newX + "-" + (int) newY;
            } else {
                command = "FolM" + (target instanceof Planet ? "P" : "M") + target.getID();
            }
            messageText = "From" + 1 + command + ":";
        }
        for(Mob mob: mobs){
            if(mob.getIsSelected()){
                if(itIsMine) {
                    if (isFirst) {
                        messageText += mob.getID();
                        isFirst = false;
                    } else {
                        messageText += "," + mob.getID();
                    }
                    i++;
                    selected[i] = mob.getID();
                }
                mob.setNextPosition(newX, newY, target);
            }
        }
        if(itIsMine) {
            MovM movM = new MovM(newX, newY, selected);
            System.out.println(messageText);
        }
        mobSelected = 0;
    }

    private void movePlanetToPoint(float newX, float newY, SuperFigure target, Boolean itIsMine){
        int selected[] = new int[planetSelected];
        int i = -1;
        String command;
        Boolean isFirst = true;
        if(itIsMine) {
            if (target == null) {
                command = "MovP" + (int) newX + "-" + (int) newY;
            } else {
                command = "FolP" + (target instanceof Planet ? "P" : "M") + target.getID();
            }
            messageText = "From" + 1 + command + ":";
        }
        for(Planet planet: planets){
            if(planet.getIsSelected()){
                if(itIsMine) {
                    if (isFirst) {
                        messageText += planet.getID();
                        isFirst = false;
                    } else {
                        messageText += "," + planet.getID();
                    }
                    i++;
                    selected[i] = planet.getID();
                }
                planet.setNextPosition(newX, newY, target);
            }
        }
        if(itIsMine) {
            System.out.println(messageText);
        }
        planetSelected = 0;
    }

    /**
     * Click on game field
     * @param screenX - screen position click X
     * @param screenY - screen position click Y
     */
    public void clickOnWorld(int screenX, int screenY) {
        screenY = (int) (game.getScreenHeight() - screenY);
        Circle helpCircle = new Circle(screenX, screenY, 1);
        if(!checkOnTouch(helpCircle)){
            if(mobSelected != 0) {
                moveToPoint(screenX, screenY, null, true);
            }
            if(planetSelected != 0){
                movePlanetToPoint(screenX, screenY, null, true);
            }
        }
    }

    /**
     * Check on hit on object
     * @param helpCircle - help figure for detect on hit
     */
    private Boolean checkOnTouch(Circle helpCircle){
        Boolean isSelected = false;
        for(Mob mob: mobs){
            if(mob.getFigure().overlaps(helpCircle)) {
                isSelected = true;
                if (mob.getHostName().equals(playerName)) {
                    mobSelected++;
                    mob.setIsSelected(true);
                } else {
                    if (mobSelected != 0) {
                        moveToPoint(mob.getFigure().x, mob.getFigure().y, mob, true);
                    }
                    if (planetSelected != 0) {
                        movePlanetToPoint(mob.getFigure().x, mob.getFigure().y, mob, true);
                    }
                }
            }
        }
        for (Planet planet: planets){
            if(planet.getFigure().overlaps(helpCircle)){
                isSelected = true;
                if (planet.getHostName().equals(playerName)) {
                    planetSelected++;
                    planet.setIsSelected(true);
                } else{
                    if(planetSelected != 0) {
                        movePlanetToPoint(planet.getFigure().x, planet.getFigure().y, planet, true);
                    }
                    if(mobSelected != 0) {
                        moveToPoint(planet.getFigure().x, planet.getFigure().y, planet, true);
                    }
                }
            }
        }
        return isSelected;
    }
}
