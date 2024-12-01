package edu.northeastern.group_10_lyricshuffle.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.time.Instant;

public class RecentPlay implements Parcelable {
    private final String songName;
    private final String artist;
    private final double score;
    private final Instant datePlayed;

    public RecentPlay(String songName, String artist, double score, Instant datePlayed) {
        this.songName = songName;
        this.artist = artist;
        this.score = score;
        this.datePlayed = datePlayed;
    }

    protected RecentPlay(Parcel in) {
        songName = in.readString();
        artist = in.readString();
        score = in.readDouble();
        datePlayed = Instant.ofEpochMilli(in.readLong());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(songName);
        dest.writeString(artist);
        dest.writeDouble(score);
        dest.writeLong(datePlayed.toEpochMilli());
    }

    public static final Creator<RecentPlay> CREATOR = new Creator<RecentPlay>() {
        @Override
        public RecentPlay createFromParcel(Parcel in) {
            return new RecentPlay(in);
        }

        @Override
        public RecentPlay[] newArray(int size) {
            return new RecentPlay[size];
        }
    };

    public String getSongName() {
        return songName;
    }

    public String getArtist() {
        return artist;
    }

    public double getScore() {
        return score;
    }

    public Instant getDatePlayed() {
        return datePlayed;
    }
}
