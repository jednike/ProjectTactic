package com.parabells.PTGameWorld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.parabells.PTGame.PTGame;
import com.parabells.PTGameObjects.Mob;
import com.parabells.PTGameObjects.Planet;

/**
 * Class for rendering
 */
public class GameRenderer {
    private PTGame game;
    private GameWorld gameWorld;
    private OrthographicCamera camera;
    private ShapeRenderer shapeRenderer;

    /**
     * Constructor
     * @param game - super game class
     * @param gameWorld - game world
     */
    public GameRenderer(PTGame game, GameWorld gameWorld){
        this.game = game;
        this.gameWorld = gameWorld;
        camera = new OrthographicCamera(game.getScreenWidth(),game.getScreenHeight());
        camera.position.set(game.getScreenWidth()/2f, game.getScreenHeight()/2f, 0);
        camera.update();
        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(camera.combined);
    }

    /**
     * Rendering method
     */
    public void render(){
        Gdx.gl20.glClearColor(0, 0, 0, 0);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        for(Planet planet: gameWorld.getPlanets()){
            shapeRenderer.setColor(planet.getColor());
            shapeRenderer.circle(planet.getFigure().x, planet.getFigure().y, planet.getFigure().radius);
        }
        for(Mob mob: gameWorld.getMobs()){
            shapeRenderer.setColor(mob.getColor());
            shapeRenderer.circle(mob.getFigure().x, mob.getFigure().y, mob.getFigure().radius);
        }

        for(int i = 0; i < gameWorld.getBulletFrom().size(); i++){
            shapeRenderer.rectLine(gameWorld.getBulletFrom().get(i), gameWorld.getBulletTo().get(i), 5);
        }
        gameWorld.getBulletFrom().clear();
        gameWorld.getBulletTo().clear();
        shapeRenderer.end();
    }
}
