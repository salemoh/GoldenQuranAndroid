package com.blackstone.goldenquran.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.blackstone.goldenquran.R;
import com.blackstone.goldenquran.models.PrayTimeSettingsItemModel;
import com.blackstone.goldenquran.models.PrayTimeSettingsTitleModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PrayTimeSettingsRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<Object> models;
    LayoutInflater layoutInflater;
    private static final int TITLE = 0;
    private static final int ITEM = 1;


    public PrayTimeSettingsRecyclerAdapter(Context context, List<Object> models) {
        this.models = models;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TITLE) {
            return new PrayTimeSettingsTitleViewHolder(layoutInflater.inflate(R.layout.pray_time_settings_title_type, parent, false));
        } else if (viewType == ITEM) {
            return new PrayTimeSettingsItemViewHolder(layoutInflater.inflate(R.layout.pray_time_settings_item_type, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder.getItemViewType() == TITLE) {
            ((PrayTimeSettingsTitleViewHolder) holder).title.setText(((PrayTimeSettingsTitleModel) models.get(position)).title);
        } else {
            PrayTimeSettingsItemViewHolder holder1 = (PrayTimeSettingsItemViewHolder) holder;
            holder1.ItemText.setText(((PrayTimeSettingsItemModel) models.get(position)).itemText);
        }

    }

    @Override
    public int getItemViewType(int position) {
        return (PrayTimeSettingsTitleModel.class.isInstance(models.get(position))) ? 0 : 1;
    }

    @Override
    public int getItemCount() {
        return models.size();
    }
}

class PrayTimeSettingsTitleViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.title)
    TextView title;

    PrayTimeSettingsTitleViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);

    }

}

class PrayTimeSettingsItemViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.selectedItem)
    ImageView selectedItem;
    @BindView(R.id.ItemText)
    TextView ItemText;

    PrayTimeSettingsItemViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}