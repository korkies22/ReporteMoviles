/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.SharedPreferences
 *  android.content.SharedPreferences$Editor
 *  android.graphics.Bitmap
 *  android.graphics.Bitmap$CompressFormat
 *  android.graphics.BitmapFactory
 *  android.os.StatFs
 *  android.util.Log
 */
package de.cisha.android.board.service;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.StatFs;
import android.util.Log;
import de.cisha.android.board.service.WebImageService;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;
import java.util.Set;

public class InternalDirWebImageService
extends WebImageService {
    private static InternalDirWebImageService _instance;
    private double _freeSpace = 0.0;
    private String _path;
    private SharedPreferences _preWebImageId;
    private SharedPreferences.Editor _preWebImageIdEditor;

    private InternalDirWebImageService(Context context) {
        this._preWebImageId = context.getSharedPreferences("web_image_id_list", 0);
        this._preWebImageIdEditor = this._preWebImageId.edit();
        if (context.getExternalCacheDir() != null && context.getExternalCacheDir().exists()) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(context.getExternalCacheDir().toString());
            stringBuilder.append("/Image/");
            this._path = stringBuilder.toString();
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(context.getCacheDir().toString());
            stringBuilder.append("/Image/");
            this._path = stringBuilder.toString();
        }
        if (this.getImagePath().exists()) {
            this._freeSpace = this.getFreeSpaceOnCache();
        }
    }

    private double getFreeSpaceOnCache() {
        StatFs statFs = new StatFs(this.getImagePath().getAbsolutePath());
        return (double)statFs.getBlockSize() * (double)statFs.getAvailableBlocks() / 1024.0 / 1024.0;
    }

    private File getImagePath() {
        File file = new File(this._path);
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }

    public static InternalDirWebImageService getInstance(Context object) {
        synchronized (InternalDirWebImageService.class) {
            if (_instance == null) {
                _instance = new InternalDirWebImageService((Context)object);
            }
            object = _instance;
            return object;
        }
    }

    private static Bitmap getLocalBitmap(String string) {
        try {
            string = BitmapFactory.decodeStream((InputStream)new FileInputStream(string));
            return string;
        }
        catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
            return null;
        }
    }

    private void updateImageTableForURL(String string, int n) {
        Map map = this._preWebImageId.getAll();
        for (String string2 : map.keySet()) {
            Object v = map.get(string2);
            if (!(v instanceof Integer) || (Integer)v != n) continue;
            this._preWebImageIdEditor.remove(string2);
        }
        this._preWebImageIdEditor.putInt(string, n);
        this._preWebImageIdEditor.commit();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    protected void addToImageCache(String string, Bitmap bitmap) {
        synchronized (this) {
            if (this._freeSpace >= 1.0) {
                boolean bl;
                Object object = this.getImagePath();
                if (!this._preWebImageId.contains(string) && (bl = object.isDirectory())) {
                    try {
                        void var2_3;
                        int n = object.listFiles().length;
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append(String.valueOf(n));
                        stringBuilder.append(".png");
                        object = new File((File)object, stringBuilder.toString());
                        object.createNewFile();
                        object = new FileOutputStream((File)object);
                        if (var2_3.compress(Bitmap.CompressFormat.PNG, 80, (OutputStream)object)) {
                            this.updateImageTableForURL(string, n);
                        }
                        object.flush();
                        object.close();
                    }
                    catch (IOException iOException) {
                        Log.d((String)WebImageService.class.getName(), (String)IOException.class.getName(), (Throwable)iOException);
                    }
                }
                this._freeSpace = this.getFreeSpaceOnCache();
            }
            return;
        }
    }

    @Override
    protected Bitmap getImageFromCache(String string) {
        StringBuilder stringBuilder;
        if (this._preWebImageId.contains(string)) {
            int n = this._preWebImageId.getInt(string, -1);
            stringBuilder = new StringBuilder();
            stringBuilder.append(this._path);
            stringBuilder.append(String.valueOf((Object)n));
            stringBuilder.append(".png");
            Bitmap bitmap = InternalDirWebImageService.getLocalBitmap(stringBuilder.toString());
            stringBuilder = bitmap;
            if (bitmap == null) {
                this._preWebImageIdEditor.remove(string);
                this._preWebImageIdEditor.commit();
                return bitmap;
            }
        } else {
            stringBuilder = null;
        }
        return stringBuilder;
    }

    @Override
    protected boolean isImageCashed(String string) {
        return this._preWebImageId.contains(string);
    }
}
