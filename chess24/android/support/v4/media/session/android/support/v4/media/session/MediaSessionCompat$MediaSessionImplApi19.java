/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.PendingIntent
 *  android.content.ComponentName
 *  android.content.Context
 *  android.media.MediaMetadataEditor
 *  android.media.Rating
 *  android.media.RemoteControlClient
 *  android.media.RemoteControlClient$MetadataEditor
 *  android.media.RemoteControlClient$OnMetadataUpdateListener
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.Parcelable
 */
package android.support.v4.media.session;

import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.media.MediaMetadataEditor;
import android.media.Rating;
import android.media.RemoteControlClient;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.annotation.RequiresApi;
import android.support.v4.media.RatingCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;

@RequiresApi(value=19)
static class MediaSessionCompat.MediaSessionImplApi19
extends MediaSessionCompat.MediaSessionImplApi18 {
    MediaSessionCompat.MediaSessionImplApi19(Context context, String string, ComponentName componentName, PendingIntent pendingIntent) {
        super(context, string, componentName, pendingIntent);
    }

    @Override
    RemoteControlClient.MetadataEditor buildRccMetadata(Bundle bundle) {
        RemoteControlClient.MetadataEditor metadataEditor = super.buildRccMetadata(bundle);
        long l = this.mState == null ? 0L : this.mState.getActions();
        if ((l & 128L) != 0L) {
            metadataEditor.addEditableKey(268435457);
        }
        if (bundle == null) {
            return metadataEditor;
        }
        if (bundle.containsKey("android.media.metadata.YEAR")) {
            metadataEditor.putLong(8, bundle.getLong("android.media.metadata.YEAR"));
        }
        if (bundle.containsKey("android.media.metadata.RATING")) {
            metadataEditor.putObject(101, (Object)bundle.getParcelable("android.media.metadata.RATING"));
        }
        if (bundle.containsKey("android.media.metadata.USER_RATING")) {
            metadataEditor.putObject(268435457, (Object)bundle.getParcelable("android.media.metadata.USER_RATING"));
        }
        return metadataEditor;
    }

    @Override
    int getRccTransportControlFlagsFromActions(long l) {
        int n;
        int n2 = n = super.getRccTransportControlFlagsFromActions(l);
        if ((l & 128L) != 0L) {
            n2 = n | 512;
        }
        return n2;
    }

    @Override
    public void setCallback(MediaSessionCompat.Callback object, Handler handler) {
        super.setCallback((MediaSessionCompat.Callback)object, handler);
        if (object == null) {
            this.mRcc.setMetadataUpdateListener(null);
            return;
        }
        object = new RemoteControlClient.OnMetadataUpdateListener(){

            public void onMetadataUpdate(int n, Object object) {
                if (n == 268435457 && object instanceof Rating) {
                    MediaSessionImplApi19.this.postToHandler(19, RatingCompat.fromRating(object));
                }
            }
        };
        this.mRcc.setMetadataUpdateListener((RemoteControlClient.OnMetadataUpdateListener)object);
    }

}
