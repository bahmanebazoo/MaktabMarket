package com.example.bazoo.maktabmarket;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bazoo.maktabmarket.model.Categories;
import com.example.bazoo.maktabmarket.network.Api;
import com.example.bazoo.maktabmarket.network.RetrofitClientInstance;
import com.google.android.material.tabs.TabLayout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;
import retrofit2.Response;


public class CategoriesFragment extends Fragment {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;

    private List<Categories> categoryList;
    private CategoryFragment categoryFragment;


    public CategoriesFragment() {
        // Required empty public constructor
    }

    public static CategoriesFragment newInstance() {

        Bundle args = new Bundle();

        CategoriesFragment fragment = new CategoriesFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_categories, container, false);


        toolbar = view.findViewById(R.id.category_toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar();
        tabLayout = view.findViewById(R.id.category_tab_layout);
        viewPager = view.findViewById(R.id.category_view_pager);
        tabLayout.setupWithViewPager(viewPager);
        adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());

        new CategoryAsyncTask().execute();


        return view;
    }

    private void setTabCategories() {
        for (int i = 0; i < categoryList.size(); i++) {
            int parent_id=-1;
            parent_id = categoryList.get(i).getParent();
            //get parent id of object i of list
            //get
            if (parent_id == 0) {
                categoryFragment = new CategoryFragment().newInstance(categoryList.get(i).getId());
                adapter.addFrag(categoryFragment, categoryList.get(i).getName());
            }
        }
        viewPager.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }


    class ViewPagerAdapter extends FragmentStatePagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }

    }

    class CategoryAsyncTask extends AsyncTask<String, String, List<Categories>> {


        @Override
        protected List<Categories> doInBackground(String... strings) {
            if (!isOnline()) {
                //publishProgress("No Internet Connection");
                return null;
            }

            Response<List<Categories>> response = null;
            try {
                response = RetrofitClientInstance.getRetrofitInstance()
                        .create(Api.class).getRootCategory().execute();
            } catch (IOException e) {
                e.printStackTrace();
            }

            List<Categories> list = new ArrayList<>();
            if (response.isSuccessful())
                list = response.body();

            return list;
        }


        @Override
        protected void onPostExecute(List<Categories> categories) {
            super.onPostExecute(categories);

            if (categories == null)
                return;
            categoryList = categories;
            setTabCategories();
        }
    }
}
