package com.blackstone.goldenquran.Fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.blackstone.goldenquran.GetTimeClasses.DateHijri;
import com.blackstone.goldenquran.R;
import com.blackstone.goldenquran.adapters.MainListAdapter;
import com.blackstone.goldenquran.models.MainListFirstItemModel;
import com.blackstone.goldenquran.models.MainListModel;
import com.blackstone.goldenquran.ui.DrawerCloser;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainListFragment extends Fragment {

    @BindView(R.id.hijryDate)
    TextView hijryDate;
    @BindView(R.id.editImageView)
    ImageView editImageView;
    @BindView(R.id.salahTime)
    TextView salahTime;
    @BindView(R.id.dateAndTime)
    TextView dateAndTime;
    @BindView(R.id.mainList)
    RecyclerView recyclerView;


    public MainListFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_list_layout, container, false);
        ButterKnife.bind(this, view);

        ((DrawerCloser) getActivity()).close(false);

        Calendar c = Calendar.getInstance();

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd  HH:mm");
        String formattedDate = df.format(c.getTime());
        dateAndTime.setText(formattedDate);

        hijryDate.setText(DateHijri.writeIslamicDate());

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        String[] mainlist = getResources().getStringArray(R.array.MainList);
        ArrayList arrayList = new ArrayList();
        arrayList.add(new MainListFirstItemModel(R.drawable.star, mainlist[0], mainlist[1], mainlist[2]));
        arrayList.add(new MainListModel(R.drawable.main_list_bookmark, mainlist[3]));
        arrayList.add(new MainListModel(R.drawable.menupng, mainlist[4]));
        arrayList.add(new MainListModel(R.drawable.green_circle, mainlist[5]));
        arrayList.add(new MainListModel(R.drawable.main_list_book, mainlist[6]));
        arrayList.add(new MainListModel(R.drawable.search, mainlist[7]));
        arrayList.add(new MainListModel(R.drawable.play_button, mainlist[8]));
        arrayList.add(new MainListModel(R.drawable.main_list_bell, mainlist[9]));
        arrayList.add(new MainListModel(R.drawable.mosque, mainlist[10]));
        arrayList.add(new MainListModel(R.drawable.main_list_colord_book_end, mainlist[11]));
        arrayList.add(new MainListModel(R.drawable.number40, mainlist[12]));
        arrayList.add(new MainListModel(R.drawable.share, mainlist[13]));
        arrayList.add(new MainListModel(R.drawable.settings, mainlist[14]));
        arrayList.add(new MainListModel(R.drawable.statistics, mainlist[15]));

        MainListAdapter mainListAdapter = new MainListAdapter(getActivity(), arrayList);
        recyclerView.setAdapter(mainListAdapter);

    }
}
