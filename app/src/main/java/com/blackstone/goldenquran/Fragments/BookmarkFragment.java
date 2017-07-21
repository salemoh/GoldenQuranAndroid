package com.blackstone.goldenquran.Fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blackstone.goldenquran.R;
import com.blackstone.goldenquran.adapters.BookmarkAdapter;
import com.blackstone.goldenquran.database.DataBaseManager;
import com.blackstone.goldenquran.models.BookmarkModel;
import com.blackstone.goldenquran.ui.DrawerCloser;
import com.blackstone.goldenquran.utilities.threads.Task;
import com.blackstone.goldenquran.utilities.threads.ThreadManager;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class BookmarkFragment extends Fragment {


    @BindView(R.id.bookmarkRecycler)
    RecyclerView recyclerView;
    DataBaseManager data;
    ArrayList<BookmarkModel> arrayList;
    @BindView(R.id.noBookmarkText)
    TextView noBookmarkText;
    Unbinder unbinder;

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
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        ((DrawerCloser) getActivity()).moveToolbarDown();

        ThreadManager.addTaskToThreadManagerPool("getPagePoints", 1, new Task() {
            @Override
            public void onPreRun() {

            }

            @Override
            public void onPreRunFailure(Exception ex) {

            }

            @Override
            public void onRunSuccess() {

            }

            @Override
            public void onRunFailure(Exception ex) {

            }

            @Override
            public void run() {
                getBookmarks();
            }
        });


    }

    public void getBookmarks() {
        data = new DataBaseManager(getActivity(), "Mus7af.db", true).createDatabase();
        data.open();
        final ArrayList<BookmarkModel> bookmarks = data.getAllBookmark();
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                arrayList = bookmarks;

                if (arrayList.isEmpty()) {
                    noBookmarkText.setVisibility(View.VISIBLE);
                } else {
                    noBookmarkText.setVisibility(View.INVISIBLE);
                    String[] suar = getActivity().getResources().getStringArray(R.array.fahrasSuras);

                    for (int i = 0; i < arrayList.size(); i++) {
                        arrayList.add(i, new BookmarkModel(0.0, 0.0, 0.0, arrayList.get(i).getSuraNo(), arrayList.get(i).getVerseNo(), arrayList.get(i).getPage()));
                        arrayList.remove(i + 1);
                    }

                    recyclerView.setAdapter(new BookmarkAdapter(getActivity(), arrayList));
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onResume() {
        super.onResume();
        ((DrawerCloser) getActivity()).title(1);
    }
}
