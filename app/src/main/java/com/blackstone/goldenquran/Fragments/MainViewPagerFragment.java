package com.blackstone.goldenquran.Fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blackstone.goldenquran.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainViewPagerFragment extends Fragment {


    @BindView(R.id.quranImageViewPager)
    ViewPager quranImageViewPager;

    public MainViewPagerFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main_view_pager, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        quranImageViewPager.setAdapter(new QuranImageViewPager(getActivity().getSupportFragmentManager()));

    }

    class QuranImageViewPager extends FragmentPagerAdapter {

        public QuranImageViewPager(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return new QuranImageFragment();
        }

        @Override
        public int getCount() {
            return 3;
        }
    }
}
