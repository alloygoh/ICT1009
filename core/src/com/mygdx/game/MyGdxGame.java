package com.mygdx.game;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader.FreeTypeFontLoaderParameter;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mygdx.game.Manager.ScreenManager;
import com.mygdx.game.Manager.SettingsManager;
import com.mygdx.game.Screen.LoadingScreen;
import com.mygdx.game.Screen.MainScreen;
import com.mygdx.game.Utils.Globals;

public class MyGdxGame extends Game implements LoadingScreen.OnLoadListener {
    private AssetManager assetManager;
    private Globals globals;

    private SettingsManager settingsManager;
    private ScreenManager screenManager;

    @Override
    public void create() {
        Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
        this.globals = new Globals(this);
        this.assetManager = Globals.getAssetManager();
        this.settingsManager = Globals.getSettingsManager();
        this.screenManager = Globals.getScreenManager();

        screenManager.addScreen(new LoadingScreen(this, this));
        initAssets();
        screenManager.setScreen(LoadingScreen.class);
    }

    private void initAssets() {
        // load characters
        assetManager.load("objects.atlas", TextureAtlas.class);
        assetManager.load("characters.atlas", TextureAtlas.class);
        // load other assets
        assetManager.load("comic/skin/comic-ui.atlas", TextureAtlas.class);
        assetManager.load("comic/skin/comic-ui.json", Skin.class);
        
        // sounds
        assetManager.load("sound/bad-food.mp3", Sound.class);
        assetManager.load("sound/combo-sound.mp3", Sound.class);
        assetManager.load("sound/countdown.mp3", Sound.class);
        assetManager.load("sound/game-end.mp3", Sound.class);
        assetManager.load("sound/good-food.mp3", Sound.class);
        assetManager.load("sound/level-up.mp3", Sound.class);
        assetManager.load("sound/meow-defence.mp3", Sound.class);
        assetManager.load("sound/pvp-bgm.mp3", Sound.class);
        assetManager.load("sound/pvp-fight.mp3", Sound.class);
        assetManager.load("sound/pvp-lose.mp3", Sound.class);
        assetManager.load("sound/pvp-win.mp3", Sound.class);

        // fonts
        FileHandleResolver resolver = new InternalFileHandleResolver();
        assetManager.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(resolver));
        assetManager.setLoader(BitmapFont.class, ".ttf", new FreetypeFontLoader(resolver));
        FreeTypeFontLoaderParameter titleFont = new FreeTypeFontLoaderParameter();
        titleFont.fontFileName = "GamePlayed.ttf";
        titleFont.fontParameters.size = 130;
        titleFont.fontParameters.borderWidth = 1;
        titleFont.fontParameters.color = Color.YELLOW;
        assetManager.load("GamePlayedTitle.ttf", BitmapFont.class, titleFont);

        FreeTypeFontLoaderParameter contentFont = new FreeTypeFontLoaderParameter();
        contentFont.fontFileName = "GamePlayed.ttf";
        contentFont.fontParameters.size = 15;
        contentFont.fontParameters.color = Color.WHITE;
        assetManager.load("GamePlayedContent.ttf", BitmapFont.class, contentFont);
        
        
        FreeTypeFontLoaderParameter scoreLabelFont = new FreeTypeFontLoaderParameter();
        scoreLabelFont.fontFileName = "GamePlayed.ttf";
        scoreLabelFont.fontParameters.size = 15;
        scoreLabelFont.fontParameters.color = Color.BLACK;
        assetManager.load("scoreLabelFont.ttf", BitmapFont.class, scoreLabelFont);

        FreeTypeFontLoaderParameter scoreFont = new FreeTypeFontLoaderParameter();
        scoreFont.fontFileName = "GamePlayed.ttf";
        scoreFont.fontParameters.size = 15;
        scoreFont.fontParameters.color = Color.BLUE;
        assetManager.load("scoreFont.ttf", BitmapFont.class, scoreFont);

        FreeTypeFontLoaderParameter timerFont = new FreeTypeFontLoaderParameter();
        timerFont.fontFileName = "GamePlayed.ttf";
        timerFont.fontParameters.size = 15;
        timerFont.fontParameters.color = Color.RED;
        assetManager.load("timerFont.ttf", BitmapFont.class, timerFont);

        FreeTypeFontLoaderParameter battleFont = new FreeTypeFontLoaderParameter();
        battleFont.fontFileName = "GamePlayed.ttf";
        battleFont.fontParameters.size = 30;
        battleFont.fontParameters.color = Color.RED;
        assetManager.load("battleFont.ttf", BitmapFont.class, battleFont);

        Globals.getLeaderboard().load();
        this.settingsManager.readFromConfig();
    }

    // when assets are done loading
    @Override
    public void onLoad() {
        screenManager.addScreen(new MainScreen(this));
        this.screenManager.setScreen(MainScreen.class);
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        assetManager.dispose();
    }

}
