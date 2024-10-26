package edu.northeastern.a6_group10;

import android.graphics.Bitmap;

/**
 * FirebaseSticker class to represent a sticker object in Firebase.
 */
public class FirebaseSticker {
    private String name;
    private String url;

    private FirebaseSticker() {
        // Default constructor required for calls to DataSnapshot.getValue(FirebaseSticker.class)
    }

    public FirebaseSticker(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

}
