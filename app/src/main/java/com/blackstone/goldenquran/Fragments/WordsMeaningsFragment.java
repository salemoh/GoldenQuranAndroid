package com.blackstone.goldenquran.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blackstone.goldenquran.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class WordsMeaningsFragment extends Fragment {


    @BindView(R.id.wordsMeanings)
    RecyclerView wordsMeanings;
    Unbinder unbinder;

    public WordsMeaningsFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_words_meanings, container, false);
        unbinder = ButterKnife.bind(this, view);
        wordsMeanings.setLayoutManager(new LinearLayoutManager(getActivity()));
//        wordsMeanings.setAdapter(new );
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
