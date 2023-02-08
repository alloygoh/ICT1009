package com.mygdx.game.Utils;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.mygdx.game.Manager.ScreenManager;
import com.mygdx.game.Manager.SettingsManager;

public class Globals {
    private static AssetManager assetManager;
    private static SettingsManager settingsManager;
    private static ScreenManager screenManager;

    public Globals(Game game) {
        Globals.assetManager = new AssetManager();
        Globals.settingsManager = new SettingsManager();
        Globals.screenManager = new ScreenManager(game);
    }

    public static ScreenManager getScreenManager() {
        return screenManager;
    }

    public static SettingsManager getSettingsManager() {
        return settingsManager;
    }

    public static AssetManager getAssetManager(){
        return assetManager;
    }
}
