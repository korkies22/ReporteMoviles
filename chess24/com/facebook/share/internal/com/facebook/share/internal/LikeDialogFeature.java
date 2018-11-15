/*
 * Decompiled with CFR 0_134.
 */
package com.facebook.share.internal;

import com.facebook.internal.DialogFeature;

@Deprecated
public enum LikeDialogFeature implements DialogFeature
{
    LIKE_DIALOG(20140701);
    
    private int minVersion;

    private LikeDialogFeature(int n2) {
        this.minVersion = n2;
    }

    @Override
    public String getAction() {
        return "com.facebook.platform.action.request.LIKE_DIALOG";
    }

    @Override
    public int getMinVersion() {
        return this.minVersion;
    }
}
