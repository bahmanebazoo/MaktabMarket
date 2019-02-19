package com.example.bazoo.maktabmarket.network;

import com.example.bazoo.maktabmarket.model.Categories;
import com.example.bazoo.maktabmarket.model.Products;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface    Api {
    @GET("products/?consumer_key=" +
            "ck_5879ba4b4a80a8b925f13ea01a60898c54915a5e"+"&consumer_secret=" +
            "cs_9f9328f2f2bd1f354bc7b28f8f39488b6633bc30")
    Call<List<Products>> getRoot();


    @GET("products/categories/?consumer_key=" +
            "ck_5879ba4b4a80a8b925f13ea01a60898c54915a5e"+"&consumer_secret=" +
            "cs_9f9328f2f2bd1f354bc7b28f8f39488b6633bc30")
    Call<List<Categories>> getRootCategory();



}
