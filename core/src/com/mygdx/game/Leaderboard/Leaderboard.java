package com.mygdx.game.Leaderboard;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;

import java.util.ArrayList;
import java.util.Collections;


public class Leaderboard {
    private final int MAX_ENTRIES = 10;

    // should be an arraylist of entries where index 0 is highest score and index 9 is lowest
    private ArrayList<LeaderboardEntry> entries;

    public Leaderboard() {
        this.entries = new ArrayList<>();
    }

    @Override
    public String toString() {
        return this.entries.toString();
    }

    public int size() {
        return this.entries.size();
    }

    public LeaderboardEntry getLeaderboardEntryOfPosition(int position) {
        if (1 <= position && position <= 10) {
            return this.entries.get(position - 1);
        }
        // return last position if any other number
        return this.entries.get(MAX_ENTRIES - 1);
    }

    public void reviseScoreboard(LeaderboardEntry entry) {
        if (this.entries.size() < MAX_ENTRIES) {
            this.entries.add(entry);
        } else if (this.entries.get(MAX_ENTRIES - 1).getScore() < entry.getScore()) {
            // replace lowest entry
            this.entries.remove(MAX_ENTRIES - 1);
            this.entries.add(entry);
        }

        // re-sort arraylist
        Collections.sort(this.entries);
        save();
    }

    public void save() {
        FileHandle file = Gdx.files.local("leaderboard.json");
        Json json = new Json();
        json.toJson(this.entries, file);
    }

    public void load() {
        FileHandle file = Gdx.files.local("leaderboard.json");
        Json json = new Json();
        if (file.exists()) {
            this.entries = json.fromJson(ArrayList.class, LeaderboardEntry.class, file);
        } 
    }

}
