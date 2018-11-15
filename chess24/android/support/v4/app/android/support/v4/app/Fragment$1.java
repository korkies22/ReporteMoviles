/*
 * Decompiled with CFR 0_134.
 */
package android.support.v4.app;

class Fragment
implements Runnable {
    Fragment() {
    }

    @Override
    public void run() {
        Fragment.this.callStartTransitionListener();
    }
}
