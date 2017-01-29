package com.blackstone.goldenquran.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blackstone.goldenquran.R;
import com.blackstone.goldenquran.models.BookmarkModel;

import java.util.List;

public class BookmarkAdapter extends RecyclerView.Adapter<BookmarkViewHolder> {

    List<BookmarkModel> list;
    LayoutInflater layoutInflater;

    public BookmarkAdapter(Context context, List<BookmarkModel> list) {
        this.list = list;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public BookmarkViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BookmarkViewHolder(layoutInflater.inflate(R.layout.bookmark_row_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(BookmarkViewHolder holder, int position) {
        holder.suraName.setText(list.get(position).suraName);
        holder.ayaNumber.setText(list.get(position).ayaNumber);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

class BookmarkViewHolder extends RecyclerView.ViewHolder {
    TextView ayaNumber, suraName;

    BookmarkViewHolder(View itemView) {
        super(itemView);
        ayaNumber = (TextView) itemView.findViewById(R.id.bookmarkAayaNumber);
        suraName = (TextView) itemView.findViewById(R.id.bookmarkSuraName);
    }
}