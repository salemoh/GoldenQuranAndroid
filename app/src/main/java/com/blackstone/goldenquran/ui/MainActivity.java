package com.blackstone.goldenquran.ui;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.SystemClock;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.blackstone.goldenquran.Application.GoldenApplication;
import com.blackstone.goldenquran.Fragments.AhadethRecyclerFragment;
import com.blackstone.goldenquran.Fragments.FridayReadingFragment;
import com.blackstone.goldenquran.Fragments.MainListFragment;
import com.blackstone.goldenquran.Fragments.Mos7afFragment;
import com.blackstone.goldenquran.Fragments.NightReadingFragment;
import com.blackstone.goldenquran.Fragments.OnFinishOfQuranPrayFragment;
import com.blackstone.goldenquran.Fragments.QuranViewPager;
import com.blackstone.goldenquran.Fragments.ReadersFragment;
import com.blackstone.goldenquran.Fragments.SearchAyatAlQuranFragment;
import com.blackstone.goldenquran.Fragments.SearchMawdoo3Fragment;
import com.blackstone.goldenquran.Fragments.SearchMo3jamFragment;
import com.blackstone.goldenquran.R;
import com.blackstone.goldenquran.api.AlertIntentSarvice;
import com.blackstone.goldenquran.database.DataBaseManager;
import com.blackstone.goldenquran.models.AhadeethModel;
import com.blackstone.goldenquran.models.Mo3jamModel;
import com.blackstone.goldenquran.models.Mos7afModel;
import com.blackstone.goldenquran.models.QuranPageTextModel;
import com.blackstone.goldenquran.models.RecitationModel;
import com.blackstone.goldenquran.models.SearchMawdoo3Model;
import com.blackstone.goldenquran.models.TableOfContents;
import com.blackstone.goldenquran.utilities.SharedPreferencesManager;
import com.blackstone.goldenquran.utilities.Utils;
import com.blackstone.goldenquran.utilities.threads.Task;
import com.blackstone.goldenquran.utilities.threads.ThreadManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements DrawerCloser, MainListFragment.NavigationDrawerListener {
    public static final String MESSAGE_PROGRESS = "message_progress";
    private static final int PERMISSION_REQUEST_CODE = 1;
    int mToolbarHeight, mAnimDuration = 600;
    @BindView(R.id.left_drawer)
    FrameLayout mLeftDrawer;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    DataBaseManager data;
    String[] mainlist;
    @BindView(R.id.SurAppBarLayout)
    AppBarLayout mAppBarLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    ValueAnimator mVaActionBar;
    Handler handler;
    Runnable r;
    AlarmManager alarmManager, frydayAlarmManager;
    ArrayList<TableOfContents> tableOfContentses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (SharedPreferencesManager.getBoolean(this, "isArabic", true)) {
            String languageToLoad = "ar";
            Locale locale = new Locale(languageToLoad);
            Locale.setDefault(locale);
            Configuration config = new Configuration();
            config.locale = locale;
            this.getResources().updateConfiguration(config, this.getResources().getDisplayMetrics());
        } else {
            String languageToLoad = "en";
            Locale locale = new Locale(languageToLoad);
            Locale.setDefault(locale);
            Configuration config = new Configuration();
            config.locale = locale;
            this.getResources().updateConfiguration(config, this.getResources().getDisplayMetrics());
        }
        mainlist = getResources().getStringArray(R.array.MainList);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        requestStoragePermission();
        if (SharedPreferencesManager.getBoolean(this, "firstTime", true)) {
            copyImages();
            SharedPreferencesManager.putBoolean(this, "firstTime", false);
        } else if (!new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath() + "/kingFahad/").exists()) {
            copyImages();
        } else {
            getSupportFragmentManager().beginTransaction().replace(R.id.container, QuranViewPager.newInstance(SharedPreferencesManager.getInteger(this, "lastPosition", 0)), QuranViewPager.TAG).commit();
        }
        setupSupportActionBar(mToolbar, true, true);
        moveToolbarDown();
        moveToolbarUpAfterTime();
        setupDrawer();
        getData();
        if (SharedPreferencesManager.getBoolean(this, "secondSwitch", true)) {
            setUpAlaert();
        } else {
            cancelAlaert();
        }
        if (SharedPreferencesManager.getBoolean(this, "firstSwitch", true)) {
            setUpFridayAlaert();
        } else {
            cancelFridayAlaert();
        }
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

    private void cancelFridayAlaert() {
        if (frydayAlarmManager != null) {
            frydayAlarmManager.cancel(PendingIntent.getService(this, 0, new Intent(this, AlertIntentSarvice.class), PendingIntent.FLAG_UPDATE_CURRENT));
        }
    }

    private void setUpFridayAlaert() {
        if (frydayAlarmManager != null) {
            frydayAlarmManager.cancel(PendingIntent.getService(this, 0, new Intent(this, AlertIntentSarvice.class), PendingIntent.FLAG_UPDATE_CURRENT));
        }

        Intent frydayIntent = new Intent(this, AlertIntentSarvice.class);
        frydayIntent.putExtra("title", "القران الذهبي");
        frydayIntent.putExtra("message", "اليوم الجمعة لا تجعل الدنيا تلهيك عن ذكر الجمعة");

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);

        frydayAlarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getService(this, 0, frydayIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        frydayAlarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }

    public void cancelAlaert() {
        if (alarmManager != null) {
            alarmManager.cancel(PendingIntent.getService(this, 0, new Intent(this, AlertIntentSarvice.class), PendingIntent.FLAG_UPDATE_CURRENT));
        }
    }

    public void setUpAlaert() {
        if (alarmManager != null)
            alarmManager.cancel(PendingIntent.getService(this, 0, new Intent(this, AlertIntentSarvice.class), PendingIntent.FLAG_UPDATE_CURRENT));

        Intent didNotOpenTheAppIntent = new Intent(this, AlertIntentSarvice.class);
        didNotOpenTheAppIntent.putExtra("title", "القران الذهبي");
        didNotOpenTheAppIntent.putExtra("message", "لم تقراء القران منذ فترة");

        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getService(this, 0, didNotOpenTheAppIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + AlarmManager.INTERVAL_DAY * 3, pendingIntent);
    }

    private void copyImages() {
        final AssetManager assetManager = getAssets();
        String[] files = null;
        try {
            files = assetManager.list("kingFahad");
        } catch (IOException e) {
            Log.e("tag", e.getMessage());
        }

        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath() + "/kingFahad/");
        if (!file.exists())
            file.mkdir();

        final ProgressDialog dialog = ProgressDialog.show(this, "Preparing", "please wait...", true, false);
        dialog.show();
        final String[] finalFiles = files;
        ThreadManager.addTaskToThreadManagerPool(GoldenApplication.getPagePoints, 1, new Task() {
            @Override
            public void onPreRun() {

            }

            @Override
            public void onPreRunFailure(Exception ex) {

            }

            @Override
            public void onRunSuccess() {
                if (this != null) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            dialog.dismiss();
                            getSupportFragmentManager().beginTransaction().replace(R.id.container, QuranViewPager.newInstance(SharedPreferencesManager.getInteger(MainActivity.this, "lastPosition", 0)), QuranViewPager.TAG).commit();
                        }
                    });

                }
            }

            @Override
            public void onRunFailure(Exception ex) {

            }

            @Override
            public void run() {
                for (String filename : finalFiles) {
                    InputStream in = null;
                    OutputStream out = null;
                    try {
                        in = assetManager.open("kingFahad/" + filename);
                        out = new FileOutputStream(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath() + "/kingFahad/" + filename);
                        copyFile(in, out);
                        in.close();
                        out.flush();
                        out.close();
                    } catch (Exception e) {
                        Log.e("tag", e.getMessage());
                    }
                }
            }
        });
    }

    private void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while ((read = in.read(buffer)) != -1) {
            out.write(buffer, 0, read);
        }
    }

    public void moveToolbarUpAfterTime() {
        handler = new Handler();
        r = new Runnable() {
            public void run() {
                hideActionBar();
            }
        };
        handler.postDelayed(r, 3000);
    }

    private void getData() {
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
                getAllMo3jam();
                getTOCData();
                getAllMawdoo3();
                getAllQuranAyat();
                getFridayReading();
                getNightReading();
                getAhadeeth();
                getOnFinishQuranDoaa();
                getReaders();
            }
        });
    }

    public void getReaders() {
        data = new DataBaseManager(this, "TafseerAndRecitation.db", true).createDatabase();
        data.open();
        final ArrayList<RecitationModel> text = data.getReaders();
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ReadersFragment.getData(text);
            }
        });


    }

    private void getNightReading() {
        data = new DataBaseManager(this, "quran_text_ar.db", true).createDatabase();
        data.open();
        final ArrayList<QuranPageTextModel> text = data.getNightReading();
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                NightReadingFragment.getData(text);
            }
        });

    }

    private void getFridayReading() {
        data = new DataBaseManager(this, "quran_text_ar.db", true).createDatabase();
        data.open();
        final String text = data.getFridayReading();
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                FridayReadingFragment.getData(text);
            }
        });
    }

    private void getAllQuranAyat() {
        data = new DataBaseManager(this, "quran_text_ar.db", true).createDatabase();
        data.open();
        final ArrayList<QuranPageTextModel> ayat = data.getAllQuranAyat();
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                SearchAyatAlQuranFragment.getData(ayat);
            }
        });
    }

    private void getAllMo3jam() {
        data = new DataBaseManager(this, "QuranMo3jm.db", true).createDatabase();
        data.open();
        final ArrayList<Mo3jamModel> mo3jams = data.getAllMo3jam();
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                SearchMo3jamFragment.getData(mo3jams);
            }
        });
    }

    private void getAllMawdoo3() {
        data = new DataBaseManager(this, "QuranMawdoo3.db", true).createDatabase();
        data.open();
        final ArrayList<SearchMawdoo3Model> arrayList = data.getAllMawdoo3();
        if (this != null)
            this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    SearchMawdoo3Fragment.getData(arrayList);
                }
            });
    }

    private void getMos7afsData() {
        data = new DataBaseManager(this, "Mus7af_1.db", true).createDatabase();
        data.open();
        final ArrayList<Mos7afModel> mos7afs = data.getMos7afs();
        if (this != null)
            this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Mos7afFragment.getData(mos7afs);
                }
            });
    }

    private void getTOCData() {
        data = new DataBaseManager(this, "Mus7af_1.db", true).createDatabase();
        data.open();
        final ArrayList<TableOfContents> toc = data.getFahrasData();
        if (this != null)
            this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    MainListFragment fragment = (MainListFragment) getSupportFragmentManager().findFragmentByTag("mainList");
                    fragment.sendTOCData(toc);
                }
            });
    }

    private void getAhadeeth() {
        data = new DataBaseManager(this, "HadithContent.db", true).createDatabase();
        data.open();
        final ArrayList<AhadeethModel> arrayList = data.getAhadith();
        if (this != null)
            this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    AhadethRecyclerFragment.getData(arrayList);
                }
            });
    }

    private void getOnFinishQuranDoaa() {
        data = new DataBaseManager(this, "HadithContent.db", true).createDatabase();
        data.open();
        final String onFinishQuranDoaa = data.getOnFinishQuran();
        if (this != null)
            this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    OnFinishOfQuranPrayFragment.getData(onFinishQuranDoaa);
                }
            });

    }

    void hideActionBar() {
        if (mToolbarHeight == 0) {
            mToolbarHeight = mToolbar.getHeight();
        }
        if (mVaActionBar != null && mVaActionBar.isRunning()) {
            return;
        }
        mVaActionBar = ValueAnimator.ofInt(mToolbarHeight, 0);
        mVaActionBar.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                ((AppBarLayout.LayoutParams) mToolbar.getLayoutParams()).height = (Integer) animation.getAnimatedValue();
                mToolbar.requestLayout();
            }
        });
        mVaActionBar.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (getSupportActionBar() != null) {
                    getSupportActionBar().hide();
                }
            }
        });
        mVaActionBar.setDuration(mAnimDuration);
        mVaActionBar.start();
    }

    void showActionBar() {
        if (mVaActionBar != null && mVaActionBar.isRunning()) {
            return;
        }
        mVaActionBar = ValueAnimator.ofInt(0, mToolbarHeight);
        mVaActionBar.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                ((AppBarLayout.LayoutParams) mToolbar.getLayoutParams()).height
                        = (Integer) animation.getAnimatedValue();
                mToolbar.requestLayout();
            }
        });
        mVaActionBar.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                if (getSupportActionBar() != null) {
                    getSupportActionBar().show();
                }
            }
        });
        mVaActionBar.setDuration(mAnimDuration);
        mVaActionBar.start();
    }

    private void setupDrawer() {
        //setup the size of the drawer to 2/3 of the screen size
        int width = Utils.getScreenWidth(this) - (Utils.getScreenWidth(this) / 3);
        DrawerLayout.LayoutParams params = (DrawerLayout.LayoutParams) mLeftDrawer.getLayoutParams();
        params.width = width;
        mLeftDrawer.setLayoutParams(params);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                mToolbar, R.string.drawer_open, R.string.drawer_close) {
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }
        };
        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
        getSupportFragmentManager().beginTransaction().replace(R.id.left_drawer, new MainListFragment(), "mainList").commit();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void close(boolean isDrawerLocked) {
        if (isDrawerLocked) {
            mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        } else {
            mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        }
    }

    @Override
    public void title(int pos) {
        mToolbar.setTitle(mainlist[pos + 2]);
        mAppBarLayout.setExpanded(true, true);
    }

    @Override
    public void moveToolbarDown() {
        if (!getSupportActionBar().isShowing())
            showActionBar();
    }

    @Override
    public void moveToolbarUp() {
        if (getSupportActionBar().isShowing())
            hideActionBar();
    }

    @Override
    public void respond(String data) {
        MainListFragment fragment = (MainListFragment) getSupportFragmentManager().findFragmentByTag("mainList");
        fragment.getData(data);
    }

    @Override
    public void onShareClick() {
        QuranViewPager viewPager = (QuranViewPager) getSupportFragmentManager().findFragmentByTag(QuranViewPager.TAG);
        if (viewPager != null) {
            if (viewPager.getQuranViewPagerAdapter() != null) {
                if (viewPager.getQuranViewPagerAdapter().getCurrentFragment() != null) {
                    String text = viewPager.getQuranViewPagerAdapter().getCurrentFragment().getCurrentPageText();
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT, text);
                    sendIntent.setType("text/plain");
                    startActivity(Intent.createChooser(sendIntent, "Where To Send"));
                }
            }
        }
    }
}
