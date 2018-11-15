/*
 * Decompiled with CFR 0_134.
 */
package com.facebook;

import com.facebook.CallbackManager;
import com.facebook.internal.CallbackManagerImpl;

public static class CallbackManager.Factory {
    public static CallbackManager create() {
        return new CallbackManagerImpl();
    }
}
