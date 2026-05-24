package ite.rupp.edu.test1.models;

import com.google.gson.annotations.SerializedName;

public class Product {
    @SerializedName("id")
    private int id;

    @SerializedName("title")
    private String title;

    @SerializedName("price")
    private double price;

    @SerializedName("description")
    private String description;

    @SerializedName("category")
    private String category;

    @SerializedName("image")
    private String imageUrl;

    @SerializedName("rating")
    private Rating rating;

    // Getters
    public int getId() { return id; }
    public String getTitle() { return title; }
    public double getPrice() { return price; }
    public String getDescription() { return description; }
    public String getCategory() { return category; }
    public String getImageUrl() { return imageUrl; }
    public Rating getRating() { return rating; }

    public static class Rating {
        private double rate;
        private int count;
        public double getRate() { return rate; }
        public int getCount() { return count; }
    }
}