/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.internal;

import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

public class zzaah
extends GoogleApiClient {
    private final UnsupportedOperationException zzazJ;

    public zzaah(String string) {
        this.zzazJ = new UnsupportedOperationException(string);
    }

    @Override
    public ConnectionResult blockingConnect() {
        throw this.zzazJ;
    }

    @Override
    public ConnectionResult blockingConnect(long l, @NonNull TimeUnit timeUnit) {
        throw this.zzazJ;
    }

    @Override
    public PendingResult<Status> clearDefaultAccountAndReconnect() {
        throw this.zzazJ;
    }

    @Override
    public void connect() {
        throw this.zzazJ;
    }

    @Override
    public void disconnect() {
        throw this.zzazJ;
    }

    @Override
    public void dump(String string, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] arrstring) {
        throw this.zzazJ;
    }

    @NonNull
    @Override
    public ConnectionResult getConnectionResult(@NonNull Api<?> api) {
        throw this.zzazJ;
    }

    @Override
    public boolean hasConnectedApi(@NonNull Api<?> api) {
        throw this.zzazJ;
    }

    @Override
    public boolean isConnected() {
        throw this.zzazJ;
    }

    @Override
    public boolean isConnecting() {
        throw this.zzazJ;
    }

    @Override
    public boolean isConnectionCallbacksRegistered(@NonNull GoogleApiClient.ConnectionCallbacks connectionCallbacks) {
        throw this.zzazJ;
    }

    @Override
    public boolean isConnectionFailedListenerRegistered(@NonNull GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        throw this.zzazJ;
    }

    @Override
    public void reconnect() {
        throw this.zzazJ;
    }

    @Override
    public void registerConnectionCallbacks(@NonNull GoogleApiClient.ConnectionCallbacks connectionCallbacks) {
        throw this.zzazJ;
    }

    @Override
    public void registerConnectionFailedListener(@NonNull GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        throw this.zzazJ;
    }

    @Override
    public void stopAutoManage(@NonNull FragmentActivity fragmentActivity) {
        throw this.zzazJ;
    }

    @Override
    public void unregisterConnectionCallbacks(@NonNull GoogleApiClient.ConnectionCallbacks connectionCallbacks) {
        throw this.zzazJ;
    }

    @Override
    public void unregisterConnectionFailedListener(@NonNull GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        throw this.zzazJ;
    }
}
