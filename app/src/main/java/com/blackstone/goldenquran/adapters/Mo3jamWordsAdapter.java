package com.blackstone.goldenquran.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blackstone.goldenquran.R;
import com.blackstone.goldenquran.models.Mo3jamModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Mo3jamWordsAdapter extends RecyclerView.Adapter<Mo3jamWordsViewHolder> {

    List<Mo3jamModel> models;
    LayoutInflater layoutInflater;

    public Mo3jamWordsAdapter(Context context, List<Mo3jamModel> models) {
        this.models = new ArrayList<>(models);
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public Mo3jamWordsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Mo3jamWordsViewHolder(layoutInflater.inflate(R.layout.mo3jam_row_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(Mo3jamWordsViewHolder holder, int position) {
        holder.mo3jamRoot.setText(models.get(position).wordMo3jam);
        holder.mo3jamWord.setText(models.get(position).wordRoot);
    }

    @Override
    public int getItemCount() {
        return models.size();
    }
}

class Mo3jamWordsViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.mo3jamRoot)
    TextView mo3jamRoot;
    @BindView(R.id.mo3jamWord)
    TextView mo3jamWord;

    Mo3jamWordsViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
