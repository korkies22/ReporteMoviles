/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.graphics.Bitmap
 *  android.media.MediaDescription
 *  android.media.MediaDescription$Builder
 *  android.net.Uri
 *  android.os.Bundle
 */
package android.support.v4.media;

import android.graphics.Bitmap;
import android.media.MediaDescription;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.media.MediaDescriptionCompatApi21;

static class MediaDescriptionCompatApi21.Builder {
    MediaDescriptionCompatApi21.Builder() {
    }

    public static Object build(Object object) {
        return ((MediaDescription.Builder)object).build();
    }

    public static Object newInstance() {
        return new MediaDescription.Builder();
    }

    public static void setDescription(Object object, CharSequence charSequence) {
        ((MediaDescription.Builder)object).setDescription(charSequence);
    }

    public static void setExtras(Object object, Bundle bundle) {
        ((MediaDescription.Builder)object).setExtras(bundle);
    }

    public static void setIconBitmap(Object object, Bitmap bitmap) {
        ((MediaDescription.Builder)object).setIconBitmap(bitmap);
    }

    public static void setIconUri(Object object, Uri uri) {
        ((MediaDescription.Builder)object).setIconUri(uri);
    }

    public static void setMediaId(Object object, String string) {
        ((MediaDescription.Builder)object).setMediaId(string);
    }

    public static void setSubtitle(Object object, CharSequence charSequence) {
        ((MediaDescription.Builder)object).setSubtitle(charSequence);
    }

    public static void setTitle(Object object, CharSequence charSequence) {
        ((MediaDescription.Builder)object).setTitle(charSequence);
    }
}
