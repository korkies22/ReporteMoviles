/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Handler
 *  android.os.Message
 */
package android.support.v4.app;

import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentController;

class FragmentActivity
extends Handler {
    FragmentActivity() {
    }

    public void handleMessage(Message message) {
        switch (message.what) {
            default: {
                super.handleMessage(message);
                return;
            }
            case 2: {
                FragmentActivity.this.onResumeFragments();
                FragmentActivity.this.mFragments.execPendingActions();
                return;
            }
            case 1: 
        }
        if (FragmentActivity.this.mStopped) {
            FragmentActivity.this.doReallyStop(false);
        }
    }
}
