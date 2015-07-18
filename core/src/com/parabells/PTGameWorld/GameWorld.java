package com.parabells.PTGameWorld;

import com.badlogic.gdx.math.Vector2;
import com.parabells.PTClient.CommandHandler;
import com.parabells.PTClient.DefaultCommand;
import com.parabells.PTClient.Delt;
import com.parabells.PTClient.DeltHandler;
import com.parabells.PTClient.Move;
import com.parabells.PTClient.MoveHandler;
import com.parabells.PTClient.PingHandler;
import com.parabells.PTClient.Setp;
import com.parabells.PTGame.PTGame;
import com.parabells.PTGameObjects.Mob;
import com.parabells.PTGameObjects.Planet;
import com.parabells.PTGameObjects.SuperFigure;
import com.parabells.PTHelpers.Circle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Stack;

/**
 * Base game methods class
 */
public class GameWorld {private PTGame game;
    private Stack<Vector2> bulletFrom, bulletTo;
    private ArrayList<Planet> planets, deltPlanets;
    private ArrayList<Mob> mobs, deltMobs;
    private ArrayList<Integer> selectedID;
    private Map<String,CommandHandler> commandHandlers = new HashMap<String, CommandHandler>();
    private DefaultCommand command;
    private int playerID;
    private boolean isSelected;

    /**
     * Constructor
     * @param game - super game class
     */
    public GameWorld(PTGame game){
        this.game = game;
        bulletFrom = new Stack<Vector2>();
        bulletTo = new Stack<Vector2>();
        deltMobs = new ArrayList<Mob>();
        deltPlanets = new ArrayList<Planet>();
        selectedID = new ArrayList<Integer>();
        isSelected = false;

        commandHandlers.put("Move", new MoveHandler(game, this));
        commandHandlers.put("Delt", new DeltHandler(game, this));
        commandHandlers.put("Ping", new PingHandler(game, this));
        startGame();
    }

    /**
     * Start actions(Server part!)
     */
    private void startGame(){
        String input = game.getInputQueue().poll();
        Setp startPosition = game.json.fromJson(input, Setp.class);
        planets = startPosition.planets;
        mobs = startPosition.mobs;
        playerID = startPosition.receiverID;
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
        if (!game.getInputQueue().isEmpty()){
            String input = game.getInputQueue().poll();
            command = game.json.fromJson(input, DefaultCommand.class);

            if(commandHandlers.containsKey(command.name)){
                commandHandlers.get(command.name).Handle(input);
            }
        }

        while (!game.getOutputQueue().isEmpty()) {
            game.getClient().getOut().println(game.getOutputQueue().poll());
        }

        for (Planet planet : planets) {
            if(!deltPlanets.isEmpty()){
                Iterator<Planet> iter = deltPlanets.iterator();
                while (iter.hasNext()) {
                    Planet deltPlanet = iter.next();
                    if (planet.getID() == deltPlanet.getID()){
                        planet = deltPlanet;
                        iter.remove();
                    }
                }
            }
            planet.update(this, delta);
        }
        for (Planet deltPlanet: deltPlanets){
            planets.add(deltPlanet);
        }
        deltPlanets.clear();

        Iterator<Mob> iter = mobs.iterator();
        while (iter.hasNext()) {
            Mob mob = iter.next();
            if(!deltMobs.isEmpty()){
                Iterator<Mob> iterator = deltMobs.iterator();
                while (iterator.hasNext()) {
                    Mob deltMob = iterator.next();
                    if (mob.getID() == deltMob.getID()){
                        mob = deltMob;
                        iterator.remove();
                    }
                }
            }
            if(mob.isRemove()){
                iter.remove();
            } else {
                mob.update(this, delta);
            }
        }
        for (Mob deltMob: deltMobs){
            mobs.add(deltMob);
        }
        deltMobs.clear();
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
     * Move object to free point or follow for target
     * @param newX - target position x
     * @param newY - target position y
     * @param target - target
     */
    public void moveToPoint(float newX, float newY, SuperFigure target, boolean itIsMine){
        for(Mob mob: mobs){
            if(itIsMine) {
                if (mob.getIsSelected()) {
                    selectedID.add(mob.getID());
                    mob.setNextPosition(newX, newY, target);
                }
            } else {
                if(selectedID.contains(mob.getID())){
                    mob.setIsSelected(true);
                    mob.setNextPosition(newX, newY,target);
                }
            }
        }
        for(Planet planet: planets){
            if(itIsMine) {
                if (planet.getIsSelected()) {
                    selectedID.add(planet.getID());
                    planet.setNextPosition(newX, newY, target);
                }
            } else {
                if(selectedID.contains(planet.getID())){
                    planet.setIsSelected(true);
                    planet.setNextPosition(newX, newY,target);
                }
            }
        }
        if(itIsMine) {
            command = new Move(newX, newY, target, selectedID);
            String temp = game.json.toJson(command);

            temp = temp.replaceAll("\n", "");
            temp = temp.replaceAll(" ", "");
            game.addToOutputQueue(temp);
            selectedID.clear();
            isSelected = false;
        }
    }

    public void setSelectedID(ArrayList<Integer> selectedID) {
        this.selectedID = selectedID;
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

    public void setDeltMobs(ArrayList<Mob> deltMobs) {
        this.deltMobs = deltMobs;
    }

    public void setDeltPlanets(ArrayList<Planet> deltPlanets) {
        this.deltPlanets = deltPlanets;
    }
}
