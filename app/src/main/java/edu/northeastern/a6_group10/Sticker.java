package edu.northeastern.a6_group10;

public class Sticker {
    //private Integer logoResId;  // TODO: Add sticker image
    private String name;
    private int count;

    public Sticker(String name, int count) {
        //this.logoResId = logoResId; // TODO: Add sticker image
        this.name = name;
        this.count = count;
    }

//    public int getLogoResId() {
//        return logoResId;
//    }

    public String getName() {
        return name;
    }

    public int getCount() {
        return count;
    }
}

