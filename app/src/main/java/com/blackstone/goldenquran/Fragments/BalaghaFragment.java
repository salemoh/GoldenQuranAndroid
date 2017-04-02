package com.blackstone.goldenquran.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blackstone.goldenquran.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BalaghaFragment extends Fragment {


    @BindView(R.id.nozoolReasonsText)
    TextView nozoolReasonsText;

    public BalaghaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nozool_reasons, container, false);
        ButterKnife.bind(this, view);
        if (getArguments() != null)
            nozoolReasonsText.setText(getArguments().getString("balagha"));
        return view;
    }

}
