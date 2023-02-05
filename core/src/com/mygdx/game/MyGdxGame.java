package com.mygdx.game;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.mygdx.game.Manager.SettingsManager;
import com.mygdx.game.Screen.MainScreen;
import com.mygdx.game.Screen.SettingsScreen;

public class MyGdxGame extends Game {
	
	SettingsManager settingsManager;
	

	@Override
	public void create () {
		Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
		initAssets();
		this.setScreen(new SettingsScreen(this,this.settingsManager));	
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
	}

	private void initAssets(){
		this.settingsManager = new SettingsManager(this);
		settingsManager.readFromConfig();
	}
}
