package edu.northeastern.a6_group10.userListRecycler;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class ItemCard implements Parcelable {

    private String username;
    private String imageUrl;

    // Constructor
    public ItemCard(String username, String imageUrl) {
        this.username = username;
        this.imageUrl = imageUrl;
    }

    protected ItemCard(Parcel in) {
        username = in.readString();
        imageUrl = in.readString();
    }

    public static final Creator<ItemCard> CREATOR = new Creator<ItemCard>() {
        @Override
        public ItemCard createFromParcel(Parcel in) {
            return new ItemCard(in);
        }

        @Override
        public ItemCard[] newArray(int size) {
            return new ItemCard[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(username);
        dest.writeString(imageUrl);
    }

    // Getters and Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getImageUrl() {
        return imageUrl;
    }

}

