/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.os.RemoteException
 */
package android.support.v4.media.session;

import android.os.Bundle;
import android.os.RemoteException;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.ParcelableVolumeInfo;
import java.util.List;

private static class MediaControllerCompat.MediaControllerImplApi21.ExtraCallback
extends MediaControllerCompat.Callback.StubCompat {
    MediaControllerCompat.MediaControllerImplApi21.ExtraCallback(MediaControllerCompat.Callback callback) {
        super(callback);
    }

    @Override
    public void onExtrasChanged(Bundle bundle) throws RemoteException {
        throw new AssertionError();
    }

    @Override
    public void onMetadataChanged(MediaMetadataCompat mediaMetadataCompat) throws RemoteException {
        throw new AssertionError();
    }

    @Override
    public void onQueueChanged(List<MediaSessionCompat.QueueItem> list) throws RemoteException {
        throw new AssertionError();
    }

    @Override
    public void onQueueTitleChanged(CharSequence charSequence) throws RemoteException {
        throw new AssertionError();
    }

    @Override
    public void onSessionDestroyed() throws RemoteException {
        throw new AssertionError();
    }

    @Override
    public void onVolumeInfoChanged(ParcelableVolumeInfo parcelableVolumeInfo) throws RemoteException {
        throw new AssertionError();
    }
}
