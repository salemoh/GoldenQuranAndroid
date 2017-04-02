package com.blackstone.goldenquran.Fragments;


import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.blackstone.goldenquran.R;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class QuranViewPager extends Fragment {

    @BindView(R.id.quranViewPager)
    ViewPager quranViewPager;
    @BindView(R.id.seek_bar_quran)
    DiscreteSeekBar seekBarQuran;
    @BindView(R.id.seekBarFrameLayout)
    FrameLayout seekBarFrameLayout;


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

        seekBarQuran.setOnProgressChangeListener(new DiscreteSeekBar.OnProgressChangeListener() {
            @Override
            public void onProgressChanged(DiscreteSeekBar seekBar, int value, boolean fromUser) {
            }

            @Override
            public void onStartTrackingTouch(DiscreteSeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(DiscreteSeekBar seekBar) {
                quranViewPager.setCurrentItem(seekBar.getProgress());
            }
        });

        seekBarFrameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                seekBarQuran.setVisibility(view.VISIBLE);
            }
        });

        quranViewPager.setAdapter(new QuranViewPagerAdapter(getChildFragmentManager()));
//        int px = Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics()));
//        quranViewPager.setPageMargin(px);
        quranViewPager.setRotationY(180);

        quranViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                seekBarQuran.setProgress(position);
            }

            @Override
            public void onPageSelected(int position) {
                seekBarQuran.setVisibility(View.VISIBLE);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        seekBarQuran.setVisibility(View.INVISIBLE);
                    }
                }, 1000);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        quranViewPager.setPageMarginDrawable(R.drawable.book_binder2);
        if (getArguments() != null && !getArguments().isEmpty()) {
            quranViewPager.setCurrentItem(getArguments().getInt("pageNumber"));
            seekBarQuran.setProgress(getArguments().getInt("pageNumber"));
        }
    }

    class QuranViewPagerAdapter extends FragmentPagerAdapter {

        QuranViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            return QuranImageFragment.getNewInstance(position);
        }

        @Override
        public int getCount() {
            return 605;
        }
    }

}
