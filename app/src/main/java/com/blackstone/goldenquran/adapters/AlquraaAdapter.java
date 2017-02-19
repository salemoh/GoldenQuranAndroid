package com.blackstone.goldenquran.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.blackstone.goldenquran.R;
import com.blackstone.goldenquran.models.ReaderModel;
import com.blackstone.goldenquran.utilities.SharedPreferencesManager;

import java.util.List;

public class AlquraaAdapter extends RecyclerView.Adapter<AlquraaAdapter.AlquraaViewHolder> {

    private List<ReaderModel> models;
    Context mContext;
    private LayoutInflater layoutInflater;

    public AlquraaAdapter(Context context, List<ReaderModel> models) {
        this.models = models;
        layoutInflater = LayoutInflater.from(context);
        mContext = context;
    }

    @Override
    public AlquraaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new AlquraaViewHolder(layoutInflater.inflate(R.layout.alquraa_row_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(final AlquraaViewHolder holder, final int position) {
        for (int i = 0; i < models.size(); i++) {
            if (i == SharedPreferencesManager.getInteger(mContext, "selectedReader", 20))
                models.get(i).setSelected(true);
        }
        holder.shakeName.setText(models.get(position).shakeName);
        holder.shakeImage.setImageResource(models.get(position).shakeImage);
        holder.mRadio.setChecked(models.get(position).isSelected());


        holder.mRadio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                models.get(position).setSelected(holder.mRadio.isChecked());
                SharedPreferencesManager.putInteger(mContext, "selectedReader", position);
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

    class AlquraaViewHolder extends RecyclerView.ViewHolder {
        ImageView shakeImage;
        TextView shakeName;
        RadioButton mRadio;

        AlquraaViewHolder(View itemView) {
            super(itemView);

            shakeImage = (ImageView) itemView.findViewById(R.id.shakeImage);
            shakeName = (TextView) itemView.findViewById(R.id.shakeName);
            mRadio = (RadioButton) itemView.findViewById(R.id.PickShakeradioButton);

        }
    }

}
