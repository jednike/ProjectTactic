package com.parabells.PTScreens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.parabells.PTClient.Client;
import com.parabells.PTGame.PTGame;
import com.parabells.PTGameWorld.GameRenderer;
import com.parabells.PTGameWorld.GameWorld;
import com.parabells.PTHelpers.GameAction;
import com.parabells.PTHelpers.MenuStage;

import java.io.IOException;

/**
 * Class for game's screen
 */
public class GameScreen implements Screen {
    private PTGame game;
    private MenuStage menuStage;
    private Stage gameStage;
    private GameAction gameAction;
    private GameWorld gameWorld;
    private GameRenderer gameRenderer;

    /**
     * Constructor
     * @param game - super game class
     */
    public GameScreen(PTGame game) throws IOException {
        this.game = game;
        gameStage = new Stage();
        connectionLabel();
        Client newGameClient = new Client(game);
        game.setClient(newGameClient);
        Thread clientThread = new Thread(newGameClient);
        clientThread.start();
    }

    private void connectionLabel(){
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = new BitmapFont();
        Label connection = new Label("Connection", labelStyle);
        connection.setPosition(game.getScreenWidth()/2 - connection.getMinWidth()/2, game.getScreenHeight()/2);
        gameStage.addActor(connection);
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
                gameStage.draw();
                if(game.getClient().getFuser() != null && !game.getClient().getFuser().equals("")){
                    gameWorld = new GameWorld(game);
                    gameRenderer = new GameRenderer(game, gameWorld);
                    gameAction = new GameAction(gameWorld, gameRenderer, game);
                    game.currentState = PTGame.GameState.RUNNING;
                    Gdx.input.setInputProcessor(new GestureDetector(gameAction));
                }
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
