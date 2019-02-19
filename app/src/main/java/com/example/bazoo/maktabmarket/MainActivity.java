package com.example.bazoo.maktabmarket;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.bazoo.maktabmarket.utils.Beginning;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_drawer);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(getParent()
                , drawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        Toast.makeText(this,"bahman",Toast.LENGTH_SHORT).show();

        switch (id) {
            case R.id.products_list:
                startActivity(BaseActivity.newIntent(this, Beginning.PRODUCTS.toString()));
                Toast.makeText(this,"bahman",Toast.LENGTH_SHORT).show();

                break;

            case R.id.best_rated:
                startActivity(BaseActivity.newIntent(this, Beginning.BEST_RATED.toString()));
                Toast.makeText(this,"bahman",Toast.LENGTH_SHORT).show();

                break;

            case R.id.best_sales:
                startActivity(BaseActivity.newIntent(this, Beginning.BEST_SALES.toString()));
                Toast.makeText(this,"bahman",Toast.LENGTH_SHORT).show();

                break;

            case R.id.most_viewed:
                startActivity(BaseActivity.newIntent(this, Beginning.MOST_VIEWWD.toString()));
                Toast.makeText(this,"bahman",Toast.LENGTH_SHORT).show();

                break;


            default:
                startActivity(BaseActivity.newIntent(this, Beginning.BEST_RATED.toString()));
                Toast.makeText(this,"bahman",Toast.LENGTH_SHORT).show();

                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


}
