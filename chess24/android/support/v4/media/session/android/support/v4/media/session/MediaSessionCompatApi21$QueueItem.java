/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.media.MediaDescription
 *  android.media.session.MediaSession
 *  android.media.session.MediaSession$QueueItem
 */
package android.support.v4.media.session;

import android.media.MediaDescription;
import android.media.session.MediaSession;
import android.support.v4.media.session.MediaSessionCompatApi21;

static class MediaSessionCompatApi21.QueueItem {
    MediaSessionCompatApi21.QueueItem() {
    }

    public static Object createItem(Object object, long l) {
        return new MediaSession.QueueItem((MediaDescription)object, l);
    }

    public static Object getDescription(Object object) {
        return ((MediaSession.QueueItem)object).getDescription();
    }

    public static long getQueueId(Object object) {
        return ((MediaSession.QueueItem)object).getQueueId();
    }
}
