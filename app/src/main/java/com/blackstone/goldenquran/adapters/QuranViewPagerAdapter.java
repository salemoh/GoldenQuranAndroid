package com.blackstone.goldenquran.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.blackstone.goldenquran.Fragments.QuranImageFragment;

/**
 * Created by Omar AlTamimi on 6/2/2017.
 */

public class QuranViewPagerAdapter extends FragmentPagerAdapter {

    QuranImageFragment currentQuranImageFragment;
    private QuranImageFragment mCurrentFragment;
//    ArrayList<TableOfContents> tableOfContentses;

    public QuranViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

//    public void tocData(ArrayList<TableOfContents> tableOfContentses){
//        this.tableOfContentses = tableOfContentses;
//    }

    @Override
    public Fragment getItem(int position) {
        currentQuranImageFragment = QuranImageFragment.getNewInstance(position);
//        currentQuranImageFragment.sendTOCData(tableOfContentses);
        return currentQuranImageFragment;
    }

    @Override
    public int getCount() {
        return 604;
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        if (getCurrentFragment() != object) {
            mCurrentFragment = ((QuranImageFragment) object);
        }
        super.setPrimaryItem(container, position, object);

    }

    public QuranImageFragment getCurrentQuranImageFragment() {
        return currentQuranImageFragment;
    }

    public QuranImageFragment getCurrentFragment() {
        return mCurrentFragment;
    }
}