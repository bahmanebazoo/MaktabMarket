package com.example.bazoo.maktabmarket.model;

import java.util.Date;
import java.util.List;

public class Products {

    private List<Images> images;

    private List<Categories> categories;

    private String name;

    private String price;

    private String description;

    private int total_sales;

    private float average_rating;
    private int rating_count;




    public Products(List<Images> images, List<Categories> categories, String name, String price, String description, int total_sales, String average_rating,int rating_count) {
        this.images = images;
        this.categories = categories;
        this.name = name;
        this.price = price;
        this.description = description;
        this.total_sales = total_sales;
        this.average_rating = Float.parseFloat(average_rating);
        this.rating_count =rating_count;

    }

    public List<Images> getImages() {
        return images;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public List<Categories> getCategories() {
        return categories;
    }

    public int getTotal_sales() {
        return total_sales;
    }

    public float getAverage_rating() {
        return average_rating;
    }

    public int getRating_count() {
        return this.rating_count;
    }
}
