package com.blackstone.goldenquran.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blackstone.goldenquran.R;
import com.blackstone.goldenquran.database.DataBaseManager;
import com.blackstone.goldenquran.managers.PlayerManager;
import com.blackstone.goldenquran.models.TabletFlagModel;
import com.blackstone.goldenquran.utilities.SharedPreferencesManager;

import java.util.List;

public class ChangeLanguageActivityAdapter extends RecyclerView.Adapter<TabletFlagViewHolder> {

    List<TabletFlagModel> models;
    LayoutInflater layoutInflater;
    Context mContext;
    DataBaseManager data;
    String text;


    public ChangeLanguageActivityAdapter(Context context, List<TabletFlagModel> models) {
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
                getTransilate(PlayerManager.getCurrentSurah(), PlayerManager.getCurrentAya());
                Intent intent = new Intent();
                intent.putExtra("text", text);
                ((AppCompatActivity) mContext).setResult(Activity.RESULT_OK, intent);
                ((AppCompatActivity) mContext).finish();
            }
        });
        holder.selectedCountry.setClickable(false);
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public void getTransilate(int surah, int ayah) {

        String country = "SwedishTranslation.sqlite";
        switch (SharedPreferencesManager.getInteger(mContext, "selectedCountry", 200)) {
            case 0:
                country = "germanTranslation.sqlite";
                break;
            case 1:
                country = "EnTranslation.sqlite";
                break;
            case 2:
                country = "BosnianTranslation.sqlite";
                break;
            case 3:
                country = "ChineseTranslation.sqlite";
                break;
            case 4:
                country = "FrTranslation.sqlite";
                break;
            case 5:
                country = "PersianTranslation.sqlite";
                break;
            case 6:
                country = "UyghurTranslation.sqlite";
                break;
            case 7:
                country = "RussianTranslation.sqlite";
                break;
            case 8:
                country = "TurkishTranslation.sqlite";
                break;
            case 9:
                country = "SwedishTranslation.sqlite";
                break;
            case 10:
                country = "SpainTranslation.sqlite";
                break;
        }

        data = new DataBaseManager(mContext, country, true).createDatabase();
        data.open();
        text = data.getTr(surah, ayah);

    }


}