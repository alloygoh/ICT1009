package com.mygdx.game.Leaderboard;

import java.util.ArrayList;
import java.util.Collections;

public class Leaderboard {
    private final int MAX_ENTRIES = 10;

    // should be an arraylist of entries where index 0 is highest score and index 9 is lowest
    private ArrayList<LeaderboardEntry> entries;
    
    public Leaderboard(){
        this.entries = new ArrayList<>();
    }

    @Override
    public String toString(){
        return this.entries.toString();
    }

    public LeaderboardEntry getLeaderboardEntryOf(int index){
        return this.entries.get(index);
    }

    public void reviseScoreboard(LeaderboardEntry entry){
        if(this.entries.size() < MAX_ENTRIES){
            this.entries.add(entry);
        } else if (this.entries.get(MAX_ENTRIES-1).getScore() < entry.getScore()){
            // replace lowest entry
            this.entries.remove(MAX_ENTRIES-1);
            this.entries.add(entry);
        }
       
        // re-sort arraylist
        Collections.sort(this.entries);
    }
}
