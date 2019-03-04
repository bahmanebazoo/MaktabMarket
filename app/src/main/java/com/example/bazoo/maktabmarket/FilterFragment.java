package com.example.bazoo.maktabmarket;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.bazoo.maktabmarket.model.Products;
import com.example.bazoo.maktabmarket.network.Api;
import com.example.bazoo.maktabmarket.network.RetrofitClientInstance;
import com.example.bazoo.maktabmarket.utils.Beginning;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Response;


public class FilterFragment extends Fragment {
    private static final String BEGAN_FROM = "beginner";

    private RecyclerView recyclerView;
    private ItemAdapter itemAdapter;
    private ProgressBar progressBar;
    private List<Products> productList = new ArrayList<>();

    public FilterFragment() {
        // Required empty public constructor
    }

    public static FilterFragment newInstance(String start) {

        Bundle args = new Bundle();
        args.putString(BEGAN_FROM, start);
        FilterFragment fragment = new FilterFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_filter, container, false);

        recyclerView = view.findViewById(R.id.filter_recycler_view_container);
        progressBar = view.findViewById(R.id.progress_circular);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        new FilterAsyncTask().execute();


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

    @Override
    public void onResume() {
        super.onResume();
    }

    private List<Products> getProductList() {

        String starter = getArguments().getString(BEGAN_FROM);
        List<Products> productList = new ArrayList<>();

        if (starter.equals(Beginning.BEST_RATED)) {


        } else if (starter.equals(Beginning.BEST_SALES)) {

        } else if (starter.equals(Beginning.MOST_VIEWED)) {

        }

        return productList;
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
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
            productImageView.setImageDrawable(null);
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

    class FilterAsyncTask extends AsyncTask<String, String, List<Products>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected List<Products> doInBackground(String... strings) {
            if (!isOnline()) {
                publishProgress("No Internet Connection");
                return null;
            }

            Response<List<Products>> response = null;
            try {
                response = RetrofitClientInstance.getRetrofitInstance()
                        .create(Api.class).getRoot().execute();
            } catch (IOException e) {
                e.printStackTrace();
            }

            List<Products> list = new ArrayList<>();
            if (response.isSuccessful())
                list = response.body();

            return list;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);


        }

        @Override
        protected void onPostExecute(List<Products> products) {
            super.onPostExecute(products);

            progressBar.setVisibility(View.GONE);

            if (products == null)
                return;
            productList = products;
            progressBar.setVisibility(View.GONE);
            updateUI();
        }
    }
}
