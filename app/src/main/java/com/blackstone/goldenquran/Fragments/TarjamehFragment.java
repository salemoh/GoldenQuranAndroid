package com.blackstone.goldenquran.Fragments;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blackstone.goldenquran.R;
import com.blackstone.goldenquran.ui.ChangeLanguageActivityForResult;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TarjamehFragment extends Fragment {


    @BindView(R.id.tarjamehText)
    TextView tarjamehText;

    public TarjamehFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tarjameh, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        if (getArguments() != null)
            tarjamehText.setText(getArguments().getString("TranslateText"));
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_translate, menu);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        getActivity().invalidateOptionsMenu();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

//        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, new TabletFlagsFragment()).commit();
        startActivityForResult(new Intent(getActivity(), ChangeLanguageActivityForResult.class), 1);
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 1) {
                tarjamehText.setText(data.getStringExtra("text"));
            }
        }
    }

}
