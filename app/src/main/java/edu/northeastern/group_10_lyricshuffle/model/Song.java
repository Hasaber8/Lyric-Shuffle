package edu.northeastern.group_10_lyricshuffle.model;

import java.util.UUID;

public class Song {
    private UUID songId;
    private String title;
    private String artist;
    private String genre;
    private String difficultyLevel;

    public Song(UUID songId, String title, String artist, String genre, String difficultyLevel) {
        this.songId = songId;
        this.title = title;
        this.artist = artist;
        this.genre = genre;
        this.difficultyLevel = difficultyLevel;
    }

    // Getters and setters
    public UUID getSongId() {
        return songId;
    }

    public void setSongId(UUID songId) {
        this.songId = songId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getDifficultyLevel() {
        return difficultyLevel;
    }

    public void setDifficultyLevel(String difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }
}
