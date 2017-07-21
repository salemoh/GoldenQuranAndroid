package com.blackstone.goldenquran.adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blackstone.goldenquran.Fragments.QuranViewPager;
import com.blackstone.goldenquran.R;
import com.blackstone.goldenquran.models.AljuzaModel;
import com.blackstone.goldenquran.models.QueryMessage;
import com.blackstone.goldenquran.models.TableOfContents;

import java.util.ArrayList;
import java.util.List;


public class AlJuzoaAdapter extends RecyclerView.Adapter<AlJuzaViewHolder> {
    List<AljuzaModel> list;
    List<AljuzaModel> models;
    List<AljuzaModel> originalModels;
    LayoutInflater layoutInflater;
    ArrayList<TableOfContents> modelsContents;
    Context context;

    public AlJuzoaAdapter(Context context, List<AljuzaModel> list, ArrayList<TableOfContents> modelsContents) {
        this.models = list;
        layoutInflater = LayoutInflater.from(context);
        this.modelsContents = modelsContents;
        this.context = context;
        originalModels = list;
    }

    public void query(QueryMessage queryMessage) {
        list = new ArrayList<>();

        for (AljuzaModel s : models) {
            if (AljuzaModel.class.isInstance(s)) {
                if (s.getName().contains(queryMessage.getMessage()))
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

    @Override
    public AlJuzaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new AlJuzaViewHolder(layoutInflater.inflate(R.layout.juzoa_row_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(AlJuzaViewHolder holder, final int position) {
        holder.juzoa.setText(originalModels.get(position).getName());
        holder.juzoRelative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                for (int i = 0; i < modelsContents.size(); i++) {
                    if (originalModels.get(position).getNumber() == 1) {
                        bundle.putInt("pageNumber", modelsContents.get(1).getPage() - 1);
                        Fragment fragment = new QuranViewPager();
                        fragment.setArguments(bundle);
                        ((FragmentActivity) context).getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
                        break;
                    } else if (originalModels.get(position).getNumber() == 4) {
                        bundle.putInt("pageNumber", modelsContents.get(3).getPage() - 1);
                        Fragment fragment = new QuranViewPager();
                        fragment.setArguments(bundle);
                        ((FragmentActivity) context).getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
                        break;
                    } else if (originalModels.get(position).getNumber() == modelsContents.get(i).getJuz() - 1) {
                        bundle.putInt("pageNumber", modelsContents.get(i).getPage() - 1);
                        Fragment fragment = new QuranViewPager();
                        fragment.setArguments(bundle);
                        ((FragmentActivity) context).getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
                        break;
                    }

                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return originalModels.size();
    }
}
