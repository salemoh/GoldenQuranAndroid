package com.blackstone.goldenquran.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blackstone.goldenquran.R;
import com.blackstone.goldenquran.models.PrayTimeSettingsItemModel;
import com.blackstone.goldenquran.models.PrayTimeSettingsItemThreeModel;
import com.blackstone.goldenquran.models.PrayTimeSettingsItemTwoModel;
import com.blackstone.goldenquran.models.PrayTimeSettingsTitleModel;
import com.blackstone.goldenquran.utilities.SharedPreferencesManager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PrayTimeSettingsRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<Object> models;
    LayoutInflater layoutInflater;
    private static final int TITLE = 0;
    private static final int ITEM_ONE = 1;
    private static final int ITEM_TWO = 2;
    private static final int ITEM_THREE = 3;
    Context mContext;

    public PrayTimeSettingsRecyclerAdapter(Context context, List<Object> models) {
        this.models = models;
        layoutInflater = LayoutInflater.from(context);
        mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TITLE) {
            return new PrayTimeSettingsTitleViewHolder(layoutInflater.inflate(R.layout.pray_time_settings_title_type, parent, false));
        } else if (viewType == ITEM_ONE) {
            return new PrayTimeSettingsItemOneViewHolder(layoutInflater.inflate(R.layout.pray_time_settings_item_type, parent, false));
        } else if (viewType == ITEM_TWO) {
            return new PrayTimeSettingsItemTwoViewHolder(layoutInflater.inflate(R.layout.pray_time_settings_item_type, parent, false));
        } else if (viewType == ITEM_THREE) {
            return new PrayTimeSettingsItemThreeViewHolder(layoutInflater.inflate(R.layout.pray_time_settings_item_type, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        if (holder.getItemViewType() == TITLE) {
            ((PrayTimeSettingsTitleViewHolder) holder).title.setText(((PrayTimeSettingsTitleModel) models.get(position)).title);
        } else if (holder.getItemViewType() == ITEM_ONE) {

            for (int i = 1; i < 11; i++) {
                if (i == SharedPreferencesManager.getInteger(mContext, "selected", 20))
                    ((PrayTimeSettingsItemModel) models.get(i)).setSelected(true);
            }


            final PrayTimeSettingsItemOneViewHolder holder1 = (PrayTimeSettingsItemOneViewHolder) holder;
            final PrayTimeSettingsItemModel thisModel = (PrayTimeSettingsItemModel) models.get(position);


            holder1.ItemText.setText(thisModel.itemText);

            if (thisModel.isSelected()) {
                holder1.selectedItem.setVisibility(View.VISIBLE);
            } else {
                holder1.selectedItem.setVisibility(View.GONE);
            }

            holder1.relativeContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    thisModel.setSelected(true);
                    SharedPreferencesManager.putInteger(mContext, "selected", position);
                    for (int i = 1; i < 11; i++) {
                        if (i != position) {
                            ((PrayTimeSettingsItemModel) models.get(i)).setSelected(false);
                        }
                    }
                    notifyDataSetChanged();
                }
            });
        } else if (holder.getItemViewType() == ITEM_TWO) {
            ((PrayTimeSettingsItemTwoViewHolder) holder).ItemText.setText(((PrayTimeSettingsItemTwoModel) models.get(position)).itemText);


        } else if (holder.getItemViewType() == ITEM_THREE) {
            ((PrayTimeSettingsItemThreeViewHolder) holder).ItemText.setText(((PrayTimeSettingsItemThreeModel) models.get(position)).itemText);

        }

    }

    @Override
    public int getItemViewType(int position) {
        if (PrayTimeSettingsTitleModel.class.isInstance(models.get(position)))
            return TITLE;
        else if (PrayTimeSettingsItemModel.class.isInstance(models.get(position)))
            return ITEM_ONE;
        else if (PrayTimeSettingsItemTwoModel.class.isInstance(models.get(position)))
            return ITEM_TWO;
        else
            return ITEM_THREE;
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

class PrayTimeSettingsItemTwoViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.selectedItem)
    ImageView selectedItem;
    @BindView(R.id.ItemText)
    TextView ItemText;
    @BindView(R.id.relativeContainer)
    RelativeLayout relativeContainer;

    public PrayTimeSettingsItemTwoViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);

    }
}

class PrayTimeSettingsItemThreeViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.selectedItem)
    ImageView selectedItem;
    @BindView(R.id.ItemText)
    TextView ItemText;
    @BindView(R.id.relativeContainer)
    RelativeLayout relativeContainer;

    public PrayTimeSettingsItemThreeViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);

    }
}

class PrayTimeSettingsItemOneViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.selectedItem)
    ImageView selectedItem;
    @BindView(R.id.ItemText)
    TextView ItemText;
    @BindView(R.id.relativeContainer)
    RelativeLayout relativeContainer;

    PrayTimeSettingsItemOneViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}