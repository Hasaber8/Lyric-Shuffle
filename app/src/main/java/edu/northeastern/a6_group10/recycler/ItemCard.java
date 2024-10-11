package edu.northeastern.a6_group10.recycler;

public class ItemCard {

    private String title;
    private String description;

    // Constructor
    public ItemCard(String title, String description) {
        this.title = title;
        this.description = description;
    }

    // Getters
    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}

