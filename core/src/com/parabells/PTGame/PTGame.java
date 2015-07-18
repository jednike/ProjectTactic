package com.parabells.PTGame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Json;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.parabells.PTClient.Client;
import com.parabells.PTScreens.MenuScreen;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Super game class
 * All textures, sounds and global variables here
 */
public class PTGame extends Game {
	private float screenWidth, screenHeight;
	private float relative;
	private Queue<String> inputQueue = new LinkedList<String>();
	private Queue<String> outputQueue = new LinkedList<String>();
	private TextureAtlas buttonAtlas;
	private Skin skin;
	private Client client;

	public Gson json;
	/**
	 * Game state's
	 */
	public enum GameState{
		PAUSE, RUNNING, CONNECTION, MENU, GAMEOVER, RESTART
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
		relative = screenWidth/screenHeight;
		buttonAtlas = new TextureAtlas(Gdx.files.internal("buttons/buttons.pack"));
		skin = new Skin(buttonAtlas);
		json = new GsonBuilder().setPrettyPrinting().create();
		setScreen(new MenuScreen(this));
	}

	/**
	 * Return design buttons
	 * @param buttonName - name button
	 * @param size - size button
	 * @return - design button
	 */
	public ImageButton.ImageButtonStyle setImageForButton(String buttonName, float size){
		ImageButton.ImageButtonStyle imageButtonStyle = new ImageButton.ImageButtonStyle();
		Drawable drawableUp = skin.newDrawable(skin.getDrawable(buttonName));
		Drawable drawableDown = skin.getDrawable(buttonName);
		imageButtonStyle.imageUp = drawableUp;
		imageButtonStyle.imageUp.setMinWidth(screenWidth / (4*size/5));
		imageButtonStyle.imageUp.setMinHeight(relative * screenHeight / (4*size/5));
		imageButtonStyle.imageDown = drawableDown;
		imageButtonStyle.imageDown.setMinWidth(screenWidth / size);
		imageButtonStyle.imageDown.setMinHeight(relative * screenHeight / size);
		imageButtonStyle.imageChecked = drawableUp;
		imageButtonStyle.imageChecked.setMinWidth(screenWidth / (4*size/5));
		imageButtonStyle.imageChecked.setMinHeight(relative * screenHeight / (4 * size / 5));
		return imageButtonStyle;
	}

	public void addToInputQueue(String command){
		if (command!=null && !command.isEmpty()){
			inputQueue.add(command);
		}
	}

	public Queue<String> getInputQueue() {
		return inputQueue;
	}

	public void addToOutputQueue(String command){
		if (command!=null && !command.isEmpty()){
			outputQueue.add(command);
		}
	}

	public Queue<String> getOutputQueue() {
		return outputQueue;
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

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}
}
