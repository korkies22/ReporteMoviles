/*
 * Decompiled with CFR 0_134.
 */
package android.support.v4.media.session;

import android.support.v4.media.VolumeProviderCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.ParcelableVolumeInfo;

class MediaSessionCompat.MediaSessionImplBase
extends VolumeProviderCompat.Callback {
    MediaSessionCompat.MediaSessionImplBase() {
    }

    @Override
    public void onVolumeChanged(VolumeProviderCompat object) {
        if (MediaSessionImplBase.this.mVolumeProvider != object) {
            return;
        }
        object = new ParcelableVolumeInfo(MediaSessionImplBase.this.mVolumeType, MediaSessionImplBase.this.mLocalStream, object.getVolumeControl(), object.getMaxVolume(), object.getCurrentVolume());
        MediaSessionImplBase.this.sendVolumeInfoChanged((ParcelableVolumeInfo)object);
    }
}
