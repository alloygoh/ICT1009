package com.mygdx.game;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mygdx.game.Manager.ScreenManager;
import com.mygdx.game.Manager.SettingsManager;
import com.mygdx.game.Screen.LoadingScreen;
import com.mygdx.game.Screen.MainScreen;
import com.mygdx.game.Utils.Globals;

public class MyGdxGame extends Game implements LoadingScreen.OnLoadListener {
	private AssetManager assetManager;
	private Globals globals;
	
	SettingsManager settingsManager;
	ScreenManager screenManager;

	@Override
	public void create () {
		Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
		this.globals = new Globals(this);
        this.assetManager = Globals.getAssetManager();
		this.settingsManager = Globals.getSettingsManager();
		this.screenManager = Globals.getScreenManager();
		screenManager.addScreen(new LoadingScreen(this, this));
		initAssets();
		screenManager.setScreen(LoadingScreen.class);
	}

	private void initAssets(){
		// load characters
		assetManager.load("characters.atlas",TextureAtlas.class);
		// load other assets
		assetManager.load("comic/skin/comic-ui.atlas",TextureAtlas.class);
		assetManager.load("comic/skin/comic-ui.json",Skin.class);
		this.settingsManager.readFromConfig();
	}

	// when assets are done loading
	@Override
	public void onLoad() {
		screenManager.addScreen(new MainScreen(this));
		this.screenManager.setScreen(MainScreen.class);
	}
	
	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		assetManager.dispose();
	}

}
