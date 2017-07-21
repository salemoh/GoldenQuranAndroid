package com.blackstone.goldenquran.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blackstone.goldenquran.R;
import com.blackstone.goldenquran.models.Mo3jamModel;
import com.blackstone.goldenquran.models.QueryMessage;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchMo3jamAdapter extends RecyclerView.Adapter<SearchMo3jamViewHolder> {


    private List<Mo3jamModel> desplaiedmodels;
    private List<Mo3jamModel> originalModels;
    LayoutInflater layoutInflater;

    @BindView(R.id.mo3jamRoot)
    TextView mo3jamRoot;
    @BindView(R.id.mo3jamWord)
    TextView mo3jamWord;

    public void query(QueryMessage queryMessage) {
        List<Mo3jamModel> searchResult = new ArrayList<>();

        for (Mo3jamModel s : originalModels) {
            if (s.wordRoot.contains(queryMessage.getMessage()))
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

    public SearchMo3jamAdapter(Context context, List<Mo3jamModel> desplaiedmodels) {
        this.desplaiedmodels = new ArrayList<>();
        this.originalModels = desplaiedmodels;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public SearchMo3jamViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SearchMo3jamViewHolder(layoutInflater.inflate(R.layout.mo3jam_row_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(SearchMo3jamViewHolder holder, int position) {
        holder.mo3jamRoot.setText(desplaiedmodels.get(position).wordRoot);
        holder.mo3jamWord.setText(desplaiedmodels.get(position).wordMo3jam);

    }

    @Override
    public int getItemCount() {
        return desplaiedmodels.size();
    }
}

class SearchMo3jamViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.mo3jamRoot)
    TextView mo3jamRoot;
    @BindView(R.id.mo3jamWord)
    TextView mo3jamWord;

    SearchMo3jamViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
