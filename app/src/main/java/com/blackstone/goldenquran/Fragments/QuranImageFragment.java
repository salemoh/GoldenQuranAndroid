package com.blackstone.goldenquran.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.blackstone.goldenquran.Application.GoldenApplication;
import com.blackstone.goldenquran.R;
import com.blackstone.goldenquran.api.DownloadService;
import com.blackstone.goldenquran.api.PlayerService;
import com.blackstone.goldenquran.database.DataBaseManager;
import com.blackstone.goldenquran.managers.PlayerManager;
import com.blackstone.goldenquran.models.AutoColorModel;
import com.blackstone.goldenquran.models.Ayah;
import com.blackstone.goldenquran.models.BookmarkModel;
import com.blackstone.goldenquran.models.ChangeDataEvent;
import com.blackstone.goldenquran.models.DataMawdo3ColorModel;
import com.blackstone.goldenquran.models.Mo3jamModel;
import com.blackstone.goldenquran.models.PreLoadImages;
import com.blackstone.goldenquran.models.TableOfContents;
import com.blackstone.goldenquran.models.models.ChangePageEvent;
import com.blackstone.goldenquran.models.models.ColorEvent;
import com.blackstone.goldenquran.models.models.WordsMeaningModel;
import com.blackstone.goldenquran.ui.DrawView;
import com.blackstone.goldenquran.ui.DrawerCloser;
import com.blackstone.goldenquran.utilities.SharedPreferencesManager;
import com.blackstone.goldenquran.utilities.threads.Task;
import com.blackstone.goldenquran.utilities.threads.ThreadManager;
import com.github.clans.fab.FloatingActionButton;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

import static com.blackstone.goldenquran.R.id.container;

public class QuranImageFragment extends Fragment {

    private static final String QURAN_PAGE_NUMBER = "quran_page_number";
    float mapX, mapY;
    ArrayList<Float> left, top, right, bottom;
    DataBaseManager data;
    ArrayList<Ayah> ayahPoints;
    Handler handler;
    Runnable r;
    private String currentPageText;
    int position;
    @BindView(R.id.backgroundQuranImage)
    ImageView backgroundQuranImage;
    @BindView(R.id.quranImage)
    DrawView imageView;
    String tafseerText;
    String nozoolReasons;
    String textOfAyah;
    String e3rab;
    String sarf;
    String balagha;
    String mawdoo3;
    String tafseer;
    ArrayList<String> suras;
    ArrayList<DataMawdo3ColorModel> dataMawdo3ColorModels;
    ArrayList<Mo3jamModel> mo3jamWords;
    ArrayList<PreLoadImages> preLoadImages;
    ArrayList<WordsMeaningModel> wordsMeaning;
    ProgressDialog dialog;
    ArrayList<DataMawdo3ColorModel> colors;
    Integer ayahNumber = 0;
    Integer suraNumber = 0;
    @BindView(R.id.fabPlay)
    FloatingActionButton mFabPlay;
    int imageWidth, imageHeight;
    int CLICK_ACTION_THRESH_HOLD = 200;
    float startX;
    float startY;
    boolean isColorOn;
    ArrayList<BookmarkModel> bookmarks;

    ArrayList<TableOfContents> toc;

    public static QuranImageFragment getNewInstance(int quranPageNumber) {
        QuranImageFragment quranImageFragment = new QuranImageFragment();
        Bundle bundle = new Bundle();

        bundle.putInt(QURAN_PAGE_NUMBER, quranPageNumber);

        quranImageFragment.setArguments(bundle);

        return quranImageFragment;
    }

    public QuranImageFragment() {
    }

    public void setPage(int position) {
        this.position = position;
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

        setPage(getArguments().getInt(QURAN_PAGE_NUMBER) + 1);

        left = new ArrayList<>();
        top = new ArrayList<>();
        right = new ArrayList<>();
        bottom = new ArrayList<>();

        imageView.left = new ArrayList<>();
        imageView.top = new ArrayList<>();
        imageView.right = new ArrayList<>();
        imageView.bottom = new ArrayList<>();

        mFabPlay.hide(true);

        initView();
        setTitle();
        return view;
    }

    private void setTitle() {
        if (getActivity() != null) {
            String[] suar = getActivity().getResources().getStringArray(R.array.fahrasSuras);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(suar[PlayerManager.getCurrentSurah() > 0 ? PlayerManager.getCurrentSurah() - 1 : PlayerManager.getCurrentSurah()]);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }

    private void initView() {
        switch (SharedPreferencesManager.getInteger(getActivity(), "color", R.color.lightOrange)) {
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

        preLoadImages = new ArrayList<>();
        colors = new ArrayList<>();

        ThreadManager.addTaskToThreadManagerPool(GoldenApplication.getPagePoints, 1, new Task() {
            @Override
            public void onPreRun() {
            }

            @Override
            public void onPreRunFailure(Exception ex) {
            }

            @Override
            public void onRunSuccess() {
            }

            @Override
            public void onRunFailure(Exception ex) {
            }

            @Override
            public void run() {
                getTOCData();
                getAllBookmarks();
            }
        });

        if (dialog == null) {
            dialog = ProgressDialog.show(getActivity(), "", getString(R.string.pleaseWait), true);
            dialog.show();

        }

        loadToArray(position);
        setImageView();

        imageView.setOnTouchListener(imgSourceOnTouchListener);

        mFabPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (PlayerService.mediaPlayer != null && !PlayerService.mediaPlayer.isPlaying()) {
                    mFabPlay.setImageResource(R.drawable.pause);
                    if (PlayerManager.isSoundPresent(SharedPreferencesManager.getString(getActivity(), "readerName", "Alafasy_128kbps"), String.valueOf(suraNumber), String.valueOf(ayahNumber))) {
                        String surah = String.valueOf(suraNumber);
                        for (int i = surah.length(); i < 3; i++) {
                            surah = "0" + surah;
                        }
                        String ayaa = String.valueOf(ayahNumber);
                        for (int i = ayaa.length(); i < 3; i++) {
                            ayaa = "0" + ayaa;
                        }
                        String pathToSound = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/" + SharedPreferencesManager.getString(getActivity(), "readerName", "Alafasy_128kbps") + "/" + surah + ayaa + ".mp3";
                        sendMessage("start", pathToSound);
                    } else {
                        Intent mIntent = new Intent(getActivity(), DownloadService.class);
                        mIntent.putExtra(DownloadService.SURAH_NUMBER, String.valueOf(suraNumber));
                        mIntent.putExtra(DownloadService.AYAH_NUMBER, String.valueOf(ayahNumber));
                        getActivity().startService(mIntent);
                    }
                } else {
                    mFabPlay.setImageResource(R.drawable.play);
                    sendMessage("stop", "");

                }
            }
        });
    }

    private void sendMessage(String action, String pathToSound) {
        Intent intent = new Intent("Service");

        intent.putExtra("message", action);
        intent.putExtra("path", pathToSound);
        LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
    }

    final Handler longPressHandler = new Handler();
    Runnable longPressedRunnable = new Runnable() {
        public void run() {
            Timber.e("Long press detected in long press Handler!");
            isLongPressHandlerActivated = true;
            if (tafseerText != null && !tafseerText.isEmpty()) {
                IntonationTranslatorFragment fragment = new IntonationTranslatorFragment();
                Bundle bundle = new Bundle();
                bundle.putString("TranslateText", tafseerText);
                bundle.putString("nozoolReasons", nozoolReasons);
                bundle.putString("e3rab", e3rab);
                bundle.putString("sarf", sarf);
                bundle.putString("balagha", balagha);
                bundle.putString("mawdoo3", mawdoo3);
                bundle.putString("tafseer", tafseer);
                bundle.putParcelableArrayList("wordsMeaning", wordsMeaning);
                bundle.putParcelableArrayList("mo3jamWords", mo3jamWords);
                fragment.setArguments(bundle);
                getActivity().getSupportFragmentManager().beginTransaction().replace(container, fragment).addToBackStack(null).commit();
            }
        }
    };

    private boolean isLongPressHandlerActivated = false;

    private boolean isActionMoveEventStored = false;
    private float lastActionMoveEventBeforeUpX;
    private float lastActionMoveEventBeforeUpY;

    View.OnTouchListener imgSourceOnTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent event) {
            Log.d("ss", event.getAction() + "");
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    longPressHandler.postDelayed(longPressedRunnable, 500);
                    Log.d("down", event.getX() + "," + event.getY());
                    startX = event.getX();
                    startY = event.getY();
                    break;
                case MotionEvent.ACTION_UP:
                    isActionMoveEventStored = false;
                    longPressHandler.removeCallbacks(longPressedRunnable);
                    if (isLongPressHandlerActivated) {
                        Timber.d("Long Press detected; halting propagation of motion event");
                        isLongPressHandlerActivated = false;
                    }
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
                                    PlayerManager.setCurrentSurah(suraNumber);
                                    PlayerManager.setCurrentAya(ayahNumber);
                                    setTitle();
                                    if (PlayerService.mediaPlayer != null && PlayerService.mediaPlayer.isPlaying())
                                        mFabPlay.setImageResource(R.drawable.pause);
                                    else
                                        mFabPlay.setImageResource(R.drawable.play);
                                    mFabPlay.show(true);
                                }
                            }

                            for (int i = 0; i < toc.size(); i++) {
                                if (toc.get(i).getSurah() == suraNumber)
                                    PlayerManager.setsCurrentSurahMaxAyah(toc.get(i).getVersesCount());
                            }

                            left.clear();
                            top.clear();
                            right.clear();
                            bottom.clear();

                            final int finalSuraNumber = suraNumber == null ? 0 : suraNumber;
                            final int finalAyahNumber = ayahNumber == null ? 0 : ayahNumber;

                            if (finalAyahNumber == 0 && finalAyahNumber == 0) {
                                return false;
                            }

                            ThreadManager.addTaskToThreadManagerPool(GoldenApplication.getPagePoints, 2, new Task() {
                                @Override
                                public void onPreRun() {

                                }

                                @Override
                                public void onPreRunFailure(Exception ex) {

                                }

                                @Override
                                public void onRunSuccess() {

                                }

                                @Override
                                public void onRunFailure(Exception ex) {

                                }

                                @Override
                                public void run() {
                                    getTransilate(finalSuraNumber, finalAyahNumber);
                                    getNozolReasons(finalSuraNumber, finalAyahNumber);
                                    getMawdow3Data(finalSuraNumber, finalAyahNumber);
                                    getQuranAddition(finalSuraNumber, finalAyahNumber);
                                    getMo3jamData(finalSuraNumber, finalAyahNumber);
                                    getWordMeaning(finalSuraNumber, finalAyahNumber);
                                    getTafseerForAyah(finalSuraNumber, finalAyahNumber);
                                }
                            });
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
                                imageView.left.add(map(left.get(i).longValue(), 0, imageWidth, 0, imageView.getWidth()));
                                imageView.top.add(map(top.get(i).longValue(), 0, imageHeight, 0, imageView.getHeight()));
                                imageView.right.add(map(right.get(i).longValue(), 0, imageWidth, 0, imageView.getWidth()));
                                imageView.bottom.add(map(bottom.get(i).longValue(), 0, imageHeight, 0, imageView.getHeight()));
                            }

                            imageView.isTouch = true;
                            imageView.invalidate();
                        }
                    } else {
                        if (startY < endY)
                            ((DrawerCloser) getActivity()).moveToolbarDown();
                        else
                            ((DrawerCloser) getActivity()).moveToolbarUp();
                    }
                    break;
                case MotionEvent.ACTION_MOVE:
                case MotionEvent.ACTION_HOVER_MOVE:
                    if (!isActionMoveEventStored) {
                        isActionMoveEventStored = true;
                        lastActionMoveEventBeforeUpX = event.getX();
                        lastActionMoveEventBeforeUpY = event.getY();
                    } else {
                        float currentX = event.getX();
                        float currentY = event.getY();
                        float firstX = lastActionMoveEventBeforeUpX;
                        float firstY = lastActionMoveEventBeforeUpY;
                        double distance = Math.sqrt(
                                (currentY - firstY) * (currentY - firstY) + ((currentX - firstX) * (currentX - firstX)));
                        if (distance > 20) {
                            longPressHandler.removeCallbacks(longPressedRunnable);
                        }
                    }
                    break;
                case MotionEvent.ACTION_CANCEL:
                    longPressHandler.removeCallbacks(longPressedRunnable);
                    break;

            }
            return true;
        }
    };

    @Subscribe
    public void changeMaxAyah(ChangeDataEvent changeDataEvent) {
        for (int i = 0; i < toc.size(); i++) {
            if (toc.get(i).getSurah() == changeDataEvent.getCurrentSurah())
                PlayerManager.setsCurrentSurahMaxAyah(toc.get(i).getVersesCount());
        }
    }

    @Subscribe
    public void autoColorAyah(AutoColorModel autoColorModel) {
        if (getUserVisibleHint()) {
            if (autoColorModel.getAyahNumber() > ayahPoints.get(ayahPoints.size() - 1).ayah && autoColorModel.getSurahNumber() == ayahPoints.get(ayahPoints.size() - 1).surah) {
                EventBus.getDefault().post(new ChangePageEvent(PlayerManager.getCurrentPage()));
                PlayerManager.setCurrentPage(PlayerManager.getCurrentPage() + 1);
            } else if (autoColorModel.getSurahNumber() > ayahPoints.get(ayahPoints.size() - 1).surah) {
                EventBus.getDefault().post(new ChangePageEvent(PlayerManager.getCurrentPage()));
                PlayerManager.setCurrentPage(PlayerManager.getCurrentPage() + 1);
            }
            left.clear();
            top.clear();
            right.clear();
            bottom.clear();
            for (int i = 0; i < ayahPoints.size(); i++) {
                if (autoColorModel.getAyahNumber() == ayahPoints.get(i).ayah && autoColorModel.getSurahNumber() == ayahPoints.get(i).surah) {
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

            imageView.isTouch = true;
            imageView.invalidate();
        }
    }

    private void loadToArray(final int position) {
        if (dialog != null)
            dialog.dismiss();

        String imageNumber = String.valueOf(position);

        String imageName;
        if (imageNumber.length() == 2)
            imageNumber = "0" + position;
        else if (imageNumber.length() == 1)
            imageNumber = "00" + position;
        imageName = imageNumber;

        if (PlayerManager.isImagePresent(imageName)) {
            Drawable drawable = PlayerManager.readImageFromInternalStorage(imageName);

            imageWidth = PlayerManager.getWidth(imageName);
            imageHeight = PlayerManager.getHeight(imageName);

            preLoadImages.add(new PreLoadImages(drawable, position));

        } else {
            if (isOnline()) {
                final String url = "https://github.com/salemoh/GoldenQuranRes/blob/master/images/" + "kingFahad/page" + imageName + ".png?raw=true";
                final String location = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath() + "/" + SharedPreferencesManager.getString(getActivity(), "fileName", "kingFahad") + "/page" + imageName + ".png";
                File file = new File(location);
                if (!file.getParentFile().exists())
                    file.getParentFile().mkdirs();
                if (!file.exists()) {
                    try {
                        file.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                ThreadManager.addTaskToThreadManagerPool(GoldenApplication.getPagePoints, 0, new Task() {
                    @Override
                    public void onPreRun() {

                    }

                    @Override
                    public void onPreRunFailure(Exception ex) {

                    }

                    @Override
                    public void onRunSuccess() {
                        if (getActivity() != null)
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (dialog != null)
                                        dialog.dismiss();
                                }
                            });

                    }

                    @Override
                    public void onRunFailure(Exception ex) {
                        if (getActivity() != null)
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (dialog != null)
                                        dialog.dismiss();
                                }
                            });

                    }

                    @Override
                    public void run() {
                        if (getActivity() != null) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    dialog = ProgressDialog.show(getActivity(), "", getString(R.string.pleaseWait), true);
                                    dialog.show();
                                }
                            });


                            download(url, location, position);

                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    setImageView();
                                }
                            });

                        }
                    }
                });
            } else {
                noConnection();
            }
        }
    }

    public void setImageView() {
        if (!preLoadImages.isEmpty()) {
            imageView.setImageDrawable(preLoadImages.get(0).Image);
        }
        //check db
        if (PlayerManager.isDBPresent("KingFahad1.db")) {
            ThreadManager.addTaskToThreadManagerPool(GoldenApplication.getPagePoints, 5, new Task() {
                @Override
                public void onPreRun() {

                }

                @Override
                public void onPreRunFailure(Exception ex) {
                }

                @Override
                public void onRunSuccess() {
                    if (getActivity() != null)
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (dialog != null)
                                    dialog.dismiss();
                            }
                        });

                }

                @Override
                public void onRunFailure(Exception ex) {
                    if (getActivity() != null)
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (dialog != null)
                                    dialog.dismiss();
                            }
                        });

                }

                @Override
                public void run() {
                    if (getActivity() != null) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                dialog = ProgressDialog.show(getActivity(), "", getString(R.string.pleaseWait), true);
                                dialog.show();

                            }
                        });

                        data = new DataBaseManager(getActivity(), SharedPreferencesManager.getString(getActivity(), "dbName", "KingFahad1.db"), false).createDatabase();
                        data.open();
                        final ArrayList<Ayah> ayahs = data.getPagePoints(position);

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ayahPoints = ayahs;
                                PlayerManager.setCurrentSurah((int) ayahPoints.get(0).surah);
                                setTitle();
                            }
                        });
                        getColor(ayahs);
                        getPageText(ayahs);
                    }

                }
            });
        } else

        {
            if (isOnline()) {
                try {

                    final String url = "https://github.com/salemoh/GoldenQuranRes/blob/master/db/" + "KingFahad1.db?raw=true";
                    final String location = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath() + "/DB" + "/" + SharedPreferencesManager.getString(getActivity(), "dbName", "KingFahad1.db");
                    File file = new File(location);
                    if (!file.getParentFile().exists())
                        file.getParentFile().mkdirs();
                    if (!file.exists()) {
                        file.createNewFile();
                    }
                    ThreadManager.addTaskToThreadManagerPool(GoldenApplication.getPagePoints, 0, new Task() {
                        @Override
                        public void onPreRun() {

                        }

                        @Override
                        public void onPreRunFailure(Exception ex) {

                        }

                        @Override
                        public void onRunSuccess() {
                            if (getActivity() != null)
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (dialog != null)
                                            dialog.dismiss();
                                    }
                                });
                        }

                        @Override
                        public void onRunFailure(Exception ex) {
                            if (getActivity() != null)
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (dialog != null)
                                            dialog.dismiss();
                                    }
                                });
                        }

                        @Override
                        public void run() {
                            if (getActivity() != null) {
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        dialog = ProgressDialog.show(getActivity(), "", getString(R.string.pleaseWait), true);
                                        dialog.show();

                                    }
                                });
                                download(url, location, position);
                            }
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                noConnection();
            }
        }
    }

    public void getColor(final ArrayList<Ayah> ayahs) {
        if (dialog != null)
            dialog.dismiss();

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                imageView.ayahs = ayahs;
            }
        });

        if (ayahs != null && !ayahs.isEmpty()) {
            data = new DataBaseManager(getActivity(), "QuranMawdoo3.db", true).createDatabase();
            data.open();
            final ArrayList<DataMawdo3ColorModel> mawdo3ColorModels = data.getPageColors(ayahs);

            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < mawdo3ColorModels.size(); i++) {
                        for (int k = 0; k < ayahs.size(); k++) {
                            if (ayahs.get(k).ayah >= mawdo3ColorModels.get(i).fromAyah && ayahs.get(k).ayah <= mawdo3ColorModels.get(i).toAyah && ayahs.get(k).surah == mawdo3ColorModels.get(i).soraNo)
                                colors.add(new DataMawdo3ColorModel(mawdo3ColorModels.get(i).fromAyah, mawdo3ColorModels.get(i).toAyah, mawdo3ColorModels.get(i).color, mawdo3ColorModels.get(i).soraNo));
                        }
                    }

                    ArrayList color = new ArrayList();
                    for (int i = 0; i < colors.size(); i++) {
                        color.add(colors.get(i).color);
                    }

                    imageView.colors = color;
                    imageView.imageWidth = imageWidth;
                    imageView.imageHeight = imageHeight;
                    if (SharedPreferencesManager.getBoolean(getActivity(), "isColorOn", false)) {
                        imageView.isColorOn = true;
                        imageView.invalidate();
                    } else {
                        EventBus.getDefault().post(new ColorEvent(false));
                    }
                }
            });
        }
    }

    @Subscribe
    public void removeColors(ColorEvent colorEvent) {
        if (!colorEvent.isColorOn()) {
            int transparent = new Color().argb(0, 255, 255, 255);
            imageView.paint.setColor(transparent);
            imageView.isColorOn = false;
            imageView.invalidate();
        }
    }

    @Subscribe
    public void addColors(ColorEvent colorEvent) {
        if (colorEvent.isColorOn()) {
            imageView.isColorOn = true;
            imageView.invalidate();
        }
    }

    public void getMawdow3Data(int surah, int ayah) {
        if (getActivity() != null) {
            data = new DataBaseManager(getActivity(), "QuranMawdoo3.db", true).createDatabase();
            data.open();
            final String mawdooa = data.getMawdoo3OfAyah(surah, ayah);
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mawdoo3 = mawdooa;
                }
            });
        }
    }

    public void getQuranAddition(int surah, int ayah) {
        if (getActivity() != null) {
            data = new DataBaseManager(getActivity(), "QuranAdditions.db", true).createDatabase();
            data.open();
            final ArrayList<String> Data = data.getDataForAyah(surah, ayah);
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (Data != null && !Data.isEmpty()) {
                        e3rab = Data.get(0);
                        sarf = Data.get(1);
                        balagha = Data.get(2);
                    }
                }
            });
        }
    }

    public void getTafseerForAyah(int surah, int ayah) {
        if (getActivity() != null) {
            data = new DataBaseManager(getActivity(), "tfseerMuyassar.db", true).createDatabase();
            data.open();
            final String s = data.getTafseerForAyah(surah, ayah);
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    tafseer = s;
                }
            });
        }
    }

    public void getWordMeaning(int surah, int ayah) {
        if (getActivity() != null) {
            data = new DataBaseManager(getActivity(), "QuranMeaningByWord.db", true).createDatabase();
            data.open();
            final ArrayList<WordsMeaningModel> mWordsMeaning = data.getWordMeaning(surah, ayah);
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    wordsMeaning = mWordsMeaning;
                }
            });
        }
    }

    public void getPageText(ArrayList<Ayah> ayahs) {
        if (getActivity() != null) {
            data = new DataBaseManager(getActivity(), "quran_text_ar.db", true).createDatabase();
            data.open();
            currentPageText = data.getPageText(ayahs);
        }
    }

    public String getCurrentPageText() {
        return currentPageText;
    }

    public void getNozolReasons(int surah, int ayah) {
        data = new DataBaseManager(getActivity(), "QuranNozoolReasons.db", true).createDatabase();
        data.open();
        final String mNozolReasons = data.getNozoolReasons(surah, ayah);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                nozoolReasons = mNozolReasons;
            }
        });
    }

    public void getMo3jamData(int surah, int ayah) {
        data = new DataBaseManager(getActivity(), "QuranMo3jm.db", true).createDatabase();
        data.open();
        final ArrayList<Mo3jamModel> mo3jamModels = data.getMo3jamWords(surah, ayah);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mo3jamWords = mo3jamModels;
            }
        });
    }

    public void download(String urls, String file, int position) {
        int count;
        try {
            URL url = new URL(urls);
            URLConnection connection = url.openConnection();
            connection.connect();

            InputStream input = new BufferedInputStream(url.openStream());
            OutputStream output = new FileOutputStream(file);

            byte data[] = new byte[1024];

            while ((count = input.read(data)) != -1) {
                output.write(data, 0, count);
            }

            output.flush();
            output.close();
            input.close();
            loadToArray(position);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getTransilate(int surah, int ayah) {

        String country = "SwedishTranslation.sqlite";
        switch (SharedPreferencesManager.getInteger(getActivity(), "selectedCountry", 1)) {
            case 0:
                country = "germanTranslation.sqlite";
                break;
            case 1:
                country = "EnTranslation.sqlite";
                break;
            case 2:
                country = "BosnianTranslation.sqlite";
                break;
            case 3:
                country = "ChineseTranslation.sqlite";
                break;
            case 4:
                country = "FrTranslation.sqlite";
                break;
            case 5:
                country = "PersianTranslation.sqlite";
                break;
            case 6:
                country = "UyghurTranslation.sqlite";
                break;
            case 7:
                country = "RussianTranslation.sqlite";
                break;
            case 8:
                country = "TurkishTranslation.sqlite";
                break;
            case 9:
                country = "SwedishTranslation.sqlite";
                break;
            case 10:
                country = "SpainTranslation.sqlite";
                break;
        }

        data = new DataBaseManager(getActivity(), country, true).createDatabase();
        data.open();
        final String tafseer = data.getTr(surah, ayah);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tafseerText = tafseer;
            }
        });
    }

    private boolean isAClick(float startX, float endX, float startY, float endY) {
        float differenceX = Math.abs(startX - endX);
        float differenceY = Math.abs(startY - endY);
        return !(differenceX > CLICK_ACTION_THRESH_HOLD || differenceY > CLICK_ACTION_THRESH_HOLD);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.quran_image_menu, menu);

        if (bookmarks != null)
            for (int i = 0; i < bookmarks.size(); i++) {
                if (bookmarks.get(i).getPage() == position) {
                    MenuItem view = menu.getItem(0);
                    view.setIcon(R.drawable.bookmark_added);
                }
            }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getIcon().getConstantState() == getActivity().getResources().getDrawable(R.drawable.bookmark_added).getConstantState()) {
            item.setIcon(R.drawable.bookmark_white);
            ThreadManager.addTaskToThreadManagerPool(GoldenApplication.getPagePoints, 1, new Task() {
                @Override
                public void onPreRun() {

                }

                @Override
                public void onPreRunFailure(Exception ex) {

                }

                @Override
                public void onRunSuccess() {

                }

                @Override
                public void onRunFailure(Exception ex) {

                }

                @Override
                public void run() {
                    removeBookmark();
                }
            });
        } else {
            item.setIcon(R.drawable.bookmark_added);
            ThreadManager.addTaskToThreadManagerPool(GoldenApplication.getPagePoints, 1, new Task() {
                @Override
                public void onPreRun() {

                }

                @Override
                public void onPreRunFailure(Exception ex) {

                }

                @Override
                public void onRunSuccess() {

                }

                @Override
                public void onRunFailure(Exception ex) {

                }

                @Override
                public void run() {
                    addBookmark();
                }
            });
        }

        return super.onOptionsItemSelected(item);
    }

    public void addBookmark() {
        data = new DataBaseManager(getActivity(), "Mus7af.db", true).createDatabase();
        data.open();
        data.addBookmarck(ayahPoints.get(0));
        bookmarks.add(new BookmarkModel(0.0, 0.0, 0.0, (int) ayahPoints.get(0).surah, (int) ayahPoints.get(0).ayah, (int) ayahPoints.get(0).pageNumber));
    }

    public void removeBookmark() {
        data = new DataBaseManager(getActivity(), "Mus7af.db", true).createDatabase();
        data.open();
        data.removeBookmark(ayahPoints.get(0));
        BookmarkModel b = null;
        for (int i = 0; i < bookmarks.size(); i++) {
            if (bookmarks.get(i).getPage() == ayahPoints.get(0).pageNumber)
                b = bookmarks.get(i);
        }
        if (b != null)
            bookmarks.remove(b);
    }

    public boolean isOnline() {
        if (getActivity() != null) {
            ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            return netInfo != null && netInfo.isConnectedOrConnecting();
        }
        return false;
    }

    void noConnection() {
        if (getActivity() != null) {
            ProgressDialog.show(getActivity(), "", "Connect your phone to internet to load content", true, true);
        }
    }

    public float map(float x, float in_min, float in_max, float out_min, float out_max) {
        return (x - in_min) * (out_max - out_min) / (in_max - in_min) + out_min;
    }

    private void getTOCData() {
        data = new DataBaseManager(getActivity(), "Mus7af_1.db", true).createDatabase();
        data.open();
        final ArrayList<TableOfContents> tableOfContentses = data.getFahrasData();
        if (getActivity() != null)
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    toc = tableOfContentses;
                }
            });
    }

    public void getAllBookmarks() {
        if (this != null) {
            data = new DataBaseManager(getActivity(), "Mus7af.db", true).createDatabase();
            data.open();
            final ArrayList<BookmarkModel> bookmarkModel = data.getAllBookmark();
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    bookmarks = bookmarkModel;
                    getActivity().invalidateOptionsMenu();
                }
            });
        }
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        RefWatcher refWatcher = GoldenApplication.getRefWatcher(getActivity());
//        refWatcher.watch(this);
    }
}

