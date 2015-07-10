package com.parabells.PTGameWorld;

import com.badlogic.gdx.Gdx;
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
            shapeRenderer.circle(planet.getFigure().x, planet.getFigure().y, planet.getFigure().radius);
        }
        for(Mob mob: gameWorld.getMobs()){
            shapeRenderer.circle(mob.getFigure().x, mob.getFigure().y, mob.getFigure().radius);
        }
        shapeRenderer.end();
    }
}
