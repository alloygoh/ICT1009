package com.mygdx.game.Settings;

import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Gdx;
import com.mygdx.game.Leaderboard.Leaderboard;

public class SaveGame extends Leaderboard {
    private static final String PREFERENCES_NAME = "saved_game";
    private static final String KEY_SCORE = "key_score";
    private static final String KEY_LEVEL = "key_level";

    private int score;

    private Preferences preferences;

    public SaveGame() {
        preferences = Gdx.app.getPreferences(PREFERENCES_NAME);
    }

    public void load() {
        score = preferences.getInteger(KEY_SCORE, 0);
    }

    public void save() {
        preferences.putInteger(KEY_SCORE, score);
        preferences.flush();
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}