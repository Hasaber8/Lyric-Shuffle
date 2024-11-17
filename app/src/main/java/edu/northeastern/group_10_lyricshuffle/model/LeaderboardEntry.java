package edu.northeastern.group_10_lyricshuffle.model;

public class LeaderboardEntry {
    private int rank;
    private String userName;
    private int score;

    public LeaderboardEntry(int rank, String userName, int score) {
        this.rank = rank;
        this.userName = userName;
        this.score = score;
    }

    // Update getters (remove profileImageResId)
    public int getRank() { return rank; }
    public String getUserName() { return userName; }
    public int getScore() { return score; }
}


