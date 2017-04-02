package com.blackstone.goldenquran.adapters;

import android.content.Context;

import com.blackstone.goldenquran.models.PrayModel;

import java.util.List;


public class WordsMeaningAdapter extends RecyclerAdapter {
    public WordsMeaningAdapter(List<PrayModel> list, Context context) {
        super(list, context);
    }
}
