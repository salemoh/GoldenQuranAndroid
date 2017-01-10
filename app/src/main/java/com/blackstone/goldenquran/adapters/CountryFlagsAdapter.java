package com.blackstone.goldenquran.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.blackstone.goldenquran.R;

import java.util.List;

/**
 * Created by Abdullah on 12/22/2016.
 */

public class CountryFlagsAdapter extends RecyclerView.Adapter<CountryViewHolder> {

    LayoutInflater layoutInflater;
    List<LanguagesModel> list;

    public CountryFlagsAdapter(Context context, List<LanguagesModel> list) {
        this.layoutInflater = LayoutInflater.from(context);
        this.list = list;
    }

    @Override
    public CountryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CountryViewHolder(layoutInflater.inflate(R.layout.language_row_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(CountryViewHolder holder, int position) {
        holder.country.setText(list.get(position).name);
        holder.flag.setImageResource(list.get(position).image);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

class CountryViewHolder extends RecyclerView.ViewHolder {

    ImageView flag;
    TextView country;

    public CountryViewHolder(View itemView) {
        super(itemView);
        flag = (ImageView) itemView.findViewById(R.id.flagImage);
        country = (TextView) itemView.findViewById(R.id.countryName);
    }
}
