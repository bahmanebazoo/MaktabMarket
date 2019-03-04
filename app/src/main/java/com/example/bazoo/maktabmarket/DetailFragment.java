package com.example.bazoo.maktabmarket;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.bazoo.maktabmarket.model.Products;
import com.google.android.material.card.MaterialCardView;
import com.squareup.picasso.Picasso;

import androidx.appcompat.widget.AppCompatRatingBar;
import androidx.cardview.widget.CardView;
import androidx.core.widget.TextViewCompat;


public class DetailFragment extends androidx.fragment.app.Fragment {

    private static final String DESCRIPTION_DETAIL = "description";
    private static final String IMAGE_DETAIL = "image";
    private static final String RATING_AVERAGE="rate";
    private static final String RATING_COUNT = "rate_count";
    private static final String  PRODUCT_NAME="product_name";

    private ImageView imageView;
    private TextView description;
    private TextView continueExpand;
    private CardView descHolder;
    private AppCompatRatingBar ratingBar;
    private TextView productName;
    private TextView ratingCount;


    public DetailFragment() {
        // Required empty public constructor
    }


    public static DetailFragment newInstance(String desc, String imagePath,float rate,int rateCount,String productName) {

        Bundle args = new Bundle();
        args.putString(IMAGE_DETAIL,imagePath);
        args.putString(DESCRIPTION_DETAIL,desc);
        args.putFloat(RATING_AVERAGE,rate);
        args.putInt(RATING_COUNT,rateCount);
        args.putString(PRODUCT_NAME,productName);
        DetailFragment fragment = new DetailFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail, container, false);

//        descHolder=view.findViewById(R.id.desc_holder);
        description = view.findViewById(R.id.product_detail_description);
        imageView = view.findViewById(R.id.product_detail_image_view);
        continueExpand = view.findViewById(R.id.continue_expand);
        productName = view.findViewById(R.id.detail_product_name);
        ratingBar = view.findViewById(R.id.ratebar);
        ratingCount=view.findViewById(R.id.detail_new_product);


        description.setText(getArguments().getString(DESCRIPTION_DETAIL));
        productName.setText(getArguments().getString(PRODUCT_NAME));

        if(getArguments().getInt(RATING_COUNT)>0)
        ratingBar.setRating(getArguments().getFloat(RATING_AVERAGE));
        else{
            ratingBar.setVisibility(View.GONE);
            ratingCount.setVisibility(View.VISIBLE);

        }
        Picasso.get().load(getArguments().getString(IMAGE_DETAIL)).placeholder(R.drawable.logo_background)
                .into(imageView);



        return view;
    }




}
