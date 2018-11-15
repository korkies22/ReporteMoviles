/*
 * Decompiled with CFR 0_134.
 */
package com.facebook.login;

import com.facebook.login.LoginClient;

static interface LoginClient.BackgroundProcessingListener {
    public void onBackgroundProcessingStarted();

    public void onBackgroundProcessingStopped();
}
