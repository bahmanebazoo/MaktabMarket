package com.example.bazoo.maktabmarket;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bazoo.maktabmarket.model.Products;
import com.example.bazoo.maktabmarket.network.Api;
import com.example.bazoo.maktabmarket.network.RetrofitClientInstance;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProductListFragment extends Fragment {

    private static final String PARENT_CATEGORY = "parent id of category";

    private RecyclerView recyclerView;
    private ItemAdapter itemAdapter;
    private ProgressBar progressBar;
    private List<Products> productList = new ArrayList<>();

    public ProductListFragment() {
        // Required empty public constructor
    }


    public static ProductListFragment newInstance(int parentCategory) {

        Bundle args = new Bundle();
        args.putInt(PARENT_CATEGORY, parentCategory);
        ProductListFragment fragment = new ProductListFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product_list, container, false);

        progressBar = view.findViewById(R.id.category_progress_bar);
        recyclerView = view.findViewById(R.id.product_list_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        methoddd();

        return view;
    }

    public void updateUI() {
        if (itemAdapter == null) {
            itemAdapter = new ItemAdapter(productList);
            recyclerView.setAdapter(itemAdapter);
        } else {
            itemAdapter.setList(productList);
            itemAdapter.notifyDataSetChanged();
        }
    }

    public void methoddd() {

        RetrofitClientInstance.getRetrofitInstance().create(Api.class)
                .getRoot().enqueue(new Callback<List<Products>>() {
            @Override
            public void onResponse(Call<List<Products>> call, Response<List<Products>> response) {
                if (response.isSuccessful()) {
                    List<Products> productList = response.body();
                    Toast.makeText(getActivity(), "" + productList.size(), Toast.LENGTH_SHORT).show();

                    for (int i = (productList.size() - 1); i >= 0; i--) {
                        if (productList.get(i).getCategories().get(0).getParent()
                                != getArguments().getInt(PARENT_CATEGORY)) {
                            productList.remove(i);
                        }
                    }
                    recyclerView.setAdapter(itemAdapter);
                    Toast.makeText(getActivity(), "succeed", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                    updateUI();
                }


            }

            @Override
            public void onFailure(Call<List<Products>> call, Throwable t) {
                Toast.makeText(getActivity(), "problem with your request" + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("onFailurrequesr", ""+t);
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
                    startActivity(DetailActivity.newIntent(getActivity(), product));
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

        public ItemAdapter(List<Products> products) {
            productList = products;
        }

        private void setList(List<Products> products) {
            productList = products;
        }

        @NonNull
        @Override
        public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.card_view_holder, parent, false);
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
