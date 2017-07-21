package com.blackstone.goldenquran.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.blackstone.goldenquran.R;
import com.blackstone.goldenquran.adapters.QuranViewPagerAdapter;
import com.blackstone.goldenquran.api.PlayerService;
import com.blackstone.goldenquran.managers.PlayerManager;
import com.blackstone.goldenquran.models.TableOfContents;
import com.blackstone.goldenquran.models.models.ChangePageEvent;
import com.blackstone.goldenquran.utilities.SharedPreferencesManager;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class QuranViewPager extends Fragment {

    @BindView(R.id.quranViewPager)
    ViewPager quranViewPager;
    @BindView(R.id.seek_bar_quran)
    DiscreteSeekBar seekBarQuran;
    @BindView(R.id.seekBarFrameLayout)
    FrameLayout seekBarFrameLayout;
    public static final String TAG = "QuranViewPager";
    QuranViewPagerAdapter mQuranViewPagerAdapter;
    ArrayList<TableOfContents> tableOfContentses;

    public void tocData(ArrayList<TableOfContents> tableOfContentses) {
        this.tableOfContentses = tableOfContentses;
    }

    public QuranViewPagerAdapter getQuranViewPagerAdapter() {
        return mQuranViewPagerAdapter;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    public QuranViewPager() {
    }

    public static QuranViewPager newInstance(int pos) {
        QuranViewPager fragment = new QuranViewPager();
        Bundle bundle = new Bundle();
        bundle.putInt("pageNumber", pos);
        fragment.setArguments(bundle);
        return fragment;
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
        Intent intent = new Intent(getActivity(), PlayerService.class);
        getActivity().startService(intent);
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
        mQuranViewPagerAdapter = new QuranViewPagerAdapter(getChildFragmentManager());

//        if (tableOfContentses != null)
//            mQuranViewPagerAdapter.tocData(tableOfContentses);

        quranViewPager.setAdapter(mQuranViewPagerAdapter);
//        int px = Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics()));
//        quranViewPager.setPageMargin(px);
        quranViewPager.setRotationY(180);
        quranViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                seekBarQuran.setProgress(position);
                PlayerManager.setCurrentPage(position + 1);
            }

            @Override
            public void onPageSelected(int position) {
                seekBarQuran.setVisibility(View.VISIBLE);
                SharedPreferencesManager.putInteger(getActivity(), "lastPosition", position);
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

    @Subscribe
    public void changePage(ChangePageEvent changePageEvent) {
        quranViewPager.setCurrentItem(changePageEvent.getPageNumber());
        seekBarQuran.setProgress(changePageEvent.getPageNumber());
    }


}
