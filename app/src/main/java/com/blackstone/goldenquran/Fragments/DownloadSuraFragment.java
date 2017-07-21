package com.blackstone.goldenquran.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.blackstone.goldenquran.R;
import com.blackstone.goldenquran.adapters.DownloadSuraAdapter;
import com.blackstone.goldenquran.api.DownloadAllSurahService;
import com.blackstone.goldenquran.models.TOCData;
import com.blackstone.goldenquran.models.TableOfContents;
import com.blackstone.goldenquran.ui.DrawerCloser;
import com.blackstone.goldenquran.utilities.SharedPreferencesManager;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DownloadSuraFragment extends Fragment {

    @BindView(R.id.downloadSuraImage)
    ImageView downloadSuraImage;
    @BindView(R.id.shakeNameSuraDownload)
    TextView shakeNameSuraDownload;
    @BindView(R.id.cardView)
    CardView cardView;
    @BindView(R.id.downloadSuraRecycler)
    RecyclerView recyclerView;
    @BindView(R.id.downloadAll)
    AppCompatButton downloadAll;
    ArrayList<TableOfContents> arrayList;


    public DownloadSuraFragment() {
    }

    @Subscribe
    public void sendTOCData(TOCData tableOfContentses) {
        arrayList = tableOfContentses.getToc();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.download_sura_layout, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().invalidateOptionsMenu();
        setHasOptionsMenu(true);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        ((DrawerCloser) getActivity()).moveToolbarDown();

        String[] readersNames = getActivity().getResources().getStringArray(R.array.readersNames);

        shakeNameSuraDownload.setText(readersNames[SharedPreferencesManager.getInteger(getActivity(), "selectedReader", 0)]);
        int[] readersImages = new int[]{
                R.drawable.reader1, R.drawable.reader2, R.drawable.reader3,
                R.drawable.reader4, R.drawable.reader5, R.drawable.reader6,
                R.drawable.reader7, R.drawable.reader8, R.drawable.reader9,
                R.drawable.reader10, R.drawable.reader11, R.drawable.reader12,
                R.drawable.reader13, R.drawable.reader14, R.drawable.reader15,
                R.drawable.reader16, R.drawable.reader17, R.drawable.reader18,
                R.drawable.reader19, R.drawable.reader20, R.drawable.reader21,
                R.drawable.reader22, R.drawable.reader23, R.drawable.reader24,
                R.drawable.reader25, R.drawable.reader26, R.drawable.reader27,
                R.drawable.reader28, R.drawable.reader29, R.drawable.reader30,
                R.drawable.reader31, R.drawable.reader33,
                R.drawable.reader34, R.drawable.reader35, R.drawable.reader36,
                R.drawable.reader37, R.drawable.reader38,
                R.drawable.reader40, R.drawable.reader41, R.drawable.reader42
        };
        downloadSuraImage.setImageResource(readersImages[SharedPreferencesManager.getInteger(getActivity(), "selectedReader", 0)]);
        recyclerView.setAdapter(new DownloadSuraAdapter(getActivity(), arrayList));

        downloadAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), DownloadAllSurahService.class);
                intent.putParcelableArrayListExtra("toc", arrayList);
                DownloadAllSurahService.isDownloadAll = true;
                getActivity().startService(intent);
            }
        });


    }
}
