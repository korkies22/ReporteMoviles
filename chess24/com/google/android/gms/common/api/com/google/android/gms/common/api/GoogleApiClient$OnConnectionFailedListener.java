/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.common.api;

import android.support.annotation.NonNull;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

public static interface GoogleApiClient.OnConnectionFailedListener {
    public void onConnectionFailed(@NonNull ConnectionResult var1);
}
