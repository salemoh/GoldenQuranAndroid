package com.blackstone.goldenquran.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blackstone.goldenquran.R;
import com.blackstone.goldenquran.models.DownloadSuraModel;

import java.util.List;

public class DownloadSuraAdapter extends RecyclerView.Adapter<DownloadSuraViewHolder> {
    List<DownloadSuraModel> list;
    LayoutInflater layoutInflater;

    public DownloadSuraAdapter(Context context, List<DownloadSuraModel> list) {
        this.list = list;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public DownloadSuraViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DownloadSuraViewHolder(layoutInflater.inflate(R.layout.download_sura_row_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(DownloadSuraViewHolder holder, int position) {
        holder.suraName.setText(list.get(position).suraName);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

class DownloadSuraViewHolder extends RecyclerView.ViewHolder {
    TextView suraName;
     DownloadSuraViewHolder(View itemView) {
        super(itemView);
        suraName = (TextView) itemView.findViewById(R.id.downloadSuraName);
    }
}
