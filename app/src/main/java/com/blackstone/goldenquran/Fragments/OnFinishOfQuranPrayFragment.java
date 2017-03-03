package com.blackstone.goldenquran.Fragments;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blackstone.goldenquran.R;
import com.blackstone.goldenquran.database.DataBaseManager;
import com.blackstone.goldenquran.ui.DrawerCloser;

import butterknife.BindView;
import butterknife.ButterKnife;


public class OnFinishOfQuranPrayFragment extends Fragment {

    DataBaseManager data;
    @BindView(R.id.onFinishQuran)
    TextView onFinishQuran;

    public OnFinishOfQuranPrayFragment() {

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
        View view = inflater.inflate(R.layout.fragment_doaa_khatem_alquran, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ((DrawerCloser) getActivity()).moveToolbarDown();
        new GetDoaa().execute();
    }

    private class GetDoaa extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
            if (getActivity() != null) {
                data = new DataBaseManager(getActivity()).createDatabase();
                data.open();
                String onFinishQuranDoaa = data.getOnFinishQuran();
                return onFinishQuranDoaa;
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            onFinishQuran.setText(s);

        }
    }
}
