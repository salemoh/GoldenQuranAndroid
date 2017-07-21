package com.blackstone.goldenquran.Fragments;


import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.blackstone.goldenquran.R;
import com.blackstone.goldenquran.adapters.Mos7afAdapter;
import com.blackstone.goldenquran.models.Mos7afModel;
import com.blackstone.goldenquran.ui.DrawerCloser;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class Mos7afFragment extends Fragment {


    @BindView(R.id.moshafRecycler)
    RecyclerView moshafRecycler;
    Unbinder unbinder;
    @BindView(R.id.addMos7af)
    FloatingActionButton addMos7af;
    static ArrayList<Mos7afModel> moodels;
    Dialog dialog;

    public Mos7afFragment() {

    }

    public static void getData(ArrayList<Mos7afModel> mos7afModels) {
        moodels = mos7afModels;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_masa7f, container, false);
        unbinder = ButterKnife.bind(this, view);
        ((DrawerCloser) getActivity()).moveToolbarDown();

        moshafRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        moshafRecycler.setAdapter(new Mos7afAdapter(getActivity(), moodels));

        addMos7af.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog = onCreateDialog();
                dialog.show();
            }
        });

        return view;
    }

    public Dialog onCreateDialog() {
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        View v = getActivity().getLayoutInflater().inflate(R.layout.mos7af_details_layout, null);

        FloatingActionButton addMoshafButton = (FloatingActionButton) v.findViewById(R.id.addMoshafButton);
        FloatingActionButton cancelButton = (FloatingActionButton) v.findViewById(R.id.cancelButton);
        final AppCompatSpinner spinner = (AppCompatSpinner) v.findViewById(R.id.mos7afType);
        final EditText mos7afNameDetails = (EditText) v.findViewById(R.id.mos7afNameDetails);
        TextView title = (TextView) v.findViewById(R.id.titleDialog);

        addMoshafButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moodels.add(new Mos7afModel(mos7afNameDetails.getText().toString(), spinner.getSelectedItem().toString()));
                moshafRecycler.getAdapter().notifyDataSetChanged();
                dialog.dismiss();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        spinner.setAdapter(new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, new String[]{"Hafs", "Warsh", "kingFahaid"}));

        alert.setView(v);

        return alert.create();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
