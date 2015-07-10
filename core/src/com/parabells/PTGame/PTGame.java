package com.parabells.PTGame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.parabells.PTScreens.GameScreen;

public class PTGame extends Game {
	private float screenWidth, screenHeight;

	public enum GameState{
		PAUSE, RUNNING, GAMEOVER
	}

	public GameState currentState;

	@Override
	public void create () {
		screenHeight = Gdx.graphics.getHeight();
		screenWidth = Gdx.graphics.getWidth();
		setScreen(new GameScreen(this));
	}

	public float getScreenHeight() {
		return screenHeight;
	}

	public float getScreenWidth() {
		return screenWidth;
	}
}
