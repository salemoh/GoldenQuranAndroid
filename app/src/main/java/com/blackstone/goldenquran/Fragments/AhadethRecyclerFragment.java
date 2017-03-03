package com.blackstone.goldenquran.Fragments;


import android.os.AsyncTask;
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
import com.blackstone.goldenquran.database.DataBaseManager;
import com.blackstone.goldenquran.models.AhadeethModel;
import com.blackstone.goldenquran.ui.DrawerCloser;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AhadethRecyclerFragment extends Fragment {


    @BindView(R.id.ahadethRecycler)
    RecyclerView recyclerView;
    int position;
    DataBaseManager data;
    ArrayList<AhadeethModel> arrayList;

    public AhadethRecyclerFragment() {

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
        new GetAhadith().execute();

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
    private class GetAhadith extends AsyncTask<Void, Void, ArrayList<AhadeethModel>> {

        @Override
        protected ArrayList<AhadeethModel> doInBackground(Void... voids) {
            if (getActivity() != null) {
                data = new DataBaseManager(getActivity()).createDatabase();
                data.open();
                arrayList = data.getAhadith();
                return arrayList;
            }
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<AhadeethModel> ahadeethModels) {
            super.onPostExecute(ahadeethModels);
            arrayList = ahadeethModels;
            recyclerView.setAdapter(new AhadethAdapter(getActivity(), arrayList));

        }
    }

}
