package com.parabells.PTHelpers;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.parabells.PTGame.PTGame;
import com.parabells.PTGameWorld.GameRenderer;
import com.parabells.PTGameWorld.GameWorld;

/**
 * Class for processing with game's screen
 */
public class GameAction implements GestureDetector.GestureListener {
    private float size, initialScale = 1.0f;
    private GameWorld gameWorld;
    private GameRenderer gameRenderer;
    private PTGame game;

    /**
     * Constructor
     * @param gameWorld - game's world
     */
    public GameAction(GameWorld gameWorld, GameRenderer gameRenderer, PTGame game){
        this.gameWorld = gameWorld;
        this.gameRenderer = gameRenderer;
        this.game = game;
        size = 1;
    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        x = x + (gameRenderer.getCamera().position.x - game.getScreenWidth()/2f);
        y = y - (gameRenderer.getCamera().position.y - game.getScreenHeight()/2f);
        gameWorld.clickOnWorld(gameRenderer.getCamera().zoom*x, gameRenderer.getCamera().zoom*y);
        initialScale = size;
        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        return false;
    }

    @Override
    public boolean longPress(float x, float y) {
        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        return false;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        gameRenderer.setNewCameraPosition(deltaX, deltaY);
        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
        float ratio = initialDistance / distance;
        size = MathUtils.clamp(initialScale * ratio, 0.3f, 2.5f);
        gameRenderer.setZoom(size);
        return false;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
        return false;
    }
}
