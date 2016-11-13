package com.blackstone.goldenquran.utilities;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.text.Html;
import android.text.TextUtils;
import android.util.DisplayMetrics;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.File;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import timber.log.Timber;

/**
 * Created by SamerGigaByte on 11/12/2016.
 */

public class Utils {
    public static void shareImage(Context context, String filePath,
                                  String subject, String shareMessage) {
        if (TextUtils.isEmpty(filePath)) {
            return;
        }

        final File file = new File(filePath);
        if (!file.exists()) {
            return;
        }

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("image/*");

        // For a file in shared storage. For data in private storage, use a
        // ContentProvider.
        if (!filePath.startsWith("file://")) {
            filePath = "file://" + filePath;
        }

        Timber.d("share image from:%s", filePath);
        Uri uri = Uri.parse(filePath);
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
        shareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, subject);
        shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareMessage);
        context.startActivity(shareIntent);
    }

    public static void share(Context context, String subject,
                             String shareMessage) {
        Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
        shareIntent.setType("text/plain");

        shareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, subject);
        shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareMessage);

        context.startActivity(shareIntent);
    }

    public static void shareAsHTML(Context context, String subject,
                                   String shareMessage) {
        Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
        shareIntent.setType("text/html");
        String body = "<!DOCTYPE html><html><body dir='rtl'>" + shareMessage
                + "</body></html>";

        shareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, subject);

        shareIntent.putExtra(android.content.Intent.EXTRA_TEXT,
                Html.fromHtml(body));

        context.startActivity(shareIntent);
    }

    public static void openBrowser(Context context, String url) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        context.startActivity(browserIntent);

    }

    public static void call(Context context, String tel) {
        if (!tel.startsWith("tel:")) {
            tel = "tel:" + tel;
        }

        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(tel));
        context.startActivity(browserIntent);
    }

    public static String getDeviceInfo() {
        Timber.i("model =%s", Build.MODEL);
        Timber.i("manf =%s", Build.MANUFACTURER);
        Timber.i("RELEASE =%s", Build.VERSION.RELEASE);
        Timber.i("SDK =%s", Build.VERSION.SDK_INT);

        return android.os.Build.VERSION.RELEASE + ","
                + android.os.Build.MANUFACTURER + "," + android.os.Build.MODEL
                + "," + android.os.Build.VERSION.SDK;

    }

    @SuppressLint("HardwareIds")
    public static String getIMEI(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getDeviceId();

    }

    public static Calendar toDate(Context context, String isoDate) {
        try {
            String format = "yyyy-MM-dd'T'HH:mm:ss";// .SSSZ";
            // "1989-04-07T00:00:00"
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            Date d = sdf.parse(isoDate);
            Calendar c = Calendar.getInstance();
            c.setTime(d);

            return c;
        } catch (Exception ex) {
            return Calendar.getInstance();

        }
    }

    public static String toISODate(Context context, Calendar c) {
        try {
            String format = "yyyy-MM-dd'T'HH:mm:ss";// .SSSZ";
            // "1989-04-07T00:00:00"
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.format(c.getTime());

        } catch (Exception ex) {
            return "";

        }
    }

    public static String loadTextFile(Context context, int resourceId) {
        try {
            Resources res = context.getResources();
            InputStream in_s = res.openRawResource(resourceId);

            byte[] b = new byte[in_s.available()];
            in_s.read(b);

            return new String(b);

        } catch (Exception e) {

        }

        return "";

    }

    public static boolean isWritableExternalStorage() {
        boolean mExternalStorageAvailable = false;
        boolean mExternalStorageWriteable = false;
        String state = Environment.getExternalStorageState();

        if (Environment.MEDIA_MOUNTED.equals(state)) {
            // We can read and write the media
            mExternalStorageAvailable = mExternalStorageWriteable = true;
        } else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            // We can only read the media
            mExternalStorageAvailable = true;
            mExternalStorageWriteable = false;
        } else {
            // Something else is wrong. It may be one of many other states, but
            // all we need
            // to know is we can neither read nor write
            mExternalStorageAvailable = mExternalStorageWriteable = false;
        }

        return mExternalStorageWriteable;

    }

    public static File getFileOnExternalStorage(Context context, String fileName) {
        String path = Environment.getExternalStorageDirectory()
                + File.separator + "Android" + File.separator + "data"
                + File.separator + context.getPackageName() + File.separator
                + "files" + File.separator;
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }

        file = new File(path, fileName);
        return file;

    }

    public static String getSavePath(Context context) {
        String path = Environment.getExternalStorageDirectory()
                + File.separator + "Android" + File.separator + "data"
                + File.separator + context.getPackageName() + File.separator
                + "files" + File.separator;

        return path;
    }
    public static int getScreenWidth(Context contexty) {
        DisplayMetrics metrics = contexty.getResources().getDisplayMetrics();

        return metrics.widthPixels;
    }

    public static int getScreenHeight(Context contexty) {
        DisplayMetrics metrics = contexty.getResources().getDisplayMetrics();

        return metrics.heightPixels;
    }
    public static JSONObject toJSONObject(Object object) {
        try {
            Gson gson = new Gson();
            String jsonString = gson.toJson(object);
            JSONObject jsonObject = new JSONObject(jsonString);
            return jsonObject;
        } catch (Exception e) {
            Timber.e(e,"JSON Conversion-Failed to convert object to json string:");

        }

        return null;

    }

    public static JSONObject toJSONObjectWithParentKey(Object object, String parentKey) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(parentKey, toJSONObject(object));

            return jsonObject;

        } catch (Exception e) {
            Timber.e(e,"JSON Conversion-Failed to convert object to json string: ");

        }

        return null;
    }

    public final static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }
    public static String getMonthLatters(int month){
        String[] months={"Jan","Fab","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
        if (month<12 && month>=0)
            return months[month];
        else {
            Timber.d("the month came empty ether a wrong value");
            return "";
        }
    }
}
