package com.majnioui.tv.prayer;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class PrayerDataProvider extends ContentProvider {
    public static final String AUTHORITY = "com.majnioui.tv.prayer.data";
    public static final Uri CONTENT_URI =
            Uri.parse("content://" + AUTHORITY + "/today");

    @Override
    public boolean onCreate() {
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                       String[] selectionArgs, String sortOrder) {
        if (getContext() == null || !CONTENT_URI.equals(uri)) return null;

        String[] columns = {
            "date", "place", "imsak", "fajr", "sunrise",
            "dhuhr", "asr", "maghrib", "isha"
        };

        MatrixCursor cursor = new MatrixCursor(columns);
        Context ctx = getContext();
        var prefs = ctx.getSharedPreferences("prayer_prefs", Context.MODE_PRIVATE);

        String date = prefs.getString("cached_date", "");
        String today = new SimpleDateFormat("yyyy-MM-dd", Locale.ROOT)
                .format(new Date());

        if (!today.equals(date)) return cursor;

        cursor.addRow(new Object[] {
            date,
            prefs.getString("place", ""),
            prefs.getString("time_Imsak", ""),
            prefs.getString("time_Fajr", ""),
            prefs.getString("time_Sunrise", ""),
            prefs.getString("time_Dhuhr", ""),
            prefs.getString("time_Asr", ""),
            prefs.getString("time_Maghrib", ""),
            prefs.getString("time_Isha", "")
        });
        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        return "vnd.android.cursor.item/vnd.com.majnioui.tv.prayer.today";
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        throw new UnsupportedOperationException("Read only");
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        throw new UnsupportedOperationException("Read only");
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        throw new UnsupportedOperationException("Read only");
    }
}
