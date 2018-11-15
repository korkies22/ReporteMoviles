/*
 * Decompiled with CFR 0_134.
 */
package android.support.v7.widget;

class RecyclerView
implements Runnable {
    RecyclerView() {
    }

    @Override
    public void run() {
        if (RecyclerView.this.mFirstLayoutComplete) {
            if (RecyclerView.this.isLayoutRequested()) {
                return;
            }
            if (!RecyclerView.this.mIsAttached) {
                RecyclerView.this.requestLayout();
                return;
            }
            if (RecyclerView.this.mLayoutFrozen) {
                RecyclerView.this.mLayoutWasDefered = true;
                return;
            }
            RecyclerView.this.consumePendingUpdateOperations();
            return;
        }
    }
}
