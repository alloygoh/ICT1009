package com.mygdx.game.Utils;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.mygdx.game.Leaderboard.Leaderboard;
import com.mygdx.game.Manager.GameStateManager;
import com.mygdx.game.Manager.ScreenManager;
import com.mygdx.game.Manager.SettingsManager;

public class Globals {
    private static AssetManager assetManager;
    private static SettingsManager settingsManager;
    private static ScreenManager screenManager;
    private static GameStateManager gameStateManager;
    private static Leaderboard leaderboard;
    private static int score;
    private static boolean inBattle = false;

    public Globals(Game game) {
        Globals.assetManager = new AssetManager();
        Globals.settingsManager = new SettingsManager();
        Globals.screenManager = new ScreenManager(game);
        Globals.gameStateManager = new GameStateManager();
        Globals.leaderboard = new Leaderboard();
        Globals.score = 0;
    }
    
    public static boolean isInBattle(){
        return Globals.inBattle;
    }
    
    public static void setInBattle(boolean inBattle){
        Globals.inBattle = inBattle;
    }

    public static int getScore() {
        return score;
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

    public static Leaderboard getLeaderboard() { return leaderboard; }
}
