package com.example.bazoo.maktabmarket;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.bazoo.maktabmarket.utils.Beginning;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

public class BaseActivity extends AppCompatActivity {

    private static final String BEGINNER_SOURCE = "sender_source";

    public static Intent newIntent(Context context, String start) {
        Intent intent = new Intent(context, BaseActivity.class);
        intent.putExtra(BEGINNER_SOURCE, start);
        return intent;

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        handlerMethod(getIntent().getStringExtra(BEGINNER_SOURCE));
    }

    private void handlerMethod(String start) {


        if (start.equals(Beginning.PRODUCTS.toString())) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.base_container,
                    CategoryFragment.newInstance()).commit();

        } else {

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.base_container,
                    FilterFragment.newInstance(start)).commit();
        }
    }
}

