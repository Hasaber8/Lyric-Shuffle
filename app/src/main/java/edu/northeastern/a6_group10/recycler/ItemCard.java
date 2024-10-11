package edu.northeastern.a6_group10.recycler;

public class ItemCard {

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

    // Getters
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

