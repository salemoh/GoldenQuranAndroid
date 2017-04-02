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

        viewPager.setAdapter(new TafseerViewPagerAdapter(getChildFragmentManager()));
        if (!getActivity().getResources().getBoolean(R.bool.is_right_to_left)) {
            viewPager.setCurrentItem(8);
        }

    }


    class TafseerViewPagerAdapter extends FragmentPagerAdapter {
        String[] titles = getActivity().getResources().getStringArray(R.array.translateTabs);

        TafseerViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    TafseerFragment fragment = new TafseerFragment();
                    fragment.setArguments(getArguments());
                    return fragment;
                case 1:
                    Mawdoo3Fragment mawdoo3Fragment = new Mawdoo3Fragment();
                    mawdoo3Fragment.setArguments(getArguments());
                    return mawdoo3Fragment;
                case 2:
                    SarfFragment sarfFragment = new SarfFragment();
                    sarfFragment.setArguments(getArguments());
                    return sarfFragment;
                case 3:
                    BalaghaFragment balaghaFragment = new BalaghaFragment();
                    balaghaFragment.setArguments(getArguments());
                    return balaghaFragment;
                case 4:
                    E3rabFragment e3rabFragment = new E3rabFragment();
                    e3rabFragment.setArguments(getArguments());
                    return e3rabFragment;
                case 6:
                case 7:
                    NozoolReasons nozoolReasons = new NozoolReasons();
                    nozoolReasons.setArguments(getArguments());
                    return nozoolReasons;
                case 8:
                    ValueFragment valueFragment = new ValueFragment();
                    valueFragment.setArguments(getArguments());
                    return valueFragment;
                default:
                    return new AlSuraFragment();
            }
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

