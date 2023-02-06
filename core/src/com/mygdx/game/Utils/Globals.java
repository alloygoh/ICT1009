package com.mygdx.game.Utils;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.mygdx.game.Manager.SettingsManager;

public class Globals {
    private static AssetManager assetManager;
    private static SettingsManager settingsManager;

    public Globals(Game game) {
        Globals.assetManager = new AssetManager();
        Globals.settingsManager = new SettingsManager(game);
    }

    public static SettingsManager getSettingsManager() {
        return settingsManager;
    }

    public static AssetManager getAssetManager(){
        return assetManager;
    }
}
