/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.ui.view;

class HorizontalListView
implements Runnable {
    HorizontalListView() {
    }

    @Override
    public void run() {
        HorizontalListView.this.adjustSubviewPositions();
    }
}
