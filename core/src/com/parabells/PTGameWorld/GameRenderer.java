package com.parabells.PTGameWorld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.parabells.PTGame.PTGame;
import com.parabells.PTGameObjects.Mob;
import com.parabells.PTGameObjects.Planet;
import com.parabells.PTHelpers.Utils;

/**
 * Class for rendering
 */
public class GameRenderer {
    private PTGame game;
    private Color color;
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
        for(Planet planet: gameWorld.getPlanets().values()){
            if(planet.getOwnerID() == 1){
                color = Color.GREEN;
            } else{
                color = Color.RED;
            }
            shapeRenderer.setColor(color);
            shapeRenderer.circle(planet.getFigure().x, planet.getFigure().y, planet.getFigure().radius);
        }
        for(Mob mob: gameWorld.getMobs().values()) {
            if(mob.getOwnerID() == 1){
                color = Color.GREEN;
            } else if(mob.getOwnerID() == Utils.NEUTRAL_OWNER_ID){
                color = Color.BLUE;
            } else if(mob.getOwnerID() == 2){
                color = Color.RED;
            }
            if(mob.getAtackedMob() != -1){
                shapeRenderer.rectLine(mob.getFigure().x, mob.getFigure().y, gameWorld.getMobs().get(mob.getAtackedMob()).getFigure().x, gameWorld.getMobs().get(mob.getAtackedMob()).getFigure().y, 5);
            }
            shapeRenderer.setColor(color);
            shapeRenderer.circle(mob.getFigure().x, mob.getFigure().y, mob.getFigure().radius);
        }

        shapeRenderer.end();
    }
}
