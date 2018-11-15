/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.os.Handler
 */
package android.support.v4.os;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.os.IResultReceiver;
import android.support.v4.os.ResultReceiver;

class ResultReceiver.MyResultReceiver
extends IResultReceiver.Stub {
    ResultReceiver.MyResultReceiver() {
    }

    @Override
    public void send(int n, Bundle bundle) {
        if (ResultReceiver.this.mHandler != null) {
            ResultReceiver.this.mHandler.post((Runnable)new ResultReceiver.MyRunnable(ResultReceiver.this, n, bundle));
            return;
        }
        ResultReceiver.this.onReceiveResult(n, bundle);
    }
}
