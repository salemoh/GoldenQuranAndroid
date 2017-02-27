package com.blackstone.goldenquran.Fragments;


import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blackstone.goldenquran.R;
import com.blackstone.goldenquran.ui.DrawerCloser;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PrayingTimeViewPagerFragment extends Fragment {

    @BindView(R.id.tabLayout)
    TabLayout tab;
    @BindView(R.id.myViewPager)
    ViewPager viewPager;

    public PrayingTimeViewPagerFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().invalidateOptionsMenu();
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_view_pager, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((DrawerCloser) getActivity()).moveToolbarDown();

        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.card_view, new QiblaFragment()).commit();

        Drawable wrappedDrawable = DrawableCompat.wrap(ContextCompat.getDrawable(getActivity(), R.drawable.pray_time_clock));
        DrawableCompat.setTint(wrappedDrawable, Color.WHITE);

        Drawable wrappedDrawable1 = DrawableCompat.wrap(ContextCompat.getDrawable(getActivity(), R.drawable.sliders));
        DrawableCompat.setTint(wrappedDrawable1, Color.BLACK);

        Drawable wrappedDrawable2 = DrawableCompat.wrap(ContextCompat.getDrawable(getActivity(), R.drawable.settings));
        DrawableCompat.setTint(wrappedDrawable2, Color.BLACK);

        if (getActivity().getResources().getBoolean(R.bool.is_right_to_left)) {
            tab.addTab(tab.newTab().setIcon(R.drawable.pray_time_clock));
            tab.addTab(tab.newTab().setIcon(R.drawable.settings));
            tab.addTab(tab.newTab().setIcon(R.drawable.sliders));
        } else {
            tab.addTab(tab.newTab().setIcon(R.drawable.sliders));
            tab.addTab(tab.newTab().setIcon(R.drawable.settings));
            tab.addTab(tab.newTab().setIcon(R.drawable.pray_time_clock));
        }

        tab.setSelectedTabIndicatorColor(Color.WHITE);
        tab.setLayoutDirection(TabLayout.LAYOUT_DIRECTION_LTR);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tab));

        tab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                Drawable wrappedDrawable = DrawableCompat.wrap(tab.getIcon());
                DrawableCompat.setTint(wrappedDrawable, Color.WHITE);

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                Drawable wrappedDrawable = DrawableCompat.wrap(tab.getIcon());
                DrawableCompat.setTint(wrappedDrawable, Color.BLACK);
                ((DrawerCloser) getActivity()).moveToolbarDown();

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        MyViewPagerAdapter myViewPagerAdapter = new MyViewPagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(myViewPagerAdapter);
        if (!getActivity().getResources().getBoolean(R.bool.is_right_to_left)) {
            viewPager.setCurrentItem(2);
        }

    }

    class MyViewPagerAdapter extends FragmentPagerAdapter {

        MyViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            if (getActivity().getResources().getBoolean(R.bool.is_right_to_left)) {
                if (position == 0)
                    fragment = new PrayTimeFragment();
                else if (position == 1)
                    fragment = new PrayTimeSettingsFragment();
                else if (position == 2)
                    fragment = new PrayTimeConfigerFragment();
            } else {
                if (position == 0)
                    fragment = new PrayTimeConfigerFragment();
                else if (position == 1)
                    fragment = new PrayTimeSettingsFragment();
                else if (position == 2)
                    fragment = new PrayTimeFragment();
            }


            return fragment;
        }

        @Override
        public int getCount() {
            return 3;
        }


    }

}
