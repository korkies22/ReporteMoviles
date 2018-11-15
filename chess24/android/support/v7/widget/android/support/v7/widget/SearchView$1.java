/*
 * Decompiled with CFR 0_134.
 */
package android.support.v7.widget;

class SearchView
implements Runnable {
    SearchView() {
    }

    @Override
    public void run() {
        SearchView.this.updateFocusedState();
    }
}
