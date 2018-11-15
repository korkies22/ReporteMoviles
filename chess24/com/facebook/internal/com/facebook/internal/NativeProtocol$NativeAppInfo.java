/*
 * Decompiled with CFR 0_134.
 */
package com.facebook.internal;

import com.facebook.internal.NativeProtocol;
import java.util.TreeSet;

private static abstract class NativeProtocol.NativeAppInfo {
    private TreeSet<Integer> availableVersions;

    private NativeProtocol.NativeAppInfo() {
    }

    static /* synthetic */ void access$1000(NativeProtocol.NativeAppInfo nativeAppInfo, boolean bl) {
        nativeAppInfo.fetchAvailableVersions(bl);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    private void fetchAvailableVersions(boolean var1_1) {
        block5 : {
            // MONITORENTER : this
            if (var1_1) ** GOTO lbl5
            if (this.availableVersions != null) break block5;
lbl5: // 2 sources:
            this.availableVersions = NativeProtocol.access$000(this);
        }
        // MONITOREXIT : this
        return;
    }

    public TreeSet<Integer> getAvailableVersions() {
        if (this.availableVersions == null) {
            this.fetchAvailableVersions(false);
        }
        return this.availableVersions;
    }

    protected abstract String getLoginActivity();

    protected abstract String getPackage();
}
