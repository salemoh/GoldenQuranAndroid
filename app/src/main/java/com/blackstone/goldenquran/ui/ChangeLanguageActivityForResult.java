package com.blackstone.goldenquran.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.blackstone.goldenquran.R;
import com.blackstone.goldenquran.adapters.ChangeLanguageActivityAdapter;
import com.blackstone.goldenquran.models.TabletFlagModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChangeLanguageActivityForResult extends AppCompatActivity {

    @BindView(R.id.changeLanguageRecycler)
    RecyclerView changeLanguageRecycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_language);
        ButterKnife.bind(this);

        ArrayList<TabletFlagModel> arrayList = new ArrayList<>();
//        arrayList.add(new TabletFlagModel(R.drawable.sa, "المملكه العربيه السعوديه"));
//        arrayList.add(new TabletFlagModel(R.drawable.al, "المجر"));
        arrayList.add(new TabletFlagModel(R.drawable.flag_of_germany, "المانيا"));
        arrayList.add(new TabletFlagModel(R.drawable.us, "الولايات المتحدة الامريكية"));
        arrayList.add(new TabletFlagModel(R.drawable.ba, "البوسنه و الهرسك"));
        arrayList.add(new TabletFlagModel(R.drawable.cn, "الصين"));
        arrayList.add(new TabletFlagModel(R.drawable.fr, "فرنسا"));
//        arrayList.add(new TabletFlagModel(R.drawable.in, "اندونيسيا"));
        arrayList.add(new TabletFlagModel(R.drawable.ir, "ايران"));
        arrayList.add(new TabletFlagModel(R.drawable.ku, "كردستام"));
//        arrayList.add(new TabletFlagModel(R.drawable.pk, "باكستان"));
        arrayList.add(new TabletFlagModel(R.drawable.ru, "روسيا"));
        arrayList.add(new TabletFlagModel(R.drawable.tr, "تركيا"));
        arrayList.add(new TabletFlagModel(R.drawable.swedish_flag, "السويد"));
        arrayList.add(new TabletFlagModel(R.drawable.spain_flag, "اسبانيا"));

        changeLanguageRecycler.setLayoutManager(new LinearLayoutManager(this));
        changeLanguageRecycler.setAdapter(new ChangeLanguageActivityAdapter(this, arrayList));
    }

}
