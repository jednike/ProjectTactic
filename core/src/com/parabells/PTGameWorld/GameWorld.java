package com.parabells.PTGameWorld;

import com.badlogic.gdx.math.Circle;
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
    private Stack<Planet> planets;
    private Stack<Mob> mobs;
    private String playerName;
    private Boolean isSelected;
    private int size = 20;

    /**
     * Constructor
     * @param game - super game class
     */
    public GameWorld(PTGame game){
        this.game = game;
        playerName = "PLAYER1";
        startGame();
    }

    /**
     * Start actions(Server part!)
     */
    private void startGame(){
        planets = new Stack<Planet>();
        isSelected = false;
        planets.push(new Planet(game.getScreenWidth()/10, game.getScreenHeight()/2, 50, "PLAYER1"));
        planets.push(new Planet(game.getScreenWidth()/2, game.getScreenHeight()/2, 50, "NEUTRAL"));
        planets.push(new Planet(9*game.getScreenWidth()/10, game.getScreenHeight()/2, 50, "PLAYER2"));
        mobs = new Stack<Mob>();
        for (int i = 0; i < planets.size(); i++){
            addMobsToPlanet(i);
        }
    }

    /**
     * Generate mobs on planet(Server part!)
     * @param numberPlanet
     */
    private void addMobsToPlanet(int numberPlanet){
        for(int i = 0; i < size; i++){
            double angle = (2*Math.PI)/size*i;
            float radius = planets.get(numberPlanet).getFigure().radius + 5;
            float posX = (float) (planets.get(numberPlanet).getFigure().x + radius*Math.cos(angle));
            float posY = (float) (planets.get(numberPlanet).getFigure().y + radius*Math.sin(angle));
            mobs.push(new Mob(posX, posY, 5, 100, planets.get(numberPlanet).getHostName()));
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
            moving(planet);
        }
        for (Mob mob: mobs){
            moving(mob);
        }
        planets.get(1).getFigure().x += 0.1;
        planets.get(1).getFigure().y += 0.1;
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
    private void moveToPoint(float newX, float newY, SuperFigure target){
        for(Planet mob: planets){
            if(mob.getIsSelected()){
                float k = (newX - mob.getFigure().x)/Math.abs(newY - mob.getFigure().y);
                mob.setStepX(k);
                mob.setStepY(newY - mob.getFigure().y > 0 ? 1:-1);
                mob.setIsSelected(false);
                mob.setNewX(newX);
                mob.setNewY(newY);
                mob.setTarget(target);
                mob.setIsMove(true);
            }
        }
        for(Mob mob: mobs){
            if(mob.getIsSelected()){
                float k = (newX - mob.getFigure().x)/Math.abs(newY - mob.getFigure().y);
                mob.setStepX(k);
                mob.setStepY(newY - mob.getFigure().y > 0 ? 1:-1);
                mob.setIsSelected(false);
                mob.setNewX(newX);
                mob.setNewY(newY);
                mob.setTarget(target);
                mob.setIsMove(true);
            }
        }
    }

    /**
     * Click on game field
     * @param screenX - screen position click X
     * @param screenY - screen position click Y
     */
    public void clickOnWorld(int screenX, int screenY) {
        screenY = (int) (game.getScreenHeight() - screenY);
        Circle helpCircle = new Circle(screenX, screenY, 1);
        if(checkOnTouch(helpCircle) == null && isSelected){
            moveToPoint(screenX, screenY, null);
        } else if(checkOnTouch(helpCircle) instanceof Planet) {
            Planet planet = (Planet) checkOnTouch(helpCircle);
            if (planet.getHostName().equals(playerName)) {
                isSelected = true;
                planet.setIsSelected(true);
            } else{
                moveToPoint(planet.getFigure().x, planet.getFigure().y, planet);
            }
        } else if(checkOnTouch(helpCircle) instanceof Mob) {
            Mob mob = (Mob) checkOnTouch(helpCircle);
            if (mob.getHostName().equals(playerName)) {
                isSelected = true;
                mob.setIsSelected(true);
            } else{
                moveToPoint(mob.getFigure().x, mob.getFigure().y, mob);
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
