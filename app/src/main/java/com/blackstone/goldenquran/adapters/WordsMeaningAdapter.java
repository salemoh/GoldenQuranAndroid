package com.blackstone.goldenquran.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blackstone.goldenquran.R;
import com.blackstone.goldenquran.models.models.WordsMeaningModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class WordsMeaningAdapter extends RecyclerView.Adapter<WordsMeaningViewHolder> {

    List<WordsMeaningModel> models;
    LayoutInflater layoutInflater;


    public WordsMeaningAdapter(Context context, List<WordsMeaningModel> models) {
        this.models = new ArrayList<>(models);
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public WordsMeaningViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new WordsMeaningViewHolder(layoutInflater.inflate(R.layout.words_meaning_row_fragment, parent, false));
    }

    @Override
    public void onBindViewHolder(WordsMeaningViewHolder holder, int position) {
        holder.meaning.setText(models.get(position).word);
        holder.word.setText(models.get(position).meaning);
    }

    @Override
    public int getItemCount() {
        return models.size();
    }
}

class WordsMeaningViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.meaning)
    TextView meaning;
    @BindView(R.id.word)
    TextView word;

    WordsMeaningViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
