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
import com.blackstone.goldenquran.models.ReadersModel;

import java.util.List;

public class AlquraaAdapter extends RecyclerView.Adapter<AlquraaAdapter.AlquraaViewHolder> {

    List<ReadersModel> list;
    LayoutInflater layoutInflater;
    int mSelectedItem = -1;

    public AlquraaAdapter(Context context, List<ReadersModel> list) {
        this.list = list;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public AlquraaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new AlquraaViewHolder(layoutInflater.inflate(R.layout.alquraa_row_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(AlquraaViewHolder holder, final int position) {
        holder.shakeName.setText(list.get(position).shakeName);
        holder.shakeImage.setImageResource(list.get(position).shakeImage);
        holder.mRadio.setChecked(position == mSelectedItem);
    }

    @Override
    public int getItemCount() {
        return list.size();
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

            View.OnClickListener clickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mSelectedItem = getAdapterPosition();
                    notifyItemRangeChanged(0, list.size());
                }
            };

            shakeName.setOnClickListener(clickListener);
            mRadio.setOnClickListener(clickListener);


        }
    }

}
