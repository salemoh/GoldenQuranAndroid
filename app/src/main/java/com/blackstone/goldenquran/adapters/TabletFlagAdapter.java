package com.blackstone.goldenquran.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blackstone.goldenquran.R;
import com.blackstone.goldenquran.models.TabletFlagModel;
import com.blackstone.goldenquran.utilities.SharedPreferencesManager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class TabletFlagAdapter extends RecyclerView.Adapter<TabletFlagViewHolder> {

    List<TabletFlagModel> models;
    LayoutInflater layoutInflater;
    Context mContext;

    public TabletFlagAdapter(Context context, List<TabletFlagModel> models) {
        this.models = models;
        layoutInflater = LayoutInflater.from(context);
        mContext = context;
    }

    @Override
    public TabletFlagViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TabletFlagViewHolder(layoutInflater.inflate(R.layout.tablet_flags_rwa_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(final TabletFlagViewHolder holder, final int position) {

        for (int i = 0; i < models.size(); i++) {
            if (i == SharedPreferencesManager.getInteger(mContext, "selectedCountry", 1))
                models.get(i).setSelected(true);
        }

        holder.flagImage.setImageResource(models.get(position).flagImage);
        holder.flagText.setText(models.get(position).countryName);
        holder.selectedCountry.setChecked(models.get(position).isSelected());

        holder.flagsRelative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                models.get(position).setSelected(holder.selectedCountry.isChecked());
                SharedPreferencesManager.putInteger(mContext, "selectedCountry", position);
                for (int i = 0; i < models.size(); i++) {
                    if (i != position) {
                        models.get(i).setSelected(false);
                    }
                }
                notifyDataSetChanged();
            }
        });
        holder.selectedCountry.setClickable(false);
    }

    @Override
    public int getItemCount() {
        return models.size();
    }
}

class TabletFlagViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.flagsRelative)
    RelativeLayout flagsRelative;
    @BindView(R.id.flagImage)
    ImageView flagImage;
    @BindView(R.id.flagText)
    TextView flagText;
    @BindView(R.id.selectedCountry)
    RadioButton selectedCountry;

    TabletFlagViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}