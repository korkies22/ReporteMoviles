/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.media.VolumeProvider
 */
package android.support.v4.media;

import android.media.VolumeProvider;
import android.support.v4.media.VolumeProviderCompatApi21;

static final class VolumeProviderCompatApi21
extends VolumeProvider {
    final /* synthetic */ VolumeProviderCompatApi21.Delegate val$delegate;

    VolumeProviderCompatApi21(int n, int n2, int n3, VolumeProviderCompatApi21.Delegate delegate) {
        this.val$delegate = delegate;
        super(n, n2, n3);
    }

    public void onAdjustVolume(int n) {
        this.val$delegate.onAdjustVolume(n);
    }

    public void onSetVolumeTo(int n) {
        this.val$delegate.onSetVolumeTo(n);
    }
}
