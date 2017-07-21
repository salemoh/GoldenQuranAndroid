package com.blackstone.goldenquran.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blackstone.goldenquran.R;
import com.blackstone.goldenquran.api.DownloadAllSurahService;
import com.blackstone.goldenquran.models.TableOfContents;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DownloadSuraAdapter extends RecyclerView.Adapter<DownloadSuraViewHolder> {
    List<TableOfContents> list;
    LayoutInflater layoutInflater;
    Context mContext;
    String[] suar;

    public DownloadSuraAdapter(Context context, List<TableOfContents> list) {
        this.list = list;
        mContext = context;
        layoutInflater = LayoutInflater.from(context);
        suar = context.getResources().getStringArray(R.array.fahrasSuras);
    }

    @Override
    public DownloadSuraViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DownloadSuraViewHolder(layoutInflater.inflate(R.layout.download_sura_row_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(DownloadSuraViewHolder holder, final int position) {
        holder.downloadSuraName.setText(suar[list.get(position).getSurah() - 1]);
        holder.downloadAllSurahRelative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, DownloadAllSurahService.class);
                intent.putExtra(DownloadAllSurahService.SURAH_NUMBER, list.get(position).getSurah() + "");
                intent.putExtra(DownloadAllSurahService.NUMBER_OF_AYAT, list.get(position).getVersesCount());
                DownloadAllSurahService.isDownloadAll = false;
                mContext.startService(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

class DownloadSuraViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.downloadAllSurahRelative)
    RelativeLayout downloadAllSurahRelative;
    @BindView(R.id.downloadSuraName)
    TextView downloadSuraName;

    DownloadSuraViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
