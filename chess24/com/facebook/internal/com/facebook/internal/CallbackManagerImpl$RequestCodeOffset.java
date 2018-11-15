/*
 * Decompiled with CFR 0_134.
 */
package com.facebook.internal;

import com.facebook.FacebookSdk;
import com.facebook.internal.CallbackManagerImpl;

public static enum CallbackManagerImpl.RequestCodeOffset {
    Login(0),
    Share(1),
    Message(2),
    Like(3),
    GameRequest(4),
    AppGroupCreate(5),
    AppGroupJoin(6),
    AppInvite(7),
    DeviceShare(8),
    InAppPurchase(9);
    
    private final int offset;

    private CallbackManagerImpl.RequestCodeOffset(int n2) {
        this.offset = n2;
    }

    public int toRequestCode() {
        return FacebookSdk.getCallbackRequestCodeOffset() + this.offset;
    }
}
