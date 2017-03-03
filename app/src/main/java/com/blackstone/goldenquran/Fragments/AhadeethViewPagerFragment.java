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

public class AhadeethViewPagerFragment extends Fragment {

    @BindView(R.id.ahadeethViewPager)
    ViewPager ahadeethViewPager;

    public AhadeethViewPagerFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ahadeeth_viewpager_layout, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ahadeethViewPager.setAdapter(new AhadeethViewPagerAdapter(getFragmentManager()));
        ahadeethViewPager.setCurrentItem(getArguments().getInt("pos"));
    }

    class AhadeethViewPagerAdapter extends FragmentPagerAdapter {

        AhadeethViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            AhadeethContentFragment fragment = new AhadeethContentFragment();
            fragment.setArguments(getArguments());
            fragment.setPage(position);
            return fragment;
        }

        @Override
        public int getCount() {
            return 42;
        }
    }
}

