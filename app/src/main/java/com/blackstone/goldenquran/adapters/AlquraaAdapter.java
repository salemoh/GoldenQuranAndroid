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
import com.blackstone.goldenquran.models.RecitationModel;
import com.blackstone.goldenquran.utilities.SharedPreferencesManager;

import java.util.List;

public class AlquraaAdapter extends RecyclerView.Adapter<AlquraaAdapter.AlquraaViewHolder> {

    private List<RecitationModel> models;
    Context mContext;
    private LayoutInflater layoutInflater;

    public AlquraaAdapter(Context context, List<RecitationModel> models) {
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
            if (i == SharedPreferencesManager.getInteger(mContext, "selectedReader", 0))
                models.get(i).setSelected(true);
        }
        int[] readersImages = new int[]{
                R.drawable.reader1, R.drawable.reader2, R.drawable.reader3,
                R.drawable.reader4, R.drawable.reader5, R.drawable.reader6,
                R.drawable.reader7, R.drawable.reader8, R.drawable.reader9,
                R.drawable.reader10, R.drawable.reader11, R.drawable.reader12,
                R.drawable.reader13, R.drawable.reader14, R.drawable.reader15,
                R.drawable.reader16, R.drawable.reader17, R.drawable.reader18,
                R.drawable.reader19, R.drawable.reader20, R.drawable.reader21,
                R.drawable.reader22, R.drawable.reader23, R.drawable.reader24,
                R.drawable.reader25, R.drawable.reader26, R.drawable.reader27,
                R.drawable.reader28, R.drawable.reader29, R.drawable.reader30,
                R.drawable.reader31, R.drawable.reader33,
                R.drawable.reader34, R.drawable.reader35, R.drawable.reader36,
                R.drawable.reader37, R.drawable.reader38,
                R.drawable.reader40, R.drawable.reader41, R.drawable.reader42
        };

        String[] readersNames = mContext.getResources().getStringArray(R.array.readersNames);

        holder.shakeName.setText(readersNames[position]);
        holder.shakeImage.setImageResource(readersImages[position]);
        holder.mRadio.setChecked(models.get(position).isSelected());


        holder.mRadio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                models.get(position).setSelected(holder.mRadio.isChecked());
                SharedPreferencesManager.putInteger(mContext, "selectedReader", position);
                SharedPreferencesManager.putString(mContext, "baseUrl", models.get(position).getBaseUrl());
                SharedPreferencesManager.putString(mContext, "readerName", models.get(position).getName());
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
