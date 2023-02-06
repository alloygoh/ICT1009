package com.mygdx.game.Utils;

import com.badlogic.gdx.assets.AssetManager;

public class Globals {
    private static AssetManager assetManager;

    public Globals() {
        Globals.assetManager = new AssetManager();
    }

    public static AssetManager getAssetManager(){
        return assetManager;
    }
}
