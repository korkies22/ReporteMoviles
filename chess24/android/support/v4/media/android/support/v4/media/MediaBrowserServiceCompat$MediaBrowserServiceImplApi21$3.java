/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.os.IBinder
 */
package android.support.v4.media;

import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.media.MediaBrowserCompatUtils;
import android.support.v4.media.MediaBrowserServiceCompat;
import android.support.v4.util.ArrayMap;
import android.support.v4.util.Pair;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

class MediaBrowserServiceCompat.MediaBrowserServiceImplApi21
implements Runnable {
    final /* synthetic */ Bundle val$options;
    final /* synthetic */ String val$parentId;

    MediaBrowserServiceCompat.MediaBrowserServiceImplApi21(String string, Bundle bundle) {
        this.val$parentId = string;
        this.val$options = bundle;
    }

    @Override
    public void run() {
        for (Object object : MediaBrowserServiceImplApi21.this.this$0.mConnections.keySet()) {
            object = (MediaBrowserServiceCompat.ConnectionRecord)MediaBrowserServiceImplApi21.this.this$0.mConnections.get(object);
            List<Pair<IBinder, Bundle>> list = object.subscriptions.get(this.val$parentId);
            if (list == null) continue;
            for (Pair pair : list) {
                if (!MediaBrowserCompatUtils.hasDuplicatedItems(this.val$options, (Bundle)pair.second)) continue;
                MediaBrowserServiceImplApi21.this.this$0.performLoadChildren(this.val$parentId, (MediaBrowserServiceCompat.ConnectionRecord)object, (Bundle)pair.second);
            }
        }
    }
}
