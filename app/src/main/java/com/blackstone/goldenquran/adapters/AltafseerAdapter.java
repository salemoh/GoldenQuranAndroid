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
import com.blackstone.goldenquran.utilities.SharedPreferencesManager;

import java.util.List;

public class AltafseerAdapter extends RecyclerView.Adapter<AltafseerAdapter.AltafseerViewHolder> {
    private List<TheExplanationModel> models;
    private LayoutInflater layoutInflater;
    Context mContext;

    public AltafseerAdapter(Context context, List<TheExplanationModel> list) {
        this.models = list;
        layoutInflater = LayoutInflater.from(context);
        mContext = context;
    }

    @Override
    public AltafseerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new AltafseerViewHolder(layoutInflater.inflate(R.layout.altafseer_row_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(final AltafseerViewHolder holder, final int position) {
        for (int i = 0; i < models.size(); i++) {
            if (i == SharedPreferencesManager.getInteger(mContext, "selectedTafseer", 20))
                models.get(i).setSelected(true);
        }
        holder.tafseerDescribtion.setText(models.get(position).tafseerDescribtion);
        holder.tafseerName.setText(models.get(position).tafseerName);
        holder.mRadio.setChecked(models.get(position).isSelected());

        holder.mRadio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                models.get(position).setSelected(holder.mRadio.isChecked());
                SharedPreferencesManager.putInteger(mContext, "selectedTafseer", position);
                for (int i = 0; i < models.size(); i++) {
                    if (i != position) {
                        models.get(i).setSelected(false);
                    }
                }
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return models.size();
    }


    class AltafseerViewHolder extends RecyclerView.ViewHolder {
        TextView tafseerName, tafseerDescribtion;
        RadioButton mRadio;

        AltafseerViewHolder(View itemView) {
            super(itemView);
            tafseerName = (TextView) itemView.findViewById(R.id.altafseerName);
            tafseerDescribtion = (TextView) itemView.findViewById(R.id.describtionTafseer);
            mRadio = (RadioButton) itemView.findViewById(R.id.tafseerRadioButton);
        }
    }
}