/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.res.Resources
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.util.Log
 *  android.util.LongSparseArray
 */
package android.support.v7.app;

import android.content.res.Resources;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.util.LongSparseArray;
import java.lang.reflect.Field;
import java.util.Map;

class ResourcesFlusher {
    private static final String TAG = "ResourcesFlusher";
    private static Field sDrawableCacheField;
    private static boolean sDrawableCacheFieldFetched;
    private static Field sResourcesImplField;
    private static boolean sResourcesImplFieldFetched;
    private static Class sThemedResourceCacheClazz;
    private static boolean sThemedResourceCacheClazzFetched;
    private static Field sThemedResourceCache_mUnthemedEntriesField;
    private static boolean sThemedResourceCache_mUnthemedEntriesFieldFetched;

    ResourcesFlusher() {
    }

    static boolean flush(@NonNull Resources resources) {
        if (Build.VERSION.SDK_INT >= 24) {
            return ResourcesFlusher.flushNougats(resources);
        }
        if (Build.VERSION.SDK_INT >= 23) {
            return ResourcesFlusher.flushMarshmallows(resources);
        }
        if (Build.VERSION.SDK_INT >= 21) {
            return ResourcesFlusher.flushLollipops(resources);
        }
        return false;
    }

    @RequiresApi(value=21)
    private static boolean flushLollipops(@NonNull Resources object) {
        if (!sDrawableCacheFieldFetched) {
            try {
                sDrawableCacheField = Resources.class.getDeclaredField("mDrawableCache");
                sDrawableCacheField.setAccessible(true);
            }
            catch (NoSuchFieldException noSuchFieldException) {
                Log.e((String)TAG, (String)"Could not retrieve Resources#mDrawableCache field", (Throwable)noSuchFieldException);
            }
            sDrawableCacheFieldFetched = true;
        }
        if (sDrawableCacheField != null) {
            try {
                object = (Map)sDrawableCacheField.get(object);
            }
            catch (IllegalAccessException illegalAccessException) {
                Log.e((String)TAG, (String)"Could not retrieve value from Resources#mDrawableCache", (Throwable)illegalAccessException);
                object = null;
            }
            if (object != null) {
                object.clear();
                return true;
            }
        }
        return false;
    }

    @RequiresApi(value=23)
    private static boolean flushMarshmallows(@NonNull Resources object) {
        block9 : {
            if (!sDrawableCacheFieldFetched) {
                try {
                    sDrawableCacheField = Resources.class.getDeclaredField("mDrawableCache");
                    sDrawableCacheField.setAccessible(true);
                }
                catch (NoSuchFieldException noSuchFieldException) {
                    Log.e((String)TAG, (String)"Could not retrieve Resources#mDrawableCache field", (Throwable)noSuchFieldException);
                }
                sDrawableCacheFieldFetched = true;
            }
            if (sDrawableCacheField != null) {
                try {
                    object = sDrawableCacheField.get(object);
                    break block9;
                }
                catch (IllegalAccessException illegalAccessException) {
                    Log.e((String)TAG, (String)"Could not retrieve value from Resources#mDrawableCache", (Throwable)illegalAccessException);
                }
            }
            object = null;
        }
        boolean bl = false;
        if (object == null) {
            return false;
        }
        boolean bl2 = bl;
        if (object != null) {
            bl2 = bl;
            if (ResourcesFlusher.flushThemedResourcesCache(object)) {
                bl2 = true;
            }
        }
        return bl2;
    }

    @RequiresApi(value=24)
    private static boolean flushNougats(@NonNull Resources object) {
        block14 : {
            if (!sResourcesImplFieldFetched) {
                try {
                    sResourcesImplField = Resources.class.getDeclaredField("mResourcesImpl");
                    sResourcesImplField.setAccessible(true);
                }
                catch (NoSuchFieldException noSuchFieldException) {
                    Log.e((String)TAG, (String)"Could not retrieve Resources#mResourcesImpl field", (Throwable)noSuchFieldException);
                }
                sResourcesImplFieldFetched = true;
            }
            if (sResourcesImplField == null) {
                return false;
            }
            try {
                object = sResourcesImplField.get(object);
            }
            catch (IllegalAccessException illegalAccessException) {
                Log.e((String)TAG, (String)"Could not retrieve value from Resources#mResourcesImpl", (Throwable)illegalAccessException);
                object = null;
            }
            if (object == null) {
                return false;
            }
            if (!sDrawableCacheFieldFetched) {
                try {
                    sDrawableCacheField = object.getClass().getDeclaredField("mDrawableCache");
                    sDrawableCacheField.setAccessible(true);
                }
                catch (NoSuchFieldException noSuchFieldException) {
                    Log.e((String)TAG, (String)"Could not retrieve ResourcesImpl#mDrawableCache field", (Throwable)noSuchFieldException);
                }
                sDrawableCacheFieldFetched = true;
            }
            if (sDrawableCacheField != null) {
                try {
                    object = sDrawableCacheField.get(object);
                    break block14;
                }
                catch (IllegalAccessException illegalAccessException) {
                    Log.e((String)TAG, (String)"Could not retrieve value from ResourcesImpl#mDrawableCache", (Throwable)illegalAccessException);
                }
            }
            object = null;
        }
        if (object != null && ResourcesFlusher.flushThemedResourcesCache(object)) {
            return true;
        }
        return false;
    }

    @RequiresApi(value=16)
    private static boolean flushThemedResourcesCache(@NonNull Object object) {
        if (!sThemedResourceCacheClazzFetched) {
            try {
                sThemedResourceCacheClazz = Class.forName("android.content.res.ThemedResourceCache");
            }
            catch (ClassNotFoundException classNotFoundException) {
                Log.e((String)TAG, (String)"Could not find ThemedResourceCache class", (Throwable)classNotFoundException);
            }
            sThemedResourceCacheClazzFetched = true;
        }
        if (sThemedResourceCacheClazz == null) {
            return false;
        }
        if (!sThemedResourceCache_mUnthemedEntriesFieldFetched) {
            try {
                sThemedResourceCache_mUnthemedEntriesField = sThemedResourceCacheClazz.getDeclaredField("mUnthemedEntries");
                sThemedResourceCache_mUnthemedEntriesField.setAccessible(true);
            }
            catch (NoSuchFieldException noSuchFieldException) {
                Log.e((String)TAG, (String)"Could not retrieve ThemedResourceCache#mUnthemedEntries field", (Throwable)noSuchFieldException);
            }
            sThemedResourceCache_mUnthemedEntriesFieldFetched = true;
        }
        if (sThemedResourceCache_mUnthemedEntriesField == null) {
            return false;
        }
        try {
            object = (LongSparseArray)sThemedResourceCache_mUnthemedEntriesField.get(object);
        }
        catch (IllegalAccessException illegalAccessException) {
            Log.e((String)TAG, (String)"Could not retrieve value from ThemedResourceCache#mUnthemedEntries", (Throwable)illegalAccessException);
            object = null;
        }
        if (object != null) {
            object.clear();
            return true;
        }
        return false;
    }
}
