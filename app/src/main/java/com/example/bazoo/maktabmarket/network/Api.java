package com.example.bazoo.maktabmarket.network;

import com.example.bazoo.maktabmarket.model.Categories;
import com.example.bazoo.maktabmarket.model.Products;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface    Api {
    @GET("products/?consumer_key=" +
            "ck_5879ba4b4a80a8b925f13ea01a60898c54915a5e"+"&consumer_secret=" +
            "cs_9f9328f2f2bd1f354bc7b28f8f39488b6633bc30")
    Call<List<Products>> getRoot();


    @GET("products/categories/?consumer_key=" +
            "ck_5879ba4b4a80a8b925f13ea01a60898c54915a5e"+"&consumer_secret=" +
            "cs_9f9328f2f2bd1f354bc7b28f8f39488b6633bc30")
    Call<List<Categories>> getRootCategory();

    @GET("products/?consumer_key=" +
            "ck_5879ba4b4a80a8b925f13ea01a60898c54915a5e"+"&consumer_secret=" +
            "cs_9f9328f2f2bd1f354bc7b28f8f39488b6633bc30")
    Call<List<Products>> getRootQuery(@QueryMap Map<String,String> options);

    @GET("products/?consumer_key=" +
            "ck_5879ba4b4a80a8b925f13ea01a60898c54915a5e"+"&consumer_secret=" +
            "cs_9f9328f2f2bd1f354bc7b28f8f39488b6633bc30"+"&orderesby=total_sales&order=des")
    Call<List<Products>> getRootQuerybestsales();



}
