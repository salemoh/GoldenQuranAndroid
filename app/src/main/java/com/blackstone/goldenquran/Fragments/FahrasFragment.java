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
import com.blackstone.goldenquran.database.DataBaseManager;
import com.blackstone.goldenquran.models.QueryMessage;
import com.blackstone.goldenquran.models.TableOfContents;
import com.blackstone.goldenquran.ui.DrawerCloser;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FahrasFragment extends Fragment {

    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.suraJuzoaViewPager)
    ViewPager viewPager;
    DataBaseManager data;

    ArrayList<TableOfContents> tableOfContentses;

    public void setTableOfContentses(ArrayList<TableOfContents> tableOfContentses) {
        this.tableOfContentses = tableOfContentses;
    }

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
        ((DrawerCloser) getActivity()).moveToolbarDown();

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

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                EventBus.getDefault().post(new QueryMessage(query));
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                EventBus.getDefault().post(new QueryMessage(newText));

                return true;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onResume() {
        super.onResume();
        ((DrawerCloser) getActivity()).title(2);
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

    private class AlsuraJuzoaViewPagerAdapter extends FragmentPagerAdapter {
        String[] titles = getResources().getStringArray(R.array.titles);

        AlsuraJuzoaViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            if (getActivity().getResources().getBoolean(R.bool.is_right_to_left)) {
                if (position == 0) {
                    fragment = new AlSuraFragment();
                    ((AlSuraFragment) fragment).sendTOCData(tableOfContentses);
                    return fragment;
                } else {
                    fragment = new AlJuzoaFragment();
                    ((AlSuraFragment) fragment).sendTOCData(tableOfContentses);
                    return fragment;
                }
            } else {
                if (position == 1) {
                    fragment = new AlSuraFragment();
                    ((AlSuraFragment) fragment).sendTOCData(tableOfContentses);
                    return fragment;
                } else {
                    fragment = new AlJuzoaFragment();
                    ((AlJuzoaFragment) fragment).sendTOCData(tableOfContentses);
                    return fragment;
                }
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
