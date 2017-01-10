package com.blackstone.goldenquran.Fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.blackstone.goldenquran.R;
import com.blackstone.goldenquran.adapters.AhadethAdapter;
import com.blackstone.goldenquran.models.AhadethModel;

import java.util.ArrayList;

public class AhadethFragment extends Fragment {

    RecyclerView recyclerView;
    Toolbar toolbar;

    public AhadethFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.ahadeth_layout, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recyclerView = (RecyclerView) getView().findViewById(R.id.ahadethRecycler);
        toolbar = (Toolbar) getView().findViewById(R.id.toolbar);
        String[] title = getActivity().getResources().getStringArray(R.array.MainList);
        toolbar.setTitle(title[12]);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        String[] ahadeth = getResources().getStringArray(R.array.ahadethNames);

        ArrayList<AhadethModel> arrayList = new ArrayList<>();
        for (int i = 0; i<40; i++){
            arrayList.add(new AhadethModel(ahadeth[i%4]));
        }

        recyclerView.setAdapter(new AhadethAdapter(getActivity(), arrayList));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.edit_button) {
//            Toast.makeText(getActivity(), "", Toast.LENGTH_LONG).show();
//            return true;
//        }

      if(id == R.id.action_search){
          Toast.makeText(getActivity(), "Search action is selected!", Toast.LENGTH_SHORT).show();
          return true;
      }

        return super.onOptionsItemSelected(item);
    }
}
