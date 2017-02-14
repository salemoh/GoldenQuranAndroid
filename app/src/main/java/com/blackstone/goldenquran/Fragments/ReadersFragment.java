package com.blackstone.goldenquran.Fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blackstone.goldenquran.R;
import com.blackstone.goldenquran.adapters.AlquraaAdapter;
import com.blackstone.goldenquran.models.ReaderModel;
import com.blackstone.goldenquran.ui.DrawerCloser;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReadersFragment extends Fragment {
    @BindView(R.id.alquraaRecycler)
    RecyclerView recyclerView;

    public ReadersFragment() {

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
        View view = inflater.inflate(R.layout.alquraa_layout, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ((DrawerCloser) getActivity()).moveToolbarDown();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        String[] shakeNames = getResources().getStringArray(R.array.ShakeNames);

        ArrayList<ReaderModel> arrayList = new ArrayList<>();
        arrayList.add(new ReaderModel(R.drawable.reader1, shakeNames[0]));
        arrayList.add(new ReaderModel(R.drawable.reader2, shakeNames[1]));
        arrayList.add(new ReaderModel(R.drawable.reader3, shakeNames[2]));
        arrayList.add(new ReaderModel(R.drawable.reader4, shakeNames[0]));
        arrayList.add(new ReaderModel(R.drawable.reader5, shakeNames[1]));
        arrayList.add(new ReaderModel(R.drawable.reader6, shakeNames[2]));
        arrayList.add(new ReaderModel(R.drawable.reader7, shakeNames[0]));
        arrayList.add(new ReaderModel(R.drawable.reader8, shakeNames[1]));
        arrayList.add(new ReaderModel(R.drawable.reader9, shakeNames[2]));
        arrayList.add(new ReaderModel(R.drawable.reader10, shakeNames[2]));
        arrayList.add(new ReaderModel(R.drawable.reader11, shakeNames[2]));
        arrayList.add(new ReaderModel(R.drawable.reader12, shakeNames[0]));
        arrayList.add(new ReaderModel(R.drawable.reader13, shakeNames[1]));
        arrayList.add(new ReaderModel(R.drawable.reader14, shakeNames[2]));
        arrayList.add(new ReaderModel(R.drawable.reader12, shakeNames[1]));
        arrayList.add(new ReaderModel(R.drawable.reader16, shakeNames[2]));
        arrayList.add(new ReaderModel(R.drawable.reader17, shakeNames[0]));
        arrayList.add(new ReaderModel(R.drawable.reader18, shakeNames[1]));
        arrayList.add(new ReaderModel(R.drawable.reader19, shakeNames[2]));
        arrayList.add(new ReaderModel(R.drawable.reader20, shakeNames[1]));
        arrayList.add(new ReaderModel(R.drawable.reader21, shakeNames[1]));
        arrayList.add(new ReaderModel(R.drawable.reader22, shakeNames[1]));
        arrayList.add(new ReaderModel(R.drawable.reader23, shakeNames[1]));
        arrayList.add(new ReaderModel(R.drawable.reader24, shakeNames[1]));
        arrayList.add(new ReaderModel(R.drawable.reader25, shakeNames[1]));
        arrayList.add(new ReaderModel(R.drawable.reader26, shakeNames[1]));
        arrayList.add(new ReaderModel(R.drawable.reader27, shakeNames[1]));
        arrayList.add(new ReaderModel(R.drawable.reader28, shakeNames[1]));
        arrayList.add(new ReaderModel(R.drawable.reader29, shakeNames[1]));
        arrayList.add(new ReaderModel(R.drawable.reader30, shakeNames[2]));
        arrayList.add(new ReaderModel(R.drawable.reader31, shakeNames[2]));
        arrayList.add(new ReaderModel(R.drawable.reader32, shakeNames[2]));
        arrayList.add(new ReaderModel(R.drawable.reader33, shakeNames[2]));
        arrayList.add(new ReaderModel(R.drawable.reader34, shakeNames[2]));
        arrayList.add(new ReaderModel(R.drawable.reader35, shakeNames[2]));
        arrayList.add(new ReaderModel(R.drawable.reader36, shakeNames[2]));
        arrayList.add(new ReaderModel(R.drawable.reader37, shakeNames[2]));
        arrayList.add(new ReaderModel(R.drawable.reader38, shakeNames[2]));
        arrayList.add(new ReaderModel(R.drawable.reader39, shakeNames[2]));
        arrayList.add(new ReaderModel(R.drawable.reader40, shakeNames[2]));
        arrayList.add(new ReaderModel(R.drawable.reader41, shakeNames[2]));
        arrayList.add(new ReaderModel(R.drawable.reader42, shakeNames[2]));



        recyclerView.setAdapter(new AlquraaAdapter(getActivity(), arrayList));

    }
}
