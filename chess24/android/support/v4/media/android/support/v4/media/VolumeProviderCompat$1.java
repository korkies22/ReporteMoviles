/*
 * Decompiled with CFR 0_134.
 */
package android.support.v4.media;

import android.support.v4.media.VolumeProviderCompatApi21;

class VolumeProviderCompat
implements VolumeProviderCompatApi21.Delegate {
    VolumeProviderCompat() {
    }

    @Override
    public void onAdjustVolume(int n) {
        VolumeProviderCompat.this.onAdjustVolume(n);
    }

    @Override
    public void onSetVolumeTo(int n) {
        VolumeProviderCompat.this.onSetVolumeTo(n);
    }
}
