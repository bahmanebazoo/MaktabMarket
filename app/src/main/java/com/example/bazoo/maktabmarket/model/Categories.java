package com.example.bazoo.maktabmarket.model;

public class Categories {

    private int id;
    private String name;
    private int parent;
    private Image image;

    public Categories(int id, String name, int parent, Image image) {
        this.id = id;
        this.name = name;
        this.parent = parent;
        this.image = image;
    }

    public Categories() {

    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getParent() {
        return parent;
    }

    public Image getImage() {
        return image;
    }
}
