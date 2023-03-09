package com.mygdx.game.Leaderboard;


public class LeaderboardEntry implements Comparable<LeaderboardEntry> {

    private int score;
    private String name;

    public LeaderboardEntry(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public LeaderboardEntry() {

    }
    public int getScore() {
        return this.score;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public int compareTo(LeaderboardEntry o) {
        return o.getScore() - this.getScore();
    }

    @Override
    public String toString() {
        return this.name + ":" + this.getScore();
    }
}
