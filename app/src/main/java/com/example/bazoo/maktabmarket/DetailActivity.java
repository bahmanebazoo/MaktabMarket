package com.example.bazoo.maktabmarket;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.bazoo.maktabmarket.model.Products;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

public class DetailActivity extends AppCompatActivity {

    private static final String DESCRIPTION = "description";
    private static final String IMAGE = "image";

    public static Intent newIntent(Context context, Products products) {

        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(DESCRIPTION, products.getDescription());
        intent.putExtra(IMAGE, products.getImages().get(0).getPath());

        return intent;

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.base_container,
                DetailFragment.newInstance(
                        getIntent().getStringExtra(DESCRIPTION)
                        , getIntent().getStringExtra(IMAGE))).commit();
    }
}
