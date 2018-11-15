/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.graphics.PorterDuff
 *  android.graphics.PorterDuff$Mode
 *  android.graphics.PorterDuffColorFilter
 */
package android.support.v7.widget;

import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.support.v4.util.LruCache;
import android.support.v7.widget.AppCompatDrawableManager;

private static class AppCompatDrawableManager.ColorFilterLruCache
extends LruCache<Integer, PorterDuffColorFilter> {
    public AppCompatDrawableManager.ColorFilterLruCache(int n) {
        super(n);
    }

    private static int generateCacheKey(int n, PorterDuff.Mode mode) {
        return 31 * (n + 31) + mode.hashCode();
    }

    PorterDuffColorFilter get(int n, PorterDuff.Mode mode) {
        return (PorterDuffColorFilter)this.get(AppCompatDrawableManager.ColorFilterLruCache.generateCacheKey(n, mode));
    }

    PorterDuffColorFilter put(int n, PorterDuff.Mode mode, PorterDuffColorFilter porterDuffColorFilter) {
        return this.put(AppCompatDrawableManager.ColorFilterLruCache.generateCacheKey(n, mode), porterDuffColorFilter);
    }
}
