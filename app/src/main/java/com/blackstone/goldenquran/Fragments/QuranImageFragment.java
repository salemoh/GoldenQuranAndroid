package com.blackstone.goldenquran.Fragments;

import android.Manifest;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.blackstone.goldenquran.R;
import com.blackstone.goldenquran.database.DataBaseManager;
import com.blackstone.goldenquran.managers.PlayerManager;
import com.blackstone.goldenquran.models.Ayah;
import com.blackstone.goldenquran.models.PreLoadImages;
import com.blackstone.goldenquran.ui.DrawerCloser;
import com.blackstone.goldenquran.utilities.SharedPreferencesManager;
import com.blackstone.goldenquran.views.DrawView;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.blackstone.goldenquran.R.id.container;

public class QuranImageFragment extends Fragment {

    private static final String QURAN_PAGE_NUMBER = "quran_page_number";
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
    int ayahNumber = 0;
    int suraNumber = 0;
    String tafseerText;
    String nozoolReasons;
    String textOfAyah;
    String e3rab;
    String sarf;
    String balagha;
    String value;
    String mawdoo3;
    ArrayList<PreLoadImages> preLoadImages;
    ArrayList<String> wordsMeaning;

    int CLICK_ACTION_THRESH_HOLD = 200;
    float startX;
    float startY;

    int imageWidth, imageHeight;

    public static QuranImageFragment getNewInstance(int quranPageNumber) {
        QuranImageFragment quranImageFragment = new QuranImageFragment();
        Bundle bundle = new Bundle();

        bundle.putInt(QURAN_PAGE_NUMBER, quranPageNumber);

        quranImageFragment.setArguments(bundle);

        return quranImageFragment;
    }

    public QuranImageFragment() {
    }

    public void setPage(int possition) {
        this.position = possition;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        getActivity().invalidateOptionsMenu();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quran_image, container, false);
        ButterKnife.bind(this, view);

        setPage(getArguments().getInt(QURAN_PAGE_NUMBER));

        left = new ArrayList<>();
        top = new ArrayList<>();
        right = new ArrayList<>();
        bottom = new ArrayList<>();

        imageView.left = new ArrayList<>();
        imageView.top = new ArrayList<>();
        imageView.right = new ArrayList<>();
        imageView.bottom = new ArrayList<>();

        initView();
        return view;
    }

    private void initView() {
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
        requestStoragePermission();

        preLoadImages = new ArrayList<>();
        if (position > 0)
            loadToArray(position - 1);
        loadToArray(position);
        loadToArray(position + 1);

        setImageView();

        imageView.setOnTouchListener(imgSourceOnTouchListener);

        imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (tafseerText != null && !tafseerText.isEmpty()) {
                    IntonationTranslatorFragment fragment = new IntonationTranslatorFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("TranslateText", tafseerText);
                    bundle.putString("nozoolReasons", nozoolReasons);
                    bundle.putString("e3rab", e3rab);
                    bundle.putString("sarf", sarf);
                    bundle.putString("balagha", balagha);
                    bundle.putString("value", value);
                    bundle.putString("mawdoo3", mawdoo3);
                    bundle.putStringArrayList("wordsMeaning", wordsMeaning);
                    fragment.setArguments(bundle);
                    getActivity().getSupportFragmentManager().beginTransaction().replace(container, fragment).addToBackStack(null).commit();
                    ((DrawerCloser) getActivity()).respond(textOfAyah);
                    return true;
                }
                return false;
            }
        });
    }

    private void loadToArray(int position) {

        String imageName = String.valueOf(position);

        if (imageName.length() == 2)
            imageName = "0" + position;
        else if (imageName.length() == 1)
            imageName = "00" + position;
//        else
//            imageName = "000" + sPageNumber;

        //check images
        if (PlayerManager.isImagePresent(imageName)) {
            Drawable drawable = PlayerManager.readImageFromInternalStorage(imageName);

            imageWidth = PlayerManager.getWidth(imageName);
            imageHeight = PlayerManager.getHeight(imageName);
            if (preLoadImages.size() < 3)
                preLoadImages.add(new PreLoadImages(drawable, position));
            else {
                preLoadImages.clear();
                preLoadImages.add(new PreLoadImages(drawable, position));
            }


        } /*try to download images*/ else {
            if (isOnline()) {
                String url = "https://raw.githubusercontent.com/salemoh/GoldenQuranRes/master/images/kingFahad/page" + imageName + ".png";
                String location = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath() + "/kingFahad/page" + imageName + ".png";
                new DownloadFile().execute(url, location, position + "");
            } else {
                noConnection();
            }

//            load image from assets
//            InputStream ims;
//            ims = getActivity().getAssets().open(imageName);
//            Drawable d = Drawable.createFromStream(ims, null);
//            imageView.setImageDrawable(d);

        }
    }


    public void setImageView() {

        if (preLoadImages.size() == 3)
            imageView.setImageDrawable(preLoadImages.get(1).Image);
        else if (!preLoadImages.isEmpty())
            imageView.setImageDrawable(preLoadImages.get(0).Image);


        //check db
        if (PlayerManager.isDBPresent("KingFahad1.db")) {
            new getPagePoints().execute("KingFahad1.db");
        }/*try to download db files*/ else {
            if (isOnline()) {
                try {
                    String url = "https://raw.githubusercontent.com/salemoh/GoldenQuranRes/master/db/KingFahad1.db";
                    String location = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath() + "/DB" + "/KingFahad1.db";
                    new DownloadFile().execute(url, location, position + "");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                noConnection();
            }
        }
    }


    private void requestStoragePermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)) {
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }

        //And finally ask for the permission
        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);

        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }

        //And finally ask for the permission
        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
    }


    View.OnTouchListener imgSourceOnTouchListener = new View.OnTouchListener() {

        @Override
        public boolean onTouch(View view, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    startX = event.getX();
                    startY = event.getY();
                    break;
                case MotionEvent.ACTION_UP:
                    float endX = event.getX();
                    float endY = event.getY();

                    if (isAClick(startX, endX, startY, endY)) {
                        float eventX = event.getX();
                        float eventY = event.getY();


                        mapX = map(eventX, 0, imageView.getWidth(), 0, imageWidth);
                        mapY = map(eventY, 0, imageView.getHeight(), 0, imageHeight);

                        if (ayahPoints != null && !ayahPoints.isEmpty()) {

                            for (int i = 0; i < ayahPoints.size(); i++) {
                                if (mapX > ayahPoints.get(i).upperLeftX && mapX < ayahPoints.get(i).upperRightX && mapY > ayahPoints.get(i).upperRightY && mapY < ayahPoints.get(i).lowerLeftY) {
                                    ayahNumber = (int) ayahPoints.get(i).ayah;
                                    suraNumber = (int) ayahPoints.get(i).surah;
                                }
                            }

                            left.clear();
                            top.clear();
                            right.clear();
                            bottom.clear();

                            new getTranslation().execute(suraNumber, ayahNumber);

                            new getNozoolReasons().execute(suraNumber, ayahNumber);

                            new getQuranData().execute(suraNumber, ayahNumber);

                            new wordsMeaning().execute(suraNumber, ayahNumber);

                            new getMawdoo3Data().execute(suraNumber, ayahNumber);

                            new getPageText().execute(ayahPoints);

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
                                imageView.left.add(map(left.get(i).longValue(), 0, imageWidth, 0, imageView.getWidth()));
                                imageView.top.add(map(top.get(i).longValue(), 0, imageHeight, 0, imageView.getHeight()));
                                imageView.right.add(map(right.get(i).longValue(), 0, imageWidth, 0, imageView.getWidth()));
                                imageView.bottom.add(map(bottom.get(i).longValue(), 0, imageHeight, 0, imageView.getHeight()));
                            }
                        }

                        imageView.touched = true;
                        imageView.invalidate();
                    } else {
                        if (startY < endY)
                            ((DrawerCloser) getActivity()).moveToolbarDown();
                        else
                            ((DrawerCloser) getActivity()).moveToolbarUp();
                    }
                    break;
            }
            return false;
        }
    };

    private boolean isAClick(float startX, float endX, float startY, float endY) {
        float differenceX = Math.abs(startX - endX);
        float differenceY = Math.abs(startY - endY);
        if (differenceX > CLICK_ACTION_THRESH_HOLD || differenceY > CLICK_ACTION_THRESH_HOLD) {
            return false;
        }
        return true;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    public boolean isOnline() {
        if (getActivity() != null) {
            ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            return netInfo != null && netInfo.isConnectedOrConnecting();
        }
        return false;
    }

    @Override
    public void onResume() {
        super.onResume();

//        handler = new Handler();
//
//        r = new Runnable() {
//            public void run() {
//                if (getActivity() != null)
//                    ((DrawerCloser) getActivity()).moveToolbarUp();
//            }
//        };
//
//        handler.postDelayed(r, 2000);

    }

    void noConnection() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("No Internet Connection");
        builder.setMessage("you'r phone has no internet Connection try to connect it to load content");
        builder.create().show();
    }

    float map(float x, float in_min, float in_max, float out_min, float out_max) {
        return (x - in_min) * (out_max - out_min) / (in_max - in_min) + out_min;
    }

    private class getPagePoints extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... strings) {
            if (getActivity() != null) {
                data = new DataBaseManager(getActivity(), strings[0], false).createDatabase();
                data.open();
                ayahPoints = data.getPagePoints(position);
            }
            return null;
        }
    }

    private class wordsMeaning extends AsyncTask<Integer, Void, Void> {
        @Override
        protected Void doInBackground(Integer... integers) {
            data = new DataBaseManager(getActivity(), "QuranMeaningByWord.db", true).createDatabase();
            data.open();
            wordsMeaning = data.getWordMeaning(integers[0], integers[1]);
            return null;
        }
    }

    private class getPageText extends AsyncTask<ArrayList<Ayah>, Void, Void> {

        @Override
        protected Void doInBackground(ArrayList<Ayah>... arrayLists) {
            if (getActivity() != null) {
                data = new DataBaseManager(getActivity(), "quran_text_ar.db", true).createDatabase();
                data.open();
                textOfAyah = data.getPageText(arrayLists[0]);
            }
            return null;
        }
    }

    class DownloadFile extends AsyncTask<String, Integer, Integer> {

        @Override
        protected Integer doInBackground(String... urls) {
            int count;
            try {
                URL url = new URL(urls[0]);
                URLConnection connection = url.openConnection();
                connection.connect();

//                int contentLength = connection.getContentLength();
//                long total = 0;

                InputStream input = new BufferedInputStream(url.openStream());
                OutputStream output = new FileOutputStream(urls[1]);

                byte data[] = new byte[1024];

                while ((count = input.read(data)) != -1) {
//                    total += count;
//                    publishProgress((int) (total * 100 / contentLength));
                    output.write(data, 0, count);
                }

                output.flush();
                output.close();
                input.close();
            } catch (Exception e) {
            }
            return Integer.parseInt(urls[2]);
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            loadToArray(integer);
            setImageView();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            Toast.makeText(getActivity(), "" + values[0], Toast.LENGTH_SHORT).show();
        }
    }

    private class getNozoolReasons extends AsyncTask<Integer, Void, Void> {

        @Override
        protected Void doInBackground(Integer... integers) {
            data = new DataBaseManager(getActivity(), "QuranNozoolReasons.db", true).createDatabase();
            data.open();
            nozoolReasons = data.getNozoolReasons(suraNumber, ayahNumber);
            return null;
        }
    }

    private class getTranslation extends AsyncTask<Integer, Void, Void> {
        @Override
        protected Void doInBackground(Integer... integers) {
            if (getActivity() != null) {

                String country = "SwedishTranslation.sqlite";
                switch (SharedPreferencesManager.getInteger(getActivity(), "selectedCountry", 200)) {
                    case 0:
                        country = "";
                        break;
                    case 1:
                        country = "";
                        break;
                    case 2:
                        country = "germanTranslation.sqlite";
                        break;
                    case 3:
                        country = "EnTranslation.sqlite";
                        break;
                    case 4:
                        country = "BosnianTranslation.sqlite";
                        break;
                    case 5:
                        country = "ChineseTranslation.sqlite";
                        break;
                    case 6:
                        country = "FrTranslation.sqlite";
                        break;
                    case 7:
                        country = "";
                        break;
                    case 8:
                        country = "PersianTranslation.sqlite";
                        break;
                    case 9:
                        country = "UyghurTranslation.sqlite";
                        break;
                    case 10:
                        country = "";
                        break;
                    case 11:
                        country = "RussianTranslation.sqlite";
                        break;
                    case 12:
                        country = "TurkishTranslation.sqlite";
                        break;
                    case 13:
                        country = "SwedishTranslation.sqlite";
                        break;
                    case 14:
                        country = "SpainTranslation.sqlite";
                        break;
                }

                data = new DataBaseManager(getActivity(), country, true).createDatabase();
                data.open();
                tafseerText = data.getTr(suraNumber, ayahNumber);
            }
            return null;
        }
    }

    private class getQuranData extends AsyncTask<Integer, Void, Void> {

        @Override
        protected Void doInBackground(Integer... integers) {
            data = new DataBaseManager(getActivity(), "QuranAdditions.db", true).createDatabase();
            data.open();
            ArrayList<String> Data = data.getDataForAyah(suraNumber, ayahNumber);
            if (Data != null) {
                e3rab = Data.get(0);
                sarf = Data.get(1);
                balagha = Data.get(2);
                value = Data.get(3);
            }
            return null;
        }
    }

    private class getMawdoo3Data extends AsyncTask<Integer, Void, Void> {
        @Override
        protected Void doInBackground(Integer... integers) {
            data = new DataBaseManager(getActivity(), "QuranMawdoo3.db", true).createDatabase();
            data.open();
            mawdoo3 = data.getMawdoo3OfAyah(integers[0], integers[1]);
            return null;
        }
    }
}

