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
import com.blackstone.goldenquran.adapters.BookmarkAdapter;
import com.blackstone.goldenquran.models.BookmarkModel;
import com.blackstone.goldenquran.ui.DrawerCloser;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BookmarkFragment extends Fragment {


    @BindView(R.id.bookmarkRecycler)
    RecyclerView recyclerView;

    public BookmarkFragment() {
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
        View view = inflater.inflate(R.layout.bookmark_layout, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        ((DrawerCloser) getActivity()).moveToolbarDown();

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
