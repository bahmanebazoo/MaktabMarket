package com.example.bazoo.maktabmarket.model;

public class Categories {

    private int id;
    private String name;
    private int parent;

    public Categories(int id, String name, int parent) {
        this.id = id;
        this.name = name;
        this.parent = parent;
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
}
