/*
 * Decompiled with CFR 0_134.
 */
package android.arch.lifecycle;

class LiveData
implements Runnable {
    LiveData() {
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void run() {
        Object object;
        Object object2 = LiveData.this.mDataLock;
        synchronized (object2) {
            object = LiveData.this.mPendingData;
            LiveData.this.mPendingData = NOT_SET;
        }
        LiveData.this.setValue(object);
    }
}
