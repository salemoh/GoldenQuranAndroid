package com.blackstone.goldenquran.Fragments;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blackstone.goldenquran.R;
import com.blackstone.goldenquran.adapters.BookmarkAdapter;
import com.blackstone.goldenquran.models.BookmarkModel;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class BookmarkFragment extends Fragment {

    RecyclerView recyclerView;
    Toolbar toolbar;

    public BookmarkFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.bookmark_layout, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recyclerView = (RecyclerView) getView().findViewById(R.id.bookmarkRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        toolbar = (Toolbar) getView().findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.bookmarksTitle);
        toolbar.setTitleTextColor(Color.WHITE);



        ArrayList<BookmarkModel> arrayList = new ArrayList<>();
        arrayList.add(new BookmarkModel(getString(R.string.bookmarkNumber), getString(R.string.bookmarckName)));
        arrayList.add(new BookmarkModel(getString(R.string.bookmarkNumber), getString(R.string.bookmarckName)));
        arrayList.add(new BookmarkModel(getString(R.string.bookmarkNumber), getString(R.string.bookmarckName)));
        arrayList.add(new BookmarkModel(getString(R.string.bookmarkNumber), getString(R.string.bookmarckName)));
        arrayList.add(new BookmarkModel(getString(R.string.bookmarkNumber), getString(R.string.bookmarckName)));
        arrayList.add(new BookmarkModel(getString(R.string.bookmarkNumber), getString(R.string.bookmarckName)));
        arrayList.add(new BookmarkModel(getString(R.string.bookmarkNumber), getString(R.string.bookmarckName)));
        arrayList.add(new BookmarkModel(getString(R.string.bookmarkNumber), getString(R.string.bookmarckName)));
        arrayList.add(new BookmarkModel(getString(R.string.bookmarkNumber), getString(R.string.bookmarckName)));
        arrayList.add(new BookmarkModel(getString(R.string.bookmarkNumber), getString(R.string.bookmarckName)));
        arrayList.add(new BookmarkModel(getString(R.string.bookmarkNumber), getString(R.string.bookmarckName)));
        arrayList.add(new BookmarkModel(getString(R.string.bookmarkNumber), getString(R.string.bookmarckName)));
        arrayList.add(new BookmarkModel(getString(R.string.bookmarkNumber), getString(R.string.bookmarckName)));
        arrayList.add(new BookmarkModel(getString(R.string.bookmarkNumber), getString(R.string.bookmarckName)));
        arrayList.add(new BookmarkModel(getString(R.string.bookmarkNumber), getString(R.string.bookmarckName)));
        arrayList.add(new BookmarkModel(getString(R.string.bookmarkNumber), getString(R.string.bookmarckName)));
        arrayList.add(new BookmarkModel(getString(R.string.bookmarkNumber), getString(R.string.bookmarckName)));
        arrayList.add(new BookmarkModel(getString(R.string.bookmarkNumber), getString(R.string.bookmarckName)));
        arrayList.add(new BookmarkModel(getString(R.string.bookmarkNumber), getString(R.string.bookmarckName)));
        arrayList.add(new BookmarkModel(getString(R.string.bookmarkNumber), getString(R.string.bookmarckName)));
        arrayList.add(new BookmarkModel(getString(R.string.bookmarkNumber), getString(R.string.bookmarckName)));
        arrayList.add(new BookmarkModel(getString(R.string.bookmarkNumber), getString(R.string.bookmarckName)));
        arrayList.add(new BookmarkModel(getString(R.string.bookmarkNumber), getString(R.string.bookmarckName)));
        arrayList.add(new BookmarkModel(getString(R.string.bookmarkNumber), getString(R.string.bookmarckName)));
        arrayList.add(new BookmarkModel(getString(R.string.bookmarkNumber), getString(R.string.bookmarckName)));
        arrayList.add(new BookmarkModel(getString(R.string.bookmarkNumber), getString(R.string.bookmarckName)));
        arrayList.add(new BookmarkModel(getString(R.string.bookmarkNumber), getString(R.string.bookmarckName)));
        arrayList.add(new BookmarkModel(getString(R.string.bookmarkNumber), getString(R.string.bookmarckName)));

        recyclerView.setAdapter(new BookmarkAdapter(getActivity(), arrayList));

    }
}
