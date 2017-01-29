package com.blackstone.goldenquran.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import com.blackstone.goldenquran.R;
import com.blackstone.goldenquran.models.TheExplanationModel;

import java.util.List;

public class AltafseerAdapter extends RecyclerView.Adapter<AltafseerAdapter.AltafseerViewHolder> {
    private List<TheExplanationModel> list;
    private LayoutInflater layoutInflater;
    private int mSelectedItem = -1;

    public AltafseerAdapter(Context context, List<TheExplanationModel> list) {
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
        holder.mRadio.setChecked(position == mSelectedItem);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class AltafseerViewHolder extends RecyclerView.ViewHolder {
        TextView tafseerName, tafseerDescribtion;
        RadioButton mRadio;

        AltafseerViewHolder(View itemView) {
            super(itemView);
            tafseerName = (TextView) itemView.findViewById(R.id.altafseerName);
            tafseerDescribtion = (TextView) itemView.findViewById(R.id.describtionTafseer);
            mRadio = (RadioButton) itemView.findViewById(R.id.tafseerRadioButton);

            View.OnClickListener clickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mSelectedItem = getAdapterPosition();
                    notifyItemRangeChanged(0, list.size());
                }
            };

            tafseerName.setOnClickListener(clickListener);
            mRadio.setOnClickListener(clickListener);

        }
    }
}