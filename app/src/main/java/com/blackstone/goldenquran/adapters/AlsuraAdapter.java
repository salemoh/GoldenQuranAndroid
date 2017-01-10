package com.blackstone.goldenquran.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.blackstone.goldenquran.R;
import com.blackstone.goldenquran.models.AljuzaModel;
import com.blackstone.goldenquran.models.AlsuraModel;

import java.util.List;



public class AlsuraAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<Object> list;
    LayoutInflater layoutInflater;
    Context context;

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
            AlsuraModel alsuraModel = (AlsuraModel) list.get(position);
            viewHolder.ayat.setText(alsuraModel.numberOfAya);
            viewHolder.name.setText(alsuraModel.suraName);
            viewHolder.number.setText(alsuraModel.number);
            if(context.getResources().getBoolean(R.bool.is_right_to_left))
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


}

class AlsurahViewHolder extends RecyclerView.ViewHolder {

    TextView name, number, ayat;
    ImageView arrow;

    public AlsurahViewHolder(View itemView) {
        super(itemView);
        name = (TextView) itemView.findViewById(R.id.suraName);
        number = (TextView) itemView.findViewById(R.id.numberOfSura);
        ayat = (TextView) itemView.findViewById(R.id.numberOfAyat);
        arrow = (ImageView) itemView.findViewById(R.id.imageLiftArrow);
    }

}

class AlJuzaViewHolder extends RecyclerView.ViewHolder {
    TextView juzoa;

    public AlJuzaViewHolder(View itemView) {
        super(itemView);
        juzoa = (TextView) itemView.findViewById(R.id.juzoaNumber);
    }
}
