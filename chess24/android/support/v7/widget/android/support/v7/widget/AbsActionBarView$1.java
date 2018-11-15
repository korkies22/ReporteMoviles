/*
 * Decompiled with CFR 0_134.
 */
package android.support.v7.widget;

class AbsActionBarView
implements Runnable {
    AbsActionBarView() {
    }

    @Override
    public void run() {
        AbsActionBarView.this.showOverflowMenu();
    }
}
