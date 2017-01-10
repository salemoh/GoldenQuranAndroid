package com.blackstone.goldenquran.Fragments;


import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blackstone.goldenquran.R;

public class PrayingTimeFragment extends Fragment {

    ViewPager viewPager;
    TabLayout tab;
    Toolbar toolbar;
    AppBarLayout appBarLayout;

    public PrayingTimeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_view_pager, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        View v = getView();

        viewPager = (ViewPager) v.findViewById(R.id.myViewPager);
        tab = (TabLayout) v.findViewById(R.id.tabLayout);
        toolbar = (Toolbar) v.findViewById(R.id.toolbar);
        appBarLayout = (AppBarLayout) v.findViewById(R.id.PrayerTimrAppBarLayout);
        toolbar.setTitle("AlarmManager");

        Drawable wrappedDrawable = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            wrappedDrawable = DrawableCompat.wrap(getActivity().getDrawable(R.drawable.clock));
        }
        DrawableCompat.setTint(wrappedDrawable, Color.WHITE);

        tab.addTab(tab.newTab().setIcon(R.drawable.clock));
        tab.addTab(tab.newTab().setIcon(R.drawable.settings));
        tab.addTab(tab.newTab().setIcon(R.drawable.sliders));
        tab.setSelectedTabIndicatorColor(Color.WHITE);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tab));
        tab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                Drawable wrappedDrawable = DrawableCompat.wrap(tab.getIcon());
                DrawableCompat.setTint(wrappedDrawable, Color.WHITE);
                appBarLayout.setExpanded(true, true);

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                Drawable wrappedDrawable = DrawableCompat.wrap(tab.getIcon());
                DrawableCompat.setTint(wrappedDrawable, Color.BLACK);

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        MyViewPagerAdapter myViewPagerAdapter = new MyViewPagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(myViewPagerAdapter);

    }

    class MyViewPagerAdapter extends FragmentPagerAdapter {

        MyViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            if (position == 0)
                fragment = new SalahTimeFragment();
            else if (position == 1)
                fragment = new SettingsSalahTimeFragment();
            else if (position == 2)
                fragment = new TimeConfigerFragment();

            return fragment;
        }

        @Override
        public int getCount() {
            return 3;
        }


    }

}
