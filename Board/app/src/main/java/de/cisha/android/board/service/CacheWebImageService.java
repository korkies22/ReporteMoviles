// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.service;

import android.content.Context;
import java.util.LinkedHashMap;
import android.graphics.Bitmap;
import java.util.HashMap;

public class CacheWebImageService extends WebImageService
{
    private static CacheWebImageService _instance;
    private HashMap<String, Bitmap> _webImageCache;
    
    public CacheWebImageService() {
        this._webImageCache = new LinkedHashMap<String, Bitmap>();
    }
    
    public static CacheWebImageService getInstance(final Context context) {
        synchronized (CacheWebImageService.class) {
            if (CacheWebImageService._instance == null) {
                CacheWebImageService._instance = new CacheWebImageService();
            }
            return CacheWebImageService._instance;
        }
    }
    
    @Override
    protected void addToImageCache(final String s, final Bitmap bitmap) {
        this._webImageCache.put(s, bitmap);
    }
    
    @Override
    protected Bitmap getImageFromCache(final String s) {
        return this._webImageCache.get(s);
    }
    
    @Override
    protected boolean isImageCashed(final String s) {
        return this._webImageCache.containsKey(s);
    }
}
