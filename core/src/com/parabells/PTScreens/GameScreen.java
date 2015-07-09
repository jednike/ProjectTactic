package com.parabells.PTScreens;

import com.badlogic.gdx.Screen;
import com.parabells.PTGame.PTGame;
import com.parabells.PTGameWorld.GameRenderer;
import com.parabells.PTGameWorld.GameWorld;

public class GameScreen implements Screen {
    private PTGame game;
    private GameWorld gameWorld;
    private GameRenderer gameRenderer;

    public GameScreen(PTGame game){
        this.game = game;
        gameWorld = new GameWorld(game);
        gameRenderer = new GameRenderer(game, gameWorld);
    }

    @Override
    public void show() {
        gameWorld.update();
        gameRenderer.render();
    }

    @Override
    public void render(float delta) {

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
