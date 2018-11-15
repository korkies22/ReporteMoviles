/*
 * Decompiled with CFR 0_134.
 */
package com.facebook.share.internal;

import com.facebook.internal.DialogFeature;

public enum AppInviteDialogFeature implements DialogFeature
{
    APP_INVITES_DIALOG(20140701);
    
    private int minVersion;

    private AppInviteDialogFeature(int n2) {
        this.minVersion = n2;
    }

    @Override
    public String getAction() {
        return "com.facebook.platform.action.request.APPINVITES_DIALOG";
    }

    @Override
    public int getMinVersion() {
        return this.minVersion;
    }
}
