/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 */
package com.facebook.internal;

import android.net.Uri;
import com.facebook.internal.ImageDownloader;

private static class ImageDownloader.RequestKey {
    private static final int HASH_MULTIPLIER = 37;
    private static final int HASH_SEED = 29;
    Object tag;
    Uri uri;

    ImageDownloader.RequestKey(Uri uri, Object object) {
        this.uri = uri;
        this.tag = object;
    }

    public boolean equals(Object object) {
        boolean bl;
        boolean bl2 = bl = false;
        if (object != null) {
            bl2 = bl;
            if (object instanceof ImageDownloader.RequestKey) {
                object = (ImageDownloader.RequestKey)object;
                bl2 = bl;
                if (object.uri == this.uri) {
                    bl2 = bl;
                    if (object.tag == this.tag) {
                        bl2 = true;
                    }
                }
            }
        }
        return bl2;
    }

    public int hashCode() {
        return (1073 + this.uri.hashCode()) * 37 + this.tag.hashCode();
    }
}
