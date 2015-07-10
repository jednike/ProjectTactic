package com.parabells.PTScreens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.parabells.PTGame.PTGame;
import com.parabells.PTGameWorld.GameRenderer;
import com.parabells.PTGameWorld.GameWorld;
import com.parabells.PTHelpers.GameAction;

/**
 * Class for game's screen
 */
public class GameScreen implements Screen {
    private PTGame game;
    private GameAction gameAction;
    private GameWorld gameWorld;
    private GameRenderer gameRenderer;

    /**
     * Constructor
     * @param game - super game class
     */
    public GameScreen(PTGame game){
        this.game = game;
        gameWorld = new GameWorld(game);
        gameRenderer = new GameRenderer(game, gameWorld);
        gameAction = new GameAction(gameWorld);
        Gdx.input.setInputProcessor(gameAction);
        game.currentState = PTGame.GameState.RUNNING;
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        switch (game.currentState){
            case PAUSE:
                break;
            case RUNNING:
                gameWorld.update(delta);
                gameRenderer.render();
                break;
            case GAMEOVER:
                break;
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
