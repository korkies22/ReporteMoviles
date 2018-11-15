/*
 * Decompiled with CFR 0_134.
 */
package android.support.v7.app;

class ToolbarActionBar
implements Runnable {
    ToolbarActionBar() {
    }

    @Override
    public void run() {
        ToolbarActionBar.this.populateOptionsMenu();
    }
}
