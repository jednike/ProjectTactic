package com.parabells.PTGameWorld;

import com.badlogic.gdx.math.Circle;
import com.parabells.PTGame.PTGame;
import com.parabells.PTGameObjects.Mob;
import com.parabells.PTGameObjects.Planet;
import com.parabells.PTGameObjects.SuperFigure;

import java.util.Stack;

public class GameWorld {
    private PTGame game;
    private Stack<Planet> planets;
    private Stack<Mob> mobs;
    private Boolean isSelected;
    private int size = 20;

    public GameWorld(PTGame game){
        this.game = game;
        createGameState();
    }

    private void createGameState(){
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

    private void addMobsToPlanet(int numberPlanet){
        for(int i = 0; i < 20; i++){
            double angle = (2*Math.PI)/size*i;
            float radius = planets.get(numberPlanet).getFigure().radius + 5;
            float posX = (float) (planets.get(numberPlanet).getFigure().x + radius*Math.cos(angle));
            float posY = (float) (planets.get(numberPlanet).getFigure().y + radius*Math.sin(angle));
            mobs.push(new Mob(posX, posY, 5, 100, planets.get(numberPlanet).getHostName()));
        }
    }

    public Stack<Mob> getMobs() {
        return mobs;
    }

    public Stack<Planet> getPlanets() {
        return planets;
    }

    public void update(float delta) {
        for (Planet planet: planets){
        }
        for(Mob mob: mobs) {
            if (mob.getIsMove()) {
                if (!mob.getFigure().overlaps(new Circle(mob.getNewX(), mob.getNewY(), mob.getFigure().radius))) {
                    mob.getFigure().x += mob.getStepX();
                    mob.getFigure().y += mob.getStepY();
                }
            }
        }
    }

    private void moveToPoint(int newX, int newY){
        for(Mob mob: mobs){
            if(mob.getIsSelected()){
                float k = (newX - mob.getFigure().x)/Math.abs(newY - mob.getFigure().y);
                mob.setStepX(k);
                mob.setStepY(newY - mob.getFigure().y > 0 ? 1:-1);
                mob.setIsSelected(false);
                mob.setNewX(newX);
                mob.setNewY(newY);
                mob.setIsMove(true);
            }
        }
    }

    public void clickOnWorld(int screenX, int screenY) {
        screenY = (int) (game.getScreenHeight() - screenY);
        Circle helpCircle = new Circle(screenX, screenY, 1);
        if(checkOnTouch(helpCircle) == null && isSelected){
            moveToPoint(screenX, screenY);
        } else if(checkOnTouch(helpCircle) instanceof Planet) {
            Planet planet = (Planet) checkOnTouch(helpCircle);
            if(isSelected){
                planet.setIsSelected(true);
            }
        } else if(checkOnTouch(helpCircle) instanceof Mob) {
            Mob mob = (Mob) checkOnTouch(helpCircle);
            isSelected = true;
            mob.setIsSelected(true);
        }
    }
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
