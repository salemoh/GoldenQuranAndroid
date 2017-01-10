package com.blackstone.goldenquran.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blackstone.goldenquran.R;
import com.blackstone.goldenquran.models.AhadethModel;

import java.util.List;


public class AhadethAdapter extends RecyclerView.Adapter<AhadethViewHolder> {

    List<AhadethModel> list;
    LayoutInflater layoutInflater;

    public AhadethAdapter(Context context, List<AhadethModel> list) {
        this.list = list;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public AhadethViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new AhadethViewHolder(layoutInflater.inflate(R.layout.ahadeth_row_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(AhadethViewHolder holder, int position) {
        holder.titlels.setText(list.get(position).titles);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

class AhadethViewHolder extends RecyclerView.ViewHolder {
    TextView titlels;
    public AhadethViewHolder(View itemView) {
        super(itemView);
        titlels = (TextView) itemView.findViewById(R.id.ahadethTitles);
    }
}
