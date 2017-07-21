package com.blackstone.goldenquran.ui;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.blackstone.goldenquran.R;
import com.blackstone.goldenquran.utilities.SharedPreferencesManager;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ColorActivity extends AppCompatActivity {

    @BindView(R.id.color1Selected)
    ImageView color1Selected;
    @BindView(R.id.color1)
    FrameLayout color1;
    @BindView(R.id.color2Selected)
    ImageView color2Selected;
    @BindView(R.id.color2)
    FrameLayout color2;
    @BindView(R.id.color3Selected)
    ImageView color3Selected;
    @BindView(R.id.color3)
    FrameLayout color3;
    @BindView(R.id.color5Selected)
    ImageView color5Selected;
    @BindView(R.id.color5)
    FrameLayout color5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        requestStoragePermission();


        switch (SharedPreferencesManager.getInteger(this, "color", R.color.lightOrange)) {
            case R.color.lightBlue: {
                color1Selected.setVisibility(View.INVISIBLE);
                color2Selected.setVisibility(View.INVISIBLE);
                color3Selected.setVisibility(View.VISIBLE);
//                color4Selected.setVisibility(View.INVISIBLE);
                color5Selected.setVisibility(View.INVISIBLE);
//                color6Selected.setVisibility(View.INVISIBLE);
                break;
            }
            case R.color.lightGreen: {
                color1Selected.setVisibility(View.INVISIBLE);
                color2Selected.setVisibility(View.VISIBLE);
                color3Selected.setVisibility(View.INVISIBLE);
//                color4Selected.setVisibility(View.INVISIBLE);
                color5Selected.setVisibility(View.INVISIBLE);
//                color6Selected.setVisibility(View.INVISIBLE);
                break;
            }
            case R.color.lightOrange: {
                color1Selected.setVisibility(View.VISIBLE);
                color2Selected.setVisibility(View.INVISIBLE);
                color3Selected.setVisibility(View.INVISIBLE);
//                color4Selected.setVisibility(View.INVISIBLE);
                color5Selected.setVisibility(View.INVISIBLE);
//                color6Selected.setVisibility(View.INVISIBLE);
                break;
            }
            case R.color.lightPink: {
                color1Selected.setVisibility(View.INVISIBLE);
                color2Selected.setVisibility(View.INVISIBLE);
                color3Selected.setVisibility(View.INVISIBLE);
//                color4Selected.setVisibility(View.INVISIBLE);
                color5Selected.setVisibility(View.VISIBLE);
//                color6Selected.setVisibility(View.INVISIBLE);
                break;
            }
            case R.color.lightYellow: {
                color1Selected.setVisibility(View.INVISIBLE);
                color2Selected.setVisibility(View.INVISIBLE);
                color3Selected.setVisibility(View.INVISIBLE);
//                color4Selected.setVisibility(View.VISIBLE);
                color5Selected.setVisibility(View.INVISIBLE);
//                color6Selected.setVisibility(View.INVISIBLE);
                break;
            }
        }

        color1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferencesManager.putInteger(ColorActivity.this, "color", R.color.lightOrange);
                color1Selected.setVisibility(View.VISIBLE);
                color2Selected.setVisibility(View.INVISIBLE);
                color3Selected.setVisibility(View.INVISIBLE);
//                color4Selected.setVisibility(View.INVISIBLE);
                color5Selected.setVisibility(View.INVISIBLE);
//                color6Selected.setVisibility(View.INVISIBLE);
                startActivity(new Intent(ColorActivity.this, MainActivity.class));
                finish();
            }
        });

        color2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferencesManager.putInteger(ColorActivity.this, "color", R.color.lightGreen);
                color1Selected.setVisibility(View.INVISIBLE);
                color2Selected.setVisibility(View.VISIBLE);
                color3Selected.setVisibility(View.INVISIBLE);
//                color4Selected.setVisibility(View.INVISIBLE);
                color5Selected.setVisibility(View.INVISIBLE);
//                color6Selected.setVisibility(View.INVISIBLE);
                startActivity(new Intent(ColorActivity.this, MainActivity.class));
                finish();

            }
        });

        color3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferencesManager.putInteger(ColorActivity.this, "color", R.color.lightBlue);
                color1Selected.setVisibility(View.INVISIBLE);
                color2Selected.setVisibility(View.INVISIBLE);
                color3Selected.setVisibility(View.VISIBLE);
//                color4Selected.setVisibility(View.INVISIBLE);
                color5Selected.setVisibility(View.INVISIBLE);
//                color6Selected.setVisibility(View.INVISIBLE);
                startActivity(new Intent(ColorActivity.this, MainActivity.class));
                finish();

            }
        });

//        color4.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                SharedPreferencesManager.putInteger(getActivity(), "color", R.color.lightYellow);
//                color1Selected.setVisibility(View.INVISIBLE);
//                color2Selected.setVisibility(View.INVISIBLE);
//                color3Selected.setVisibility(View.INVISIBLE);
//                color4Selected.setVisibility(View.VISIBLE);
//                color5Selected.setVisibility(View.INVISIBLE);
//                color6Selected.setVisibility(View.INVISIBLE);
//            }
//        });

        color5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferencesManager.putInteger(ColorActivity.this
                        , "color", R.color.lightPink);
                color1Selected.setVisibility(View.INVISIBLE);
                color2Selected.setVisibility(View.INVISIBLE);
                color3Selected.setVisibility(View.INVISIBLE);
//                color4Selected.setVisibility(View.INVISIBLE);
                color5Selected.setVisibility(View.VISIBLE);
//                color6Selected.setVisibility(View.INVISIBLE);
                startActivity(new Intent(ColorActivity.this, MainActivity.class));
                finish();

            }
        });

    }

    private void requestStoragePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }
        //And finally ask for the permission
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }
        //And finally ask for the permission
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
    }

}
