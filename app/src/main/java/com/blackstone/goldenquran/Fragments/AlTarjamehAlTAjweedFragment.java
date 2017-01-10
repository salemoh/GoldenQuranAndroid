package com.blackstone.goldenquran.Fragments;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blackstone.goldenquran.R;

public class AlTarjamehAlTAjweedFragment extends Fragment {


    ViewPager viewPager;
    Toolbar toolbar;
    TabLayout tabLayout;


    public AlTarjamehAlTAjweedFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_al_tarjameh_al_tajweed, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        View view = getView();
        viewPager = (ViewPager) view.findViewById(R.id.tafseerViewPager);
        viewPager.setAdapter(new TafseerViewPAgerAdapter(getChildFragmentManager()));
        toolbar = (Toolbar) view.findViewById(R.id.tarjamehToolbar);
        tabLayout = (TabLayout) view.findViewById(R.id.tabLayout);

        toolbar.setTitle(R.string.Translate);
        toolbar.setTitleTextColor(Color.WHITE);

        tabLayout.addTab(tabLayout.newTab());
        tabLayout.addTab(tabLayout.newTab());
        tabLayout.addTab(tabLayout.newTab());
        tabLayout.addTab(tabLayout.newTab());
        tabLayout.addTab(tabLayout.newTab());
        tabLayout.addTab(tabLayout.newTab());
        tabLayout.addTab(tabLayout.newTab());
        tabLayout.addTab(tabLayout.newTab());
        tabLayout.addTab(tabLayout.newTab());

        tabLayout.setupWithViewPager(viewPager);

    }


    class TafseerViewPAgerAdapter extends FragmentPagerAdapter {
        String[] titles = new String[]{getString(R.string.atafseer), getString(R.string.reasonsOfNosol), getString(R.string.wordMeanings), getString(R.string.dictionari), getString(R.string.alErab), getString(R.string.albalagha), getString(R.string.alsarf), getString(R.string.ayatTopic), getString(R.string.translate)};

        public TafseerViewPAgerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return new AlSuraFragment();
        }

        @Override
        public int getCount() {
            return 9;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
    }
}

