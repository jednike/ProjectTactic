package com.parabells.PTGame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.parabells.PTScreens.GameScreen;

/**
 * Super game class
 * All textures, sounds and global variables here
 */
public class PTGame extends Game {
	private float screenWidth, screenHeight;

	/**
	 * Game state's
	 */
	public enum GameState{
		PAUSE, RUNNING, GAMEOVER
	}

	/**
	 * Global variable for state's
	 */
	public GameState currentState;

	/**
	 * Create(start) game
	 */
	@Override
	public void create () {
		screenHeight = Gdx.graphics.getHeight();
		screenWidth = Gdx.graphics.getWidth();
		setScreen(new GameScreen(this));
	}

	/**
	 * Screen height
	 * @return - screen height
	 */
	public float getScreenHeight() {
		return screenHeight;
	}

	/**
	 * Screen width
	 * @return - screen width
	 */
	public float getScreenWidth() {
		return screenWidth;
	}
}
