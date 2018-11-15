/*
 * Decompiled with CFR 0_134.
 */
package android.support.v4.provider;

import android.support.v4.provider.FontsContractCompat;

class FontsContractCompat
implements Runnable {
    final /* synthetic */ int val$resultCode;

    FontsContractCompat(int n) {
        this.val$resultCode = n;
    }

    @Override
    public void run() {
        4.this.val$callback.onTypefaceRequestFailed(this.val$resultCode);
    }
}
