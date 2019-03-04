package com.example.bazoo.maktabmarket;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.bazoo.maktabmarket.model.Categories;
import com.example.bazoo.maktabmarket.utils.Beginning;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

public class BaseActivity extends AppCompatActivity {

    private static final String BEGINNER_SOURCE = "sender_source";
    private static final String CATEGORY_ID="category_id";

    public static Intent newIntent(Context context, String start) {
        Intent intent = new Intent(context, BaseActivity.class);
        intent.putExtra(BEGINNER_SOURCE, start);
        return intent;

    }

    public static Intent newIntent(Context context, String start,int id) {
        Intent intent = new Intent(context, BaseActivity.class);
        intent.putExtra(BEGINNER_SOURCE, start);
        intent.putExtra(CATEGORY_ID,id);
        return intent;

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        handlerMethod(getIntent().getStringExtra(BEGINNER_SOURCE),getIntent().getIntExtra(CATEGORY_ID,-1));
    }

    private void handlerMethod(String start,int id) {


        if (start.equals(Beginning.PRODUCTS.toString())) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.base_container,
                    CategoriesFragment.newInstance()).commit();

        } else {

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.base_container,
                    FilterFragment.newInstance(start)).commit();
        }
    }
}

