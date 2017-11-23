package yummylau.common.util;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.IOException;

public class DbUtil {

    private static final String TAG = DbUtil.class.getSimpleName();

    public static void exportDatabase(Context context) {
        final String dbDir = context.getFilesDir().getParentFile().getPath() + "/databases";
        final File dbDirFile = new File(dbDir);
        Log.v(TAG, "exportDatabase dbDir:" + dbDir);
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            //SD卡已装入
        }
        final String exportedDir = Environment.getExternalStorageDirectory().getPath() + "/RapidDvpt/databases";
        final File exportedDirFile = new File(exportedDir);
        if (!exportedDirFile.exists()) {
            exportedDirFile.mkdirs();
        }
        if (dbDirFile.exists() && dbDirFile.listFiles() != null) {
            new Thread() {
                @Override
                public void run() {
                    for (File file : dbDirFile.listFiles()) {
                        if (file.isFile()) {
                            FileUtils.copyFile(file, new File(exportedDirFile, file.getName()));
                        }
                    }
                }
            }.start();
        }
    }

    public static void exportSharedPrefs(Context context) {
        final String sharedPrefsDir = context.getFilesDir().getParentFile().getPath() + "/shared_prefs";
        final File sharedPrefsDirFile = new File(sharedPrefsDir);
        Log.v(TAG, "exportSharedPrefs sharedPrefsDir:" + sharedPrefsDir);
        final String exportedDir = Environment.getExternalStorageDirectory().getPath() + "/RapidDvpt/shared_prefs";
        final File exportedDirFile = new File(exportedDir);
        if (!exportedDirFile.exists()) {
            exportedDirFile.mkdirs();
        }
        if (sharedPrefsDirFile.exists() && sharedPrefsDirFile.listFiles() != null) {
            new Thread() {
                @Override
                public void run() {
                    for (File file : sharedPrefsDirFile.listFiles()) {
                        if (file.isFile()) {
                            FileUtils.copyFile(file, new File(exportedDirFile, file.getName()));
                        }
                    }
                }
            }.start();
        }
    }

}
