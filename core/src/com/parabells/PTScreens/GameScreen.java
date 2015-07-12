package com.parabells.PTScreens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.parabells.PTGame.PTGame;
import com.parabells.PTGameWorld.GameRenderer;
import com.parabells.PTGameWorld.GameWorld;
import com.parabells.PTHelpers.GameAction;
import com.parabells.PTHelpers.MenuStage;

/**
 * Class for game's screen
 */
public class GameScreen implements Screen {
    private PTGame game;
    private MenuStage menuStage;
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
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        if(delta > .033f){
            delta = .033f;
        }

        switch (game.currentState){
            case PAUSE:
                menuStage = new MenuStage(game);
                break;
            case RUNNING:
                gameWorld.update(delta);
                gameRenderer.render();
                break;
            case GAMEOVER:
                menuStage = new MenuStage(game);
                break;
            case CONNECTION:
                break;
            case MENU:
                menuStage.draw();
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
