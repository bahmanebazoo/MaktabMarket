package com.example.bazoo.maktabmarket.model;

import java.util.List;

public class Products {

    private List<Images> images;

    private List<Categories> categories;

    private String name;

    private String price;

    private String description;


    public Products(List<Images> images, List<Categories> categories, String name, String price, String description) {
        this.images = images;
        this.categories = categories;
        this.name = name;
        this.price = price;
        this.description = description;
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
}
