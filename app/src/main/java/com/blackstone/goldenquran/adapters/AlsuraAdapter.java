package com.blackstone.goldenquran.adapters;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blackstone.goldenquran.Fragments.QuranViewPager;
import com.blackstone.goldenquran.R;
import com.blackstone.goldenquran.database.DataBaseManager;
import com.blackstone.goldenquran.models.AljuzaModel;
import com.blackstone.goldenquran.models.AlsuraModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AlsuraAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<Object> list;
    LayoutInflater layoutInflater;
    Context context;
    DataBaseManager data;
    int pageNumber;


    public AlsuraAdapter(Context context, List<Object> list) {
        this.list = list;
        layoutInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 1)
            return new AlJuzaViewHolder(layoutInflater.inflate(R.layout.juzoa_row_layout, parent, false));
        else if (viewType == 0)
            return new AlsurahViewHolder(layoutInflater.inflate(R.layout.sura_row_layout, parent, false));
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder.getItemViewType() == 1) {
            AlJuzaViewHolder viewHolder = (AlJuzaViewHolder) holder;
            AljuzaModel aljuzaModel = (AljuzaModel) list.get(position);
            viewHolder.juzoa.setText(aljuzaModel.name);
        } else if (holder.getItemViewType() == 0) {
            AlsurahViewHolder viewHolder = (AlsurahViewHolder) holder;
            final AlsuraModel alsuraModel = (AlsuraModel) list.get(position);
            viewHolder.ayat.setText(alsuraModel.numberOfAya);
            viewHolder.name.setText(alsuraModel.suraName);
            viewHolder.number.setText(alsuraModel.number);
            viewHolder.contentPanel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new getPageNumber().execute(Integer.parseInt(alsuraModel.number));
                    if (pageNumber > 0) {
                        Fragment fragment = new QuranViewPager();
                        Bundle bundle = new Bundle();
                        bundle.putInt("pageNumber", pageNumber);
                        fragment.setArguments(bundle);
                        ((FragmentActivity) context).getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
                    }
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
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (AljuzaModel.class.isInstance(list.get(position)))
            return 1;
        else if (AlsuraModel.class.isInstance(list.get(position)))
            return 0;
        return 2;
    }


    class AlsurahViewHolder extends RecyclerView.ViewHolder {
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

        AlsurahViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }


    class getPageNumber extends AsyncTask<Integer, Void, Integer> {

        @Override
        protected Integer doInBackground(Integer... integers) {
            data = new DataBaseManager(context, "KingFahad1.db", false).createDatabase();
            data.open();
            return data.getPageNumber(integers[0] + "");
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            pageNumber = integer;
        }
    }


}

class AlJuzaViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.juzoaNumber)
    TextView juzoa;

    AlJuzaViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
