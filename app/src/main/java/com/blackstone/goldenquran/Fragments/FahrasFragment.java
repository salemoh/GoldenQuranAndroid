package com.blackstone.goldenquran.Fragments;


import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.blackstone.goldenquran.R;
import com.blackstone.goldenquran.ui.DrawerCloser;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FahrasFragment extends Fragment {

    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    //    @BindView(R.id.SurAppBarLayout)
//    AppBarLayout appBarLayout;
    @BindView(R.id.suraJuzoaViewPager)
    ViewPager viewPager;

    public FahrasFragment() {

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
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.sura_juzoa_layout, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        tabLayout.addTab(tabLayout.newTab());
        tabLayout.addTab(tabLayout.newTab());
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setLayoutDirection(TabLayout.LAYOUT_DIRECTION_LTR);


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                ((DrawerCloser) getActivity()).moveToolbarDown();

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager.setAdapter(new AlsuraJuzoaViewPagerAdapter(getChildFragmentManager()));
        if (!getActivity().getResources().getBoolean(R.bool.is_right_to_left)) {
            viewPager.setCurrentItem(1);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_layout, menu);
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        searchView.setIconifiedByDefault(true);
        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    class AlsuraJuzoaViewPagerAdapter extends FragmentPagerAdapter {
        String[] titles = getResources().getStringArray(R.array.titles);

        AlsuraJuzoaViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (getActivity().getResources().getBoolean(R.bool.is_right_to_left)) {
                if (position == 0)
                    return new AlSuraFragment();
                else
                    return new AlJuzoaFragment();
            } else {
                if (position == 0)
                    return new AlJuzoaFragment();

                else
                    return new AlSuraFragment();
            }
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }

        @Override
        public int getCount() {
            return 2;
        }
    }

}
