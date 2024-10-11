package edu.northeastern.a6_group10.recycler;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class ItemCard implements Parcelable {

    private String title;
    private String rating;
    private String imageUrl;
    private String type;

    // Constructor
    public ItemCard(String title, String rating, String imageUrl, String type) {
        this.title = title;
        this.rating = rating;
        this.imageUrl = imageUrl;
        this.type = type;
    }

    protected ItemCard(Parcel in) {
        title = in.readString();
        rating = in.readString();
        imageUrl = in.readString();
        type = in.readString();
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
        dest.writeString(title);
        dest.writeString(rating);
        dest.writeString(imageUrl);
        dest.writeString(type);
    }

    // Getters and Setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

