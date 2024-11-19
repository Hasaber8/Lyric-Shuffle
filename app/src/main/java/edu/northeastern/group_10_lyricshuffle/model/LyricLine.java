package edu.northeastern.group_10_lyricshuffle.model;

public class LyricLine {
    private String text;
    private int points;
    private int correctPosition;
    private int originalIndex;

    public LyricLine(String text, int points, int correctPosition) {
        this.text = text;
        this.points = points;
        this.correctPosition = correctPosition;
        this.originalIndex = correctPosition;
    }

    public String getText() { return text; }
    public int getPoints() { return points; }
    public int getCorrectPosition() { return correctPosition; }
    public int getOriginalIndex() { return originalIndex; }
}
