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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blackstone.goldenquran.R;
import com.blackstone.goldenquran.adapters.AltafseerAdapter;
import com.blackstone.goldenquran.api.RetrofitInterface;
import com.blackstone.goldenquran.models.TheExplanationModel;
import com.blackstone.goldenquran.ui.DrawerCloser;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ExplanationFragment extends Fragment {

    @BindView(R.id.flagImage)
    ImageView flagImage;
    @BindView(R.id.anyTafseer)
    TextView anyTafseer;
    @BindView(R.id.downloadTafseer)
    TextView downloadTafseer;
    @BindView(R.id.header)
    RelativeLayout header;
    @BindView(R.id.altafseerRecycler)
    RecyclerView altafseerRecycler;
    private ArrayList<TheExplanationModel> theExplanationModel;

    public ExplanationFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().invalidateOptionsMenu();
        setHasOptionsMenu(true);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://download.quranicaudio.com/")
                .build();

        RetrofitInterface retrofitInterface = retrofit.create(RetrofitInterface.class);

        Call<ArrayList<TheExplanationModel>> request = retrofitInterface.getExplanationData();

        request.enqueue(new Callback<ArrayList<TheExplanationModel>>() {
            @Override
            public void onResponse(Call<ArrayList<TheExplanationModel>> call, Response<ArrayList<TheExplanationModel>> response) {
                theExplanationModel=response.body();
                processData();
            }

            @Override
            public void onFailure(Call<ArrayList<TheExplanationModel>> call, Throwable t) {

            }
        });


    }

    private void processData() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.altafseet_layout, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        altafseerRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        ((DrawerCloser) getActivity()).moveToolbarDown();

        ArrayList<TheExplanationModel> arrayList = new ArrayList<>();
        arrayList.add(new TheExplanationModel(getString(R.string.tafseerName), getString(R.string.tafseerDescription)));
        arrayList.add(new TheExplanationModel(getString(R.string.tafseerName), getString(R.string.tafseerDescription)));
        arrayList.add(new TheExplanationModel(getString(R.string.tafseerName), getString(R.string.tafseerDescription)));
        arrayList.add(new TheExplanationModel(getString(R.string.tafseerName), getString(R.string.tafseerDescription)));
        arrayList.add(new TheExplanationModel(getString(R.string.tafseerName), getString(R.string.tafseerDescription)));
        arrayList.add(new TheExplanationModel(getString(R.string.tafseerName), getString(R.string.tafseerDescription)));
        arrayList.add(new TheExplanationModel(getString(R.string.tafseerName), getString(R.string.tafseerDescription)));
        arrayList.add(new TheExplanationModel(getString(R.string.tafseerName), getString(R.string.tafseerDescription)));


        altafseerRecycler.setAdapter(new AltafseerAdapter(getActivity(), arrayList));
    }
}
