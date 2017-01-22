package com.blackstone.goldenquran.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.blackstone.goldenquran.R;
import com.blackstone.goldenquran.models.PrayModel;

import java.util.List;

import static java.lang.Integer.parseInt;

public class ConfigureAdapter extends RecyclerView.Adapter<MyConfigureViewHolder> {
    private LayoutInflater layoutInflater;
    private Context context;
    private List<PrayModel> list;
    private SharedPreferences.Editor editor;
    private List counter;

    public ConfigureAdapter(Context context, List<PrayModel> list, List countList) {
        this.context = context;
        this.list = list;
        this.counter = countList;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public MyConfigureViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (context == null)
            context = parent.getContext();
        View view = layoutInflater.inflate(R.layout.configure_fragment, parent, false);
        return new MyConfigureViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyConfigureViewHolder holder, final int position) {


        holder.counter.setText(counter.get(position) + "");


        editor = context.getSharedPreferences("counter", Context.MODE_PRIVATE).edit();


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

        

        holder.name.setText(list.get(position).name);


        holder.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int c = parseInt(holder.counter.getText().toString());
                c++;
                holder.counter.setText(c + "");
                editor.putString(position + "", "" + c);
                editor.apply();

                //make the change inside the time string
                String[] s = holder.time.getText().toString().replaceAll(" ", "").split(":");

                int s1 = Integer.parseInt(s[1]);
                s[1] = ++s1 + "";

                if (Integer.parseInt(s[1]) >= 60) {
                    s[1] = "0";
                    int s2 = Integer.parseInt(s[0]);
                    s[0] = ++s2 + "";
                    if (Integer.parseInt(s[0]) >= 24) {
                        s[0] = "1";
                    }
                }

                if (s[0].length() == 1)
                    s[0] = "0" + s[0];
                holder.time.setText((Integer.parseInt(s[1]) >= 10) ? s[0] + ":" + s[1] : s[0] + ":" + "0" + s[1]);
            }
        });


        holder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int c = parseInt(holder.counter.getText().toString());
                c--;
                holder.counter.setText(c + "");
                editor.putString(position + "", "" + c);
                editor.apply();

                String[] s = holder.time.getText().toString().replaceAll(" ", "").split(":");

                int s1 = Integer.parseInt(s[1]);
                s[1] = --s1 + "";

                if (Integer.parseInt(s[1]) < 0) {
                    s[1] = "59";
                    int s2 = Integer.parseInt(s[0]);
                    s[0] = --s2 + "";
                    if (Integer.parseInt(s[0]) <= 0) {
                        s[0] = "23";
                    }
                }
                if (s[0].length() == 1)
                    s[0] = "0" + s[0];
                holder.time.setText((Integer.parseInt(s[1]) >= 10) ? s[0] + ":" + s[1] : s[0] + ":" + "0" + s[1]);
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

class MyConfigureViewHolder extends RecyclerView.ViewHolder {
    ImageView plus, minus;
    TextView counter, name, time;

    MyConfigureViewHolder(View itemView) {
        super(itemView);
        plus = (ImageView) itemView.findViewById(R.id.plusImage);
        minus = (ImageView) itemView.findViewById(R.id.minusImage);
        counter = (TextView) itemView.findViewById(R.id.counter);
        name = (TextView) itemView.findViewById(R.id.nameOfSalahE);
        time = (TextView) itemView.findViewById(R.id.salahTimeE);

    }

}
