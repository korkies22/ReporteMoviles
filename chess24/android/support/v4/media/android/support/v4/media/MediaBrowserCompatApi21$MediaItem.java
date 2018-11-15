/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.media.MediaDescription
 *  android.media.browse.MediaBrowser
 *  android.media.browse.MediaBrowser$MediaItem
 */
package android.support.v4.media;

import android.media.MediaDescription;
import android.media.browse.MediaBrowser;
import android.support.v4.media.MediaBrowserCompatApi21;

static class MediaBrowserCompatApi21.MediaItem {
    MediaBrowserCompatApi21.MediaItem() {
    }

    public static Object getDescription(Object object) {
        return ((MediaBrowser.MediaItem)object).getDescription();
    }

    public static int getFlags(Object object) {
        return ((MediaBrowser.MediaItem)object).getFlags();
    }
}
