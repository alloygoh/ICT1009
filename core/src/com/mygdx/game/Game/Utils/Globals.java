package com.mygdx.game.Game.Utils;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.mygdx.game.Engine.Leaderboard.Leaderboard;
import com.mygdx.game.Engine.Manager.GameStateManager;
import com.mygdx.game.Engine.Manager.ScreenManager;
import com.mygdx.game.Engine.Manager.SettingsManager;

public class Globals {
    private static AssetManager assetManager;
    private static SettingsManager settingsManager;
    private static ScreenManager screenManager;
    private static GameStateManager gameStateManager;
    private static Leaderboard leaderboard;
    private static int score;
    private static float countDown;
    private static boolean inBattle = false;

    public Globals(Game game) {
        Globals.assetManager = new AssetManager();
        Globals.settingsManager = new SettingsManager();
        Globals.screenManager = new ScreenManager(game);
        Globals.gameStateManager = new GameStateManager();
        Globals.leaderboard = new Leaderboard();
        Globals.score = 0;
        Globals.countDown = 30f;
    }

    public static void restoreCountDown() {
        Globals.countDown = 30f;
    }

    public static float getCountDown() {
        return countDown;
    }

    public static void setCountDown(float countDown) {
        Globals.countDown = countDown;
    }

    public static boolean isInBattle() {
        return Globals.inBattle;
    }

    public static void setInBattle(boolean inBattle) {
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

    public static Leaderboard getLeaderboard() {
        return leaderboard;
    }
}
