package yummylau.common.util.util.common;

import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.text.DecimalFormat;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

/**
 * 文件处理工具类
 * Created by yummyLau on 17-4-30
 * Email: yummyl.lau@gmail.com
 */

public class FileUtils {

    private static final String TAG = FileUtils.class.getSimpleName();

    public static final int SIZE_TYPE_B = 0x0001;
    public static final int SIZE_TYPE_KB = 0x0002;
    public static final int SIZE_TYPE_MB = 0x0003;
    public static final int SIZE_TYPE_GB = 0x0004;

    /**
     * 判断文件是否存在
     *
     * @param filePath
     * @return
     */
    public static boolean isFileExist(String filePath) {
        File file = new File(filePath);
        return file.isFile();
    }

    /**
     * 删除文件
     *
     * @param path
     */
    public static void delete(String path) {
        File file = new File(path);
        delete(file);
    }

    /**
     * 递归删除文件
     *
     * @param file
     */
    public static void delete(File file) {
        if (file == null || !file.exists()) {
            return;
        }
        if (file.isFile()) {
            file.delete();
            return;
        }

        if (file.isDirectory()) {
            File[] childFiles = file.listFiles();
            if (childFiles == null || childFiles.length == 0) {
                file.delete();
                return;
            }
            for (File childFile : childFiles) {
                delete(childFile);
            }
            file.delete();
        }
    }


    /**
     * 获取文件大小，考虑K和M的情况
     *
     * @param filePath
     * @return
     */
    public static String getFileSize(String filePath) {
        File file = new File(filePath);
        long blockSize = 0;
        try {
            blockSize = getFileSize(file);
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
        DecimalFormat df = new DecimalFormat("#.0");
        double fileSizeLong;
        fileSizeLong = Double.valueOf(df.format((double) blockSize / 1024));
        if (fileSizeLong < 1024) {
            return new StringBuilder().append(fileSizeLong).append("K").toString();
        } else {
            fileSizeLong = Double.valueOf(df.format(fileSizeLong / 1024f));
            return new StringBuilder().append(fileSizeLong).append("M").toString();
        }
    }


    /**
     * 获取文件大小
     *
     * @param file
     * @return
     * @throws Exception
     */
    private static long getFileSize(File file) throws Exception {
        long size = 0;
        if (file.exists()) {
            FileInputStream fis = null;
            fis = new FileInputStream(file);
            size = fis.available();
        } else {
            file.createNewFile();
            Log.e(TAG, "文件不存在!");
        }
        return size;
    }

    /**
     * 获取指定类型的文件夹大小
     *
     * @param filePath
     * @param sizeType
     * @return
     */
    public static double getFileSizeForType(String filePath, int sizeType) {
        File file = new File(filePath);
        long blockSize = 0;
        try {
            blockSize = getFileSize(file);
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
        DecimalFormat df = new DecimalFormat("#.00");
        double fileSizeLong = 0;
        switch (sizeType) {
            case SIZE_TYPE_B:
                fileSizeLong = Double.valueOf(df.format((double) blockSize));
                break;
            case SIZE_TYPE_KB:
                fileSizeLong = Double.valueOf(df.format((double) blockSize / 1024));
                break;
            case SIZE_TYPE_MB:
                fileSizeLong = Double.valueOf(df.format((double) blockSize / 1048576));
                break;
            case SIZE_TYPE_GB:
                fileSizeLong = Double.valueOf(df.format((double) blockSize / 1073741824));
                break;
            default:
                break;
        }
        return fileSizeLong;
    }


    /**
     * 根据uri获取path
     *
     * @param context
     * @param uri
     * @return
     */
    @SuppressLint("NewApi")
    public static String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/"
                            + split[1];
                }

                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"),
                        Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};

                return getDataColumn(context, contentUri, selection,
                        selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {

            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    public static String getDataColumn(Context context, Uri uri,
                                       String selection, String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};

        try {
            cursor = context.getContentResolver().query(uri, projection,
                    selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }


    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }


    /**
     * 解压缩功能. 将zipFile文件解压到folderPath目录下.
     *
     * @throws Exception
     */
    public static int upZipFile(File zipFile, String folderPath) throws IOException {
        ZipFile zfile = new ZipFile(zipFile);
        Enumeration zList = zfile.entries();
        ZipEntry ze;
        byte[] buf = new byte[1024];
        while (zList.hasMoreElements()) {
            ze = (ZipEntry) zList.nextElement();
            if (ze.isDirectory()) {
                Log.d(TAG, "upZipFile: ze.getName() = " + ze.getName());
                String dirstr = folderPath + ze.getName();
                dirstr = new String(dirstr.getBytes("8859_1"), "GB2312");
                Log.d("upZipFile", "str = " + dirstr);
                File f = new File(dirstr);
                f.mkdir();
                continue;
            }
            Log.d(TAG, "upZipFile: ze.getName() = " + ze.getName());
            OutputStream os = new BufferedOutputStream(new FileOutputStream(getRealFileName(folderPath, ze.getName())));
            InputStream is = new BufferedInputStream(zfile.getInputStream(ze));
            int readLen;
            while ((readLen = is.read(buf, 0, 1024)) != -1) {
                os.write(buf, 0, readLen);
            }
            is.close();
            os.close();
        }
        zfile.close();
        Log.d(TAG, "upZipFile: unzip finished");
        return 0;
    }

    /**
     * 给定根目录，返回一个相对路径所对应的实际文件名.
     *
     * @param baseDir     指定根目录
     * @param absFileName 相对路径名
     * @return java.io.File 实际的文件
     */
    public static File getRealFileName(String baseDir, String absFileName) {
        String[] dirs = absFileName.split("/");
        File ret = new File(baseDir);
        String substr;
        if (dirs.length > 1) {
            for (int i = 0; i < dirs.length - 1; i++) {
                substr = dirs[i];
                try {
                    substr = new String(substr.getBytes("8859_1"), "GB2312");
                } catch (UnsupportedEncodingException e) {
                    Log.e(TAG, e.toString());
                }
                ret = new File(ret, substr);
            }
            if (!ret.exists())
                ret.mkdirs();
            substr = dirs[dirs.length - 1];
            try {
                substr = new String(substr.getBytes("8859_1"), "GB2312");
            } catch (UnsupportedEncodingException e) {
                Log.e(TAG, e.toString());
            }
            ret = new File(ret, substr);
            return ret;
        }
        return ret;
    }


    /**于
     * 把字符串以文件形式保存特定路径中
     *
     * @param str
     * @param filename
     */
    public static String writeToFile(String str, String filename) {
        try {
            File file = new File(filename);
            if (!file.exists()) {
                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }
                file.createNewFile();
            }
            Writer fWriter = new OutputStreamWriter(new FileOutputStream(file), "utf-8");
            fWriter.write(str);
            fWriter.flush();
            fWriter.close();
            return filename;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 从压缩文件中读取特定的文件，以字符串的形式返回
     * @param assetManager
     * @param zipFileName
     * @param fileName
     * @return
     */
    public static String getContent(AssetManager assetManager, String zipFileName, String fileName) {
        String content = null;
        try {
            InputStream zipFileStream = assetManager.open(zipFileName);
            ZipInputStream zipInputStream = new ZipInputStream(zipFileStream);
            ZipEntry nextEntry = zipInputStream.getNextEntry();
            while (nextEntry != null) {
                if (!nextEntry.isDirectory() && nextEntry.getName().equals(fileName)) {
                    content = getContent(zipInputStream);
                }
                nextEntry = zipInputStream.getNextEntry();
            }
            zipInputStream.close();
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
        return content;
    }

    /**
     * 读取压缩文件流成字符串
     *
     * @param zipinputstream
     * @return
     */
    private static String getContent(ZipInputStream zipinputstream) {
        String content = null;
        if (zipinputstream == null) {
            return content;
        }
        try {
            ByteArrayOutputStream contentStream = new ByteArrayOutputStream();
            try {
                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = zipinputstream.read(buffer)) >= 0) {
                    contentStream.write(buffer, 0, bytesRead);
                }
                content = new String(contentStream.toByteArray());
            } finally {
                contentStream.close();
            }
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
        return content;
    }

}
