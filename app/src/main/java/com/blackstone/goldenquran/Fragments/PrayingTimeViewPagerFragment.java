package com.blackstone.goldenquran.Fragments;


import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.blackstone.goldenquran.R;
import com.blackstone.goldenquran.ui.DrawerCloser;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PrayingTimeViewPagerFragment extends Fragment {

    @BindView(R.id.tabLayout)
    TabLayout tab;
    @BindView(R.id.myViewPager)
    ViewPager viewPager;
    @BindView(R.id.pusaleh)
    ImageView pusaleh;
    @BindView(R.id.card_view)
    CardView cardView;

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


        Drawable wrappedDrawable = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            wrappedDrawable = DrawableCompat.wrap(getActivity().getDrawable(R.drawable.pray_time_clock));
        }
        DrawableCompat.setTint(wrappedDrawable, Color.WHITE);
        tab.addTab(tab.newTab().setIcon(R.drawable.pray_time_clock));
        tab.addTab(tab.newTab().setIcon(R.drawable.settings));
        tab.addTab(tab.newTab().setIcon(R.drawable.sliders));
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

    }

    class MyViewPagerAdapter extends FragmentPagerAdapter {

        MyViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            if (position == 0)
                fragment = new PrayTimeFragment();
            else if (position == 1)
                fragment = new MainListFragment();
            else if (position == 2)
                fragment = new PrayTimeConfigerFragment();


            return fragment;
        }

        @Override
        public int getCount() {
            return 3;
        }


    }

}
