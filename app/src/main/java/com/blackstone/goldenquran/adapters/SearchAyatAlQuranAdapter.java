package com.blackstone.goldenquran.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blackstone.goldenquran.R;
import com.blackstone.goldenquran.models.QueryMessage;
import com.blackstone.goldenquran.models.QuranPageTextModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchAyatAlQuranAdapter extends RecyclerView.Adapter<SearchAyatAlQuranViewHolder> {

    ArrayList<QuranPageTextModel> desplaiedmodels;
    private List<QuranPageTextModel> originalModels;
    LayoutInflater layoutInflater;
    Context context;

    public void query(QueryMessage queryMessage) {
        List<QuranPageTextModel> searchResult = new ArrayList<>();

        for (QuranPageTextModel s : originalModels) {
            if (s.getQuranText().contains(queryMessage.getMessage()))
                searchResult.add(s);
        }

        if (!queryMessage.getMessage().isEmpty()) {
            desplaiedmodels = new ArrayList<>(searchResult);
            notifyDataSetChanged();
        } else {
            desplaiedmodels = new ArrayList<>();
            notifyDataSetChanged();
        }
    }

    public SearchAyatAlQuranAdapter(Context context, ArrayList<QuranPageTextModel> models) {
        this.desplaiedmodels = new ArrayList<>();
        this.originalModels = models;
        layoutInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public SearchAyatAlQuranViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SearchAyatAlQuranViewHolder(layoutInflater.inflate(R.layout.search_ayat_al_quran, parent, false));
    }

    @Override
    public void onBindViewHolder(SearchAyatAlQuranViewHolder holder, int position) {
        holder.searchAatAlQuran.setText(desplaiedmodels.get(position).getQuranText() + " " + desplaiedmodels.get(position).getAyah());
        String[] suar = context.getResources().getStringArray(R.array.fahrasSuras);
        holder.searchAyatAlquranSuraName.setText(suar[desplaiedmodels.get(position).getSurah() - 1]);
    }

    @Override
    public int getItemCount() {
        return desplaiedmodels.size();
    }
}

class SearchAyatAlQuranViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.searchAyatAlquranSuraName)
    TextView searchAyatAlquranSuraName;
    @BindView(R.id.searchAatAlQuran)
    TextView searchAatAlQuran;

    SearchAyatAlQuranViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
