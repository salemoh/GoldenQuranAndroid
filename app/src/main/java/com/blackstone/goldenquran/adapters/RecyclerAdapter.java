package com.blackstone.goldenquran.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.blackstone.goldenquran.R;
import com.blackstone.goldenquran.models.PrayModel;

import java.util.List;


public class RecyclerAdapter extends RecyclerView.Adapter<MyViewHolder> {

    List<PrayModel> list;
    List counter;
    LayoutInflater layoutInflater;
    Context context;
    int[] images = new int[]{R.drawable.fajr,
            R.drawable.duhor,
            R.drawable.aser,
            R.drawable.maghrib,
            R.drawable.isha
    };

    public RecyclerAdapter(List<PrayModel> list, Context context, List counter) {
        this.list = list;
        this.counter = counter;
        layoutInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = layoutInflater.inflate(R.layout.row, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.name.setText(list.get(position).name);

        String[] s = list.get(position).time.replaceAll(" ", "").split(":");

        int minutes = Integer.parseInt(counter.get(position) + "") + Integer.parseInt(s[0] + "") * 60 + Integer.parseInt(s[1]);
        int hours = minutes / 60;
        if (hours >= 24) {
            hours = hours % 24;
        }
        minutes = minutes % 60;

        s[0] = hours + "";
        s[1] = minutes + "";
        if (s[0].length() == 1)
            s[0] = "0" + s[0];
        holder.time.setText((Integer.parseInt(s[1]) >= 10) ? s[0] + ":" + s[1] : s[0] + ":" + "0" + s[1]);

        holder.imageView.setImageResource(images[position]);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

class MyViewHolder extends RecyclerView.ViewHolder {

    TextView time, name;
    ImageView imageView;

    public MyViewHolder(View itemView) {
        super(itemView);
        time = (TextView) itemView.findViewById(R.id.salahTime);
        name = (TextView) itemView.findViewById(R.id.nameOfSalah);
        imageView = (ImageView) itemView.findViewById(R.id.sunImage);
    }
}
