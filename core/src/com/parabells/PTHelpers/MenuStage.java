package com.parabells.PTHelpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.parabells.PTGame.PTGame;
import com.parabells.PTScreens.GameScreen;
import com.parabells.PTScreens.MenuScreen;

import java.io.IOException;

public class MenuStage extends Stage {
    private PTGame game;

    public MenuStage(final PTGame game){
        this.game = game;

        ImageButton startButton = null, resumeButton, relativeButton = null;

        String play = "";
        switch (game.currentState) {
            case PAUSE:
                resumeButton = new ImageButton(game.setImageForButton("start_button", 15));
                resumeButton.setPosition(game.getScreenWidth() / 2 - resumeButton.getWidth()/2, game.getScreenHeight()/2 - 2.5f * resumeButton.getHeight());
                resumeButton.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        game.currentState = PTGame.GameState.RUNNING;
                        dispose();
                    }
                });
                this.addActor(resumeButton);
                relativeButton = resumeButton;
                break;
            case MENU:
                play = "start";
                startButton = new ImageButton(game.setImageForButton(play + "_button", 15));
                startButton.setPosition(game.getScreenWidth() / 2 - startButton.getWidth()/2, game.getScreenHeight()/2 - 2.5f * startButton.getHeight());
                break;
            case GAMEOVER:
                play = "restart";
                startButton = new ImageButton(game.setImageForButton(play + "_button", 15));
                startButton.setPosition(game.getScreenWidth() / 2 - startButton.getWidth()/2, game.getScreenHeight()/2 - 2.5f * startButton.getHeight());
                relativeButton = startButton;
                break;
        }

        if(startButton == null){
            startButton = new ImageButton(game.setImageForButton(play + "_button", 20));
            startButton.setPosition(relativeButton.getX() + relativeButton.getWidth()/4 + 1.5f*startButton.getWidth(),
                    relativeButton.getY() - startButton.getHeight() / 2);
        }

        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                    game.currentState = PTGame.GameState.CONNECTION;
                try {
                    game.setScreen(new GameScreen(game));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                dispose();
            }
        });
        this.addActor(startButton);

        if(!play.equals("start")){
            ImageButton menuButton = new ImageButton(game.setImageForButton("home_button", 20));
            menuButton.setPosition(relativeButton.getX() - 1.5f*menuButton.getWidth(),
                    relativeButton.getY() - menuButton.getHeight()/2);
            menuButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    game.setScreen(new MenuScreen(game));
                    dispose();
                }
            });
            this.addActor(menuButton);
        } else{
            ImageButton storeMonetsButton = new ImageButton(game.setImageForButton("store_button", 20));
            storeMonetsButton.setPosition(startButton.getX() - storeMonetsButton.getWidth(),
                    startButton.getY() - storeMonetsButton.getHeight());
            storeMonetsButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                }
            });
            this.addActor(storeMonetsButton);

            ImageButton storeBonusesButton = new ImageButton(game.setImageForButton("white_star", 20));
            storeBonusesButton.setPosition(startButton.getX() + storeMonetsButton.getWidth(),
                    startButton.getY() - storeBonusesButton.getHeight());
            storeBonusesButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                }
            });
            this.addActor(storeBonusesButton);
        }

        addGeneralButton(20);

        Gdx.input.setInputProcessor(this);
    }

    private void addGeneralButton(final int size){

        ImageButton achievesButton = new ImageButton(game.setImageForButton("achieves_button", size));
        achievesButton.setPosition(game.getScreenWidth() - achievesButton.getWidth(), game.getScreenHeight() / 2);
        achievesButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            }
        });
        this.addActor(achievesButton);

        ImageButton leaderboardButton = new ImageButton(game.setImageForButton("leaderboard_button", size));
        leaderboardButton.setPosition(0, game.getScreenHeight() / 2);
        leaderboardButton.addListener(new ClickListener() {
        });
        this.addActor(leaderboardButton);

        ImageButton coinsButton = new ImageButton(game.setImageForButton("golden_star", size));
        coinsButton.setPosition(game.getScreenWidth() - coinsButton.getHeight(), game.getScreenHeight() - coinsButton.getWidth());
        coinsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

            }
        });
        this.addActor(coinsButton);

        ImageButton castButton = new ImageButton(game.setImageForButton("play_store_button", size));
        castButton.setPosition(game.getScreenWidth() - castButton.getWidth(), 0);
        castButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            }
        });
        this.addActor(castButton);

        ImageButton dopOptionsButton = new ImageButton(game.setImageForButton("dop_options_button", size));
        dopOptionsButton.setPosition(0, 0);
        dopOptionsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            }
        });
        this.addActor(dopOptionsButton);

        final ImageButton muteButton;
        muteButton = new ImageButton(game.setImageForButton("mute_off_button", size));
        muteButton.setPosition(0, game.getScreenHeight() - muteButton.getWidth());
        muteButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            }
        });
        this.addActor(muteButton);
    }
}
