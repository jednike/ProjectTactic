package com.parabells.PTGameWorld;

import com.parabells.PTClient.Add;
import com.parabells.PTClient.AddHandler;
import com.parabells.PTClient.AttackHandler;
import com.parabells.PTClient.CommandHandler;
import com.parabells.PTClient.DefaultCommand;
import com.parabells.PTClient.DeltHandler;
import com.parabells.PTClient.DeltaUpdateHandler;
import com.parabells.PTClient.Move;
import com.parabells.PTClient.MoveHandler;
import com.parabells.PTClient.NewOwnerHandler;
import com.parabells.PTClient.PingHandler;
import com.parabells.PTClient.RemoveHandler;
import com.parabells.PTClient.StartConfiguration;
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
    private Map<Integer, Planet> planets;
    private Map<Integer, Mob> mobs;
    private ArrayList<Integer> selectedID;
    private Map<String,CommandHandler> commandHandlers = new HashMap<String, CommandHandler>();
    private DefaultCommand command;
    private int playerID;
    private int levelWidth, levelHeight;
    private boolean isSelected;
    private float planetRadius;

    /**
     * Constructor
     * @param game - super game class
     */
    public GameWorld(PTGame game){
        this.game = game;
        selectedID = new ArrayList<Integer>();
        isSelected = false;
        planetRadius = 30;

        commandHandlers.put("Move", new MoveHandler(game, this));
        commandHandlers.put("Attack", new AttackHandler(game, this));
        commandHandlers.put("Delt", new DeltHandler(game, this));
        commandHandlers.put("Ping", new PingHandler(game, this));
        commandHandlers.put("Add", new AddHandler(game, this));
        commandHandlers.put("Remove", new RemoveHandler(game, this));
        commandHandlers.put("NewOwner", new NewOwnerHandler(game, this));
        commandHandlers.put("DeltaUpdate", new DeltaUpdateHandler(game, this));

        planets = new HashMap<Integer, Planet>();
        mobs = new HashMap<Integer, Mob>();

        startGame();
    }

    /**
     * Start actions
     */
    private void startGame(){
        String input = game.getInputQueue().poll();
        StartConfiguration startConfig = game.json.fromJson(input, StartConfiguration.class);
        for(Add add: startConfig.add){
            addNewObject(add.id, add.x, add.y, add.radius, add.ownerID);
        }
        levelWidth = startConfig.levelWidth;
        levelHeight = startConfig.levelHeight;
        playerID = startConfig.receiverID;
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
        while (!game.getInputQueue().isEmpty()){
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
            planet.getValue().update(this, delta);
        }
        for(Map.Entry<Integer, Mob> mob: mobs.entrySet()){
            mob.getValue().update(this, delta);
        }
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
        }
        selectedID.clear();
    }

    public int getPlayerID() {
        return playerID;
    }

    public void setSelectedID(ArrayList<Integer> selectedID) {
        this.selectedID = selectedID;
    }

    /**
     * Click on game field
     * @param screenX - screen position click X
     * @param screenY - screen position click Y
     */
    public void clickOnWorld(float screenX, float screenY) {
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

    public void addNewObject(int id, float x, float y, float radius, int ownerID) {
        if(id < 50){
            planets.put(id, new Planet(x, y, radius, -1, -1, ownerID));
        } else{
            mobs.put(id, new Mob(x, y, radius, -1, -1, -1, -1, ownerID));
        }
    }

    public void removeObject(int id) {
        if(planets.containsKey(id)){
            planets.remove(id);
        } else if(mobs.containsKey(id)){
            mobs.remove(id);
        }
    }

    public float getPlanetRadius() {
        return planetRadius;
    }

    public void deltaUpdate(ArrayList<Integer> id, ArrayList<Float> xPosition, ArrayList<Float> yPosition) {
        for(int i = 0; i < id.size(); i++) {
            if (planets.containsKey(id.get(i))) {
                planets.get(id.get(i)).getFigure().setPosition(xPosition.get(i), yPosition.get(i));
            } else if (mobs.containsKey(id.get(i))) {
                mobs.get(id.get(i)).getFigure().setPosition(xPosition.get(i), yPosition.get(i));
            }
        }
    }
}
