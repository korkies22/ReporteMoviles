/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 */
package android.support.v4.media;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.media.MediaBrowserCompat;
import java.util.List;

public static abstract class MediaBrowserCompat.SearchCallback {
    public void onError(@NonNull String string, Bundle bundle) {
    }

    public void onSearchResult(@NonNull String string, Bundle bundle, @NonNull List<MediaBrowserCompat.MediaItem> list) {
    }
}
