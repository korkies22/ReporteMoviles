/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.graphics.Bitmap
 *  android.media.MediaMetadata
 *  android.media.MediaMetadata$Builder
 *  android.media.Rating
 */
package android.support.v4.media;

import android.graphics.Bitmap;
import android.media.MediaMetadata;
import android.media.Rating;
import android.support.v4.media.MediaMetadataCompatApi21;

public static class MediaMetadataCompatApi21.Builder {
    public static Object build(Object object) {
        return ((MediaMetadata.Builder)object).build();
    }

    public static Object newInstance() {
        return new MediaMetadata.Builder();
    }

    public static void putBitmap(Object object, String string, Bitmap bitmap) {
        ((MediaMetadata.Builder)object).putBitmap(string, bitmap);
    }

    public static void putLong(Object object, String string, long l) {
        ((MediaMetadata.Builder)object).putLong(string, l);
    }

    public static void putRating(Object object, String string, Object object2) {
        ((MediaMetadata.Builder)object).putRating(string, (Rating)object2);
    }

    public static void putString(Object object, String string, String string2) {
        ((MediaMetadata.Builder)object).putString(string, string2);
    }

    public static void putText(Object object, String string, CharSequence charSequence) {
        ((MediaMetadata.Builder)object).putText(string, charSequence);
    }
}
