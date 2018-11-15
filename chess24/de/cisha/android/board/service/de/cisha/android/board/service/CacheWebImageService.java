/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.Bitmap
 */
package de.cisha.android.board.service;

import android.content.Context;
import android.graphics.Bitmap;
import de.cisha.android.board.service.WebImageService;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class CacheWebImageService
extends WebImageService {
    private static CacheWebImageService _instance;
    private HashMap<String, Bitmap> _webImageCache = new LinkedHashMap<String, Bitmap>();

    public static CacheWebImageService getInstance(Context object) {
        synchronized (CacheWebImageService.class) {
            if (_instance == null) {
                _instance = new CacheWebImageService();
            }
            object = _instance;
            return object;
        }
    }

    @Override
    protected void addToImageCache(String string, Bitmap bitmap) {
        this._webImageCache.put(string, bitmap);
    }

    @Override
    protected Bitmap getImageFromCache(String string) {
        return this._webImageCache.get(string);
    }

    @Override
    protected boolean isImageCashed(String string) {
        return this._webImageCache.containsKey(string);
    }
}
