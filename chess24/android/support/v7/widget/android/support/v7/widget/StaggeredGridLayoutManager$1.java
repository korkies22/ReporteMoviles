/*
 * Decompiled with CFR 0_134.
 */
package android.support.v7.widget;

class StaggeredGridLayoutManager
implements Runnable {
    StaggeredGridLayoutManager() {
    }

    @Override
    public void run() {
        StaggeredGridLayoutManager.this.checkForGaps();
    }
}
