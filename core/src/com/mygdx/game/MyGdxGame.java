package com.mygdx.game;


import com.badlogic.gdx.Game;
import com.mygdx.game.Screen.LoadingScreen;
import com.mygdx.game.Screen.MainScreen;

public class MyGdxGame extends Game {
	
	@Override
	public void create () {
		this.setScreen(new LoadingScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
	}
}
