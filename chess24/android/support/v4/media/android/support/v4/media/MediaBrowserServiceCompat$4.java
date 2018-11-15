/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 */
package android.support.v4.media;

import android.os.Bundle;
import android.support.v4.media.MediaBrowserServiceCompat;
import android.support.v4.os.ResultReceiver;

class MediaBrowserServiceCompat
extends MediaBrowserServiceCompat.Result<Bundle> {
    final /* synthetic */ ResultReceiver val$receiver;

    MediaBrowserServiceCompat(Object object, ResultReceiver resultReceiver) {
        this.val$receiver = resultReceiver;
        super(object);
    }

    @Override
    void onErrorSent(Bundle bundle) {
        this.val$receiver.send(-1, bundle);
    }

    @Override
    void onProgressUpdateSent(Bundle bundle) {
        this.val$receiver.send(1, bundle);
    }

    @Override
    void onResultSent(Bundle bundle) {
        this.val$receiver.send(0, bundle);
    }
}
