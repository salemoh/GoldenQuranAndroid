package com.blackstone.goldenquran.Fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.blackstone.goldenquran.R;
import com.blackstone.goldenquran.ui.DrawerCloser;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IntonationTranslatorFragment extends Fragment {


    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.tafseerViewPager)
    ViewPager viewPager;
    @BindView(R.id.activity_al_tafseer)
    RelativeLayout activityAlTafseer;


    public IntonationTranslatorFragment() {

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

        View view = inflater.inflate(R.layout.fragment_al_tarjameh_al_tajweed, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((DrawerCloser) getActivity()).moveToolbarDown();

        tabLayout.addTab(tabLayout.newTab());
        tabLayout.addTab(tabLayout.newTab());
        tabLayout.addTab(tabLayout.newTab());
        tabLayout.addTab(tabLayout.newTab());
        tabLayout.addTab(tabLayout.newTab());
        tabLayout.addTab(tabLayout.newTab());
        tabLayout.addTab(tabLayout.newTab());
        tabLayout.addTab(tabLayout.newTab());
        tabLayout.addTab(tabLayout.newTab());
        tabLayout.setLayoutDirection(TabLayout.LAYOUT_DIRECTION_LTR);

        tabLayout.setupWithViewPager(viewPager);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                ((DrawerCloser) getActivity()).moveToolbarDown();
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager.setAdapter(new TafseerViewPAgerAdapter(getChildFragmentManager()));
        if (!getActivity().getResources().getBoolean(R.bool.is_right_to_left)) {
            viewPager.setCurrentItem(8);
        }

    }


    class TafseerViewPAgerAdapter extends FragmentPagerAdapter {
        String[] titles = getActivity().getResources().getStringArray(R.array.translateTabs);

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

