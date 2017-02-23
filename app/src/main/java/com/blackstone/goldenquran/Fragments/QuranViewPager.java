package com.blackstone.goldenquran.Fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blackstone.goldenquran.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class QuranViewPager extends Fragment {

    @BindView(R.id.quranViewPager)
    ViewPager quranViewPager;
    int y;


    public QuranViewPager() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quran_view_pager, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        quranViewPager.setAdapter(new QuranViewPagerAdapter(getChildFragmentManager()));
        int px = Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics()));
        quranViewPager.setPageMargin(px);
        quranViewPager.setPageMarginDrawable(R.drawable.book_binder2);
//        if (getArguments() != null && !getArguments().isEmpty()) {
//            y = getArguments().getInt("pageNumber");
//            quranViewPager.setCurrentItem(y);
//        }
    }

    class QuranViewPagerAdapter extends FragmentPagerAdapter {

        QuranViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            QuranImageFragment fragment = new QuranImageFragment();
            fragment.setPage(position);
            return fragment;
        }

        @Override
        public int getCount() {
            return 624;
        }
    }

}
