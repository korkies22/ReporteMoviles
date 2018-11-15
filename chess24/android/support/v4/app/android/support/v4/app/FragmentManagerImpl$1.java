/*
 * Decompiled with CFR 0_134.
 */
package android.support.v4.app;

class FragmentManagerImpl
implements Runnable {
    FragmentManagerImpl() {
    }

    @Override
    public void run() {
        FragmentManagerImpl.this.execPendingActions();
    }
}
