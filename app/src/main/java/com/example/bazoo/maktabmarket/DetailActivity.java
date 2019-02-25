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
    private static final String RATING_AVERAGE = "rate_average";
    private static final String RATING_COUNT = "rating+count";
    public static final String PRODUCT_NAME="product_name";

    public static Intent newIntent(Context context, Products products) {

        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(DESCRIPTION, products.getDescription());
        intent.putExtra(IMAGE, products.getImages().get(0).getPath());
        intent.putExtra(RATING_AVERAGE, products.getAverage_rating());
        intent.putExtra(RATING_COUNT, products.getRating_count());
        intent.putExtra(PRODUCT_NAME,products.getName());

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
                        , getIntent().getStringExtra(IMAGE), getIntent().getFloatExtra(RATING_AVERAGE, 0), getIntent().getIntExtra(RATING_COUNT, 0),getIntent().getStringExtra(PRODUCT_NAME))).commit();
    }
}
