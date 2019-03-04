package com.example.bazoo.maktabmarket;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bazoo.maktabmarket.model.Products;
import com.example.bazoo.maktabmarket.network.Api;
import com.example.bazoo.maktabmarket.network.RetrofitClientInstance;
import com.example.bazoo.maktabmarket.utils.Beginning;
import com.example.bazoo.maktabmarket.utils.RecyclerEnum;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private TextView BestSalesTV;
    private RecyclerView recyclerViewBestSales;
    private ItemAdapter itemAdapterBestSales;
    private List<Products> productListBestSales = new ArrayList<>();
    private Map<String, String> mapBestSales = new HashMap<>(2);
    private TextView BestQualityTV;
    private RecyclerView recyclerViewBestQuality;
    private ItemAdapter itemAdapteBestQuality;
    private List<Products> productListBestQuality = new ArrayList<>();
    private Map<String, String> mapBestQuality = new HashMap<>(2);
    private TextView NewestTV;
    private RecyclerView recyclerViewNewest;
    private ItemAdapter itemAdapterNewest;
    private List<Products> productListNewest = new ArrayList<>();
    private Map<String, String> mapNewest = new HashMap<>(2);

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


        recyclerViewBestQuality = findViewById(R.id.best_quality_product);
        recyclerViewBestQuality.setLayoutManager(new LinearLayoutManager(getApplicationContext(),RecyclerView.HORIZONTAL,false));
        recyclerViewBestSales = findViewById(R.id.best_sale_product);
        recyclerViewBestSales.setLayoutManager(new LinearLayoutManager(getApplicationContext(),RecyclerView.HORIZONTAL,false));
        recyclerViewNewest = findViewById(R.id.newest_products);
        recyclerViewNewest.setLayoutManager(new LinearLayoutManager(getApplicationContext(),RecyclerView.HORIZONTAL,false));
        NewestTV = findViewById(R.id.newest_products_text);
        BestSalesTV = findViewById(R.id.best_sale_text);
        BestQualityTV = findViewById(R.id.best_quality_text);


        retrofitProductListGetter(RecyclerEnum.BEST_SALES + "");
        retrofitProductListGetter(RecyclerEnum.BEST_QUALITY + "");
        retrofitProductListGetter(RecyclerEnum.NEWEST + "");


    }


    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        Toast.makeText(this, "bahman", Toast.LENGTH_SHORT).show();

        switch (id) {
            case R.id.products_list:
                startActivity(BaseActivity.newIntent(this, Beginning.CATEGORIES.toString()));
                Toast.makeText(this, "bahman", Toast.LENGTH_SHORT).show();

                break;

            case R.id.best_rated:
                startActivity(BaseActivity.newIntent(this, Beginning.BEST_RATED.toString()));
                Toast.makeText(this, "bahman", Toast.LENGTH_SHORT).show();

                break;

            case R.id.best_sales:
                startActivity(BaseActivity.newIntent(this, Beginning.BEST_SALES.toString()));
                Toast.makeText(this, "bahman", Toast.LENGTH_SHORT).show();

                break;

            case R.id.most_viewed:
                startActivity(BaseActivity.newIntent(this, Beginning.MOST_VIEWED.toString()));
                Toast.makeText(this, "bahman", Toast.LENGTH_SHORT).show();

                break;


            default:
                startActivity(BaseActivity.newIntent(this, Beginning.BEST_RATED.toString()));
                Toast.makeText(this, "bahman", Toast.LENGTH_SHORT).show();

                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


    private RecyclerView checkRecyclerView(String string) {

        if (string.equals(RecyclerEnum.BEST_QUALITY.toString())) {

            return recyclerViewBestQuality;

        } else if (string.equals(RecyclerEnum.BEST_SALES.toString())) {

            return recyclerViewBestSales;
        } else {

            return recyclerViewNewest;

        }

    }

    private ItemAdapter checkItemAdapter(String string) {

        if (string.equals(RecyclerEnum.BEST_QUALITY.toString())) {
            return itemAdapteBestQuality;

        } else if (string.equals(RecyclerEnum.BEST_SALES.toString())) {

            return itemAdapterBestSales;
        } else {

            return itemAdapterNewest;
        }
    }

    private List<Products> checkProductList(String string) {

        if (string.equals(RecyclerEnum.BEST_QUALITY.toString())) {

            return productListBestQuality;

        } else if (string.equals(RecyclerEnum.BEST_SALES.toString())) {

            return productListBestSales;
        } else {

            return productListNewest;

        }
    }

    private List<Products> checkProductList(String string, List<Products> productsList) {

        if (string.equals(RecyclerEnum.BEST_QUALITY.toString())) {
            productListBestQuality = productsList;
            return productListBestQuality;

        } else if (string.equals(RecyclerEnum.BEST_SALES.toString())) {
            productListBestSales = productsList;
            return productListBestSales;
        } else {
            productListNewest = productsList;
            return productListNewest;

        }
    }


    public void updateUI(String string) {
        ItemAdapter itemAdapter = checkItemAdapter(string);
        RecyclerView recyclerView = checkRecyclerView(string);
        List<Products> productList = checkProductList(string);
        if (itemAdapter == null) {
            itemAdapter = new ItemAdapter(productList);
            recyclerView.setAdapter(itemAdapter);
        } else {
            itemAdapter.setList(productList);
            itemAdapter.notifyDataSetChanged();
        }
    }

    public void retrofitProductListGetter(final String string) {


        List<Products> productList = checkProductList(string);
        RetrofitClientInstance.getRetrofitInstance().create(Api.class)
                .getRoot().enqueue(new Callback<List<Products>>() {
            @Override
            public void onResponse(Call<List<Products>> call, Response<List<Products>> response) {
                if (response.isSuccessful()) {

                    List<Products> productList = checkProductList(string,response.body());


                    Toast.makeText(getApplicationContext(), "succeed" + productList.get(0).getName(), Toast.LENGTH_SHORT).show();

                    updateUI(string);
                }


            }


            @Override
            public void onFailure(Call<List<Products>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "problem with your request" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }

        });
    }


    class ItemViewHolder extends RecyclerView.ViewHolder {

        private ImageView productImageView;
        private TextView productName, productPrice;
        private Products product;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            productImageView = itemView.findViewById(R.id.product_image_view);
            productName = itemView.findViewById(R.id.product_name_text_view);
            productPrice = itemView.findViewById(R.id.product_price_text_view);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(DetailActivity.newIntent(getApplicationContext(), product));
                }
            });

        }

        public void bind(Products product) {
            this.product = product;
            productName.setText(product.getName());
            productPrice.setText(product.getPrice());
            Picasso.get().load(product.getImages().get(0).getPath()).placeholder(R.drawable.logo_background)
                    .into(productImageView);
        }


    }

    class ItemAdapter extends RecyclerView.Adapter<ItemViewHolder> {
        private List<Products> productList;

        public ItemAdapter(List<Products> products) {
            productList = products;
        }

        private void setList(List<Products> products) {
            productList = products;
        }

        @NonNull
        @Override
        public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.card_view_holder, parent, false);
            ItemViewHolder itemViewHolder = new ItemViewHolder(view);
            return itemViewHolder;
        }


        @Override
        public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
            holder.bind(productList.get(position));
        }


        @Override
        public int getItemCount() {
            return productList.size();
        }
    }

}
