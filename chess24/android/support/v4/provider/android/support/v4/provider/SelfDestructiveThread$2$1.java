/*
 * Decompiled with CFR 0_134.
 */
package android.support.v4.provider;

import android.support.v4.provider.SelfDestructiveThread;

class SelfDestructiveThread
implements Runnable {
    final /* synthetic */ Object val$result;

    SelfDestructiveThread(Object object) {
        this.val$result = object;
    }

    @Override
    public void run() {
        2.this.val$reply.onReply(this.val$result);
    }
}
