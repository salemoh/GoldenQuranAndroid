package com.blackstone.goldenquran.adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blackstone.goldenquran.Fragments.AhadeethViewPagerFragment;
import com.blackstone.goldenquran.R;
import com.blackstone.goldenquran.models.AhadeethModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class AhadethAdapter extends RecyclerView.Adapter<AhadethViewHolder> {

    List<AhadeethModel> list;
    LayoutInflater layoutInflater;
    Context mContext;

    public AhadethAdapter(Context context, List<AhadeethModel> list) {
        this.list = list;
        layoutInflater = LayoutInflater.from(context);
        mContext = context;
    }

    @Override
    public AhadethViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new AhadethViewHolder(layoutInflater.inflate(R.layout.ahadeth_row_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(AhadethViewHolder holder, final int position) {
        holder.ahadethTitles.setText(list.get(position).title);
        holder.ahadeethRelative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Fragment fragment = new AhadeethViewPagerFragment();
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("data", (ArrayList) list);
                bundle.putInt("pos", position);
                fragment.setArguments(bundle);

                ((FragmentActivity) mContext).getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).addToBackStack(null).commit();
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

class AhadethViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.ahadethTitles)
    TextView ahadethTitles;
    @BindView(R.id.ahadeethRelative)
    RelativeLayout ahadeethRelative;

    AhadethViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
