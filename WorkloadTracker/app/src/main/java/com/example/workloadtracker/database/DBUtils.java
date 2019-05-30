package com.example.workloadtracker.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import androidx.annotation.NonNull;

import java.io.File;

/**
 * Created by Anton Maniskevich on 1/23/15.
 * Refactored by Oleksandr Sizov on 06/25/18.
 * Do not use SPCache or other classes that related to SPCache or DBUtils here!!!
 */
@SuppressWarnings("DefaultFileTemplate")
public class DBUtils {

    @SuppressWarnings("unused")
    private static final String TAG = DBUtils.class.getSimpleName();

    public boolean isDatabaseExists(Context context) {
        return getDatabaseFile(context).exists();
    }

    private File getDatabaseFile(Context context) {
        return new File(context.getFilesDir() + AppDatabase.DB_RELATIVE_PATH);
    }

    @SuppressWarnings("UnusedReturnValue")
    public boolean deleteDatabase(Context context) {
        return SQLiteDatabase.deleteDatabase(getDatabaseFile(context));
    }

    @NonNull
    public AppDatabase initDb(Context context) {
        return AppDatabase.Companion.createInstance(context);
    }
}