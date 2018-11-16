// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.service;

import android.util.Log;
import java.io.IOException;
import java.io.OutputStream;
import android.graphics.Bitmap.CompressFormat;
import java.io.FileOutputStream;
import java.util.Iterator;
import java.util.Map;
import java.io.FileNotFoundException;
import java.io.InputStream;
import android.graphics.BitmapFactory;
import java.io.FileInputStream;
import android.graphics.Bitmap;
import java.io.File;
import android.os.StatFs;
import android.content.Context;
import android.content.SharedPreferences.Editor;
import android.content.SharedPreferences;

public class InternalDirWebImageService extends WebImageService
{
    private static InternalDirWebImageService _instance;
    private double _freeSpace;
    private String _path;
    private SharedPreferences _preWebImageId;
    private SharedPreferences.Editor _preWebImageIdEditor;
    
    private InternalDirWebImageService(final Context context) {
        this._freeSpace = 0.0;
        this._preWebImageId = context.getSharedPreferences("web_image_id_list", 0);
        this._preWebImageIdEditor = this._preWebImageId.edit();
        if (context.getExternalCacheDir() != null && context.getExternalCacheDir().exists()) {
            final StringBuilder sb = new StringBuilder();
            sb.append(context.getExternalCacheDir().toString());
            sb.append("/Image/");
            this._path = sb.toString();
        }
        else {
            final StringBuilder sb2 = new StringBuilder();
            sb2.append(context.getCacheDir().toString());
            sb2.append("/Image/");
            this._path = sb2.toString();
        }
        if (this.getImagePath().exists()) {
            this._freeSpace = this.getFreeSpaceOnCache();
        }
    }
    
    private double getFreeSpaceOnCache() {
        final StatFs statFs = new StatFs(this.getImagePath().getAbsolutePath());
        return statFs.getBlockSize() * statFs.getAvailableBlocks() / 1024.0 / 1024.0;
    }
    
    private File getImagePath() {
        final File file = new File(this._path);
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }
    
    public static InternalDirWebImageService getInstance(final Context context) {
        synchronized (InternalDirWebImageService.class) {
            if (InternalDirWebImageService._instance == null) {
                InternalDirWebImageService._instance = new InternalDirWebImageService(context);
            }
            return InternalDirWebImageService._instance;
        }
    }
    
    private static Bitmap getLocalBitmap(final String s) {
        try {
            return BitmapFactory.decodeStream((InputStream)new FileInputStream(s));
        }
        catch (FileNotFoundException ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    private void updateImageTableForURL(final String s, final int n) {
        final Map all = this._preWebImageId.getAll();
        for (final String s2 : all.keySet()) {
            final V value = all.get(s2);
            if (value instanceof Integer && (int)value == n) {
                this._preWebImageIdEditor.remove(s2);
            }
        }
        this._preWebImageIdEditor.putInt(s, n);
        this._preWebImageIdEditor.commit();
    }
    
    @Override
    protected void addToImageCache(final String s, final Bitmap bitmap) {
        synchronized (this) {
            if (this._freeSpace >= 1.0) {
                final File imagePath = this.getImagePath();
                if (!this._preWebImageId.contains(s) && imagePath.isDirectory()) {
                    try {
                        final int length = imagePath.listFiles().length;
                        final StringBuilder sb = new StringBuilder();
                        sb.append(String.valueOf(length));
                        sb.append(".png");
                        final File file = new File(imagePath, sb.toString());
                        file.createNewFile();
                        final FileOutputStream fileOutputStream = new FileOutputStream(file);
                        if (bitmap.compress(Bitmap.CompressFormat.PNG, 80, (OutputStream)fileOutputStream)) {
                            this.updateImageTableForURL(s, length);
                        }
                        fileOutputStream.flush();
                        fileOutputStream.close();
                    }
                    catch (IOException ex) {
                        Log.d(WebImageService.class.getName(), IOException.class.getName(), (Throwable)ex);
                    }
                }
                this._freeSpace = this.getFreeSpaceOnCache();
            }
        }
    }
    
    @Override
    protected Bitmap getImageFromCache(final String s) {
        Bitmap bitmap;
        if (this._preWebImageId.contains(s)) {
            final int int1 = this._preWebImageId.getInt(s, -1);
            final StringBuilder sb = new StringBuilder();
            sb.append(this._path);
            sb.append(String.valueOf((Object)int1));
            sb.append(".png");
            final Bitmap localBitmap = getLocalBitmap(sb.toString());
            if ((bitmap = localBitmap) == null) {
                this._preWebImageIdEditor.remove(s);
                this._preWebImageIdEditor.commit();
                return localBitmap;
            }
        }
        else {
            bitmap = null;
        }
        return bitmap;
    }
    
    @Override
    protected boolean isImageCashed(final String s) {
        return this._preWebImageId.contains(s);
    }
}
