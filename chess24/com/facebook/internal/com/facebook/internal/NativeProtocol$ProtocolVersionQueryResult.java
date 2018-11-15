/*
 * Decompiled with CFR 0_134.
 */
package com.facebook.internal;

import android.support.annotation.Nullable;
import com.facebook.internal.NativeProtocol;

public static class NativeProtocol.ProtocolVersionQueryResult {
    private NativeProtocol.NativeAppInfo nativeAppInfo;
    private int protocolVersion;

    private NativeProtocol.ProtocolVersionQueryResult() {
    }

    static /* synthetic */ NativeProtocol.NativeAppInfo access$700(NativeProtocol.ProtocolVersionQueryResult protocolVersionQueryResult) {
        return protocolVersionQueryResult.nativeAppInfo;
    }

    static /* synthetic */ int access$800(NativeProtocol.ProtocolVersionQueryResult protocolVersionQueryResult) {
        return protocolVersionQueryResult.protocolVersion;
    }

    public static NativeProtocol.ProtocolVersionQueryResult create(NativeProtocol.NativeAppInfo nativeAppInfo, int n) {
        NativeProtocol.ProtocolVersionQueryResult protocolVersionQueryResult = new NativeProtocol.ProtocolVersionQueryResult();
        protocolVersionQueryResult.nativeAppInfo = nativeAppInfo;
        protocolVersionQueryResult.protocolVersion = n;
        return protocolVersionQueryResult;
    }

    public static NativeProtocol.ProtocolVersionQueryResult createEmpty() {
        NativeProtocol.ProtocolVersionQueryResult protocolVersionQueryResult = new NativeProtocol.ProtocolVersionQueryResult();
        protocolVersionQueryResult.protocolVersion = -1;
        return protocolVersionQueryResult;
    }

    @Nullable
    public NativeProtocol.NativeAppInfo getAppInfo() {
        return this.nativeAppInfo;
    }

    public int getProtocolVersion() {
        return this.protocolVersion;
    }
}
