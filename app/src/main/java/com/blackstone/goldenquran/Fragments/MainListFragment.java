package com.blackstone.goldenquran.Fragments;


import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
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
import com.blackstone.goldenquran.models.TableOfContents;
import com.blackstone.goldenquran.ui.DrawerCloser;
import com.blackstone.goldenquran.utilities.SharedPreferencesManager;

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
    //    @BindView(R.id.salahTime)
//    TextView salahTime;
    @BindView(R.id.dateAndTime)
    TextView dateAndTime;
    @BindView(R.id.mainList)
    RecyclerView recyclerView;
    MainListAdapter mainListAdapter;
    String data;
    private NavigationDrawerListener mListener;
    ArrayList<TableOfContents> tableOfContentses;

    public MainListFragment() {

    }

    public void getData(String data) {
        if (mainListAdapter != null)
            mainListAdapter.setData(data);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_list_layout, container, false);
        ButterKnife.bind(this, view);

        ((DrawerCloser) getActivity()).close(false);

        Calendar c = Calendar.getInstance();

        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd -  HH:mm");
        String formattedDate = df.format(c.getTime());
        dateAndTime.setText(formattedDate);

        hijryDate.setText(DateHijri.writeIslamicDate(getActivity()));

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((DrawerCloser) getActivity()).moveToolbarDown();

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        editImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(R.string.change_hijri_date);
                builder.setItems(new String[]{"+1", "0", "-1"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (i == 0)
                            SharedPreferencesManager.putInteger(getActivity(), "hijriEdit", 1);
                        else if (i == 1)
                            SharedPreferencesManager.putInteger(getActivity(), "hijriEdit", 0);
                        else
                            SharedPreferencesManager.putInteger(getActivity(), "hijriEdit", -1);

                        hijryDate.setText(DateHijri.writeIslamicDate(getActivity()));

                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        String[] mainlist = getResources().getStringArray(R.array.MainList);
        ArrayList arrayList = new ArrayList();
        arrayList.add(new MainListFirstItemModel(R.drawable.main_list_star, mainlist[0], mainlist[1], mainlist[2]));
        arrayList.add(new MainListModel(R.drawable.main_list_book_mark, mainlist[3]));
        arrayList.add(new MainListModel(R.drawable.main_list_fahras, mainlist[4]));
        arrayList.add(new MainListModel(R.drawable.green_circle, mainlist[5]));
//        arrayList.add(new MainListModel(R.drawable.main_list_tarjameh_tajweed, mainlist[6]));
        arrayList.add(new MainListModel(R.drawable.main_list_search, mainlist[6]));
        arrayList.add(new MainListModel(R.drawable.main_list_play_button, mainlist[7]));
//        arrayList.add(new MainListModel(R.drawable.main_list_notification, mainlist[9]));
        arrayList.add(new MainListModel(R.drawable.main_list_mosque, mainlist[8]));
        arrayList.add(new MainListModel(R.drawable.main_list_doaa, mainlist[9]));
        arrayList.add(new MainListModel(R.drawable.main_list_40, mainlist[10]));
        arrayList.add(new MainListModel(R.drawable.main_list_share, mainlist[11]));
        arrayList.add(new MainListModel(R.drawable.main_list_settings_icon, mainlist[12]));
//        arrayList.add(new MainListModel(R.drawable.main_list_statistic_icon, mainlist[15]));

        mainListAdapter = new MainListAdapter(getActivity(), arrayList, new MainListAdapter
                .AdapterCallback() {
            @Override
            public void onShareClick() {
                if (mListener != null) {
                    mListener.onShareClick();
                }
            }
        });

        recyclerView.setAdapter(mainListAdapter);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof NavigationDrawerListener) {
            mListener = (NavigationDrawerListener) context;
        } else {
            throw new IllegalArgumentException("the activity must impalement NavigationDrawerListener");
        }
    }

    public void sendTOCData(ArrayList<TableOfContents> toc) {
        mainListAdapter.tocData(toc);
    }

    public interface NavigationDrawerListener {
        void onShareClick();
    }
}
