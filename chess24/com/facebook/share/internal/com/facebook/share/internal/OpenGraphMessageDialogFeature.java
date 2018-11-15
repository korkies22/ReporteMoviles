/*
 * Decompiled with CFR 0_134.
 */
package com.facebook.share.internal;

import com.facebook.internal.DialogFeature;

public enum OpenGraphMessageDialogFeature implements DialogFeature
{
    OG_MESSAGE_DIALOG(20140204);
    
    private int minVersion;

    private OpenGraphMessageDialogFeature(int n2) {
        this.minVersion = n2;
    }

    @Override
    public String getAction() {
        return "com.facebook.platform.action.request.OGMESSAGEPUBLISH_DIALOG";
    }

    @Override
    public int getMinVersion() {
        return this.minVersion;
    }
}
