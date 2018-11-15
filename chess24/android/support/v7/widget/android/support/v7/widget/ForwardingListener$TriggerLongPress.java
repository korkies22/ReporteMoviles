/*
 * Decompiled with CFR 0_134.
 */
package android.support.v7.widget;

import android.support.v7.widget.ForwardingListener;

private class ForwardingListener.TriggerLongPress
implements Runnable {
    ForwardingListener.TriggerLongPress() {
    }

    @Override
    public void run() {
        ForwardingListener.this.onLongPress();
    }
}
