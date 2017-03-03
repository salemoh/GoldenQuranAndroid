package com.blackstone.goldenquran.Fragments;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.blackstone.goldenquran.R;
import com.blackstone.goldenquran.database.DataBaseManager;
import com.blackstone.goldenquran.models.Ayah;
import com.blackstone.goldenquran.ui.DrawerCloser;
import com.blackstone.goldenquran.utilities.SharedPreferencesManager;
import com.blackstone.goldenquran.views.DrawView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class QuranImageFragment extends Fragment {

    float mapX, mapY;
    ArrayList<Float> left, top, right, bottom;
    DataBaseManager data;
    ArrayList<Ayah> ayahPoints;
    Handler handler;
    Runnable r;
    int position;
    @BindView(R.id.backgroundQuranImage)
    ImageView backgroundQuranImage;
    @BindView(R.id.quranImage)
    DrawView imageView;


    public void setPage(int possition) {
        this.position = possition;
    }


    public QuranImageFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        getActivity().invalidateOptionsMenu();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quran_image, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        left = new ArrayList<>();
        top = new ArrayList<>();
        right = new ArrayList<>();
        bottom = new ArrayList<>();

        imageView.left = new ArrayList<>();
        imageView.top = new ArrayList<>();
        imageView.right = new ArrayList<>();
        imageView.bottom = new ArrayList<>();

        switch (SharedPreferencesManager.getInteger(getActivity(), "color", Color.WHITE)) {
            case R.color.lightBlue: {
                if (position % 2 == 0)
                    backgroundQuranImage.setImageResource(R.drawable.page_rightview_blue);
                else
                    backgroundQuranImage.setImageResource(R.drawable.page_leftview_blue);
                break;
            }
            case R.color.lightGreen: {
                if (position % 2 == 0)
                    backgroundQuranImage.setImageResource(R.drawable.page_rightview_green);
                else
                    backgroundQuranImage.setImageResource(R.drawable.page_leftview_green);
                break;
            }
            case R.color.lightOrange: {
                if (position % 2 == 0)
                    backgroundQuranImage.setImageResource(R.drawable.page_rightview_white);
                else
                    backgroundQuranImage.setImageResource(R.drawable.page_leftview_white);
                break;
            }
            case R.color.lightPink: {
                if (position % 2 == 0)
                    backgroundQuranImage.setImageResource(R.drawable.page_rightview_red);
                else
                    backgroundQuranImage.setImageResource(R.drawable.page_leftview_red);
                break;
            }
            case R.color.lightYellow: {
                if (position % 2 == 0)
                    backgroundQuranImage.setImageResource(R.drawable.page_rightview_white);
                else
                    backgroundQuranImage.setImageResource(R.drawable.page_leftview_white);
                break;
            }
            default: {
                if (position % 2 == 0)
                    backgroundQuranImage.setImageResource(R.drawable.page_rightview_red);
                else
                    backgroundQuranImage.setImageResource(R.drawable.page_leftview_red);
                break;
            }

        }

        setImageView(position);
        imageView.setOnTouchListener(imgSourceOnTouchListener);

    }

    @Override
    public void onResume() {
        super.onResume();

        handler = new Handler();

        r = new Runnable() {
            public void run() {
                if (getActivity() != null)
                    ((DrawerCloser) getActivity()).moveToolbarUp();
            }
        };

        handler.postDelayed(r, 2000);

    }

    public void setImageView(int sPageNumber) {
        InputStream ims;
        try {
            String s = String.valueOf(sPageNumber);
            if (s.length() == 3)
                s = "_0" + sPageNumber + ".png";
            else if (s.length() == 2)
                s = "_00" + sPageNumber + ".png";
            else
                s = "_000" + sPageNumber + ".png";

            ims = getActivity().getAssets().open(s);
            Drawable d = Drawable.createFromStream(ims, null);
            imageView.setImageDrawable(d);

            new getPagePoints().execute(sPageNumber);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    View.OnTouchListener imgSourceOnTouchListener = new View.OnTouchListener() {

        @Override
        public boolean onTouch(View view, MotionEvent event) {

            float eventX = event.getX();
            float eventY = event.getY();

            mapX = map(eventX, 0, imageView.getWidth(), 0, 432);
            mapY = map(eventY, 0, imageView.getHeight(), 0, 694);

            if (ayahPoints != null && !ayahPoints.isEmpty()) {

                float ayahNumber = 0;
                float suraNumber = 0;
                for (int i = 0; i < ayahPoints.size(); i++) {
                    if (mapX > ayahPoints.get(i).upperLeftX && mapX < ayahPoints.get(i).upperRightX && mapY > ayahPoints.get(i).upperRightY && mapY < ayahPoints.get(i).lowerLeftY) {
                        ayahNumber = ayahPoints.get(i).ayah;
                        suraNumber = ayahPoints.get(i).surah;
                    }
                }

                left.clear();
                top.clear();
                right.clear();
                bottom.clear();

                for (int i = 0; i < ayahPoints.size(); i++) {
                    if (ayahNumber == ayahPoints.get(i).ayah && suraNumber == ayahPoints.get(i).surah) {
                        left.add(ayahPoints.get(i).upperLeftX);
                        top.add(ayahPoints.get(i).upperLeftY);
                        right.add(ayahPoints.get(i).upperRightX);
                        bottom.add(ayahPoints.get(i).lowerLeftY);
                    }
                }

                imageView.left.clear();
                imageView.top.clear();
                imageView.right.clear();
                imageView.bottom.clear();

                for (int i = 0; i < left.size(); i++) {
                    imageView.left.add(map(left.get(i).longValue(), 0, 432, 0, imageView.getWidth()));
                    imageView.top.add(map(top.get(i).longValue(), 0, 694, 0, imageView.getHeight()));
                    imageView.right.add(map(right.get(i).longValue(), 0, 432, 0, imageView.getWidth()));
                    imageView.bottom.add(map(bottom.get(i).longValue(), 0, 694, 0, imageView.getHeight()));
                }
            }

            imageView.touched = true;
            imageView.invalidate();

            return true;
        }
    };

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    float map(float x, float in_min, float in_max, float out_min, float out_max) {
        return (x - in_min) * (out_max - out_min) / (in_max - in_min) + out_min;
    }

    private class getPagePoints extends AsyncTask<Integer, Void, ArrayList<Ayah>> {

        @Override
            protected ArrayList<Ayah> doInBackground(Integer... integers) {
            if (getActivity() != null) {
                data = new DataBaseManager(getActivity()).createDatabase();
                data.open();
                ayahPoints = data.getPagePoints(position);
                return ayahPoints;
            }
            return null;
        }
    }
}

