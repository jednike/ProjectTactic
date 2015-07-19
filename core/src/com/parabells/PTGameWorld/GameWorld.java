package com.parabells.PTGameWorld;

import com.parabells.PTClient.CommandHandler;
import com.parabells.PTClient.DefaultCommand;
import com.parabells.PTClient.DeltHandler;
import com.parabells.PTClient.Move;
import com.parabells.PTClient.MoveHandler;
import com.parabells.PTClient.PingHandler;
import com.parabells.PTClient.Setp;
import com.parabells.PTGame.PTGame;
import com.parabells.PTGameObjects.Mob;
import com.parabells.PTGameObjects.Planet;
import com.parabells.PTHelpers.Circle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Base game methods class
 */
public class GameWorld {private PTGame game;
    private Map<Integer, Planet> planets, deltPlanets;
    private Map<Integer, Mob> mobs, deltMobs;
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
        deltMobs = new HashMap<Integer, Mob>();
        deltPlanets = new HashMap<Integer, Planet>();
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
    public Map<Integer, Mob> getMobs() {
        return mobs;
    }

    /**
     * Getter for stack planets
     * @return - stack's planets(for render)
     */
    public Map<Integer, Planet> getPlanets() {
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

        for(Map.Entry<Integer, Planet> planet: planets.entrySet()){
            if(deltPlanets.containsKey(planet.getKey())){
                planet.setValue(deltPlanets.get(planet.getKey()));
                deltPlanets.remove(planet.getKey());
            }
            planet.getValue().update(this, delta);
        }
        planets.putAll(deltPlanets);
        deltPlanets.clear();

        for(Map.Entry<Integer, Mob> mob: mobs.entrySet()){
            if(deltMobs.containsKey(mob.getKey())) {
                mob.setValue(deltMobs.get(mob.getKey()));
                deltMobs.remove(mob.getKey());
            }
            if(mob.getValue().isRemove()){
                mobs.remove(mob.getKey());
            } else {
                mob.getValue().update(this, delta);
            }
        }
        mobs.putAll(deltMobs);
        deltMobs.clear();
    }

    /**
     * Move object to free point or follow for target
     * @param newX - target position x
     * @param newY - target position y
     * @param targetID - target
     */
    public void moveToPoint(float newX, float newY, int targetID, boolean itIsMine) {
        if (!itIsMine) {
            for (Integer id : selectedID) {
                if (mobs.containsKey(id)) {
                    mobs.get(id).setIsSelected(true);
                    mobs.get(id).setNextPosition(newX, newY, targetID);
                } else if (planets.containsKey(id)) {
                    planets.get(id).setIsSelected(true);
                    planets.get(id).setNextPosition(newX, newY, targetID);
                }
            }
        } else {
            for (Map.Entry<Integer, Mob> mob : mobs.entrySet()) {
                if (mob.getValue().getIsSelected()) {
                    selectedID.add(mob.getKey());
                    mob.getValue().setNextPosition(newX, newY, targetID);
                }
            }
            for (Map.Entry<Integer, Planet> planet : planets.entrySet()) {
                if (planet.getValue().getIsSelected()) {
                    selectedID.add(planet.getKey());
                    planet.getValue().setNextPosition(newX, newY, targetID);
                }
            }

            command = new Move(newX, newY, targetID, selectedID);
            String temp = game.json.toJson(command);

            temp = temp.replaceAll("\n", "");
            temp = temp.replaceAll(" ", "");
            game.addToOutputQueue(temp);
            isSelected = false;

            selectedID.clear();
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
                moveToPoint(screenX, screenY, -1, true);
            }
        }
    }

    /**
     * Check on hit on object
     * @param helpCircle - help figure for detect on hit
     */
    private boolean checkOnTouch(Circle helpCircle){
        boolean isSelect = false;
        for(Map.Entry<Integer, Mob> mob: mobs.entrySet()){
            if(mob.getValue().getFigure().overlaps(helpCircle)) {
                if (mob.getValue().getOwnerID() == playerID) {
                    isSelected = true;
                    mob.getValue().setIsSelected(true);
                } else {
                    if (isSelected) {
                        moveToPoint(mob.getValue().getFigure().x, mob.getValue().getFigure().y, mob.getKey(), true);
                    }
                }
                isSelect = true;
            }
        }
        for (Map.Entry<Integer, Planet> planet: planets.entrySet()){
            if(planet.getValue().getFigure().overlaps(helpCircle)){
                if (planet.getValue().getOwnerID() == playerID) {
                    isSelected = true;
                    planet.getValue().setIsSelected(true);
                } else{
                    if(isSelected) {
                        moveToPoint(planet.getValue().getFigure().x, planet.getValue().getFigure().y, planet.getKey(), true);
                    }
                }
                isSelect = true;
            }
        }
        return isSelect;
    }

    public void setDeltMobs(Map<Integer, Mob> deltMobs) {
        this.deltMobs = deltMobs;
    }

    public void setDeltPlanets(Map<Integer, Planet> deltPlanets) {
        this.deltPlanets = deltPlanets;
    }
}
