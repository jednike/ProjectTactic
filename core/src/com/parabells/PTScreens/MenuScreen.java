package com.parabells.PTScreens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.parabells.PTGame.PTGame;
import com.parabells.PTHelpers.MenuStage;

public class MenuScreen implements Screen {
    private PTGame game;
    private MenuStage menuStage;

    public MenuScreen(PTGame game){
        this.game = game;
        game.currentState = PTGame.GameState.MENU;
        menuStage = new MenuStage(game);
        Gdx.input.setInputProcessor(menuStage);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl20.glClearColor(0, 0, 0, 0);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
        menuStage.act(delta);
        menuStage.draw();
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
