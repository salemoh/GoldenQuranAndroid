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
import com.blackstone.goldenquran.adapters.AlsuraAdapter;
import com.blackstone.goldenquran.models.AljuzaModel;
import com.blackstone.goldenquran.models.AlsuraModel;
import com.blackstone.goldenquran.models.QueryMessage;
import com.blackstone.goldenquran.models.TableOfContents;
import com.blackstone.goldenquran.ui.DrawerCloser;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AlSuraFragment extends Fragment {


    @BindView(R.id.alSuraRecyclerView)
    RecyclerView alSuraRecyclerView;

    ArrayList<TableOfContents> data;

    public AlSuraFragment() {

    }

    public void sendTOCData(ArrayList<TableOfContents> tableOfContentses) {
        data = tableOfContentses;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().invalidateOptionsMenu();
        setHasOptionsMenu(true);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void query(QueryMessage queryMessage) {
        ((AlsuraAdapter) alSuraRecyclerView.getAdapter()).query(queryMessage);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_al_sura, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((DrawerCloser) getActivity()).moveToolbarDown();

        alSuraRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        ArrayList arrayList = new ArrayList();
        String[] ajzaNames = getResources().getStringArray(R.array.fahrasAjzaa);
        String[] suarNames = getResources().getStringArray(R.array.fahrasSuras);
        arrayList.add(new AljuzaModel(ajzaNames[0], 0));
        int juzNumber = 1;
        for (int i = 0; i < data.size() - 1; i++) {
            arrayList.add(new AlsuraModel(data.get(i).getSurah() + "", data.get(i).getVersesCount() + "", suarNames[data.get(i).getSurah() - 1]));
            if (data.get(i).getJuz() != data.get(i + 1).getJuz()) {
                for (int j = 0; j < (data.get(i + 1).getJuz() - data.get(i).getJuz()); j++) {
                    arrayList.add(new AljuzaModel(ajzaNames[data.get(i).getJuz() + j], juzNumber++));
                }
            }
        }
        arrayList.add(new AlsuraModel(data.get(data.size() - 1).getSurah() + "", data.get(data.size() - 1).getVersesCount() + "", suarNames[data.get(data.size() - 1).getSurah() - 1]));

        alSuraRecyclerView.setAdapter(new AlsuraAdapter(getActivity(), arrayList, data));
    }
}
