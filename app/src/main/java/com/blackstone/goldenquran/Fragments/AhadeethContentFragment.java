package com.blackstone.goldenquran.Fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blackstone.goldenquran.R;
import com.blackstone.goldenquran.models.AhadeethModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AhadeethContentFragment extends Fragment {


    @BindView(R.id.ahadeethTitle)
    TextView ahadeethTitle;
    @BindView(R.id.ahadeethText)
    TextView ahadeethText;
    int position;
    ArrayList<AhadeethModel> arrayList;


    public AhadeethContentFragment() {
    }

    public void setPage(int possition) {
        this.position = possition;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ahadeeth_content, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        arrayList = (ArrayList<AhadeethModel>) getArguments().getSerializable("data");
        ahadeethText.setText(arrayList.get(position).body);
        ahadeethTitle.setText(arrayList.get(position).title);
    }
}
