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

/**
 * A simple {@link Fragment} subclass.
 */
public class E3rabFragment extends Fragment {


    @BindView(R.id.nozoolReasonsText)
    TextView nozoolReasonsText;

    public E3rabFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nozool_reasons, container, false);
        ButterKnife.bind(this, view);
        if (getArguments() != null)
            if (getArguments().getString("e3rab") != null)
                if (!getArguments().getString("e3rab").equalsIgnoreCase("(null)"))
                    nozoolReasonsText.setText(getArguments().getString("e3rab"));
                else
                    nozoolReasonsText.setText("الاعراب غير متوفر لهذه الاية الكريمة");
            else
                nozoolReasonsText.setText("الاعراب غير متوفر لهذه الاية الكريمة");
        return view;
    }
}
