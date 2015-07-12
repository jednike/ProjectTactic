package com.parabells.PTGameWorld;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.parabells.PTCommands.MovM;
import com.parabells.PTHelpers.Circle;
import com.parabells.PTGame.PTGame;
import com.parabells.PTGameObjects.Mob;
import com.parabells.PTGameObjects.Planet;
import com.parabells.PTGameObjects.SuperFigure;

import java.util.Stack;

/**
 * Base game methods class
 */
public class GameWorld {
    private PTGame game;
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
        timeToRespawn = 1;

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
            if(onlyEnemiesNearPlanet(planet)){
                if(!planet.getHostName().equals("NEUTRAL")) {
                    if (planet.getTimeToControl() >= 0) {
                        planet.setTimeToControl(planet.getTimeToControl() - delta);
                    } else {
                        if(planet.getHostName().equals(playerName) && planet.getIsSelected()){
                            planetSelected--;
                        }
                        planet.setHostName("NEUTRAL");
                        planet.setColor(Color.BLUE);
                        planet.setTimeToControl(5);
                    }
                } else{
                    if(!whoIsInvader(planet).equals("")){
                        if (planet.getTimeToControl() >= 0) {
                            planet.setTimeToControl(planet.getTimeToControl() - delta);
                        } else {
                            planet.setHostName(whoIsInvader(planet));
                            planet.setColor(Color.GREEN);
                            planet.setTimeToControl(5);
                        }
                    }
                }
            } else {
                moving(planet);
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
            moving(mob);
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
     * Check if player's mobs near planet
     * @param planet - planet
     * @return - true, if only enemies
     */
    private Boolean onlyEnemiesNearPlanet(Planet planet){
        int count = 0;
        for(Mob mob: mobs){
            if(mob.getFigure().overlaps(new Circle(planet.getFigure().x, planet.getFigure().y, 2*planet.getFigure().radius)) && mob.getHostName().equals(planet.getHostName())){
                return false;
            } else if(mob.getFigure().overlaps(new Circle(planet.getFigure().x, planet.getFigure().y, 2*planet.getFigure().radius)) && !mob.getHostName().equals(planet.getHostName())){
                count++;
            }
        }
        if(count > 0){
            return true;
        }
        return false;
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
                if(invader.equals("")){
                    invader = mob.getHostName();
                } else if(!invader.equals(mob.getHostName())){
                    return "";
                }
            }
        }
        return invader;
    }

    /**
     * Method for moving to target
     * @param superFigure - object for moving
     */
    private void moving(SuperFigure superFigure) {
        if (superFigure.getIsMove()) {
            if(superFigure.getTarget() == null) {
                if (!superFigure.getFigure().overlaps(new Circle(superFigure.getNewX(), superFigure.getNewY(), superFigure.getFigure().radius))) {
                    superFigure.getFigure().x += superFigure.getStepX();
                    superFigure.getFigure().y += superFigure.getStepY();
                }
            } else {
                if (!superFigure.getFigure().overlaps(superFigure.getTarget().getFigure())) {
                    superFigure.getFigure().x += (superFigure.getTarget().getFigure().x - superFigure.getFigure().x)/200;
                    superFigure.getFigure().y += (superFigure.getTarget().getFigure().y - superFigure.getFigure().y)/200;
                }
            }
        }
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
                float k = (newX - mob.getFigure().x)/Math.abs(newY - mob.getFigure().y);
                mob.setStepX((newX - mob.getFigure().x)/200);
                mob.setStepY((newY - mob.getFigure().y)/200);
                mob.setIsSelected(false);
                mob.setNewX(newX);
                mob.setNewY(newY);
                mob.setTarget(target);
                mob.setIsMove(true);
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
                float k = (newX - planet.getFigure().x)/Math.abs(newY - planet.getFigure().y);
                planet.setStepX((newX - planet.getFigure().x)/200);
                planet.setStepY((newY - planet.getFigure().y)/200);
                planet.setIsSelected(false);
                planet.setNewX(newX);
                planet.setNewY(newY);
                planet.setTarget(target);
                planet.setIsMove(true);
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
        if(checkOnTouch(helpCircle) == null){
            if(mobSelected != 0) {
                moveToPoint(screenX, screenY, null, true);
            }
            if(planetSelected != 0){
                movePlanetToPoint(screenX, screenY, null, true);
            }
        } else if(checkOnTouch(helpCircle) instanceof Planet) {
            Planet planet = (Planet) checkOnTouch(helpCircle);
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
        } else if(checkOnTouch(helpCircle) instanceof Mob) {
            Mob mob = (Mob) checkOnTouch(helpCircle);
            if (mob.getHostName().equals(playerName)) {
                mobSelected++;
                mob.setIsSelected(true);
            } else{
                if(mobSelected != 0) {
                    moveToPoint(mob.getFigure().x, mob.getFigure().y, mob, true);
                }
                if(planetSelected != 0) {
                    movePlanetToPoint(mob.getFigure().x, mob.getFigure().y, mob, true);
                }
            }
        }
    }

    /**
     * Check on hit on object
     * @param helpCircle - help figure for detect on hit
     * @return - object or null
     */
    private SuperFigure checkOnTouch(Circle helpCircle){
        for(Mob mob: mobs){
            if(mob.getFigure().overlaps(helpCircle)){
                return mob;
            }
        }
        for (Planet planet: planets){
            if(planet.getFigure().overlaps(helpCircle)){
                return planet;
            }
        }
        return null;
    }
}
