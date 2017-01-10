package com.blackstone.goldenquran.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.blackstone.goldenquran.R;
import com.blackstone.goldenquran.models.AljuzaModel;

import java.util.List;


public class AlJuzoaAdapter extends RecyclerView.Adapter<AlJuzaViewHolder> {
    List<AljuzaModel> list;
    LayoutInflater layoutInflater;

    public AlJuzoaAdapter(Context context, List<AljuzaModel> list) {
        this.list = list;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public AlJuzaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new AlJuzaViewHolder(layoutInflater.inflate(R.layout.juzoa_row_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(AlJuzaViewHolder holder, int position) {
        holder.juzoa.setText(list.get(position).name);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
