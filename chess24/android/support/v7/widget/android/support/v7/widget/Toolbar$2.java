/*
 * Decompiled with CFR 0_134.
 */
package android.support.v7.widget;

class Toolbar
implements Runnable {
    Toolbar() {
    }

    @Override
    public void run() {
        Toolbar.this.showOverflowMenu();
    }
}
