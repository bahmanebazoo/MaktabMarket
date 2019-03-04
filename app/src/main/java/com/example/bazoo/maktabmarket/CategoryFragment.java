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

import com.example.bazoo.maktabmarket.model.Categories;
import com.example.bazoo.maktabmarket.network.Api;
import com.example.bazoo.maktabmarket.network.RetrofitClientInstance;
import com.example.bazoo.maktabmarket.utils.Beginning;
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


public class CategoryFragment extends Fragment {

    private static final String PARENT_CATEGORY = "parent id of category";

    private RecyclerView recyclerView;
    private ItemAdapter itemAdapter;
    private ProgressBar progressBar;
    private List<Categories> categoriesList = new ArrayList<>();

    public CategoryFragment() {
        // Required empty public constructor
    }


    public static CategoryFragment newInstance(int parentCategory) {

        Bundle args = new Bundle();
        args.putInt(PARENT_CATEGORY, parentCategory);
        CategoryFragment fragment = new CategoryFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_category, container, false);

        progressBar = view.findViewById(R.id.categories_progress_bar);
        recyclerView = view.findViewById(R.id.categories_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        retrofitMethod();

        return view;
    }

    public void updateUI() {
        if (itemAdapter == null) {
            itemAdapter = new ItemAdapter(categoriesList);
            recyclerView.setAdapter(itemAdapter);
        } else {
            itemAdapter.setList(categoriesList);
            itemAdapter.notifyDataSetChanged();
        }
    }

    public void retrofitMethod() {

        RetrofitClientInstance.getRetrofitInstance().create(Api.class)
                .getRootCategory().enqueue(new Callback<List<Categories>>() {
            @Override
            public void onResponse(Call<List<Categories>> call, Response<List<Categories>> response) {
                if (response.isSuccessful()) {
                    categoriesList = response.body();

                    Categories categories = new Categories();
                    for (int i = (categoriesList.size() - 1); i >= 0; i--) {
                        if (categoriesList.get(i).getParent()
                                != getArguments().getInt(PARENT_CATEGORY)) {
                            if (categoriesList.get(i).getId() == getArguments().getInt(PARENT_CATEGORY)) {
                                categories = categoriesList.get(i);
                            }
                            categoriesList.remove(i);
                        }
                        if (categoriesList.size() == 0) {
                            categoriesList.add(categories);
                        }
                    }
                    recyclerView.setAdapter(itemAdapter);
                    // Toast.makeText(getActivity(), "succeed", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                    updateUI();
                }


            }

            @Override
            public void onFailure(Call<List<Categories>> call, Throwable t) {
                Toast.makeText(getActivity(), "problem with your request" + t.getMessage(),
                        Toast.LENGTH_SHORT).show();
                Log.d("onFailurrequesr", "" + t);
            }

        });
    }


    class ItemViewHolder extends RecyclerView.ViewHolder {

        private ImageView CategoryImageView;
        private TextView categoryName, productPrice;
        private Categories category;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            CategoryImageView = itemView.findViewById(R.id.product_image_view);
            categoryName = itemView.findViewById(R.id.product_name_text_view);
            productPrice = itemView.findViewById(R.id.product_price_text_view);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        startActivity(BaseActivity.newIntent(getContext(), Beginning.PRODUCTS.toString(),category.getId()));
                }
            });

        }

        public void bind(Categories category) {
            this.category = category;
            categoryName.setText(category.getName());

            try {
                Picasso.get().load(category.getImage().getPath()).placeholder(R.drawable.logo_background)
                        .into(CategoryImageView);

            } catch (NullPointerException e) {
                Log.e("Exception", "", e);
            }

        }

    }

    class ItemAdapter extends RecyclerView.Adapter<ItemViewHolder> {

        public ItemAdapter(List<Categories> categories) {
            categoriesList = categories;
        }

        private void setList(List<Categories> categories) {
            categoriesList = categories;
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
            holder.bind(categoriesList.get(position));
        }


        @Override
        public int getItemCount() {
            return categoriesList.size();
        }
    }


}
