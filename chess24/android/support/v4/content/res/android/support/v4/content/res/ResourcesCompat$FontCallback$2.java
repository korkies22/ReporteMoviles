/*
 * Decompiled with CFR 0_134.
 */
package android.support.v4.content.res;

import android.support.v4.content.res.ResourcesCompat;

class ResourcesCompat.FontCallback
implements Runnable {
    final /* synthetic */ int val$reason;

    ResourcesCompat.FontCallback(int n) {
        this.val$reason = n;
    }

    @Override
    public void run() {
        FontCallback.this.onFontRetrievalFailed(this.val$reason);
    }
}
