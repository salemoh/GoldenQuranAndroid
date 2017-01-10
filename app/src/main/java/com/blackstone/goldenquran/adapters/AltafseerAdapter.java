package com.blackstone.goldenquran.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blackstone.goldenquran.R;
import com.blackstone.goldenquran.models.ALtafseerModel;

import java.util.List;

/**
 * Created by Abdullah on 12/28/2016.
 */

public class AltafseerAdapter extends RecyclerView.Adapter<AltafseerViewHolder> {
    List<ALtafseerModel> list;
    LayoutInflater layoutInflater;

    public AltafseerAdapter(Context context, List<ALtafseerModel> list) {
        this.list = list;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public AltafseerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new AltafseerViewHolder(layoutInflater.inflate(R.layout.altafseer_row_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(AltafseerViewHolder holder, int position) {
        holder.tafseerDescribtion.setText(list.get(position).tafseerDescribtion);
        holder.tafseerName.setText(list.get(position).tafseerName);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

class AltafseerViewHolder extends RecyclerView.ViewHolder {
    TextView tafseerName, tafseerDescribtion;
    public AltafseerViewHolder(View itemView) {
        super(itemView);
        tafseerName = (TextView) itemView.findViewById(R.id.altafseerName);
        tafseerDescribtion = (TextView) itemView.findViewById(R.id.describtionTafseer);

    }
}
