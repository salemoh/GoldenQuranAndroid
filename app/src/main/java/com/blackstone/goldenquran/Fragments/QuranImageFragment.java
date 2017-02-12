package com.blackstone.goldenquran.Fragments;

import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.blackstone.goldenquran.R;
import com.blackstone.goldenquran.adapters.TestAdapter;
import com.blackstone.goldenquran.models.Ayah;
import com.blackstone.goldenquran.views.DrawView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class QuranImageFragment extends Fragment {

    DrawView imageView;
    float mapX, mapY;
    ArrayList<Float> left, top, right, bottom;
    int pageNumber;
    TestAdapter data;
    Cursor ayahPoints;
    ArrayList<Ayah> ayah;

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
        return inflater.inflate(R.layout.fragment_quran_image, container, false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        imageView = (DrawView) getView().findViewById(R.id.quranImage);

        left = new ArrayList<>();
        top = new ArrayList<>();
        right = new ArrayList<>();
        bottom = new ArrayList<>();


        imageView.left = new ArrayList<>();
        imageView.top = new ArrayList<>();
        imageView.right = new ArrayList<>();
        imageView.bottom = new ArrayList<>();

        setImageView("_0006.png");

        imageView.setOnTouchListener(imgSourceOnTouchListener);

    }

    public void setImageView(String sPageNumber) {
        InputStream ims;
        try {
            ims = getActivity().getAssets().open(sPageNumber);
            Drawable d = Drawable.createFromStream(ims, null);
            imageView.setImageDrawable(d);

            pageNumber = Integer.parseInt(sPageNumber.substring(1, 5));

            new getPagePoints().execute(pageNumber);

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

            if (!ayah.isEmpty()) {

                float ayahNumber = 0;
                for (int i = 0; i < ayah.size(); i++) {
                    if (mapX > ayah.get(i).upperLeftX && mapX < ayah.get(i).upperRightX && mapY > ayah.get(i).upperRightY && mapY < ayah.get(i).lowerLeftY) {
                        ayahNumber = ayah.get(i).ayah;
                    }
                }

                left.clear();
                top.clear();
                right.clear();
                bottom.clear();

                for (int i = 0; i < ayah.size(); i++) {
                    if (ayahNumber == ayah.get(i).ayah) {
                        left.add(ayah.get(i).upperLeftX);
                        top.add(ayah.get(i).upperLeftY);
                        right.add(ayah.get(i).upperRightX);
                        bottom.add(ayah.get(i).lowerLeftY);
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

    float map(float x, float in_min, float in_max, float out_min, float out_max) {
        return (x - in_min) * (out_max - out_min) / (in_max - in_min) + out_min;
    }

    private class getPagePoints extends AsyncTask<Integer, Void, Cursor> {

        @Override
        protected Cursor doInBackground(Integer... integers) {
            data = new TestAdapter(getActivity()).createDatabase();
            data.open();
            ayahPoints = data.getPagePoints(pageNumber);
            return ayahPoints;
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            super.onPostExecute(cursor);

            ayah = new ArrayList<>();

            while (cursor.moveToNext()) {
                ayah.add(new Ayah(
                        cursor.getFloat(cursor.getColumnIndex("x")),
                        cursor.getFloat(cursor.getColumnIndex("y")),
                        cursor.getFloat(cursor.getColumnIndex("width")),
                        cursor.getFloat(cursor.getColumnIndex("height")),
                        cursor.getFloat(cursor.getColumnIndex("upper_left_x")),
                        cursor.getFloat(cursor.getColumnIndex("upper_left_y")),
                        cursor.getFloat(cursor.getColumnIndex("upper_right_x")),
                        cursor.getFloat(cursor.getColumnIndex("upper_right_y")),
                        cursor.getFloat(cursor.getColumnIndex("lower_right_x")),
                        cursor.getFloat(cursor.getColumnIndex("lower_right_y")),
                        cursor.getFloat(cursor.getColumnIndex("lower_left_x")),
                        cursor.getFloat(cursor.getColumnIndex("lower_left_y")),
                        cursor.getFloat(cursor.getColumnIndex("ayah")),
                        cursor.getFloat(cursor.getColumnIndex("line")),
                        cursor.getFloat(cursor.getColumnIndex("surah")),
                        cursor.getFloat(cursor.getColumnIndex("page_number")),
                        cursor.getFloat(cursor.getColumnIndex("id"))
                ));
            }
        }
    }
}

