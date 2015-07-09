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
	
	@Override
	public void create () {
		setScreen(new GameScreen(this));
	}
}
