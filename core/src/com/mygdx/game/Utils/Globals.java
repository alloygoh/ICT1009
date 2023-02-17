package com.mygdx.game.Utils;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.mygdx.game.Manager.GameStateManager;
import com.mygdx.game.Manager.ScreenManager;
import com.mygdx.game.Manager.SettingsManager;

public class Globals {
    private static AssetManager assetManager;
    private static SettingsManager settingsManager;
    private static ScreenManager screenManager;
    private static GameStateManager gameStateManager;
    private static int score;

    public Globals(Game game) {
        Globals.assetManager = new AssetManager();
        Globals.settingsManager = new SettingsManager();
        Globals.screenManager = new ScreenManager(game);
        Globals.gameStateManager = new GameStateManager();
        Globals.score = 0;
    }

    public static int getScore() {
        return score;
    }

    public static void incrementScore() {
        Globals.score += 1;
    }

    public static void setScore(int score) {
        Globals.score = score;
    }

    public static GameStateManager getGameStateManager() {
        return gameStateManager;
    }

    public static ScreenManager getScreenManager() {
        return screenManager;
    }

    public static SettingsManager getSettingsManager() {
        return settingsManager;
    }

    public static AssetManager getAssetManager() {
        return assetManager;
    }
}
