/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 */
package android.support.v4.os;

import android.os.Bundle;
import android.support.v4.os.ResultReceiver;

class ResultReceiver.MyRunnable
implements Runnable {
    final int mResultCode;
    final Bundle mResultData;

    ResultReceiver.MyRunnable(int n, Bundle bundle) {
        this.mResultCode = n;
        this.mResultData = bundle;
    }

    @Override
    public void run() {
        ResultReceiver.this.onReceiveResult(this.mResultCode, this.mResultData);
    }
}
