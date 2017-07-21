package com.blackstone.goldenquran.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blackstone.goldenquran.R;
import com.blackstone.goldenquran.models.QueryMessage;
import com.blackstone.goldenquran.models.SearchMawdoo3Model;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Abdullah on 4/27/2017.
 */

public class SearchMawdoo3Adapter extends RecyclerView.Adapter<SearchMawdoo3ViewHolder> {

    List<SearchMawdoo3Model> models;
    List<SearchMawdoo3Model> searchResult;
    List<SearchMawdoo3Model> originalModels;
    LayoutInflater layoutInflater;

    public SearchMawdoo3Adapter(Context context, List<SearchMawdoo3Model> models) {
        this.models = new ArrayList<>();
        layoutInflater = LayoutInflater.from(context);
        this.originalModels = models;
    }

    public void query(QueryMessage queryMessage) {
        searchResult = new ArrayList<>();

        for (SearchMawdoo3Model s : originalModels) {
            if (s.getMawdoo3().contains(queryMessage.getMessage()))
                searchResult.add(s);
        }

        if (!queryMessage.getMessage().isEmpty()) {
            models = new ArrayList<>(searchResult);
            notifyDataSetChanged();
        } else {
            models = new ArrayList<>();
            notifyDataSetChanged();
        }
    }

    @Override
    public SearchMawdoo3ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SearchMawdoo3ViewHolder(layoutInflater.inflate(R.layout.search_mawdoo3_row_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(SearchMawdoo3ViewHolder holder, int position) {

        holder.searchMawdoo3RowText.setText(models.get(position).getMawdoo3());
    }

    @Override
    public int getItemCount() {
        return models.size();
    }
}

class SearchMawdoo3ViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.searchMawdoo3RowText)
    TextView searchMawdoo3RowText;

    SearchMawdoo3ViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
