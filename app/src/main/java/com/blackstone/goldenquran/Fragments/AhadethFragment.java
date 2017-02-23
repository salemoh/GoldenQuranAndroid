package com.blackstone.goldenquran.Fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.blackstone.goldenquran.R;
import com.blackstone.goldenquran.adapters.AhadethAdapter;
import com.blackstone.goldenquran.models.AhadethModel;
import com.blackstone.goldenquran.ui.DrawerCloser;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AhadethFragment extends Fragment {


    @BindView(R.id.ahadethRecycler)
    RecyclerView recyclerView;

    public AhadethFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().invalidateOptionsMenu();
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.ahadeth_layout, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((DrawerCloser) getActivity()).moveToolbarDown();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        String[] ahadeth = getResources().getStringArray(R.array.ahadethNames);

        ArrayList<AhadethModel> arrayList = new ArrayList<>();
        for (int i = 0; i < 40; i++) {
            arrayList.add(new AhadethModel(ahadeth[i % 4]));
        }

        recyclerView.setAdapter(new AhadethAdapter(getActivity(), arrayList));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_search) {
            Toast.makeText(getActivity(), "Search action is selected!", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
