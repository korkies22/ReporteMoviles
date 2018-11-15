/*
 * Decompiled with CFR 0_134.
 */
package com.facebook.places.internal;

class BleScannerImpl
implements Runnable {
    final /* synthetic */ Object val$lock;

    BleScannerImpl(Object object) {
        this.val$lock = object;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void run() {
        try {
            Object object = this.val$lock;
            synchronized (object) {
                this.val$lock.notify();
            }
        }
        catch (Exception exception) {
            com.facebook.places.internal.BleScannerImpl.logException("Exception waiting for main looper", exception);
            return;
        }
    }
}
