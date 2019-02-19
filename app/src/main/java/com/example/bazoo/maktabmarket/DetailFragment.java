package com.example.bazoo.maktabmarket;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bazoo.maktabmarket.model.Products;
import com.squareup.picasso.Picasso;

import androidx.core.widget.TextViewCompat;


public class DetailFragment extends androidx.fragment.app.Fragment {

    private static final String DESCRIPTION_DETAIL = "description";
    private static final String IMAGE_DETAIL = "image";

    private ImageView imageView;
    private TextView description;

    public DetailFragment() {
        // Required empty public constructor
    }


    public static DetailFragment newInstance(String desc, String imagePath) {

        Bundle args = new Bundle();
        args.putString(IMAGE_DETAIL,imagePath);
        args.putString(DESCRIPTION_DETAIL,desc);
        DetailFragment fragment = new DetailFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        description = view.findViewById(R.id.product_detail_description);
        imageView = view.findViewById(R.id.product_detail_image_view);

        description.setText(getArguments().getString(DESCRIPTION_DETAIL));
        Picasso.get().load(getArguments().getString(IMAGE_DETAIL)).placeholder(R.drawable.logo_background)
                .into(imageView);
        return view;
    }



}
