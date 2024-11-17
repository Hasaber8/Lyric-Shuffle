package edu.northeastern.group_10_lyricshuffle.model;

public class LeaderboardEntry {
    private int rank;
    private String userName;
    private int score;
    private int profileImageResId;

    public LeaderboardEntry(int rank, String userName, int score, int profileImageResId) {
        this.rank = rank;
        this.userName = userName;
        this.score = score;
        this.profileImageResId = profileImageResId;
    }

    public int getRank() { return rank; }
    public String getUserName() { return userName; }
    public int getScore() { return score; }
    public int getProfileImageResId() { return profileImageResId; }
}


