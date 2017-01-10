package com.blackstone.goldenquran.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blackstone.goldenquran.Fragments.AhadethFragment;
import com.blackstone.goldenquran.Fragments.AlTarjamehAlTAjweedFragment;
import com.blackstone.goldenquran.Fragments.BookmarkFragment;
import com.blackstone.goldenquran.Fragments.DoaaKhatemAlquranFragment;
import com.blackstone.goldenquran.Fragments.FahrasFragment;
import com.blackstone.goldenquran.Fragments.FridayREadingFragment;
import com.blackstone.goldenquran.Fragments.NightREadingFragment;
import com.blackstone.goldenquran.Fragments.NotificationFragment;
import com.blackstone.goldenquran.Fragments.PlayerFragment;
import com.blackstone.goldenquran.Fragments.PrayingTimeFragment;
import com.blackstone.goldenquran.Fragments.SearchFragment;
import com.blackstone.goldenquran.Fragments.SettingsFragment;
import com.blackstone.goldenquran.Fragments.ShareTheCurrentPageFragment;
import com.blackstone.goldenquran.Fragments.StatisticsFragment;
import com.blackstone.goldenquran.R;
import com.blackstone.goldenquran.models.MainListFirstModel;
import com.blackstone.goldenquran.models.MainListModel;

import java.util.List;

public class MainListAdapter extends RecyclerView.Adapter {

    List<Object> list;
    Context context;
    LayoutInflater layoutInflater;
    private ActionBarDrawerToggle mDrawerToggle;


    public MainListAdapter(Context context, List<Object> list) {
        this.list = list;
        layoutInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0)
            return new MainListFirstViewHolder(layoutInflater.inflate(R.layout.main_list_first_item, parent, false));
        else if (viewType == 1)
            return new MainListViewHolder(layoutInflater.inflate(R.layout.main_list_row, parent, false));
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == 0) {
            MainListFirstViewHolder viewHolder = (MainListFirstViewHolder) holder;
            MainListFirstModel mainListFirstModel = (MainListFirstModel) list.get(position);
            viewHolder.image.setImageResource(mainListFirstModel.image);
            viewHolder.jumaa.setText(mainListFirstModel.jumaa);
            viewHolder.night.setText(mainListFirstModel.night);
            viewHolder.athkar.setText(mainListFirstModel.athkar);
            if (context.getResources().getBoolean(R.bool.is_right_to_left)) {
                viewHolder.downArrow.setImageResource(R.drawable.small_two_right_arrow);
                viewHolder.topArrow.setImageResource(R.drawable.small_two_right_arrow);
            } else {
                viewHolder.downArrow.setImageResource(R.drawable.two_left_arrow);
                viewHolder.topArrow.setImageResource(R.drawable.two_left_arrow);
            }

            ((MainListFirstViewHolder) holder).first.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((FragmentActivity) context).getSupportFragmentManager().beginTransaction().replace(R.id.contener2, new NightREadingFragment()).commit();
                }
            });

            ((MainListFirstViewHolder) holder).second.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((FragmentActivity) context).getSupportFragmentManager().beginTransaction().replace(R.id.contener2, new FridayREadingFragment()).commit();
                }
            });

        } else {
            MainListViewHolder mainListViewHolder = (MainListViewHolder) holder;
            MainListModel mainListModel = (MainListModel) list.get(position);
            mainListViewHolder.title.setText(mainListModel.title);
            mainListViewHolder.image.setImageResource(mainListModel.image);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (MainListFirstModel.class.isInstance(list.get(position)))
            return 0;
        else if (MainListModel.class.isInstance(list.get(position)))
            return 1;
        return 1;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    private class MainListFirstViewHolder extends RecyclerView.ViewHolder {
        TextView athkar, night, jumaa;
        ImageView image, topArrow, downArrow;
        RelativeLayout first, second;

        MainListFirstViewHolder(View itemView) {
            super(itemView);
            athkar = (TextView) itemView.findViewById(R.id.mainListFirstText);
            night = (TextView) itemView.findViewById(R.id.nightWerd);
            jumaa = (TextView) itemView.findViewById(R.id.jumaaWerd);
            image = (ImageView) itemView.findViewById(R.id.mainListFirstImage);
            topArrow = (ImageView) itemView.findViewById(R.id.firstTwoLiftArrow);
            downArrow = (ImageView) itemView.findViewById(R.id.twoLiftArrow);
            first = (RelativeLayout) itemView.findViewById(R.id.firstRelative);
            second = (RelativeLayout) itemView.findViewById(R.id.secondRelative);
        }

    }

    private class MainListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView image;
        TextView title;

        MainListViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            image = (ImageView) itemView.findViewById(R.id.mainListImage);
            title = (TextView) itemView.findViewById(R.id.mainListText);
        }

        @Override
        public void onClick(View v) {
            Fragment fragment = null;
            switch (getPosition()) {
                case 1:
                    fragment = new BookmarkFragment();
                    break;
                case 2:
                    fragment = new FahrasFragment();
                    break;
                case 4:
                    fragment = new AlTarjamehAlTAjweedFragment();
                    break;
                case 5:
                    fragment = new SearchFragment();
                    break;
                case 6:
                    fragment = new PlayerFragment();
                    break;
                case 7:
                    fragment = new NotificationFragment();
                    break;
                case 8:
                    fragment = new PrayingTimeFragment();
                    break;
                case 9:
                    fragment = new DoaaKhatemAlquranFragment();
                    break;
                case 10:
                    fragment = new AhadethFragment();
                    break;
                case 11:
                    fragment = new ShareTheCurrentPageFragment();
                    break;
                case 12:
                    fragment = new SettingsFragment();
                    break;
                case 13:
                    fragment = new StatisticsFragment();
                    break;
                default:
                    Toast.makeText(context, getPosition() + "", Toast.LENGTH_SHORT).show();
            }

            if (fragment != null) {
                ((FragmentActivity) context).getSupportFragmentManager().beginTransaction().replace(R.id.contener2, fragment).commit();

            }
        }
    }
}


