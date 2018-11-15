/*
 * Decompiled with CFR 0_134.
 */
package com.facebook.share.internal;

import com.facebook.internal.DialogFeature;

public enum CameraEffectFeature implements DialogFeature
{
    SHARE_CAMERA_EFFECT(20170417);
    
    private int minVersion;

    private CameraEffectFeature(int n2) {
        this.minVersion = n2;
    }

    @Override
    public String getAction() {
        return "com.facebook.platform.action.request.CAMERA_EFFECT";
    }

    @Override
    public int getMinVersion() {
        return this.minVersion;
    }
}
