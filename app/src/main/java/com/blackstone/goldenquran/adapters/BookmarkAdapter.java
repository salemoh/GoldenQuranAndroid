package com.blackstone.goldenquran.adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blackstone.goldenquran.Fragments.QuranViewPager;
import com.blackstone.goldenquran.R;
import com.blackstone.goldenquran.models.BookmarkModel;

import java.util.List;

public class BookmarkAdapter extends RecyclerView.Adapter<BookmarkViewHolder> {

    List<BookmarkModel> list;
    LayoutInflater layoutInflater;
    Context context;
    private String[] suar;

    public BookmarkAdapter(Context context, List<BookmarkModel> list) {
        this.list = list;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        suar = context.getResources().getStringArray(R.array.fahrasSuras);
    }

    @Override
    public BookmarkViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BookmarkViewHolder(layoutInflater.inflate(R.layout.bookmark_row_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(BookmarkViewHolder holder, final int position) {
        holder.suraName.setText(context.getString(R.string.surah) + " " + suar[list.get(position).getSuraNo() - 1]);
        holder.pageNumber.setText(context.getString(R.string.pageNumber) + " (" + list.get(position).getPage() + ")");
        holder.bookmarkRelative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt("pageNumber", list.get(position).getPage() - 1);
                Fragment fragment = new QuranViewPager();
                fragment.setArguments(bundle);
                ((FragmentActivity) context).getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).addToBackStack(null).commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

class BookmarkViewHolder extends RecyclerView.ViewHolder {
    TextView pageNumber, suraName;
    RelativeLayout bookmarkRelative;

    BookmarkViewHolder(View itemView) {
        super(itemView);
        pageNumber = (TextView) itemView.findViewById(R.id.bookmarkAayaNumber);
        suraName = (TextView) itemView.findViewById(R.id.bookmarkSuraName);
        bookmarkRelative = (RelativeLayout) itemView.findViewById(R.id.bookMarkRelative);
    }
}