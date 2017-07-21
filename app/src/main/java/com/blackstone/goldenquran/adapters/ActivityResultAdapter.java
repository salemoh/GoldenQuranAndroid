package com.blackstone.goldenquran.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blackstone.goldenquran.R;
import com.blackstone.goldenquran.models.AljuzaModel;
import com.blackstone.goldenquran.models.AlsuraModel;
import com.blackstone.goldenquran.models.QueryMessage;
import com.blackstone.goldenquran.models.TableOfContents;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Abdullah on 5/6/2017.
 */

public class ActivityResultAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<Object> list;
    List<Object> models;
    List<Object> originalModels;
    LayoutInflater layoutInflater;
    Context context;
    ArrayList<TableOfContents> modelsData;

    public void query(QueryMessage queryMessage) {
        list = new ArrayList<>();

        for (Object s : models) {
            if (!AljuzaModel.class.isInstance(s)) {
                if (((AlsuraModel) s).suraName.contains(queryMessage.getMessage()))
                    list.add(s);
            }
        }

        if (!queryMessage.getMessage().isEmpty()) {
            originalModels = new ArrayList<>(list);
            notifyDataSetChanged();
        } else {
            originalModels = new ArrayList<>(models);
            notifyDataSetChanged();
        }
    }

    public ActivityResultAdapter(Context context, List<Object> list, ArrayList<TableOfContents> modelsData) {
        layoutInflater = LayoutInflater.from(context);
        this.context = context;
        this.modelsData = modelsData;
        this.originalModels = list;
        this.models = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 1)
            return new AlJuzaViewHolder1(layoutInflater.inflate(R.layout.juzoa_row_layout, parent, false));
        else if (viewType == 0)
            return new AlsurahViewHolder1(layoutInflater.inflate(R.layout.sura_row_layout, parent, false));
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == 1) {
            AlJuzaViewHolder1 viewHolder = (AlJuzaViewHolder1) holder;
            final AljuzaModel aljuzaModel = (AljuzaModel) originalModels.get(position);
            viewHolder.juzoa.setText(aljuzaModel.getName());
            viewHolder.juzoRelative.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        } else if (holder.getItemViewType() == 0) {
            AlsurahViewHolder1 viewHolder = (AlsurahViewHolder1) holder;
            final AlsuraModel alsuraModel = (AlsuraModel) originalModels.get(position);
            viewHolder.ayat.setText(alsuraModel.numberOfAya);
            viewHolder.name.setText(alsuraModel.suraName);
            viewHolder.number.setText(alsuraModel.number);

            viewHolder.contentPanel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent data = new Intent();
                    data.putExtra("suraName", alsuraModel.suraName + "");
                    data.putExtra("suraVersesCount", alsuraModel.numberOfAya);
                    data.putExtra("suraNumber", alsuraModel.number);
                    ((AppCompatActivity) context).setResult(Activity.RESULT_OK, data);
                    ((AppCompatActivity) context).finish();
                }
            });
            if (context.getResources().getBoolean(R.bool.is_right_to_left))
                viewHolder.arrow.setImageResource(R.drawable.right_arrow);
            else
                viewHolder.arrow.setImageResource(R.drawable.left_arrow);
        }
    }

    @Override
    public int getItemCount() {
        return originalModels.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (AljuzaModel.class.isInstance(originalModels.get(position)))
            return 1;
        else if (AlsuraModel.class.isInstance(originalModels.get(position)))
            return 0;
        return 2;
    }

    class AlsurahViewHolder1 extends RecyclerView.ViewHolder {
        @BindView(R.id.imageLiftArrow)
        ImageView arrow;
        @BindView(R.id.suraName)
        TextView name;
        @BindView(R.id.numberOfSura)
        TextView number;
        @BindView(R.id.numberOfAyat)
        TextView ayat;
        @BindView(R.id.row)
        RelativeLayout contentPanel;

        AlsurahViewHolder1(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}

class AlJuzaViewHolder1 extends RecyclerView.ViewHolder {

    @BindView(R.id.juzoaNumber)
    TextView juzoa;
    @BindView(R.id.juzoRelative)
    RelativeLayout juzoRelative;

    AlJuzaViewHolder1(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
