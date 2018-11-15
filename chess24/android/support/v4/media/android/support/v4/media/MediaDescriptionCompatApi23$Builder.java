/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.media.MediaDescription
 *  android.media.MediaDescription$Builder
 *  android.net.Uri
 */
package android.support.v4.media;

import android.media.MediaDescription;
import android.net.Uri;
import android.support.v4.media.MediaDescriptionCompatApi21;
import android.support.v4.media.MediaDescriptionCompatApi23;

static class MediaDescriptionCompatApi23.Builder
extends MediaDescriptionCompatApi21.Builder {
    MediaDescriptionCompatApi23.Builder() {
    }

    public static void setMediaUri(Object object, Uri uri) {
        ((MediaDescription.Builder)object).setMediaUri(uri);
    }
}
