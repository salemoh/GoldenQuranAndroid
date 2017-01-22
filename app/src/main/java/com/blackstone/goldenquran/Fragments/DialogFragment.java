package com.blackstone.goldenquran.Fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.blackstone.goldenquran.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DialogFragment extends Fragment {


    @BindView(R.id.imageView4n)
    ImageView imageView4n;
    @BindView(R.id.textView4x)
    TextView textView4x;
    @BindView(R.id.imageView42)
    ImageView imageView42;
    @BindView(R.id.textView41v)
    TextView textView41v;
    @BindView(R.id.imageView4cz)
    ImageView imageView4cz;
    @BindView(R.id.textView4c)
    TextView textView4c;
    @BindView(R.id.imageView4g)
    ImageView imageView4g;
    @BindView(R.id.textView4d)
    TextView textView4d;
    @BindView(R.id.imageView431)
    ImageView imageView431;
    @BindView(R.id.textView411123)
    TextView textView411123;
    @BindView(R.id.imageView442)
    ImageView imageView442;
    @BindView(R.id.textView412)
    TextView textView412;
    @BindView(R.id.imageView44445)
    ImageView imageView44445;
    @BindView(R.id.textView444)
    TextView textView444;
    @BindView(R.id.imageView49)
    ImageView imageView49;
    @BindView(R.id.textView48)
    TextView textView48;
    @BindView(R.id.imageView47)
    ImageView imageView47;
    @BindView(R.id.textView4666)
    TextView textView4666;
    @BindView(R.id.imageView455)
    ImageView imageView455;
    @BindView(R.id.textView4324)
    TextView textView4324;
    @BindView(R.id.imageView411)
    ImageView imageView411;
    @BindView(R.id.textView54)
    TextView textView54;
    @BindView(R.id.imageView12)
    ImageView imageView12;
    @BindView(R.id.textView33)
    TextView textView33;
    @BindView(R.id.imageView444)
    ImageView imageView444;
    @BindView(R.id.textView41)
    TextView textView41;

    public DialogFragment() {

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
        View view = inflater.inflate(R.layout.dialog_layout, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

}
