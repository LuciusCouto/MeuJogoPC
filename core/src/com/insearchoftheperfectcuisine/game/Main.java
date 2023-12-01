package com.insearchoftheperfectcuisine.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.insearchoftheperfectcuisine.game.screens.mainMenu.MainMenuScreen;

public class Main extends Game {

	public float unitScale = 1/32f;

	@Override
	public void create () {
		setScreen(new MainMenuScreen(this));
	}

	@Override
	public void render() {
		super.render();
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}

	@Override
	public void pause() {
		Gdx.graphics.setContinuousRendering(false);
		super.pause();
	}

	@Override
	public void resume() {
		Gdx.graphics.setContinuousRendering(true);
		super.resume();
	}

	@Override
	public void dispose() {
		super.dispose();
	}
}