/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.database.ContentObserver
 *  android.os.Handler
 */
package android.support.v4.content;

import android.database.ContentObserver;
import android.os.Handler;
import android.support.v4.content.Loader;

public final class Loader.ForceLoadContentObserver
extends ContentObserver {
    public Loader.ForceLoadContentObserver() {
        super(new Handler());
    }

    public boolean deliverSelfNotifications() {
        return true;
    }

    public void onChange(boolean bl) {
        Loader.this.onContentChanged();
    }
}
