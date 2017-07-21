package com.blackstone.goldenquran.ui;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.blackstone.goldenquran.R;
import com.blackstone.goldenquran.adapters.ActivityResultAdapter;
import com.blackstone.goldenquran.models.AljuzaModel;
import com.blackstone.goldenquran.models.AlsuraModel;
import com.blackstone.goldenquran.models.QueryMessage;
import com.blackstone.goldenquran.models.TableOfContents;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PlayerActivityForResult extends AppCompatActivity {

    @BindView(R.id.activityForResultRecycler)
    RecyclerView activityForResultRecycler;
    @BindView(R.id.activityyForResultToolbar)
    Toolbar toolbar;
    ArrayList<TableOfContents> data;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_for_result);
        ButterKnife.bind(this);

        toolbar.setTitle(R.string.pickSurah);
        setSupportActionBar(toolbar);


        activityForResultRecycler.setLayoutManager(new LinearLayoutManager(this));
        ArrayList arrayList = new ArrayList();
        String[] ajzaNames = getResources().getStringArray(R.array.fahrasAjzaa);
        String[] suarNames = getResources().getStringArray(R.array.fahrasSuras);
        arrayList.add(new AljuzaModel(ajzaNames[0], 0));
        int juzNumber = 1;
        for (int i = 0; i < data.size() - 1; i++) {
            arrayList.add(new AlsuraModel(data.get(i).getSurah() + "", data.get(i).getVersesCount() + "", suarNames[data.get(i).getSurah() - 1]));
            if (data.get(i).getJuz() != data.get(i + 1).getJuz()) {
                for (int j = 0; j < (data.get(i + 1).getJuz() - data.get(i).getJuz()); j++) {
                    arrayList.add(new AljuzaModel(ajzaNames[data.get(i).getJuz() + j], juzNumber++));
                }
            }
        }
        arrayList.add(new AlsuraModel(data.get(data.size() - 1).getSurah() + "", data.get(data.size() - 1).getVersesCount() + "", suarNames[data.get(data.size() - 1).getSurah() - 1]));

        activityForResultRecycler.setAdapter(new ActivityResultAdapter(this, arrayList, data));

    }

    @Subscribe
    public void query(QueryMessage queryMessage) {
        ((ActivityResultAdapter) activityForResultRecycler.getAdapter()).query(queryMessage);
    }


    @Override
    public void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_layout, menu);
        SearchManager searchManager = (SearchManager) this.getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(this.getComponentName()));
        searchView.setIconifiedByDefault(true);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                EventBus.getDefault().post(new QueryMessage(query));
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                EventBus.getDefault().post(new QueryMessage(newText));

                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
