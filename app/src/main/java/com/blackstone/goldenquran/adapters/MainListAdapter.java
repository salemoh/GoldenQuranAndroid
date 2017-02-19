package com.blackstone.goldenquran.adapters;

import android.content.Context;
import android.content.Intent;
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
import com.blackstone.goldenquran.Fragments.BookmarkFragment;
import com.blackstone.goldenquran.Fragments.FahrasFragment;
import com.blackstone.goldenquran.Fragments.FridayReadingFragment;
import com.blackstone.goldenquran.Fragments.IntonationTranslatorFragment;
import com.blackstone.goldenquran.Fragments.NightReadingFragment;
import com.blackstone.goldenquran.Fragments.NotificationFragment;
import com.blackstone.goldenquran.Fragments.OnFinishOfQuranPrayFragment;
import com.blackstone.goldenquran.Fragments.PlayeSettingsFragment;
import com.blackstone.goldenquran.Fragments.PlayerServicesFragment;
import com.blackstone.goldenquran.Fragments.PrayingTimeViewPagerFragment;
import com.blackstone.goldenquran.Fragments.SearchFragment;
import com.blackstone.goldenquran.Fragments.SettingsFragment;
import com.blackstone.goldenquran.Fragments.StatisticsFragment;
import com.blackstone.goldenquran.R;
import com.blackstone.goldenquran.models.MainListFirstItemModel;
import com.blackstone.goldenquran.models.MainListModel;
import com.blackstone.goldenquran.ui.DrawerCloser;

import java.util.List;

public class MainListAdapter extends RecyclerView.Adapter {

    List<Object> list;
    Context context;
    LayoutInflater layoutInflater;
    Fragment fragment = null;
    private ActionBarDrawerToggle mDrawerToggle;
    int count;


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
            MainListFirstItemModel mainListFirstItemModel = (MainListFirstItemModel) list.get(position);
            viewHolder.image.setImageResource(mainListFirstItemModel.image);
            viewHolder.jumaa.setText(mainListFirstItemModel.jumaa);
            viewHolder.night.setText(mainListFirstItemModel.night);
            viewHolder.athkar.setText(mainListFirstItemModel.athkar);
            if (context.getResources().getBoolean(R.bool.is_right_to_left)) {
                viewHolder.downArrow.setImageResource(R.drawable.main_list_first_item_right_arrow);
                viewHolder.topArrow.setImageResource(R.drawable.main_list_first_item_right_arrow);
            } else {
                viewHolder.downArrow.setImageResource(R.drawable.main_list_first_item_left_arrow);
                viewHolder.topArrow.setImageResource(R.drawable.main_list_first_item_left_arrow);
            }

            ((MainListFirstViewHolder) holder).first.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((FragmentActivity) context).getSupportFragmentManager().beginTransaction().replace(R.id.container, new NightReadingFragment())
                            .commit();
                    ((DrawerCloser) context).close(true);
                    ((DrawerCloser) context).close(false);
                    ((DrawerCloser) context).title(0);

                }
            });

            ((MainListFirstViewHolder) holder).second.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((FragmentActivity) context).getSupportFragmentManager().beginTransaction().replace(R.id.container, new FridayReadingFragment()).addToBackStack(null).commit();
                    ((DrawerCloser) context).close(true);
                    ((DrawerCloser) context).close(false);
                    ((DrawerCloser) context).title(1);

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
        if (MainListFirstItemModel.class.isInstance(list.get(position)))
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

            int pos = 0;

            switch (getPosition()) {
                case 1:
                    fragment = new BookmarkFragment();
                    pos = 2;
                    break;
                case 2:
                    fragment = new FahrasFragment();
                    pos = 3;
                    break;
                case 3:
                    fragment = new PlayerServicesFragment();
                    pos = 4;
                    break;
                case 4:
                    fragment = new IntonationTranslatorFragment();
                    pos = 5;
                    break;
                case 5:
                    fragment = new SearchFragment();
                    pos = 6;
                    break;
                case 6:
                    fragment = new PlayeSettingsFragment();
                    pos = 7;
                    break;
                case 7:
                    fragment = new NotificationFragment();
                    pos = 8;
                    break;
                case 8:
                    fragment = new PrayingTimeViewPagerFragment();
                    pos = 9;
                    break;
                case 9:
                    fragment = new OnFinishOfQuranPrayFragment();
                    pos = 10;
                    break;
                case 10:
                    fragment = new AhadethFragment();
                    pos = 11;
                    break;
                case 11: {
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT, "Hi");
                    sendIntent.setType("text/plain");
                    context.startActivity(Intent.createChooser(sendIntent, "Where To Send"));
                    pos = 12;
                    break;
                }
                case 12:
                    fragment = new SettingsFragment();
                    pos = 13;
                    break;
                case 13:
                    fragment = new StatisticsFragment();
                    pos = 14;
                    break;
                default:
                    Toast.makeText(context, getPosition() + "", Toast.LENGTH_SHORT).show();
            }

            if (fragment != null) {
                Fragment currentFragment = ((FragmentActivity) context).getSupportFragmentManager().findFragmentById(R.id.container);
                if (!currentFragment.getClass().equals(fragment.getClass()))
                    ((FragmentActivity) context).getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).addToBackStack(pos + "").commit();
            }
            ((DrawerCloser) context).close(true);
            ((DrawerCloser) context).close(false);
            ((DrawerCloser) context).title(pos);
        }

    }
}



